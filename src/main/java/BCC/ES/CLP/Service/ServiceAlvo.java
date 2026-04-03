package BCC.ES.CLP.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import BCC.ES.CLP.Excepitons.AlvoJaRegistradoException;
import BCC.ES.CLP.Excepitons.AlvoNaoEncontradoException;
import BCC.ES.CLP.Model.Alvo;
import BCC.ES.CLP.Repository.RepositoryAlvo;

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
    public void DeletarAlvo(Long id){
        repositoryAlvo.findById(id)
        .orElseThrow(() -> new AlvoNaoEncontradoException("Alvo não encontrado"));
        repositoryAlvo.deleteById(id);
    }

   
}
