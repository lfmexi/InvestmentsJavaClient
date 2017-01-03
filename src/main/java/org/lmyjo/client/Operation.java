/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lmyjo.client;

/**
 *
 * @author luis
 */
public class Operation {
    private String nombre;
    private String tipo_operacion;
    private String unidad_tiempo;
    private String tipo_factor;
    private double cantidad_monetaria;
    private double tasa_interes;
    private int periodo_inicial;
    private int duracion;
    private double incremento;

    private String id;
    private String projectId;
    
    private Client client;
    
    
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
    
    Client getClient() {
        return client;
    }
    
    private String accessToken;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo_operacion() {
        return tipo_operacion;
    }

    public void setTipo_operacion(String tipo_operacion) {
        this.tipo_operacion = tipo_operacion;
    }

    public String getUnidad_tiempo() {
        return unidad_tiempo;
    }

    public void setUnidad_tiempo(String unidad_tiempo) {
        this.unidad_tiempo = unidad_tiempo;
    }

    public String getTipo_factor() {
        return tipo_factor;
    }

    public void setTipo_factor(String tipo_factor) {
        this.tipo_factor = tipo_factor;
    }

    public double getCantidad_monetaria() {
        return cantidad_monetaria;
    }

    public void setCantidad_monetaria(double cantidad_monetaria) {
        this.cantidad_monetaria = cantidad_monetaria;
    }

    public double getTasa_interes() {
        return tasa_interes;
    }

    public void setTasa_interes(double tasa_interes) {
        this.tasa_interes = tasa_interes;
    }

    public int getPeriodo_inicial() {
        return periodo_inicial;
    }

    public void setPeriodo_inicial(int periodo_inicial) {
        this.periodo_inicial = periodo_inicial;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public double getIncremento() {
        return incremento;
    }

    public void setIncremento(double incremento) {
        this.incremento = incremento;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    
    void setClient(Client client) {
        this.client = client;
    }
}
