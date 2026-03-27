package BCC.ES.CLP.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        repositoryAlvo.save(alvo);
    }
}
