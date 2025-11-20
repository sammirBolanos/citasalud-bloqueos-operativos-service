package com.citasalud.demo.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.citasalud.demo.models.jpaEntitys.BloqueoOperativo;

@Repository
public interface BloqueoOperativoRepository extends JpaRepository<BloqueoOperativo, Integer> {

    List<BloqueoOperativo> findByIdProfesional(Integer idProfesional);

    @Query("""
        SELECT b
        FROM BloqueoOperativo b
        WHERE b.idProfesional = :idProfesional
          AND b.fechaInicio <= :fechaFin
          AND b.fechaFin >= :fechaInicio
        """)
    List<BloqueoOperativo> findOverlapping(
        @Param("idProfesional") Integer idProfesional,
        @Param("fechaInicio") LocalDate fechaInicio,
        @Param("fechaFin") LocalDate fechaFin
    );


    @Query("""
        SELECT b
        FROM BloqueoOperativo b
        WHERE b.idProfesional = :idProfesional
          AND :fecha BETWEEN b.fechaInicio AND b.fechaFin
        """)
    List<BloqueoOperativo> findByIdProfesionalAndFechaEnBloque(
        @Param("idProfesional") Integer idProfesional,
        @Param("fecha") LocalDate fecha
    );
}
