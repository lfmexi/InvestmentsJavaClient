/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lmyjo.client;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import org.lmyjo.client.exceptions.LmyjoException;
import org.lmyjo.client.exceptions.UnauthenticatedException;
import org.lmyjo.client.resources.OperationRequestBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 *
 * @author luis
 */
public final class Project {
    private String id;
    private String name;
    private Date startingDate;
    private String description;

    private Client client;
    private String accessToken;
    
    private List<Operation> operations;

    public List<Operation> getOperations() 
            throws UnauthenticatedException, IOException, LmyjoException {
        if (this.operations == null) {
            this.loadOperations();
        }
        return operations;
    }
    
    public Operation createOperation(OperationRequestBody body) 
            throws UnauthenticatedException, IOException, LmyjoException {
        if (this.accessToken != null && this.client != null) {
            if (this.operations == null) {
                this.loadOperations();
            }
            Call<Operation> call = this.client.getService()
                                    .createOperation(this.accessToken, id, body);
            Response<Operation> response = call.execute();
            
            int statusCode = response.code();
            
            if (statusCode < 400) {
                Operation operation = response.body();
                operation.setAccessToken(this.accessToken);
                operation.setClient(this.client);
                this.operations.add(operation);
                return operation;
            }
            throw client.throwNewException(statusCode, 
                    response.errorBody().string());
        }
        throw new UnauthenticatedException("Owner not authenticated");
    }
    
    public Project updateProject (String newName, Date newDate, String desc) 
            throws UnauthenticatedException, LmyjoException, IOException {
        if (this.accessToken != null && this.client != null) {
            Call<Project> projectCall;
            projectCall = this.client.getService()
                        .updateProject(this.accessToken, this.id,
                                    newName, newDate, desc);
            
            Response<Project> projectResponse = projectCall.execute();
            
            int statusCode = projectResponse.code();
            
            if (statusCode < 400) {
                Project newProject = projectResponse.body();
                this.name = newProject.name;
                this.startingDate =  newProject.startingDate;
                this.description = newProject.description;
                return this;
            }
            throw client.throwNewException(statusCode, 
                    projectResponse.errorBody().string());
        }
        throw new UnauthenticatedException("Owner not authenticated");
    }
    
    private List<Operation> loadOperations () 
            throws UnauthenticatedException, IOException, LmyjoException {
        if (this.accessToken != null && this.client != null) {
            Call<List<Operation>> projectsCall;
            projectsCall = this.client.getService()
                                .getOperations(this.accessToken, id);
            
            Response<List<Operation>> response = projectsCall.execute();
            
            int statusCode = response.code();
            
            if (statusCode < 400) {
                 List<Operation> operationsResult = response.body();
                
                operationsResult.stream().map((operation) -> {
                    operation.setClient(client);
                    return operation;
                }).forEach((operation) -> {
                    operation.setAccessToken(accessToken);
                });
                this.operations = operationsResult;
                return this.operations;
            }
            throw client.throwNewException(statusCode, 
                    response.errorBody().string());
        }
        throw new UnauthenticatedException("Owner not authenticated");
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(Date startingDate) {
        this.startingDate = startingDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    Client getClient() {
        return client;
    }

    void setClient(Client client) {
        this.client = client;
    }

    public String getAccessToken() {
        return accessToken;
    }

    void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    
}
