package com.xperta.projects.notif.main;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

@Deprecated
/**
 * instead of use EMail class in same package
 * @author xperta
 *
 */
public class MGSample {

    public static void main(String[] args) {
		
    	try {
			
    		System.out.println( sendSimpleMessage() );
			
		} catch (UnirestException e) {
			e.printStackTrace();
		}
	}

    public static JsonNode sendSimpleMessage(String from, String to, /*String cc,*/ String subject, String text) throws UnirestException {
        
    	HttpResponse<JsonNode> request = Unirest.post("https://api.mailgun.net/v3/" + "sandbox14f8ddbc36484cf7bc16a7db65028da6.mailgun.org" + "/messages")
            .basicAuth("api", "12858830f3878665f641e8d961f4156d-0a4b0c40-9a4f1d6f")
            .field("from", from)
            .field("to", to)
//            .field("cc", cc)         
            .field("subject", subject)
            .field("text", text)
            .asJson();
    	System.out.println(request.getBody());
        return request.getBody();
    }
    
    public static JsonNode sendSimpleMessage() throws UnirestException {

        HttpResponse<JsonNode> request = Unirest.post("https://api.mailgun.net/v3/" + "sandbox14f8ddbc36484cf7bc16a7db65028da6.mailgun.org" + "/messages")
            .basicAuth("api", "12858830f3878665f641e8d961f4156d-0a4b0c40-9a4f1d6f")
            .field("from", "bcemkucuk@gmail.com")
            .field("to", "bkasal92@gmail.com")
            .field("subject", "hello")
            .field("text", "testing")
            .asJson();

        return request.getBody();
    }
}