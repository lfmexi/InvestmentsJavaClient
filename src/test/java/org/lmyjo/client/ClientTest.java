/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lmyjo.client;

import java.io.IOException;
import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.Test;
import org.junit.Assert;
import org.lmyjo.client.exceptions.LmyjoException;
/**
 *
 * @author luis
 */
public class ClientTest {
    @Test
    public void evaluateSingleton () {
        Client client = Client.getInstance(null);
        Client anotherClient = Client.getInstance(null);
        Assert.assertEquals(client, anotherClient);
    }
    
    @Test
    public void evaluateServiceInstance () {
        Client client = Client.getInstance(null);
        LmyjoService service = client.getService();
        Assert.assertNotNull(service);
    }
    
    
    @Test
    public void createOwnerTest () throws IOException, LmyjoException {
        MockWebServer server = new MockWebServer();
        
        String username = "fakeUser";
        String email = "fakemail@fake.mail";
        String password = "superFake";
        
        String fakeResponse = "{\"username\": \"" + username + "\", ";
        fakeResponse += "\"email\": \"" + email + "\", ";
        fakeResponse += "\"id\": \"2up3rf4k31d\"}";
        
        MockResponse response = new MockResponse()
            .addHeader("Content-Type", "application/json; charset=utf-8")
            .addHeader("Cache-Control", "no-cache")
            .setBody(fakeResponse);
        
        server.enqueue(response);
        
        HttpUrl baseUrl = server.url("/");
        
        Client client = Client.getInstance(baseUrl.toString());
        Owner owner = client.createOwner(username, email, password);
        Assert.assertNotNull(owner);
        Assert.assertEquals("2up3rf4k31d", owner.getId());
        Assert.assertEquals(username, owner.getUsername());
        Assert.assertEquals(email, owner.getEmail());
        
    }
    
    @Test
    public void loginWithUsernameTest () throws IOException, LmyjoException {
        MockWebServer server = new MockWebServer();
        
        String username = "fakeUser";
        String email = "fakemail@fake.mail";
        String password = "superFake";
        
        String fakeLogin = "{\"id\": \"QSROSD34FLK323ALKD01LSK2\", ";
        fakeLogin += "\"ttl\": \"12000\", ";
        fakeLogin += "\"created\": \"2016-05-02\", ";
        fakeLogin += "\"userId\": \"2up3rf4k31d\"}";
                
        MockResponse response = new MockResponse()
            .addHeader("Content-Type", "application/json; charset=utf-8")
            .addHeader("Cache-Control", "no-cache")
            .setBody(fakeLogin);
        
        server.enqueue(response);
        
        String fakeOwner = "{\"username\": \"" + username + "\", ";
        fakeOwner += "\"email\": \"" + email + "\", ";
        fakeOwner += "\"id\": \"2up3rf4k31d\"}";
                
        response = new MockResponse()
            .addHeader("Content-Type", "application/json; charset=utf-8")
            .addHeader("Cache-Control", "no-cache")
            .setBody(fakeOwner);
        
        server.enqueue(response);
        
        HttpUrl baseUrl = server.url("/");
        
        Client client = Client.getInstance(baseUrl.toString());
        Owner owner = client.loginWithUsername(username, password);
        Assert.assertNotNull(owner);
        Assert.assertEquals("2up3rf4k31d", owner.getId());
        Assert.assertEquals(username, owner.getUsername());
        Assert.assertEquals(email, owner.getEmail());
        Assert.assertEquals("QSROSD34FLK323ALKD01LSK2", owner.getAccessToken());
    }
}
