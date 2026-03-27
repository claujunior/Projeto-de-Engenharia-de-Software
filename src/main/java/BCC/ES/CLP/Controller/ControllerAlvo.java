package BCC.ES.CLP.Controller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import BCC.ES.CLP.Model.Alvo;
import BCC.ES.CLP.Service.ServiceInterface;

@RestController
@RequestMapping("/Alvo")
public class ControllerAlvo {

   @Autowired
   ServiceInterface serviceInterface;
    
   @GetMapping("/get")
   public ResponseEntity<List<Alvo>> obterAlvos(){
        return ResponseEntity.ok(serviceInterface.AllAlvos());
   }

   @PostMapping("/post")
   public ResponseEntity<String> CadastrarAlvos(@RequestBody Alvo alvo){
      serviceInterface.SalvarAlvo(alvo);
      return ResponseEntity.ok("Alvo cadastrado");
   }

   
}
