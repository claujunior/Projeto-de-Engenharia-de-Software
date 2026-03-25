package BCC.ES.CLP.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import BCC.ES.CLP.Model.Scan;

@Repository
public interface RepositoryScan extends JpaRepository<Scan, Long>{

}