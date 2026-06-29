import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApiHealthChecker {

    private static final Logger logger = LoggerFactory.getLogger(ApiHealthChecker.class);

    private final HttpClient client;

    public ApiHealthChecker() {
        this.client = HttpClient.newHttpClient();
    }

    public ApiHealthChecker(HttpClient client) {
        this.client = client;
    }

    public static void main(String[] args) {
        ApiHealthChecker checker = new ApiHealthChecker();
        checker.checkApi("https://jsonplaceholder.typicode.com/posts/1");
        checker.checkApi("https://jsonplaceholder.typicode.com/users");
        checker.checkApi("https://jsonplaceholder.typicode.com/comments");
    }

    public void checkApi(String url) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        try {
            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            logger.info("API: {}", url);
            logger.info("Status Code: {}", response.statusCode());

            if (response.statusCode() == 200) {
                logger.info("✓ API is UP");
            } else {
                logger.warn("✗ API returned an unexpected status");
            }

            logger.info("--------------------------------");

        } catch (IOException e) {
            logger.error("API: {} is DOWN (IO error)", url, e);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("API: {} was interrupted", url, e);
        }
    }
}
