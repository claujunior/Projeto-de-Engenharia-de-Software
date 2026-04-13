package BCC.ES.CLP.controller;

import BCC.ES.CLP.dto.LoginDto;
import BCC.ES.CLP.dto.RegisterDto;
import BCC.ES.CLP.model.User;
import BCC.ES.CLP.model.UserRole;
import BCC.ES.CLP.repository.RepositoryUser;
import BCC.ES.CLP.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class ControllerLogin {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    RepositoryUser repositoryUser;

    @Autowired
    TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto){
    var usernamePassword = new UsernamePasswordAuthenticationToken(loginDto.getLogin(),loginDto.getSenha());
    var auth = authenticationManager.authenticate(usernamePassword);

    var token = tokenService.gerarToken((User) auth.getPrincipal());

    return ResponseEntity.ok(token);
    }

    @PostMapping("/register")
    public ResponseEntity<String> registrar(@RequestBody RegisterDto registerDto){
        if(repositoryUser.findByLogin(registerDto.getLogin())!=null){
            return ResponseEntity.badRequest().build();
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(registerDto.getSenha());

        User newUser = new User(null,registerDto.getLogin(),encryptedPassword, registerDto.getCpf(), UserRole.USER);
        repositoryUser.save(newUser);
        return ResponseEntity.ok("Resgistrado com sucesso");
    }
}
