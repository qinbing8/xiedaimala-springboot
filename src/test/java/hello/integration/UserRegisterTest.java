package hello.integration;

import hello.Application;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:test.properties")
public class UserRegisterTest {
//    @Inject
//    Environment environment;
//
//    @BeforeEach
//    public void setUp() {
//        File projectDir = new File(System.getProperty("basedir", System.getProperty("user.dir")));
//        ClassicConfiguration conf = new ClassicConfiguration();
//        conf.setDataSource(
//            "jdbc:h2:mem:test",
//            "test",
//            "test");
//        Flyway flyway = new Flyway(conf);
//        flyway.clean();
//        flyway.migrate();
//    }
//
//    @Test
//    public void canRegisterNewUser() throws IOException, InterruptedException {
//        String port = environment.getProperty("local.server.port");
//
//        Map<String, String> values = new HashMap<>() {{
//            put("username", "aaaa");
//            put("password", "cccc");
//        }};
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        String requestBody = objectMapper
//            .writeValueAsString(values);
//
//        HttpClient client = HttpClient.newHttpClient();
//        HttpRequest request = HttpRequest.newBuilder()
//            .uri(URI.create("http://localhost:" + port + "/auth/register"))
//            .header("Content-Type", "application/json;charset=utf-8")
//            .POST(HttpRequest.BodyPublishers.ofString(requestBody))
//            .build();
//        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//
//        Assertions.assertEquals(200, response.statusCode());
//        Assertions.assertTrue(response.body().contains("注册成功"));
//    }
}
