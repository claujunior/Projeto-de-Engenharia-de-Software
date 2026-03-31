package BCC.ES.CLP.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import BCC.ES.CLP.Model.Scan;
import BCC.ES.CLP.Repository.RepositoryScan;

@RestController
@RequestMapping("/Scan")
public class ControllerScan {

    @Autowired
    private RepositoryScan repositoryScan;

    @GetMapping("/get")
    public ResponseEntity<List<Scan>> listarScans() {
        return ResponseEntity.ok(repositoryScan.findAll());
    }
}