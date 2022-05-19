package com.estockmarket.kafkamongo;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class KafkaConsumerMongodbApplicationTests {

	@Test
	void contextLoads() {
		assertNotNull(getClass());
	}

}
