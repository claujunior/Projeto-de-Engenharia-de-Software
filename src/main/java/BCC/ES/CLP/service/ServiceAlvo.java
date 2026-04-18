package BCC.ES.CLP.service;

import java.net.InetAddress;
import java.util.List;

import BCC.ES.CLP.exceptions.*;
import BCC.ES.CLP.model.Scan;
import BCC.ES.CLP.repository.RepositoryScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import BCC.ES.CLP.model.Alvo;
import BCC.ES.CLP.repository.RepositoryAlvo;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServiceAlvo implements ServiceInterface{
    

    private final RepositoryAlvo repositoryAlvo;

    private final RepositoryScan repositoryScan;

    public ServiceAlvo(RepositoryAlvo repositoryAlvo,RepositoryScan repositoryScan){
        this.repositoryAlvo=repositoryAlvo;
        this.repositoryScan=repositoryScan;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Alvo> allAlvos(){
        return repositoryAlvo.findAll();
    }

    @Override
    @Transactional
    public void salvarAlvo(Alvo alvo){
        if(repositoryAlvo.findByIp(alvo.getIp()).isPresent()){
            throw new AlvoJaRegistradoException();
        }
        if(alvo.getIp() == null || alvo.getIp().isEmpty()){
            alvo = new Alvo(alvo.getUrl());
        }
        repositoryAlvo.save(alvo);
    }

    @Override
    @Transactional
    public void atualizarAlvo(Alvo alvo){

     Alvo alvoOriginal = repositoryAlvo.findById(alvo.getId())
    .orElseThrow(() -> new AlvoNaoEncontradoException("Usuário não encontrado"));
    if(repositoryAlvo.findByIp(alvo.getIp()).isEmpty()){
        throw new IpJaNaoExistente("Esse Ip não existe no banco");
    }
    if(repositoryAlvo.findByUrl(alvo.getUrl()).isEmpty()){
            throw new UrlJaNaoExistente("Esse Url não existe no banco de dados não foi possivel atualizar o Alvo");
    }
    validarAlvo(alvo);
    alvoOriginal.setIp(alvo.getIp());
    alvoOriginal.setUrl(alvo.getUrl());
    repositoryAlvo.save(alvoOriginal);
    }

    @Override
    @Transactional
    public Alvo deletarAlvo(Long id){
        Alvo alvoEncontrado = repositoryAlvo.findById(id)
        .orElseThrow(() -> new AlvoNaoEncontradoException("Alvo não encontrado"));
        List<Scan> listaScan = repositoryScan.findByAlvo(alvoEncontrado);
        if(listaScan.isEmpty()) {
            repositoryAlvo.deleteById(id);
        }
        else{
            for(Scan scan : listaScan){
            repositoryScan.deleteById(scan.getId());
            }
            repositoryAlvo.deleteById(id);
        }
         return alvoEncontrado;
    }
    private void validarAlvo(Alvo alvo){
        try {
            InetAddress.getByName(alvo.getIp());
        } catch (Exception e) {
            throw new AlvoInvalido("Alvo invalido");
        }
    }
}
