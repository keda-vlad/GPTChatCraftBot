package tt.kvlad.tgpt.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;
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
import tt.kvlad.tgpt.model.TelegramChat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TelegramChatRepositoryTest {
    @Autowired
    private TelegramChatRepository telegramChatRepository;

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
    @DisplayName("Verify getByChatIdWithTargetContext() method works for TelegramChatRepository")
    void getByChatIdWithTargetContext_ValidChatId_returnTelegramChat() {
        Optional<TelegramChat> actual = telegramChatRepository
                .getByChatIdWithTargetContext(123456789L);
        assertTrue(actual.isPresent());
        assertEquals(testChat(), actual.get());
    }

    @Test
    @DisplayName("Verify getByChatIdWithAllContexts() method works for TelegramChatRepository")
    void getByChatIdWithAllContexts_ValidChatId_returnTelegramChat() {
        Optional<TelegramChat> actual = telegramChatRepository
                .getByChatIdWithAllContexts(123456789L);
        assertTrue(actual.isPresent());
        assertEquals(testChat(), actual.get());
    }

    @Test
    @DisplayName("Verify getAllWithAllContexts() method works for TelegramChatRepository")
    void getAllWithAllContexts_ValidChatId_returnListTelegramChat() {
        Pageable pageable = PageRequest.of(0, 10);
        List<TelegramChat> actual = telegramChatRepository.getAllWithAllContexts(pageable);
        assertEquals(List.of(testChat()), actual);
    }

    @Test
    @DisplayName("Verify getByIdWithAllContexts() method works for TelegramChatRepository")
    void getByIdWithAllContexts_ValidChatId_returnTelegramChat() {
        Optional<TelegramChat> actual = telegramChatRepository.getByIdWithAllContexts(1L);
        assertTrue(actual.isPresent());
        assertEquals(testChat(), actual.get());
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

    private TelegramChat testChat() {
        return new TelegramChat()
                .setId(1L)
                .setChatId(123456789L);
    }
}
