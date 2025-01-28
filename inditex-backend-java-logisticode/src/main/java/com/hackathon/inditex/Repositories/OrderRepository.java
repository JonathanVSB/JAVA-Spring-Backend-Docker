package com.hackathon.inditex.Repositories;

import com.hackathon.inditex.Entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para la gestión de órdenes en la base de datos.
 * Proporciona métodos para interactuar con las entidades Order.
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    /**
     * Encuentra todas las órdenes con un estado específico.
     *
     * @param status Estado de las órdenes (e.g., PENDING, ASSIGNED).
     * @return Lista de órdenes que coincidan con el estado dado.
     */
    List<Order> findByStatus(String status);

    /**
     * Encuentra todas las órdenes asignadas a un centro logístico específico.
     *
     * @param assignedCenter Nombre del centro logístico asignado.
     * @return Lista de órdenes asignadas al centro especificado.
     */
    List<Order> findByAssignedCenter(String assignedCenter);

    /**
     * Busca todas las órdenes de un cliente específico.
     *
     * @param customerId Identificador del cliente.
     * @return Lista de órdenes pertenecientes al cliente.
     */
    List<Order> findByCustomerId(Long customerId);
}
