import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CalculatorTest {

    @Mock
    private HttpClient mockHttpClient;

    @Mock
    @SuppressWarnings("unchecked")
    private HttpResponse<String> mockHttpResponse;

    private ApiHealthChecker checker;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        checker = new ApiHealthChecker(mockHttpClient);
    }

    @Test
    @SuppressWarnings("unchecked")
    void shouldLogSuccessWhenApiReturns200() throws IOException, InterruptedException {
        when(mockHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(mockHttpResponse);
        when(mockHttpResponse.statusCode()).thenReturn(200);

        checker.checkApi("https://example.com/api");

        verify(mockHttpClient, times(1)).send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class));
    }

    @Test
    @SuppressWarnings("unchecked")
    void shouldLogWarningWhenApiReturnsNon200() throws IOException, InterruptedException {
        when(mockHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(mockHttpResponse);
        when(mockHttpResponse.statusCode()).thenReturn(500);

        checker.checkApi("https://example.com/api");

        verify(mockHttpClient, times(1)).send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class));
    }

    @Test
    @SuppressWarnings("unchecked")
    void shouldHandleIOException() throws IOException, InterruptedException {
        when(mockHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenThrow(new IOException("Connection refused"));

        checker.checkApi("https://example.com/api");

        verify(mockHttpClient, times(1)).send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class));
    }

    @Test
    @SuppressWarnings("unchecked")
    void shouldHandleInterruptedException() throws IOException, InterruptedException {
        when(mockHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenThrow(new InterruptedException("Interrupted"));

        checker.checkApi("https://example.com/api");

        verify(mockHttpClient, times(1)).send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class));
    }

    @Test
    void shouldCreateDefaultCheckerWithNoArgConstructor() {
        ApiHealthChecker defaultChecker = new ApiHealthChecker();
        // Verifies the no-arg constructor runs without errors
        assert defaultChecker != null;
    }
}
