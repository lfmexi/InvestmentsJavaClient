/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lmyjo.client.exceptions;

/**
 *
 * @author luis
 */
public class UnauthenticatedException extends LmyjoException{

    public UnauthenticatedException() {
    }

    public UnauthenticatedException(String message) {
        super(message);
    }
    
}
