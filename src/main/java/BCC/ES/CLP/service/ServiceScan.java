package BCC.ES.CLP.service;

import BCC.ES.CLP.dto.ScanRawResult;
import BCC.ES.CLP.model.Scan;
import BCC.ES.CLP.model.Select;
import BCC.ES.CLP.model.Vulnerabilidade;
import BCC.ES.CLP.repository.RepositoryScan;
import BCC.ES.CLP.repository.RepositoryVulnerabilidade;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ServiceScan {

    private final RepositoryScan repositoryScan;
    private final RepositoryVulnerabilidade repositoryVulnerabilidade;
    private final ServiceOrquestrador serviceOrquestrador;
    private final ServiceNmap serviceNmap;
    private final ServiceNuclei serviceNuclei;

    public ServiceScan(RepositoryScan repositoryScan,
                       RepositoryVulnerabilidade repositoryVulnerabilidade,
                       ServiceOrquestrador serviceOrquestrador,
                       ServiceNmap serviceNmap,
                       ServiceNuclei serviceNuclei) {
        this.repositoryScan = repositoryScan;
        this.repositoryVulnerabilidade = repositoryVulnerabilidade;
        this.serviceOrquestrador = serviceOrquestrador;
        this.serviceNmap = serviceNmap;
        this.serviceNuclei = serviceNuclei;
    }

    @Transactional(readOnly = true)
    public List<Scan> allScan() {
        return repositoryScan.findAll();
    }

    @Transactional(readOnly = true)
    public List<Vulnerabilidade> allVulnerabilidades() {
        return repositoryVulnerabilidade.findAll();
    }

    public String seletor(Long id, Select select) {
        ScanRawResult resultado = serviceOrquestrador.executarScan(id, select).join();
        return switch (select) {
            case NMAP   -> serviceNmap.processar(resultado);
            case NUCLEI -> serviceNuclei.processar(resultado);
        };
    }
}
