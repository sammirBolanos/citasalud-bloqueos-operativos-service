package com.citasalud.demo.client;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class DisponibilidadesClient {

    private final WebClient webClient;

    public DisponibilidadesClient(@Value("${microservices.disponibilidades.url}") String citasUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(citasUrl)
                .build();
    }

    public void inactivarDisponibilidadesPorBloqueo(Integer idProfesional, LocalDate fechaInicio, LocalDate fechaFin) {
        try {
            webClient.post()
                    .uri("/inactivar-por-bloqueo")
                    .bodyValue(new DeshabilitarDisponibilidades(idProfesional, fechaInicio, fechaFin))
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();
            
            log.info("Disponibilidades inactivadas para profesional {} entre {} y {}", 
                     idProfesional, fechaInicio, fechaFin);
        } catch (Exception e) {
            log.error("Error al inactivar disponibilidades para profesional {}: {}", idProfesional, e.getMessage());
            // Aquí puedes decidir si lanzas la excepción o solo la logueas
            throw new RuntimeException("Error al comunicarse con el microservicio de disponibilidades", e);
        }
    }

    private record DeshabilitarDisponibilidades(
        Integer idProfesional,
        LocalDate fechaInicio,
        LocalDate fechaFin
    ) {}
}
