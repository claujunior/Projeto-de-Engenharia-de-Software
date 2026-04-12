package BCC.ES.CLP.controller;

import java.util.List;

import BCC.ES.CLP.service.ServiceOrquestrador;
import BCC.ES.CLP.service.ServiceScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import BCC.ES.CLP.model.Alvo;
import BCC.ES.CLP.model.Scan;


@RestController
@RequestMapping("/Scan")
public class ControllerScan {

    @Autowired
    ServiceOrquestrador serviceOrquestrador;

    @Autowired
    ServiceScan serviceScan;

    @GetMapping("/get")
    public ResponseEntity<List<Scan>> listarScans() {

        return ResponseEntity.ok(serviceScan.AllScan());
    }

    @PostMapping("/post")
    public ResponseEntity<String> executar(@RequestBody Alvo alvo) {


        serviceScan.AdicionarBd(serviceOrquestrador.ExecutarScan(alvo).join());

        return ResponseEntity.ok("xd");
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