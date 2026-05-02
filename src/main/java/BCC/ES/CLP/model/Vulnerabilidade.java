package BCC.ES.CLP.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "Vulnerabilidade")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Vulnerabilidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false)
    private LocalDateTime dataHora;

    @Column(nullable = false)
    private String templateId;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String severidade;

    @Column(nullable = false)
    private String matchedAt;

    @ManyToOne(optional = false)
    @JoinColumn(name = "Alvo_id")
    private Alvo alvo;

    @PrePersist
    private void prePersist() {
        if (dataHora == null) {
            dataHora = LocalDateTime.now().withNano(0);
        }
    }
}
