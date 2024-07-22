package com.justeattakeaway.codechallenge.service.common;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static org.mockito.Mockito.verify;

class MessageSenderServiceTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private MessageSenderService messageSenderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void sendMessage() {
        String queueName = "myQueue";
        Integer message = 10;

        messageSenderService.sendMessage(queueName, message);

        verify(rabbitTemplate).convertAndSend(queueName, message);
    }
}