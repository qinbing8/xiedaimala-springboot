package hello.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.Application;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.ClassicConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:test.properties")
public class UserRegisterTest {
    @Inject
    Environment environment;

    @BeforeEach
    public void setUp() {
        File projectDir = new File(System.getProperty("basedir", System.getProperty("user.dir")));
        ClassicConfiguration conf = new ClassicConfiguration();
        conf.setDataSource(
            "jdbc:h2:mem:test",
            "test",
            "test");
        Flyway flyway = new Flyway(conf);
        flyway.clean();
        flyway.migrate();
    }

    @Test
    public void canRegisterNewUser() throws IOException, InterruptedException {
        String port = environment.getProperty("local.server.port");

        Map<String, String> values = new HashMap<>() {{
            put("username", "aaaa");
            put("password", "cccc");
        }};

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper
            .writeValueAsString(values);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:" + port + "/auth/register"))
            .header("Content-Type", "application/json;charset=utf-8")
            .POST(HttpRequest.BodyPublishers.ofString(requestBody))
            .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertTrue(response.body().contains("注册成功"));
    }
}
