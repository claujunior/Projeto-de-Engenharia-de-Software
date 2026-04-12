package BCC.ES.CLP.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import BCC.ES.CLP.excepitons.ScanOrquestracaoException;
import BCC.ES.CLP.model.Alvo;

@Service
public class ServiceOrquestrador {

    private static final String base_Dir = System.getProperty("user.dir");
    private static final String infra_Dir = base_Dir + "/infra";
    private static final String resultados_Dir = base_Dir + "/resultados";

    public CompletableFuture<String> ExecutarScan(Alvo alvo) {
        return CompletableFuture.supplyAsync(() -> {
            try {

                new File(resultados_Dir).mkdirs();

                String[] command = {
                        "docker", "run", "--rm", "--network", "host",
                        "-v", infra_Dir + ":/app/infra",
                        "-v", resultados_Dir + ":/app/resultados",
                        "scanner-image",
                        "ansible-playbook",
                        "-i", alvo.getIp() + ",",
                        "/app/infra/playbooks/nmapscan.yml"
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
                    throw new RuntimeException("Timeout no scan");
                }


                Pattern pattern = Pattern.compile("(\\d+)/tcp\\s+open\\s+(\\w+)");
                Matcher matcher = pattern.matcher(output);

                Set<String> portas = new LinkedHashSet<>();

                while (matcher.find()) {
                    portas.add(matcher.group(1) + ":" + matcher.group(2));
                }


                if (portas.isEmpty()) {
                    throw new RuntimeException("Nenhuma porta aberta encontrada");
                }


                return "{"
                        + "\"host\":\"" + alvo.getIp() + "\","
                        + "\"portas\":\"" + portas + "\""
                        + "}";

            } catch (Exception e) {
                e.printStackTrace();
                throw new ScanOrquestracaoException("Falha no scan em " + alvo.getIp(), e);
            }
        });
    }
}