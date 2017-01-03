/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lmyjo.client;

import org.lmyjo.client.exceptions.UnauthenticatedException;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import okhttp3.ResponseBody;
import org.lmyjo.client.exceptions.LmyjoException;
import retrofit2.Call;
import retrofit2.Response;

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
    private List<Project> projects;
    
    public Owner updateOwner (String username, String email, String password) 
            throws IOException, UnauthenticatedException, LmyjoException {
        if (this.accessToken != null && this.client != null) {
            Call<Owner> ownerCall;
            ownerCall = this.client.getService()
                            .updateOwner(this.accessToken, username, 
                                    email, password);
            
            Response<Owner> ownerResponse = ownerCall.execute();
            
            int statusCode = ownerResponse.code();
            
            if (statusCode < 400) {
                Owner newOwner = ownerResponse.body();
                this.username = newOwner.username;
                this.email = newOwner.email;
                return this;
            }
            
            throw client.throwNewException(statusCode, 
                    ownerResponse.errorBody().string());
        }
        throw new UnauthenticatedException("Owner not authenticated");
    }
    
    public Project createProject(String name, Date starting, String description) 
            throws IOException, UnauthenticatedException, LmyjoException {
        if (this.accessToken != null && this.client != null) {
            if (this.projects == null) {
                loadProjects();
            }
            Call<Project> projectCall;
            projectCall = this.client.getService()
                            .createProject(this.accessToken, this.id, 
                                    name, starting, description);
            
            Response<Project> projectResponse = projectCall.execute(); 
            
            int statusCode = projectResponse.code();
            
            if (statusCode < 400) {
                Project project = projectResponse.body();
                project.setAccessToken(this.accessToken);
                project.setClient(this.client);
                this.projects.add(project);
                return project;
            }
            throw this.client.throwNewException(statusCode, 
                    projectResponse.errorBody().string());
        }
        throw new UnauthenticatedException("Owner not authenticated");
    }
    
    private List<Project> loadProjects () 
            throws IOException, UnauthenticatedException, LmyjoException {
        if (this.accessToken != null && this.client != null) {
            Call<List<Project>> projectsCall;
            projectsCall = this.client.getService()
                                .getProjects(this.accessToken, id);
            
            Response<List<Project>> projectsResponse = projectsCall.execute();
            
            int statusCode = projectsResponse.code();
            
            if (statusCode < 400) {
                List<Project> answeredProjects = projectsResponse.body();
                
                answeredProjects.stream().map((project) -> {
                    project.setClient(client);
                    return project;
                }).forEach((project) -> {
                    project.setAccessToken(accessToken);
                });
                this.projects = answeredProjects;
                return answeredProjects;
            }
            throw this.client.throwNewException(statusCode, projectsResponse
                    .errorBody().string());
        }
        throw new UnauthenticatedException("Owner not authenticated");
    }
    
    public boolean deleteProject(String id)
            throws UnauthenticatedException, IOException, LmyjoException {
        if (this.accessToken != null && this.client != null) {
            Project projectToDelete = this.deleteProjectFromList(id);
            if (projectToDelete == null) {
                return false;
            }
            Call<ResponseBody> deleteCall;
            deleteCall = this.client.getService()
                            .deleteProject(this.accessToken, id);
            
            Response<ResponseBody> response = deleteCall.execute();
            
            int statusCode = response.code();
            
            if (statusCode < 400) {
                return true;
            }
            this.recoverProject(projectToDelete);
            throw this.client.throwNewException(statusCode, 
                    response.errorBody().string());
            
        }
        throw new UnauthenticatedException("Owner not authenticated");        
    }
    
    private void recoverProject (Project project) {
        if (this.projects != null) {
            this.projects.add(project);
        } 
    }
    
    private Project deleteProjectFromList (String id) {
        if (this.projects != null) {
            int projectsQuantity = this.projects.size();
            for (int i = 0; i < projectsQuantity; i++) {
                Project project = this.projects.get(i);
                if (project.getId().equals(id)) {
                    return this.projects.remove(i);
                }
            }
        }
        return null;
    }
    
    public List<Project> getProjects() throws IOException, LmyjoException {
        if (this.projects == null) {
            this.loadProjects();
        }
        return projects;
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
