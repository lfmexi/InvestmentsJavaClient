/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lmyjo.client;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import retrofit2.Call;

/**
 *
 * @author luis
 */
public final class Owner {
    
    private String id;
    private String username;
    private String email;
    private String accessToken;
        
    private Client client;

    public Owner updateOwner (String username, String email, String password) 
            throws IOException, UnauthenticatedException {
        if (this.accessToken != null) {
            Call<Owner> ownerCall;
            ownerCall = this.client.getService()
                            .updateOwner(this.accessToken, username, 
                                    email, password);
            Owner newOwner = ownerCall.execute().body();
            this.username = newOwner.username;
            this.email = newOwner.email;
            return this;
        }
        throw new UnauthenticatedException();
    }
    
    public Project createProject(String name, Date starting, String description) 
            throws IOException, UnauthenticatedException {
        if (this.accessToken != null) {
            Call<Project> projectCall;
            projectCall = this.client.getService()
                            .createProject(this.accessToken, this.id, 
                                    name, starting, description);
            Project project = projectCall.execute().body();
            project.setAccessToken(this.accessToken);
            project.setClient(this.client);
            
            return project;
        }
        throw new UnauthenticatedException();
    }
    
    public List<Project> getProjects () throws IOException, UnauthenticatedException {
        if (this.accessToken != null) {
            Call<List<Project>> projectsCall;
            projectsCall = this.client.getService()
                                .getProjects(this.accessToken, id);
            
            List<Project> projects = projectsCall.execute().body();
            
            projects.stream().map((project) -> {
                project.setClient(client);
                return project;
            }).forEach((project) -> {
                project.setAccessToken(accessToken);
            });
            
            return projects;
        }
        throw new UnauthenticatedException();
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    final void setClient(Client client) {
        this.client = client;
    }

    final Client getClient() {
        return client;
    }

}
