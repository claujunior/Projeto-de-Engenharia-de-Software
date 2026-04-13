package BCC.ES.CLP.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegisterDto {
    private String login;
    private String senha;
    private String cpf;
}
