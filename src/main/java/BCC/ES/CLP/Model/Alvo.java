package BCC.ES.CLP.Model;

import java.net.InetAddress;
import java.net.UnknownHostException;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Alvo")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Alvo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String ip;

    @Column(unique=true)
    private String url;

    Alvo(String url){
        this.url = url;
         try {
        InetAddress endereco = InetAddress.getByName(url);
        this.ip = endereco.getHostAddress();;
        } catch (UnknownHostException e) {
        throw new RuntimeException("URL inválida: " + url);
        }
    }
   
}
