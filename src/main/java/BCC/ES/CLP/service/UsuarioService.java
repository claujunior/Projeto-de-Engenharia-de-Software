package BCC.ES.CLP.service;

import BCC.ES.CLP.dto.LoginDto;
import BCC.ES.CLP.dto.RegisterDto;
import BCC.ES.CLP.exceptions.UsuarioJaExistente;
import BCC.ES.CLP.model.User;
import BCC.ES.CLP.model.UserRole;
import BCC.ES.CLP.repository.RepositoryUser;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final RepositoryUser repositoryUser;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(AuthenticationManager authenticationManager,
                       TokenService tokenService,RepositoryUser repositoryUser,PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.repositoryUser=repositoryUser;
        this.passwordEncoder=passwordEncoder;
    }


    @Transactional(readOnly = true)
    public String login(LoginDto loginDto){
        var usernamePassword = new UsernamePasswordAuthenticationToken(loginDto.getLogin(),loginDto.getSenha());
        var auth = authenticationManager.authenticate(usernamePassword);
        return tokenService.gerarToken((User) auth.getPrincipal());
    }
    @Transactional
    public void registrar(RegisterDto registerDto){
        if(repositoryUser.findByLogin(registerDto.getLogin())!=null){
            throw new UsuarioJaExistente("já existe esse usuario");
        }
        String encryptedPassword = passwordEncoder.encode(registerDto.getSenha());

        User newUser = new User(null,registerDto.getLogin(),encryptedPassword, registerDto.getCpf(), UserRole.USER);
        repositoryUser.save(newUser);
    }
}
