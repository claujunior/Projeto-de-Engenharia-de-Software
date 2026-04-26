package BCC.ES.CLP.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum Select {
    NMAP("scan","/app/infra/playbooks/nmapscan.yml",System.getProperty("user.dir") + "/infra");

    private String tipo;
    private String ansible;
    private String infra_Dir;
}
