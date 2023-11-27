package tt.kvlad.tgpt.api;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestTemplate;
import tt.kvlad.tgpt.dto.gpt.GptChatRequestDto;
import tt.kvlad.tgpt.dto.gpt.GptChatResponseDto;
import tt.kvlad.tgpt.dto.gpt.GptMessageDto;
import tt.kvlad.tgpt.exception.GptException;

@RequiredArgsConstructor
public class ChatGptClient {
    private static final double TEMPERATURE = 1.0;
    private static final int CHOICE_NUMBER = 1;
    private static final int CHOICE = 0;
    private final RestTemplate openaiRestTemplate;
    private final String model;
    private final String apiUrl;

    public GptMessageDto makeRequest(List<GptMessageDto> context) {
        GptChatRequestDto request = new GptChatRequestDto(
                model, context, CHOICE_NUMBER, TEMPERATURE
        );
        GptChatResponseDto response = openaiRestTemplate.postForObject(
                apiUrl, request, GptChatResponseDto.class
        );
        responseCheck(response);
        return response.choices().get(CHOICE).message();
    }

    private void responseCheck(GptChatResponseDto responseDto) {
        if (responseDto == null
                || responseDto.choices() == null
                || responseDto.choices().isEmpty()) {
            throw new GptException("No response from chat GPT");
        }
    }
}
