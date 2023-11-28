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
import org.springframework.jdbc.datasource.init.ScriptUtils;
import tt.kvlad.tgpt.model.Context;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ContextRepositoryTest {
    @Autowired
    private ContextRepository contextRepository;

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
    @DisplayName("Verify getAllByChatId() method works for ContextRepository")
    void getAllByChatId_ValidChatId_returnContextList() {
        List<Context> actual = contextRepository.getAllByChatId(123456789L);
        assertEquals(2, actual.size());
        assertEquals(testContextByChat(), actual);
    }

    @Test
    @DisplayName("Verify findByChatIdAndName() method works for ContextRepository")
    void findByChatIdAndName_ValidChatIdAndName_returnOptionalContext() {
        Optional<Context> actual = contextRepository.findByChatIdAndName(123456789L, "Context1");
        assertTrue(actual.isPresent());
        assertEquals(testContextByChatAndName(), actual.get());
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

    private List<Context> testContextByChat() {
        return List.of(
                new Context()
                        .setId(1L)
                        .setName("Context1")
                        .setContextType(Context.ContextType.ADMIN),
                new Context()
                        .setId(2L)
                        .setName("Context2")
                        .setContextType(Context.ContextType.GPT)
        );
    }

    private Context testContextByChatAndName() {
        return new Context()
                .setId(1L)
                .setName("Context1")
                .setContextType(Context.ContextType.ADMIN);
    }
}
