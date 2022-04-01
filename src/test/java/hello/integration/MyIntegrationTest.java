package hello.integration;

import hello.Application;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.inject.Inject;
import java.io.IOException;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:test.properties")
public class MyIntegrationTest {
    @Inject
    Environment environment;

    @Test
    public void notLoggedInByDefault() throws IOException, InterruptedException {
        OkHttpClient client = new OkHttpClient();

        String port = environment.getProperty("local.server.port");

        String url = "http://localhost:" + port + "/auth";

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        ResponseBody body = response.body();

        Assertions.assertEquals(200, response.code());
        Assertions.assertNotNull(body);
        Assertions.assertTrue(body.string().contains("用户没有登录"));

        // use Java 11
        //String port = environment.getProperty("local.server.port");
        //HttpClient client = HttpClient.newHttpClient();
        //HttpRequest request = HttpRequest.newBuilder()
        //        .uri(URI.create("http://localhost:" + port + "/auth"))
        //        .build();
        //HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        //Assertions.assertEquals(200, response.statusCode());
        //Assertions.assertTrue(response.body().contains("用户没有登录"));
    }
}
