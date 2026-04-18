package BCC.ES.CLP.controller;

import BCC.ES.CLP.dto.LoginDto;
import BCC.ES.CLP.dto.RegisterDto;
import BCC.ES.CLP.model.User;
import BCC.ES.CLP.model.UserRole;
import BCC.ES.CLP.repository.RepositoryUser;
import BCC.ES.CLP.service.TokenService;
import BCC.ES.CLP.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class ControllerLogin {

    private final UsuarioService usuarioService;

    public ControllerLogin(UsuarioService usuarioService){
        this.usuarioService=usuarioService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto){
    return ResponseEntity.ok(usuarioService.login(loginDto));
    }

    @PostMapping("/register")
    public ResponseEntity<String> registrar(@RequestBody RegisterDto registerDto){
        usuarioService.registrar(registerDto);
        return ResponseEntity.ok("Resgistrado com sucesso");
    }
}
