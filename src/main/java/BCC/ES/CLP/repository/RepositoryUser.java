package BCC.ES.CLP.repository;

import BCC.ES.CLP.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryUser extends JpaRepository<User,Long> {
    UserDetails findByLogin(String login);
}
