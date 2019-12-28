package com.fmattaperdomo.restful.resfulclient;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.InvocationCallback;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before; 
import org.junit.Test; 

/**
 *
 * @author Francisco Matta Perdomo
 */
public class RestApiClient {
    private Client client;
    private WebTarget api;
    
    @Before
    public void initClient(){
        this.client = ClientBuilder.newClient();
        this.api = this.client.target("http://localhost:8080/rest-app/api/paises");
    }
    
    @Test
    public void isJavaEEWorkshop(){
        String content = this.api.request("application/json").get(String.class);
        System.out.println(content);
        assertTrue(content.contains("name"));
        assertFalse(content.contains("scala"));
    }
    
    @Test
    public void asyncInvocation() throws InterruptedException{
        this.api.request("application/json").async().get(new InvocationCallback<String>(){
            @Override
            public void completed(String response) {
                System.out.println("----");
                System.out.println(response);
            }

            @Override
            public void failed(Throwable throwable) {
            }
        });
        Thread.sleep(2000);
    }
}
