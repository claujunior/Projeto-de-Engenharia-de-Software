package BCC.ES.CLP.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import BCC.ES.CLP.excepitons.AlvoJaRegistradoException;
import BCC.ES.CLP.excepitons.AlvoNaoEncontradoException;
import BCC.ES.CLP.model.Alvo;
import BCC.ES.CLP.repository.RepositoryAlvo;

@Service
public class ServiceAlvo implements ServiceInterface{
    
    @Autowired
    RepositoryAlvo repositoryAlvo;

    @Override
    public List<Alvo> AllAlvos(){
        return repositoryAlvo.findAll();
    }

    @Override 
    public void SalvarAlvo(Alvo alvo){
        repositoryAlvo.findByIp(alvo.getIp())
        .ifPresent(a -> {
            throw new AlvoJaRegistradoException("Alvo já registrado");
        });
        repositoryAlvo.save(alvo);
    }

    @Override 
    public void AtualizarAlvo(Alvo alvo){

     Alvo alvoOriginal = repositoryAlvo.findById(alvo.getId())
    .orElseThrow(() -> new AlvoNaoEncontradoException("Usuário não encontrado"));

    alvoOriginal.setIp(alvo.getIp());
    alvoOriginal.setUrl(alvo.getUrl());
    repositoryAlvo.save(alvoOriginal);
    }

    @Override
    public Alvo DeletarAlvo(Long id){
        Alvo xd = repositoryAlvo.findById(id)
        .orElseThrow(() -> new AlvoNaoEncontradoException("Alvo não encontrado"));
         repositoryAlvo.deleteById(id);
         return xd;
    }

   
}
