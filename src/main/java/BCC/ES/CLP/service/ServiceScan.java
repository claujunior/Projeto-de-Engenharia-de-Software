package BCC.ES.CLP.service;

import BCC.ES.CLP.exceptions.ErroAoEncontrarIp;
import BCC.ES.CLP.exceptions.ScanOrquestracaoException;
import BCC.ES.CLP.model.Alvo;
import BCC.ES.CLP.model.Scan;
import BCC.ES.CLP.repository.RepositoryAlvo;
import BCC.ES.CLP.repository.RepositoryScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceScan {
    @Autowired
    RepositoryScan repositoryScan;

    @Autowired
    RepositoryAlvo repositoryAlvo;

    @Transactional(readOnly = true)
    public List<Scan> allScan(){
        return repositoryScan.findAll();
    }

    @Transactional
    public void adicionarBd(String jsonString) {
        ObjectMapper mapper = new ObjectMapper();

        JsonNode root;
        try {
            root = mapper.readTree(jsonString);
        } catch (Exception e) {
            throw new ScanOrquestracaoException("JSON de resultado inválido: " + e.getMessage(), e);
        }

        String host = root.get("host").asText();
        String portasStr = root.get("portas").asText();


        portasStr = portasStr.replace("[", "").replace("]", "");


        String[] entries = portasStr.split(",");
        Optional<Alvo> optional = repositoryAlvo.findByIp(host);
        if(optional.isEmpty()){
            throw new ErroAoEncontrarIp("Esse Ip não existe no banco de dados");
        }

        for (String entry : entries) {
            String[] parts = entry.trim().split(":");

            int port = Integer.parseInt(parts[0]);
            String service = parts[1];

            repositoryScan.save(new Scan(null, null, port,service,optional.get()));
        }
    }
}
