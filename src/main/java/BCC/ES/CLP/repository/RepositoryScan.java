package BCC.ES.CLP.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import BCC.ES.CLP.model.Scan;

@Repository
public interface RepositoryScan extends JpaRepository<Scan, Long>{

}