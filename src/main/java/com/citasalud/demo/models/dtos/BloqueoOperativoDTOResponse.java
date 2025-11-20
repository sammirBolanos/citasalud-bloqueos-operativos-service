package com.citasalud.demo.models.dtos;

import java.time.LocalDate;

public record BloqueoOperativoDTOResponse(
        Integer id,
        Integer idProfesional,
        LocalDate fechaInicio,
        LocalDate fechaFin,
        String detalle
) {}