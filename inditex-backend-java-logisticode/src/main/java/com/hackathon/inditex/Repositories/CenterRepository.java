package com.hackathon.inditex.Repositories;

import com.hackathon.inditex.Entities.Center;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

@Repository
public interface CenterRepository extends JpaRepository<Center, Long> {

    /**
     * Busca un centro logístico por coordenadas específicas.
     *
     * @param latitude  Latitud de las coordenadas.
     * @param longitude Longitud de las coordenadas.
     * @return Un centro logístico opcional que coincida con las coordenadas.
     */
    Optional<Center> findByCoordinates_LatitudeAndCoordinates_Longitude(Double latitude, Double longitude);

    /**
     * Verifica si existe un centro logístico en las coordenadas especificadas.
     *
     * @param latitude  Latitud de las coordenadas.
     * @param longitude Longitud de las coordenadas.
     * @return True si existe un centro, False en caso contrario.
     */
    boolean existsByCoordinates_LatitudeAndCoordinates_Longitude(Double latitude, Double longitude);

    /**
     * Encuentra centros disponibles que cumplan con la capacidad especificada,
     * estén en estado 'AVAILABLE' y tengan capacidad disponible.
     *
     * @param capacity Tipo de capacidad (S, M, B, combinaciones).
     * @return Lista de centros disponibles que coincidan con los criterios.
     */
    @Query("""
           SELECT c 
           FROM Center c 
           WHERE c.capacity LIKE %:capacity% 
           AND c.status = 'AVAILABLE' 
           AND c.currentLoad < c.maxCapacity
           """)
    List<Center> findAvailableCenters(String capacity);
}
