
package BCC.ES.CLP.config;

import BCC.ES.CLP.model.User;
import BCC.ES.CLP.model.UserRole;
import BCC.ES.CLP.repository.RepositoryUser;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import BCC.ES.CLP.model.Alvo;
import BCC.ES.CLP.model.Scan;
import BCC.ES.CLP.repository.RepositoryAlvo;
import BCC.ES.CLP.repository.RepositoryScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class DataLoader {
    @Bean
    CommandLineRunner loadData(RepositoryAlvo repo, RepositoryScan repoScan, RepositoryUser repositoryUser) {
    return new CommandLineRunner() {
        @Override
        public void run(String... args) throws Exception {
            Alvo google = repo.save(new Alvo("www.google.com", "142.250.190.78"));
            repo.save(new Alvo("www.github.com", "10.255.255.1"));
            repo.save(new Alvo("www.stackoverflow.com", "45.33.32.156"));
            repoScan.save(new Scan(null, null, 80,"HTTP",google));
            repoScan.save(new Scan(null, null, 8080,"SSH",google));
            String encryptedPassword = new BCryptPasswordEncoder().encode("123");
            repositoryUser.save(new User(null,"usuario",encryptedPassword,"123", UserRole.ADMIN));
            }
        };
    }
}
