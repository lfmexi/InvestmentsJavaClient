/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lmyjo.client.resources;

import java.util.Date;

/**
 *
 * @author luis
 */
public class SessionResource {
    public final String id;
    public final String ttl;
    public final Date created;
    public final String userId;

    public SessionResource(String id, String ttl, Date created, String userId) {
        this.id = id;
        this.ttl = ttl;
        this.created = created;
        this.userId = userId;
    }
}
