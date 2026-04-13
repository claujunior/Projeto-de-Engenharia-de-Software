package BCC.ES.CLP.repository;

import BCC.ES.CLP.model.Alvo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import BCC.ES.CLP.model.Scan;

import java.util.List;
import java.util.Optional;

@Repository
public interface RepositoryScan extends JpaRepository<Scan, Long>{
    public List<Scan> findByAlvo(Alvo alvo);

}