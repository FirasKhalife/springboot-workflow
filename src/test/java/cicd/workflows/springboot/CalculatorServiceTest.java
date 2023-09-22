package cicd.workflows.springboot;

import cicd.workflows.springboot.Services.CalculatorService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class CalculatorServiceTest {

    // Create a mock of CalculatorService
    @Mock
    private CalculatorService calculatorService;

    // Initialize Mockito annotations
    public CalculatorServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAdd() {
        // Define the behavior of the mock
        when(calculatorService.add(3, 4)).thenReturn(7);

        // Test the add method
        int result = calculatorService.add(3, 4);

        // Verify that the result matches the expected value
        assertEquals(7, result);
    }

    @Test
    public void testSubtract() {
        // Define the behavior of the mock
        when(calculatorService.subtract(8, 3)).thenReturn(5);

        // Test the subtract method
        int result = calculatorService.subtract(8, 3);

        // Verify that the result matches the expected value
        assertEquals(5, result);
    }
}
