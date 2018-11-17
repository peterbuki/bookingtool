package com.peterbuki.bookingtool.service;

import com.peterbuki.bookingtool.dao.ServerDao;
import com.peterbuki.bookingtool.model.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.util.Collection;
import java.util.List;

@Component
@PropertySource("application.properties")
public class ServerService {

    @Autowired
    private ServerDao serverDao;

    @Transactional
    public void add(Server server)
    {
        serverDao.persist(server);
    }

    @Transactional
    public void addAll(Collection<Server> servers)
    {
        for (Server server : servers) {
            add(server);
        }
    }

    @Transactional(readOnly = true)
    public List<Server> listAll() {
        return serverDao.findAll();
    }

    @Transactional
    public Server findByHostname(String hostname) throws NoResultException
    {
        return serverDao.findByHostname(hostname);
    }

    public Long count() {
        return serverDao.count();
    }

    public int update(Server server) {
        return serverDao.updateUsageByHostname(server.getHostname(), server.getUsage());
    }
}
