import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiHealthChecker {

    public static void main(String[] args) {
        checkApi("https://jsonplaceholder.typicode.com/posts/1");
        checkApi("https://jsonplaceholder.typicode.com/users");
        checkApi("https://jsonplaceholder.typicode.com/comments");
    }

    private static void checkApi(String url) {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        try {
            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("API: " + url);
            System.out.println("Status Code: " + response.statusCode());

            if (response.statusCode() == 200) {
                System.out.println("✓ API is UP");
            } else {
                System.out.println("✗ API returned an unexpected status");
            }

            System.out.println("--------------------------------");

        } catch (IOException | InterruptedException e) {
            System.out.println("API: " + url);
            System.out.println("✗ API is DOWN");
            System.out.println("Error: " + e.getMessage());
            System.out.println("--------------------------------");
        }
    }
}
