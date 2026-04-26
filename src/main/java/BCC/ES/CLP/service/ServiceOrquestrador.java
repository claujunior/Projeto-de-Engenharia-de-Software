package BCC.ES.CLP.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import BCC.ES.CLP.exceptions.AlvoNaoEncontradoException;
import BCC.ES.CLP.exceptions.PortasFechadas;
import BCC.ES.CLP.exceptions.TimeoutScan;
import BCC.ES.CLP.model.Select;
import BCC.ES.CLP.repository.RepositoryAlvo;
import org.springframework.stereotype.Service;

import BCC.ES.CLP.exceptions.ScanOrquestracaoException;
import BCC.ES.CLP.model.Alvo;

@Service
public class ServiceOrquestrador {

    private final RepositoryAlvo repositoryAlvo;

    public ServiceOrquestrador(RepositoryAlvo repositoryAlvo){
        this.repositoryAlvo = repositoryAlvo;
    }
    public CompletableFuture<String> executarScan(Long id, Select select) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Alvo alvo = repositoryAlvo.findById(id)
                        .orElseThrow(AlvoNaoEncontradoException::new);


                String[] command = {
                        "docker", "run", "--rm", "--network", "host",
                        "-v", select.getInfra_Dir() + ":/app/infra",
                        "scanner-image",
                        "ansible-playbook",
                        "-i", alvo.getIp() + ",",
                        select.getAnsible()
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


                Pattern pattern = Pattern.compile("(\\d+)/tcp\\s+open\\s+(\\w+)");
                Matcher matcher = pattern.matcher(output);

                Set<String> portas = new LinkedHashSet<>();

                while (matcher.find()) {
                    portas.add(matcher.group(1) + ":" + matcher.group(2));
                }


                if (portas.isEmpty()) {
                    throw new PortasFechadas("Nenhuma porta aberta encontrada");
                }


                return "{"
                        + "\"host\":\"" + alvo.getIp() + "\","
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