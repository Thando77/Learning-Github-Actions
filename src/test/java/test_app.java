import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    @Test
    void shouldAddTwoNumbers() {
        // Arrange
        int a = 5;
        int b = 10;

        // Act
        int result = a + b;

        // Assert
        assertEquals(15, result);
    }

    @Test
    void shouldReturnTrue() {
        // Arrange
        boolean isApiAvailable = true;

        // Act & Assert
        assertTrue(isApiAvailable);
    }

    @Test
    void shouldNotBeNull() {
        // Arrange
        String response = "Success";

        // Act & Assert
        assertNotNull(response);
    }
}
