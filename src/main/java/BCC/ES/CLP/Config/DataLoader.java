package BCC.ES.CLP.Config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import BCC.ES.CLP.Model.Alvo;
import BCC.ES.CLP.Repository.RepositoryAlvo;

@Configuration
public class DataLoader {
    @Bean
    CommandLineRunner loadData(RepositoryAlvo repo) {
    return args -> {
        // Usando construtor que aceita IP manual
        repo.save(new Alvo("www.google.com", "142.250.190.78"));
        repo.save(new Alvo("www.github.com", "140.82.121.3"));
        repo.save(new Alvo("www.stackoverflow.com", "151.101.1.69"));
    };
}
}
