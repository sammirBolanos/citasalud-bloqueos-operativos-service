package com.citasalud.demo.models.dtos;

import java.time.LocalDate;

public record BloqueoOperativoDTORequest(
        Integer idProfesional,
        LocalDate fechaInicio,
        LocalDate fechaFin,
        String detalle
) {}

