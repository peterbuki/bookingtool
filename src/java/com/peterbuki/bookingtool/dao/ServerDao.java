package com.peterbuki.bookingtool.dao;

import com.peterbuki.bookingtool.model.ServerDto;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Component
public class ServerDao {

    private static Integer id = 0;

    @PersistenceContext
    private EntityManager em;

    public void persist(ServerDto server) {
        if (server.getId() == null) {
            server.setId(id++);
        }
        em.persist(server);
    }

    public List<ServerDto> findAll() {
        return em.createQuery("SELECT s FROM ServerDto s").getResultList();
    }

    public ServerDto findByHostname(String hostname) throws NoResultException {
        return (ServerDto) em.createQuery("SELECT s FROM ServerDto s where s.hostname = :hostname")
                .setParameter("hostname", hostname)
                .setMaxResults(1)
                .getSingleResult();
    }

    public Long count() {
        return (Long) em.createQuery("SELECT count(*) FROM ServerDto s").getSingleResult();
    }

    @Modifying
    @Transactional
    public int updateUsageByHostname(@Param("hostname") String hostname, @Param("usage") String usage) {
        Query query = em.createQuery("UPDATE ServerDto s set s.usage = :usage where s.hostname = :hostname");
        return query.setParameter("hostname", hostname).setParameter("usage", usage).executeUpdate();
    }
}
