package BCC.ES.CLP.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import BCC.ES.CLP.excepitons.AlvoNaoEncontradoException;
import BCC.ES.CLP.model.Alvo;
import BCC.ES.CLP.model.Scan;
import BCC.ES.CLP.repository.RepositoryAlvo;
import BCC.ES.CLP.repository.RepositoryScan;

@RestController
@RequestMapping("/Scan")
public class ControllerScan {

    @Autowired
    private RepositoryScan repositoryScan;

    @Autowired
    private RepositoryAlvo repositoryAlvo;

    @GetMapping("/get")
    public ResponseEntity<List<Scan>> listarScans() {
        return ResponseEntity.ok(repositoryScan.findAll());
    }
    
    @PostMapping("/post")
    public ResponseEntity<String> cadastrarScan(@RequestBody Scan scan) {

        if (scan.getAlvo() == null || scan.getAlvo().getId() == null) {
            throw new AlvoNaoEncontradoException("Alvo não informado (envie alvo.id)");
        }

        Long alvoId = scan.getAlvo().getId();
        Alvo alvo = repositoryAlvo.findById(alvoId)
                .orElseThrow(() -> new AlvoNaoEncontradoException("Alvo não encontrado: id=" + alvoId));

        scan.setAlvo(alvo);

        repositoryScan.save(scan);
        return ResponseEntity.ok("Scan cadastrado com sucesso");
    }

    //http://localhost:8080/Scan/get
    // Testar se é possível ver o histórico

    // Verificar Alvos cadastrados:
    // curl http://localhost:8080/Alvo/get

    // Cadastrar alvo manualmente (Se for cadastrar outro, precisa ter ip diferente):
    // ex: curl -X POST http://localhost:8080/Alvo/post -H "Content-Type: application/json" -d '{"ip":"192.168.0.1","url":"meusite.com"}'

    // Cadastrar scan (relativo ao alvo) manualmente:
    // ex: curl -X POST http://localhost:8080/Scan/post -H "Content-Type: application/json" -d '{"porta":"80","serviço":"HTTP","alvo":{"id":1}}'

    // Ir para: http://localhost:8080/Scan/get
    // Verificar se tudo deu certo
}