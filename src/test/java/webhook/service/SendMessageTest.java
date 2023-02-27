package webhook.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;

public class SendMessageTest {
	
	
	@Test
	void sendTest() {
		
		try {
			sendSlack();
		} catch (Exception e) {
			System.out.println("sendTest Error occurred err: " + e.toString());
		}
	}
	
	public void sendSlack() throws IOException {
		String urlStr = "https://slack.com/api/chat.postMessage";
		String token = "xoxb-3965962934229-4864046163136-KcOQ3hM...........";
		String channelId = "C04R30.....";
		String text = "자바 테스터로 메시지 전송 테스트";
		
		urlStr += "?channel="+channelId;
		urlStr += "&text="+URLEncoder.encode(text, "UTF-8");

		HttpURLConnection conn = null;
		URL url = new URL(urlStr);
		conn = (HttpURLConnection) url.openConnection();
		conn.setRequestProperty("Accept", "application/json");
		conn.setRequestProperty("Authorization", "Bearer "+token);
		conn.setRequestMethod("POST");
		conn.setConnectTimeout(5000);
		conn.setReadTimeout(5000);
		conn.connect();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
		StringBuffer response = new StringBuffer();
		
		if (conn.getResponseCode()==200) {
			response.append(br.readLine());
//			System.out.println(response.toString());
			if (response!=null) {
				if (String.valueOf(response).contains("\"ok\":true")) {
			    	System.out.println("슬랙 메시지 발송 성공");
			    }else {
			    	System.out.println("슬랙 메시지 발송 실패");
			    }
			}
		} else {
			System.out.println("슬랙 API 오류 발생 code: " + conn.getResponseCode() + " message: " + conn.getResponseMessage());
		}
		
		br.close();
		conn.disconnect();
	}
}
