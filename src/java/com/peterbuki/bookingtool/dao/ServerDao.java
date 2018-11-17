package com.peterbuki.bookingtool.dao;

import com.peterbuki.bookingtool.model.Server;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
public class ServerDao {

    private static Integer id = 0;

    @PersistenceContext
    private EntityManager em;

    public void persist(Server server) {
        if ( server.getId() == null )
        {
            server.setId(id++);
        }
        em.persist(server);
    }

    public List<Server> findAll() {
        return em.createQuery("SELECT s FROM Server s").getResultList();
    }

    public Server findByHostname(String hostname) throws NoResultException {
        return (Server) em.createQuery("SELECT s FROM Server s where s.hostname = :hostname")
                .setParameter("hostname", hostname)
                .setMaxResults(1)
                .getSingleResult();
    }

    public Long count() {
        return (Long) em.createQuery("SELECT count(*) FROM Server s").getSingleResult();
    }
}
