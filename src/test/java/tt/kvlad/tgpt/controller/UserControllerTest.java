package tt.kvlad.tgpt.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.shaded.com.github.dockerjava.core.MediaType;
import org.testcontainers.shaded.org.apache.commons.lang3.builder.EqualsBuilder;
import tt.kvlad.tgpt.dto.user.UpdateUserDto;
import tt.kvlad.tgpt.dto.user.UserDto;
import tt.kvlad.tgpt.dto.user.UserRegistrationRequestDto;
import tt.kvlad.tgpt.model.Role;
import tt.kvlad.tgpt.model.User;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {
    private static MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void beforeAll(
            @Autowired WebApplicationContext applicationContext
    ) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @DisplayName("Verify register() method works for UserController")
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @Sql(scripts = {
            "classpath:database/user/remove-test-user.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void register_ValidRequestDto_ReturnUserDto() throws Exception {
        UserRegistrationRequestDto requestDto = validRegistrationRequest();
        UserDto expected = testUser();
        String jsonRequest = objectMapper.writeValueAsString(requestDto);
        MvcResult result = mockMvc.perform(
                        post("/admins/register")
                                .contentType(MediaType.APPLICATION_JSON.getMediaType())
                                .content(jsonRequest)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        UserDto actual = objectMapper.readValue(
                result.getResponse().getContentAsByteArray(), UserDto.class
        );
        assertTrue(EqualsBuilder.reflectionEquals(expected, actual, "id"));
    }

    @DisplayName("Verify getInfo() method works for UserController")
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @Sql(scripts = {
            "classpath:database/user/add-test-user.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/user/remove-test-user.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getInfo_ValidRequest_ReturnUserDto() throws Exception {
        UserDto expected = testUser();
        MvcResult result = mockMvc.perform(
                        get("/admins/me")
                                .contentType(MediaType.APPLICATION_JSON.getMediaType())
                                .with(SecurityMockMvcRequestPostProcessors
                                        .authentication(authentication()))
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        UserDto actual = objectMapper.readValue(
                result.getResponse().getContentAsByteArray(), UserDto.class
        );
        assertEquals(expected, actual);
    }

    @DisplayName("Verify update() method works for UserController")
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @Sql(scripts = {
            "classpath:database/user/add-test-user.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/user/remove-test-user.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void update_ValidUpdateUserDto_ReturnUserDto() throws Exception {
        UserDto expected = updatedTestUser();
        UpdateUserDto updateUserDto = updateUserDto();
        String jsonRequest = objectMapper.writeValueAsString(updateUserDto);
        MvcResult result = mockMvc.perform(
                        put("/admins/me")
                                .contentType(MediaType.APPLICATION_JSON.getMediaType())
                                .content(jsonRequest)
                                .with(SecurityMockMvcRequestPostProcessors
                                        .authentication(authentication()))
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        UserDto actual = objectMapper.readValue(
                result.getResponse().getContentAsByteArray(), UserDto.class
        );
        assertEquals(expected, actual);
    }

    @DisplayName("Verify getInfo() method works for UserController")
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @Sql(scripts = {
            "classpath:database/user/add-test-user.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/user/remove-test-user.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getAll_ValidRequest_ReturnListUserDto() throws Exception {
        List<UserDto> expected = List.of(defaultAdmin(), testUser());
        MvcResult result = mockMvc.perform(
                        get("/admins")
                                .contentType(MediaType.APPLICATION_JSON.getMediaType())
                                .with(SecurityMockMvcRequestPostProcessors
                                        .authentication(authentication()))
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        UserDto[] actual = objectMapper.readValue(
                result.getResponse().getContentAsByteArray(), UserDto[].class
        );
        assertEquals(expected, Arrays.stream(actual).toList());
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @DisplayName("Verify delete() method works for UserController")
    @Sql(scripts = {
            "classpath:database/user/add-test-user.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/user/remove-test-user.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void delete_ValidBookId_Successful() throws Exception {
        mockMvc.perform(
                        delete("/admins/2")
                                .contentType(MediaType.APPLICATION_JSON.getMediaType())
                )
                .andExpect(status().isNoContent());
    }

    private UpdateUserDto updateUserDto() {
        return new UpdateUserDto(
                "somenew@example.com",
                "someNewPassword",
                "someNewFirstName",
                "someNewLastName"
        );
    }

    private UserRegistrationRequestDto validRegistrationRequest() {
        return new UserRegistrationRequestDto(
                "somenew@example.com",
                "someNewPassword",
                "someNewPassword",
                "someFirstName",
                "someLastName");
    }

    private UserDto testUser() {
        return new UserDto(
                2L,
                "somenew@example.com",
                "someFirstName",
                "someLastName"
        );
    }

    private UserDto updatedTestUser() {
        return new UserDto(
                2L,
                "somenew@example.com",
                "someNewFirstName",
                "someNewLastName"
        );
    }

    private UserDto defaultAdmin() {
        return new UserDto(
                1L,
                "admin@example.com",
                "Vlad",
                "Keda"
        );
    }

    private Authentication authentication() {
        User user = new User()
                .setId(2L)
                .setEmail("somenew@example.com")
                .setFirstName("someFirstName")
                .setLastName("someLastName")
                .setPassword("someNewPassword")
                .setRoles(Set.of(
                        new Role()
                                .setName(Role.RoleName.ROLE_ADMIN)
                                .setId(1L)));
        return new UsernamePasswordAuthenticationToken(
                user,
                user.getPassword(),
                user.getAuthorities()
        );
    }
}
