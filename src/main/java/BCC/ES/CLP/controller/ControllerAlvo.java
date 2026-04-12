package BCC.ES.CLP.controller;



import java.util.List;
import java.util.UUID;

import BCC.ES.CLP.service.ServiceOrquestrador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import BCC.ES.CLP.model.Alvo;
import BCC.ES.CLP.service.ServiceInterface;

@RestController
@RequestMapping("/Alvo")
public class ControllerAlvo {

   @Autowired
   ServiceInterface serviceInterface;

   @Autowired
   ServiceOrquestrador teste;
   @GetMapping("/get")
   public ResponseEntity<List<Alvo>> obterAlvos(){
        return ResponseEntity.ok(serviceInterface.AllAlvos());
   }
   //http://localhost:8080/Alvo/get

   @PostMapping("/post")
   public ResponseEntity<Alvo> CadastrarAlvos(@RequestBody Alvo alvo){
        serviceInterface.SalvarAlvo(alvo);

      return ResponseEntity.ok(alvo);
   }
   //http://localhost:8080/Alvo/post
   // {"id": null,"ip": "154.250.190.78","url": "www.google1.com"}
    @PutMapping("/update")
    public ResponseEntity<Alvo> AtualizarAlvo(@RequestBody Alvo alvo){
      serviceInterface.AtualizarAlvo(alvo);
      return ResponseEntity.ok(alvo);
    }
//http://localhost:8080/Alvo/update
// {"id": 1,"ip": "155.250.190.78","url": "www.google12.com"}
    
    @DeleteMapping("/delete/{id}")
      public ResponseEntity<Alvo> deleteAlvo(@PathVariable Long id){
        return ResponseEntity.ok(serviceInterface.DeletarAlvo(id));
    }
    //http://localhost:8080/Alvo/post/2

    @PostMapping("/post1")
    public ResponseEntity<String> executar(@RequestBody Alvo alvo) {

        String resultado = teste.ExecutarScan(alvo).join();

        return ResponseEntity.ok(resultado);
    }
}
