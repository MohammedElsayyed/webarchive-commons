package org.archive.format.http;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.archive.util.IAUtils;
import org.archive.util.TestUtils;
import org.archive.format.http.HttpHeader;
import org.archive.format.http.HttpHeaders;
import org.archive.format.http.HttpParseException;
import org.archive.format.http.HttpResponse;
import org.archive.format.http.HttpResponseParser;

import junit.framework.TestCase;

public class HttpResponseParserTest extends TestCase {

	public void testParse() throws IOException {

		HttpResponseParser parser = new HttpResponseParser();
		String message = "200 OK\r\nContent-Type: text/plain\r\n\r\nHi there";
		try {
			HttpResponse response = 
				parser.parse(new ByteArrayInputStream(message.getBytes(IAUtils.UTF8)));
			assertNotNull(response);
			HttpHeaders headers = response.getHeaders();
			assertNotNull(headers);
			assertEquals(1,headers.size());
			HttpHeader header = headers.get(0);
			assertEquals("Content-Type",header.getName());
			assertEquals("text/plain",header.getValue());
			TestUtils.assertStreamEquals(response, "Hi there".getBytes(IAUtils.UTF8));
			
		} catch (HttpParseException e) {
			e.printStackTrace();
			fail();
		}
		
	}

	public void testParseWithLf() throws IOException {

		HttpResponseParser parser = new HttpResponseParser();
		String message = "200 OK\nContent-Type: text/plain\n\nHi there";
		try {
			HttpResponse response = 
				parser.parse(new ByteArrayInputStream(message.getBytes(IAUtils.UTF8)));
			assertNotNull(response);
			HttpHeaders headers = response.getHeaders();
			assertNotNull(headers);
			assertEquals(1,headers.size());
			
		} catch (HttpParseException e) {
			e.printStackTrace();
			fail();
		}
		
	}

}
