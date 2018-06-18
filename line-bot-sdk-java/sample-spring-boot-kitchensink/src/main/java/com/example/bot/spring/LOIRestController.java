package com.example.bot.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.bind.annotation.*;
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

    @RequestMapping(value="/multicast/{userIDs}/{message}")
    public String multicast(@PathVariable("userIDs") String userIDs, @PathVariable("message") String message) {
        return doSend(userIDs, message);
    }

    @RequestMapping(value="/multicast2", method = RequestMethod.POST)
    public String multicast2(@RequestBody  MulticastPojo requestObj){
        try {
            doSend(requestObj.getUserIDs(), requestObj.getMessage());
            return "Success";
        }catch(RuntimeException e){
            e.printStackTrace();
            return "Failed";
        }
    }

    private String doSend(String userIDs, String message) {
        //String userIDs = "Ubac5b3a0bfba18e114afbc0afc08724d";
        String url = "https://api.line.me/v2/bot/message/multicast";

        String users = "\""+userIDs + "\"";

        String msg = "{\"to\": [ "+users+" ],\"messages\": [{\"type\":\"text\",\"text\":\"" +message+ "\"}]}";
        return send(url, msg);
    }


    public String send(String url, String msg){

        //String plainCreds = user + ":" + pwd;
        //byte[] plainCredsBytes = plainCreds.getBytes();
        //byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
        //String base64Creds = new String(base64CredsBytes);
        String base64Creds = "6bBF7E4GEuJETgVgrTRB4hH75moub8MFo83HHPOqVBItnk018rP67GQ/qwenQfmsAbxT8hJTGO2T2UqVKqvcpqwvccun3/qdIAtkqkXkqJ6Zx+OyC9q07EMCrDK+McRYnKNa04qohBKv+d6yHw1a8QdB04t89/1O/w1cDnyilFU=";

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