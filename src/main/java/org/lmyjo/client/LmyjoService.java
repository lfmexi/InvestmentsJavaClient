/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lmyjo.client;

import java.util.Date;
import java.util.List;
import okhttp3.ResponseBody;
import org.lmyjo.client.resources.OperationRequestBody;
import org.lmyjo.client.resources.SessionResource;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 *
 * @author luis
 */
interface LmyjoService {
    @FormUrlEncoded
    @POST("/api/owners")
    Call<Owner> createOwner(@Field("username") String username,
                                @Field("email") String email,
                                @Field("password") String password);
    
    @FormUrlEncoded
    @POST("/api/owners/login")
    Call<SessionResource> loginWithUsername(@Field("username") String username,
                                @Field("password") String password);
    
    @FormUrlEncoded
    @POST("/api/owners/login")
    Call<SessionResource> loginWithEmail(@Field("email") String email,
                                @Field("password") String password);
    
    @GET("/api/owners/{id}")
    Call<Owner> getOwner(@Header("Authorization") String authorization,
                                @Path("id") String id);

    @FormUrlEncoded
    @PUT("/api/owners/{id}")
    Call<Owner> updateOwner (@Header("Authorization") String authorization,
                                @Field("username") String username,
                                @Field("email") String email,
                                @Field("password") String password);

    @GET("/api/owners/{ownerId}/projects")
    Call<List<Project>> getProjects(@Header("Authorization") String authorization,
                                @Path("ownerId") String ownerId);
    
    @FormUrlEncoded
    @POST("/api/owners/{ownerId}/projects")
    Call<Project> createProject(@Header("Authorization") String authorization,
                                @Path("ownerId") String ownerId,
                                @Field("name") String name,
                                @Field("startingDate") Date startingDate,
                                @Field("description") String description);
    
    @FormUrlEncoded
    @PUT("/api/projects/{id}")
    Call<Project> updateProject (@Header("Authorization") String authorization,
                                @Path("id") String id,
                                @Field("name") String name,
                                @Field("startingDate") Date startingDate,
                                @Field("description") String description);
    
    @DELETE("/api/projects/{id}")
    Call<ResponseBody> deleteProject (@Header("Authorization") String authorization,
                                  @Path("id") String id);
    
    @GET("/api/projects/{id}/operaciones")
    Call<List<Operation>> getOperations (@Header("Authorization") String authorization,
                                  @Path("id") String id);
    
    @FormUrlEncoded
    @POST("/api/projects/{id}/operaciones")
    Call<Operation> createOperation (@Header("Authorization") String authorization,
                                  @Path("id") String id,
                                  @Body OperationRequestBody body);
}
