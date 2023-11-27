package tt.kvlad.tgpt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import tt.kvlad.tgpt.api.ChatGptClient;

@Configuration
public class ChatGptConfig {
    @Value("${openai.api.key}")
    private String openaiApiKey;
    @Value("${openai.api.model}")
    private String model;
    @Value("${openai.api.url}")
    private String apiUrl;

    @Bean
    @Qualifier("openaiRestTemplate")
    public RestTemplate openaiRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().add("Authorization", "Bearer " + openaiApiKey);
            return execution.execute(request, body);
        });
        return restTemplate;
    }

    @Bean
    public ChatGptClient chatGptClient(@Autowired RestTemplate openaiRestTemplate) {
        return new ChatGptClient(openaiRestTemplate, model, apiUrl);
    }
}
