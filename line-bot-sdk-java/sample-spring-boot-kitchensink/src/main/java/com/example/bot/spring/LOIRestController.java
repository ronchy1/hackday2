package com.example.bot.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(value="/hack")
public class LOIRestController {

    private RestTemplate restTemplate;

    private ClientHttpRequestFactory factory;

    public LOIRestController(){
        setFactory();
        setRestTemplate();
    }

    public void setFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        this.factory = factory;
    }

    public void setRestTemplate(){
        this.restTemplate = new RestTemplate(this.factory);
    }

    @RequestMapping(value="/test/{param1}")
    public String test(@PathVariable("param1") String param1) {
        String url = "https://api.line.me/v2/bot/message/multicast";
        String msg = "{\"to\": [\"Ubac5b3a0bfba18e114afbc0afc08724d\"],\"messages\":[{\"type\":\"text\",\"text\":\"Hello, world1\"},{\"type\":\"text\",\"text\":\"Hello, world2\"}]}";
        return send(url, msg);
    }


    public String send(String url, String msg){

        //String plainCreds = user + ":" + pwd;
        //byte[] plainCredsBytes = plainCreds.getBytes();
        //byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
        //String base64Creds = new String(base64CredsBytes);
        String base64Creds = "5vbUI7nvI3tJLqDilx1EfQeOuKvPhp2eTc8WKrj0VTgJS4PYBBlFVbZylS6EOy2HAbxT8hJTGO2T2UqVKqvcpqwvccun3/qdIAtkqkXkqJ6/GZGeXd0pl00zDOZBRZYTxwSCmRKcQcGk5/bo3cnEWQdB04t89/1O/w1cDnyilFU=";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + base64Creds);

        headers.setContentType(MediaType.APPLICATION_JSON);

        String requestBody = msg;
        HttpEntity<String> request = new HttpEntity<String>(requestBody, headers);
        HttpMethod httpMethod = HttpMethod.POST;

        ResponseEntity<String> result = restTemplate.exchange(url, httpMethod, request, String.class);
        String response = result.getBody();

        return response;
    }
}