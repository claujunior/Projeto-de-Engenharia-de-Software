package BCC.ES.CLP.Repository;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import BCC.ES.CLP.Model.Alvo;

@Repository
public interface RepositoryAlvo extends JpaRepository<Alvo, Long>{
 public Optional<Alvo> findById(Long Id);
 public Optional<Alvo> findByIp(String ip);
}
