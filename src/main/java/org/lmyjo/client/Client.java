/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lmyjo.client;

import java.io.IOException;
import okhttp3.ResponseBody;
import org.lmyjo.client.exceptions.LmyjoException;
import org.lmyjo.client.exceptions.UnauthorizedException;
import org.lmyjo.client.exceptions.UnprocessableEntityException;
import org.lmyjo.client.resources.SessionResource;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
/**
 *
 * @author luis
 */
public final class Client {
    
    private static final String SERVICE_URL = "api.lmyjo.org";
    private static final String SERVICE_PROTOCOL = "http";
    
    private LmyjoService service; 

    private Client () {               
    }
    
    private void setService (String url) {
        String baseUrl = (url != null)? url : SERVICE_PROTOCOL + "://"
                + SERVICE_URL;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        
        this.service = retrofit.create(LmyjoService.class); 
    }
    
    private static class ClientHolder {
        private static final Client INSTANCE = new Client();
    }
    
    public static Client getInstance(String url) {
        ClientHolder.INSTANCE.setService(url);
        return ClientHolder.INSTANCE;
    }
   
    public final Owner createOwner (String username, String email, String password) 
            throws IOException, LmyjoException {
        
        Call<Owner> call = this.service.createOwner(username, email, 
                                                            password);
        Response<Owner> response = call.execute();
        
        int statusCode = response.code();
        
        if (statusCode < 400) {
            Owner owner = response.body();
        
            if (owner != null) 
                owner.setClient(this);

            return owner;
        }
        
        ResponseBody errorBody = response.errorBody();
        
        throw this.throwNewException(statusCode, errorBody.string());
    }
    
    public final Owner loginWithUsername (String username, String password) 
                                            throws IOException, LmyjoException {
        Call<SessionResource> loginCall = this.service
                .loginWithUsername(username, password);
        
        Response<SessionResource> sessionResponse = loginCall.execute();
        
        if (sessionResponse.code() < 400) {
            SessionResource sessionResource = sessionResponse.body();
                
            String userId = sessionResource.userId;
            String accessToken = sessionResource.id;

            Call<Owner> ownerCall = this.service.getOwner(accessToken, userId);

            Response<Owner> ownerResponse = ownerCall.execute();
            
            if (ownerResponse.code() < 400) {
                
                Owner owner = ownerResponse.body();

                if (owner != null) {
                    owner.setClient(this);
                    owner.setAccessToken(accessToken);
                }

                return owner;
            }
            throw this.throwNewException(ownerResponse.code(), 
                    ownerResponse.errorBody().string());
        }
        
        throw this.throwNewException(sessionResponse.code(), 
                sessionResponse.errorBody().string()); 
    }
    
    LmyjoService getService() {
        return this.service;
    }
    
    LmyjoException throwNewException (int statusCode, String message) {
        switch (statusCode) {
            case 401: 
            case 403: {
                return new UnauthorizedException(message);
            }
            case 422: {
                return new UnprocessableEntityException(message);
            }
        }
        return new LmyjoException ("Unknown exception " + message);
    }
}
