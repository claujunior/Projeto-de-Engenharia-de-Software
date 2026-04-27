package BCC.ES.CLP.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import BCC.ES.CLP.exceptions.AlvoNaoEncontradoException;
import BCC.ES.CLP.exceptions.PortasFechadas;
import BCC.ES.CLP.exceptions.ScanOrquestracaoException;
import BCC.ES.CLP.exceptions.TimeoutScan;
import BCC.ES.CLP.model.Alvo;
import BCC.ES.CLP.repository.RepositoryAlvo;

@Service
public class ServiceOrquestradorTeste {

    
    private static final String BASE_DIR = System.getProperty("user.dir");
    private static final String INFRA_DIR = BASE_DIR + "/infra";
    private static final String RESULTS_DIR = BASE_DIR + "/infra/results";

    private final RepositoryAlvo repositoryAlvo;

    public ServiceOrquestradorTeste(RepositoryAlvo repositoryAlvo){
        this.repositoryAlvo = repositoryAlvo;
    }

    private static final List<String> PLAYBOOKS_PERMITIDOS = List.of(
        "nmapscan"

    );

    public CompletableFuture<String> executarScan(Long id, String nomePlaybook) {
        return CompletableFuture.supplyAsync(() -> {
            try {

                Alvo alvo = repositoryAlvo.findById(id)
                        .orElseThrow(AlvoNaoEncontradoException::new);
                
                String ipTarget = alvo.getIp();

                if (!PLAYBOOKS_PERMITIDOS.contains(nomePlaybook)) {
                    throw new IllegalArgumentException("Acesso negado: Playbook não autorizado ou inexistente -> " + nomePlaybook);
                }

                
                String caminhoPlaybook = "/app/infra/playbooks/" + nomePlaybook + ".yml";

                new File(RESULTS_DIR).mkdirs();

                // 4. O comando agora é genérico e agnóstico
                String[] command = {
                    "docker", "run", "--rm", "--network", "host",
                    "-v", INFRA_DIR + ":/app/infra",
                    "-v", RESULTS_DIR + ":/app/results",
                    "scanner-image",
                    "ansible-playbook",
                    "-i", ipTarget + ",", 
                    caminhoPlaybook
                };

                Process process = new ProcessBuilder(command)
                        .redirectErrorStream(true)
                        .start();

                String output;
                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {

                    output = reader.lines().collect(Collectors.joining("\n"));
                }

                boolean finished = process.waitFor(90, java.util.concurrent.TimeUnit.SECONDS);

                if (!finished) {
                    process.destroyForcibly();
                    throw new TimeoutScan("Timeout no scan");
                }
                
                System.out.println("--- SAÍDA DO DOCKER ---");
                System.out.println(output);
                System.out.println("-----------------------");

                Pattern pattern = Pattern.compile("(\\d+)/tcp\\s+open\\s+(.+)");
                Matcher matcher = pattern.matcher(output);

                Set<String> portas = new LinkedHashSet<>();

                while (matcher.find()) {
                    String servicoLimpo = matcher.group(2).replaceAll("[\"'\n\r\\\\]", "").trim();
                    
                    if(servicoLimpo.contains(" ")) servicoLimpo = servicoLimpo.split(" ")[0];

                    portas.add(matcher.group(1) + ":" + servicoLimpo);
                }

                if (portas.isEmpty()) {
                    throw new PortasFechadas("Nenhuma porta aberta encontrada");
                }

                return "{"
                        + "\"host\":\"" + repositoryAlvo.findById(id).get().getIp() + "\","
                        + "\"portas\":\"" + portas + "\""
                        + "}";
            } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new ScanOrquestracaoException("Falha no scan em " + repositoryAlvo.findById(id).get().getIp(), e);
        }
        });
    }
}
