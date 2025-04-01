package com.eurokids.ptm_application.Service;

import com.eurokids.ptm_application.Config.ChatGptConfig;
import com.eurokids.ptm_application.Config.SsmConfig;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OpenAISummaryService {

    private final WebClient webClient;

    public OpenAISummaryService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://api.openai.com/v1/chat/completions").build();
    }

    public String getSummary(String textToSummarize) {
        String requestBody = createRequestBody(textToSummarize);
        //System.out.println("Request Body (Before Sending): " + requestBody);

        try {
            String response = webClient.post()
                    .header("Authorization", "Bearer " + SsmConfig.getParameterValue(ChatGptConfig.API_KEY_PATH_NON_PROD))
                    .header("Content-Type", "application/json")
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(String.class)
                    .onErrorResume(WebClientResponseException.class, e -> {
                        System.out.println("Error response: " + e.getResponseBodyAsString());
                        return Mono.just(e.getResponseBodyAsString());
                    })
                    .block();

            // Extract "content" from the response
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response);
            return rootNode.path("choices").get(0).path("message").path("content").asText();

        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch summary from OpenAI", e);
        }
    }

    private String createRequestBody(String textToSummarize) {
        try {
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", "gpt-3.5-turbo");

            List<Map<String, String>> messages = new ArrayList<>();
            messages.add(Map.of("role", "system", "content", "You are a helpful assistant that summarizes text concisely."));
            messages.add(Map.of("role", "user", "content", "Summarize the following text: " + textToSummarize));

            requestBody.put("messages", messages);

            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(requestBody);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create JSON request body", e);
        }
    }
}
