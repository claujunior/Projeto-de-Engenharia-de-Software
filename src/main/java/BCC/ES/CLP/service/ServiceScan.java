package BCC.ES.CLP.service;

import BCC.ES.CLP.model.Alvo;
import BCC.ES.CLP.model.Scan;
import BCC.ES.CLP.repository.RepositoryAlvo;
import BCC.ES.CLP.repository.RepositoryScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ServiceScan {
    @Autowired
    RepositoryScan repositoryScan;

    @Autowired
    RepositoryAlvo repositoryAlvo;

    public List<Scan> AllScan(){
        return repositoryScan.findAll();
    }
    public void AdicionarBd(String jsonString) {
        ObjectMapper mapper = new ObjectMapper();

        JsonNode root = mapper.readTree(jsonString);

        String host = root.get("host").asText();
        String portasStr = root.get("portas").asText();

// remove colchetes
        portasStr = portasStr.replace("[", "").replace("]", "");

        // separa cada porta
        String[] entries = portasStr.split(",");



        for (String entry : entries) {
            String[] parts = entry.trim().split(":");

            int port = Integer.parseInt(parts[0]);
            String service = parts[1];
            Optional<Alvo> optional = repositoryAlvo.findByIp(host);
            repositoryScan.save(new Scan(null, null, port,service,optional.get()));
        }

    }
}
