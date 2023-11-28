package tt.kvlad.tgpt.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.sql.DataSource;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import tt.kvlad.tgpt.dto.message.MessageDto;
import tt.kvlad.tgpt.model.ChatMessage;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ChatMessageRepositoryTest {
    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @BeforeAll
    static void beforeAll(
            @Autowired DataSource dataSource
    ) {
        teardown(dataSource);
        setup(dataSource);
    }

    @AfterAll
    static void afterAll(
            @Autowired DataSource dataSource
    ) {
        teardown(dataSource);
    }

    @Test
    @DisplayName("Verify findLatestByContextId() method works for ChatMessageRepository")
    void findLatestByContextId_ValidContextIdAndPageable_returnChatMessageList() {
        Pageable pageable = PageRequest.of(0, 10);
        List<ChatMessage> actual = chatMessageRepository.findLatestByContextId(1L, pageable);
        assertEquals(3, actual.size());
        assertEquals(testMessagesByContext(), actual);
    }

    @Test
    @DisplayName("Verify getAllByContextId() method works for ChatMessageRepository")
    void getAllByContextId_ValidContextIdAndPageable_returnChatMessageList() {
        Pageable pageable = PageRequest.of(0, 10);
        List<ChatMessage> actual = chatMessageRepository.getAllByContextId(1L, pageable);
        assertEquals(3, actual.size());
        assertEquals(testMessagesByContext(), actual);
    }

    @Test
    @DisplayName("Verify getAllByChatId() method works for ChatMessageRepository")
    void getAllByChatId_ValidContextIdAndPageable_returnChatMessageList() {
        Pageable pageable = PageRequest.of(0, 10);
        List<ChatMessage> actual = chatMessageRepository.getAllByChatId(1L, pageable);
        assertEquals(6, actual.size());
        assertEquals(testMessagesByChat(), actual);
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

    private List<ChatMessage> testMessagesByContext() {
        DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");
        LocalDateTime creatingTime = LocalDateTime
                .parse("2023-11-27 13:27:25.447832", formatter);
        return List.of(
                new ChatMessage()
                        .setId(1L)
                        .setText("Message1 for Context1")
                        .setSignature("user")
                        .setCreationTime(creatingTime),
                new ChatMessage()
                        .setId(2L)
                        .setText("Message2 for Context1")
                        .setSignature("user")
                        .setCreationTime(creatingTime),
                new ChatMessage()
                        .setId(3L)
                        .setText("Message3 for Context1")
                        .setSignature("user")
                        .setCreationTime(creatingTime)
        );
    }

    private List<ChatMessage> testMessagesByChat() {
        DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");
        LocalDateTime creatingTime = LocalDateTime
                .parse("2023-11-27 13:27:25.447832", formatter);
        return List.of(
                new ChatMessage()
                        .setId(1L)
                        .setText("Message1 for Context1")
                        .setSignature("user")
                        .setCreationTime(creatingTime),
                new ChatMessage()
                        .setId(2L)
                        .setText("Message2 for Context1")
                        .setSignature("user")
                        .setCreationTime(creatingTime),
                new ChatMessage()
                        .setId(3L)
                        .setText("Message3 for Context1")
                        .setSignature("user")
                        .setCreationTime(creatingTime),
                new ChatMessage()
                        .setId(4L)
                        .setText("Message1 for Context2")
                        .setSignature("user")
                        .setCreationTime(creatingTime),
                new ChatMessage()
                        .setId(5L)
                        .setText("Message2 for Context2")
                        .setSignature("user")
                        .setCreationTime(creatingTime),
                new ChatMessage()
                        .setId(6L)
                        .setText("Message3 for Context2")
                        .setSignature("user")
                        .setCreationTime(creatingTime)
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
                new MessageDto(3L, "Message3 for Context1", "user", creatingTime),
                new MessageDto(4L, "Message1 for Context2", "user", creatingTime),
                new MessageDto(5L, "Message2 for Context2", "user", creatingTime),
                new MessageDto(6L, "Message3 for Context2", "user", creatingTime)
        );
    }
}
