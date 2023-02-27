package webhook.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import webhook.config.SystemProperties;
import webhook.util.SlackChatUtil;

@SpringBootTest
public class ExceptionTest {

	@Autowired
	SlackChatUtil slackChatUtil;
	
	@Autowired
	SystemProperties systemProperties;
	
	@Test
	void generateException() {
		try {
			try {
				throw new RuntimeException();			
			} catch (RuntimeException e) {
				System.out.println("RunTimeException error occurred -> " + e);
				slackChatUtil.sendSlackErr(e.toString());
			}
		} catch (IOException e) {
			System.out.println("SlackChatUtil.sendSlack() error occurred -> " + e);
		}
		assertTrue(systemProperties.isErrorFlag());
	}
}
