/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lmyjo.client;

import java.io.IOException;
import org.lmyjo.client.resources.SessionResource;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
/**
 *
 * @author luis
 */
public final class Client {
    
    private static final String SERVICE_URL = "api.lmyjo.org";
    private static final String SERVICE_PROTOCOL = "http";
    
    private final LmyjoService service; 

    private Client () {
        String baseUrl = SERVICE_PROTOCOL + "://" + SERVICE_URL;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        
        this.service = retrofit.create(LmyjoService.class);        
    }
    
    private static class ClientHolder {
        private static final Client INSTANCE = new Client();
    }
    
    public static Client getInstance() {
        return ClientHolder.INSTANCE;
    }
   
    public final Owner createOwner (String username, String email, 
                                    String password) throws IOException {
        
        Call<Owner> call = this.service.createOwner(username, email, 
                                                            password);
        
        Owner owner = call.execute().body();
        owner.setClient(this);
        return owner;
    }
    
    public final Owner loginWithUsername (String username, String password) 
                                            throws IOException {
        Call<SessionResource> loginCall = this.service.loginWithUsername(username, password);
        
        SessionResource sessionResource = loginCall.execute().body();
        
        String userId = sessionResource.userId;
        String accessToken = sessionResource.id;
        
        Call<Owner> ownerCall = this.service.getOwner(accessToken, userId);
        
        Owner owner = ownerCall.execute().body();
        owner.setClient(this);
        owner.setAccessToken(accessToken);
        return owner;
    }
    
    LmyjoService getService() {
        return this.service;
    }
}
