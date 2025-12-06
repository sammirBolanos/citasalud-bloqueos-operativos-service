package com.citasalud.demo.models.dtos;

import java.time.LocalDate;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BloqueoOperativoDTOResponse extends RepresentationModel<BloqueoOperativoDTOResponse> {
    private Integer id;
    private Integer idProfesional;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String detalle;

    public BloqueoOperativoDTOResponse() {}

    public BloqueoOperativoDTOResponse(Integer id, Integer idProfesional, LocalDate fechaInicio, 
                                       LocalDate fechaFin, String detalle) {
        this.id = id;
        this.idProfesional = idProfesional;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.detalle = detalle;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    
    public Integer getIdProfesional() { return idProfesional; }
    public void setIdProfesional(Integer idProfesional) { this.idProfesional = idProfesional; }
    
    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }
    
    public LocalDate getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDate fechaFin) { this.fechaFin = fechaFin; }
    
    public String getDetalle() { return detalle; }
    public void setDetalle(String detalle) { this.detalle = detalle; }
}