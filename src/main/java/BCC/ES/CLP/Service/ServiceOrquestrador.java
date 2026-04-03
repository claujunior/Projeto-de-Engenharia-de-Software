package BCC.ES.CLP.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import BCC.ES.CLP.Model.Alvo;


@Service
public class ServiceOrquestrador {
    
    private static final String base_Dir = System.getProperty("user.dir");
    private static final String infra_Dir = base_Dir + "/infra";
    private static final String resultados_Dir = base_Dir + "/resultados";

    public CompletableFuture<String> ExecutarScan(Alvo alvo){
        return CompletableFuture.supplyAsync(() -> {
            try {
                new File(resultados_Dir).mkdirs();

                String[] command = {
                    "docker", "run", "--rm", "--network", "host",
                    "-v", infra_Dir + ":/app/infra",
                    "-v", resultados_Dir + ":/app/resultados",
                    "scanner-image",
                    "-i", alvo.getIp() + ",", 
                    "app/infra/playbooks/nmapscan.yml",
                };

                ProcessBuilder processBuilder = new ProcessBuilder(command);
                processBuilder.redirectErrorStream(true);

                Process process = processBuilder.start();

                String jsonOutput;
                try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {
                        jsonOutput = reader.lines().collect(Collectors.joining("\n"));    
                }
                int exitCode = process.waitFor();
                if (exitCode != 0) {
                    System.err.println("Erro!!! Container finalizou com código de saída: " + exitCode);
                    System.err.println("Alvo falho: " + alvo.getUrl() + " (" + alvo.getIp() + ")");
                }

                return jsonOutput;
            } catch (Exception e) {
                throw new RuntimeException("Falha critica ao executar o scan em " + alvo.getUrl(), e);
            }
        });
    }

}
