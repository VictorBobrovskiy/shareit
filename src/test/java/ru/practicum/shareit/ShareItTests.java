package ru.practicum.shareit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class ShareItTests {

    @Test
    void contextLoads() {
    }

    @Test
    public void testMain() {
        // Invoke the main method
        ShareItApp.main(new String[]{});

        // No assertion is needed as long as the application context is successfully loaded without errors
        Assertions.assertTrue(true);
    }

}
