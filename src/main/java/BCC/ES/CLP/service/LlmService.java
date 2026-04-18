package BCC.ES.CLP.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class LlmService {

    @Value("${openrouter.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    private static final String URL = "https://openrouter.ai/api/v1/chat/completions";

    public String perguntar(String prompt) {
        String body = """
            {
             "model": "openrouter/free",
              "messages": [
                { "role": "user", "content": "%s" }
              ]
            }
            """.formatted(prompt.replace("\"", "\\\""));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        HttpEntity<String> request = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(URL, request, Map.class);

        var choices = (List<Map>) response.getBody().get("choices");
        var message = (Map) choices.get(0).get("message");
        return (String) message.get("content");
    }
}
