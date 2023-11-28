package tt.kvlad.tgpt.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import javax.sql.DataSource;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.shaded.com.github.dockerjava.core.MediaType;
import tt.kvlad.tgpt.dto.context.ContextDtoWithoutMessages;
import tt.kvlad.tgpt.dto.message.MessageDto;
import tt.kvlad.tgpt.model.Context;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ContextControllerTest {
    private static MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void beforeAll(
            @Autowired DataSource dataSource,
            @Autowired WebApplicationContext applicationContext
    ) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
        teardown(dataSource);
        setup(dataSource);
    }

    @AfterAll
    static void afterAll(
            @Autowired DataSource dataSource
    ) {
        teardown(dataSource);
    }

    @DisplayName("Verify getAll() method works for ContextController")
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    void getAll_ValidRequest_ReturnListContextDtoWithoutMessages()
            throws Exception {
        List<ContextDtoWithoutMessages> expected = testAllContexts();
        MvcResult result = mockMvc.perform(
                        get("/contexts")
                                .contentType(MediaType.APPLICATION_JSON.getMediaType())
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        ContextDtoWithoutMessages[] actual = objectMapper.readValue(
                result.getResponse().getContentAsByteArray(),
                ContextDtoWithoutMessages[].class
        );
        assertEquals(expected, Arrays.stream(actual).toList());
    }

    @DisplayName("Verify getById() method works for ContextController")
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    void getById_ValidRequest_ReturnContextDtoWithoutMessages()
            throws Exception {
        ContextDtoWithoutMessages expected = testChat();
        MvcResult result = mockMvc.perform(
                        get("/contexts/1")
                                .contentType(MediaType.APPLICATION_JSON.getMediaType())
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        ContextDtoWithoutMessages actual = objectMapper.readValue(
                result.getResponse().getContentAsByteArray(),
                ContextDtoWithoutMessages.class
        );
        assertEquals(expected, actual);
    }

    @DisplayName("Verify getLog() method works for ContextController")
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    void getLog_ValidRequest_ReturnListMessageDto() throws Exception {
        List<MessageDto> expected = testLog();
        MvcResult result = mockMvc.perform(
                        get("/contexts/1/messages")
                                .contentType(MediaType.APPLICATION_JSON.getMediaType())
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        MessageDto[] actual = objectMapper.readValue(
                result.getResponse().getContentAsByteArray(), MessageDto[].class
        );
        assertEquals(expected, Arrays.stream(actual).toList());
    }

    @SneakyThrows
    static void setup(DataSource dataSource) {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(true);
            ScriptUtils.executeSqlScript(
                    connection,
                    new ClassPathResource("database/chat/add-test-chat.sql")
            );
        }
    }

    @SneakyThrows
    static void teardown(DataSource dataSource) {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(true);
            ScriptUtils.executeSqlScript(
                    connection,
                    new ClassPathResource("database/chat/remove-test-chat.sql")
            );
        }
    }

    private ContextDtoWithoutMessages testChat() {
        return new ContextDtoWithoutMessages(
                1L,
                "Context1",
                Context.ContextType.ADMIN
        );
    }

    private List<ContextDtoWithoutMessages> testAllContexts() {
        return List.of(
                new ContextDtoWithoutMessages(
                        1L,
                        "Context1",
                        Context.ContextType.ADMIN
                ),
                new ContextDtoWithoutMessages(
                        2L,
                        "Context2",
                        Context.ContextType.GPT
                )
        );
    }

    private List<MessageDto> testLog() {
        DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");
        LocalDateTime creatingTime = LocalDateTime
                .parse("2023-11-27 13:27:25.447832", formatter);
        return List.of(
                new MessageDto(1L, "Message1 for Context1", "user", creatingTime),
                new MessageDto(2L, "Message2 for Context1", "user", creatingTime),
                new MessageDto(3L, "Message3 for Context1", "user", creatingTime)
        );
    }
}
