package BCC.ES.CLP.service;

import java.net.InetAddress;
import java.util.List;

import BCC.ES.CLP.excepitons.*;
import BCC.ES.CLP.model.Scan;
import BCC.ES.CLP.repository.RepositoryScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import BCC.ES.CLP.model.Alvo;
import BCC.ES.CLP.repository.RepositoryAlvo;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServiceAlvo implements ServiceInterface{
    
    @Autowired
    RepositoryAlvo repositoryAlvo;
    @Autowired
    private RepositoryScan repositoryScan;

    @Override
    @Transactional(readOnly = true)
    public List<Alvo> AllAlvos(){
        return repositoryAlvo.findAll();
    }

    @Override
    @Transactional
    public void SalvarAlvo(Alvo alvo){
        if(repositoryAlvo.findByIp(alvo.getIp()).isPresent()){
            throw new AlvoJaRegistradoException();
        }
        if(alvo.getIp().isEmpty() || alvo.getIp() == null){
            alvo = new Alvo(alvo.getUrl());
        }
        repositoryAlvo.save(alvo);
    }

    @Override
    @Transactional
    public void AtualizarAlvo(Alvo alvo){

     Alvo alvoOriginal = repositoryAlvo.findById(alvo.getId())
    .orElseThrow(() -> new AlvoNaoEncontradoException("Usuário não encontrado"));
    if(repositoryAlvo.findByIp(alvo.getIp()).isPresent()){
        throw new IpJaExistente("Esse Ip ja existe no banco de dados não foi possivel atualizar o Alvo");
    }
    if(repositoryAlvo.findByUrl(alvo.getIp()).isPresent()){
            throw new UrlJaExistente("Esse Url ja existe no banco de dados não foi possivel atualizar o Alvo");
    }
    validarAlvo(alvo);
    alvoOriginal.setIp(alvo.getIp());
    alvoOriginal.setUrl(alvo.getUrl());
    repositoryAlvo.save(alvoOriginal);
    }

    @Override
    @Transactional
    public Alvo DeletarAlvo(Long id){
        Alvo xd = repositoryAlvo.findById(id)
        .orElseThrow(() -> new AlvoNaoEncontradoException("Alvo não encontrado"));
        List<Scan> listaScan = repositoryScan.findByAlvo(xd);
        if(listaScan.isEmpty()) {
            repositoryAlvo.deleteById(id);
        }
        else{
            for(Scan scan : listaScan){
            repositoryScan.deleteById(scan.getId());
            }
            repositoryAlvo.deleteById(id);
        }
         return xd;
    }
    private void validarAlvo(Alvo alvo){
        try {
            InetAddress.getByName(alvo.getIp());
        } catch (Exception e) {
            throw new AlvoInvalido("Alvo invalido");
        }
    }
}
