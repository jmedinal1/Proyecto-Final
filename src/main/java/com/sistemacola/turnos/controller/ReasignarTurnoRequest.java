package com.sistemacola.turnos.controller;

public class ReasignarTurnoRequest {

    private String nuevaArea;
    private String motivo;

    public String getNuevaArea() {
        return nuevaArea;
    }

    public void setNuevaArea(String nuevaArea) {
        this.nuevaArea = nuevaArea;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
}
