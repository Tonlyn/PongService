package com.tek.pongservice

import com.tek.pongservice.controller.PongController
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate
import spock.lang.Specification
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.*;

@SpringBootTest
class PongServiceTests extends Specification {
    @Autowired
    private PongController pongController

    @MockBean
    private RestTemplate restTemplate

    @Test
    void testGetPongSuccess() {
        given: "a successful response"
        pongController = spy(new PongController())
        pongController.lastResetTime = LocalDateTime.now().minusSeconds(2) // Ensure no throttling occurs
        pongController.requestCount = new AtomicInteger(0) // Reset request count for testing
        1 * restTemplate.getForObject(_, _) >> "World" // Mocking a successful response from Ping Service
        ResponseEntity<String> response = pongController.getPong()
        assertEquals(OK, response.getStatusCode())
        assertEquals("World", response.getBody())
    }

    @Test
    void testGetPongThrottled() {
        given: "a throttled response due to too many requests"
        pongController = spy(new PongController())
        pongController.lastResetTime = LocalDateTime.now().minusSeconds(2) // Ensure no reset occurs during test
        pongController.requestCount = new AtomicInteger(2) // Simulate two requests already made within the same second
        ResponseEntity<String> response = pongController.getPong()
        assertEquals(TOO_MANY_REQUESTS, response.getStatusCode())
        assertNull(response.getBody()) // No body expected for a 429 response
    }
}
