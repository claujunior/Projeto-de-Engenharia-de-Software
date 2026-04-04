package BCC.ES.CLP.Config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import BCC.ES.CLP.Model.Alvo;
import BCC.ES.CLP.Model.Scan;
import BCC.ES.CLP.Repository.RepositoryAlvo;
import BCC.ES.CLP.Repository.RepositoryScan;

@Configuration
public class DataLoader {
    @Bean
    CommandLineRunner loadData(RepositoryAlvo repo, RepositoryScan repoScan) {
    return new CommandLineRunner() {
        @Override
        public void run(String... args) throws Exception {
            Alvo google = repo.save(new Alvo("www.google.com", "142.250.190.78"));
            repo.save(new Alvo("www.github.com", "140.82.121.3"));
            repo.save(new Alvo("www.stackoverflow.com", "151.101.1.69"));
            repoScan.save(new Scan(null, "80", "HTTP", google));
            repoScan.save(new Scan(null, "8080", "SSH", google));
            }
        };
    }
}
