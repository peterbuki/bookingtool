package com.peterbuki.bookingtool.service;

import com.peterbuki.bookingtool.dao.ServerDao;
import com.peterbuki.bookingtool.model.Dto;
import com.peterbuki.bookingtool.model.ServerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.util.Collection;
import java.util.List;

@Component
@PropertySource("application.properties")
public class ServerService extends Service<ServerDto> {

    @Autowired
    private ServerDao serverDao;

    @Transactional
    public void add(ServerDto server)
    {
        serverDao.persist(server);
    }

    @Transactional
    public void addAll(Collection<ServerDto> servers)
    {
        for (ServerDto server : servers) {
            add(server);
        }
    }

    @Transactional(readOnly = true)
    public List<ServerDto> listAll() {
        return serverDao.findAll();
    }

    @Transactional
    public ServerDto findByHostname(String hostname) throws NoResultException
    {
        return serverDao.findByHostname(hostname);
    }

    public Long count() {
        return serverDao.count();
    }

    public int update(ServerDto server) {
        return serverDao.updateUsageByHostname(server.getHostname(), server.getUsage());
    }

    @Override
    public ServerDto findByExample(ServerDto example) {
        return serverDao.findByHostname(example.getHostname());
    }
}
