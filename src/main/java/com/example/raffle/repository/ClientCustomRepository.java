package com.example.raffle.repository;

import com.example.raffle.model.Client;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class ClientCustomRepository {

    private final EntityManager entityManager;

    public ClientCustomRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Client> findClient(Long id, String name, String city) {
        String query = "SELECT c FROM Client AS c";
        String condition = " where ";
        if (id != null) {
            query += condition + " c.id = :id";
            condition = " and ";
        }
        if (name != null) {
            query += condition + " c.name = :name";
            condition = " and ";
        }
        if (city != null) {
            query += condition + " c.city = :city";
            condition = " and ";
        }

        var createQuery = entityManager.createQuery(query, Client.class);
        if (id != null) {
            createQuery.setParameter("id", id);
        }
        if (name != null) {
            createQuery.setParameter("name", name);
        }
        if (city != null) {
            createQuery.setParameter("city", city);
        }

        return createQuery.getResultList();
    }
}
