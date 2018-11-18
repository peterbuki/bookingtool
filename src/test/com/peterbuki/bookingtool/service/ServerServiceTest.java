package com.peterbuki.bookingtool.service;

import com.peterbuki.bookingtool.model.ServerDto;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import javax.persistence.EntityExistsException;

import java.util.Arrays;

import static org.junit.Assert.*;

public class ServerServiceTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private static Integer id = 0;
    private static ServerService serverService;
    private static ClassPathXmlApplicationContext ctx;

    private ServerDto generateServer(String hostname) {
        ServerDto server = new ServerDto();

        server.setId(++id);
        server.setType("HP DL380 G7");
        server.setHostname(hostname);
        server.setIp("10.11.12." + id);
        server.setContact("info@contact.com");
        server.setTeam("A-Team");
        server.setCluster("Main Cluster");
        server.setComponent("Acme");
        server.setRelease("v12.0.1");
        server.setUsage("CI cluster");

        return server;
    }

    @BeforeClass
    public static void setUp() {
        //Create Spring application context
        ctx = new ClassPathXmlApplicationContext("file:src/spring.xml");

        //Get service from context. (service's dependency (ProductDAO) is autowired in ProductService)
        serverService = ctx.getBean(ServerService.class);
    }

    @Test
    public void testAdd() {
        //Do some data operation
        int initialNumberOfServers = serverService.listAll().size();

        serverService.add(generateServer("first"));
        serverService.add(generateServer("second"));

        System.out.println("listAll: " + serverService.listAll());

        assertEquals("Two servers should have been added.",
                initialNumberOfServers + 2, serverService.listAll().size());
    }

    @Test
    public void testAddAll() {
        //Test transaction rollback (duplicated key)
        int initialNumberOfServers = serverService.listAll().size();

        expectedException.expect(EntityExistsException.class);

        ServerDto server1 = generateServer("legal");
        ServerDto server2 = generateServer("illegal2");
        server2.setId(server1.getId());

        serverService.addAll(Arrays.asList(server1, server2));

        assertEquals("One servers should have been added.",
                initialNumberOfServers+1, serverService.listAll().size());
    }

    @Test
    public void findByHostname() {
        ServerDto server;
        id=10;
        for (int i = 11; i<=20; i++)
        {
            server = generateServer("host" + i);
            serverService.add(server);
        }

        server = serverService.findByHostname("host15");
        assertEquals("10.11.12.15", server.getIp());
    }

    @Test
    @Ignore
    public void addALotOfRecords() {
        final int recordCountToBeAdded = 1000;
        final int initialRecordCount = serverService.listAll().size();
        Long startTime = System.currentTimeMillis();
        ServerDto server;
        for (int i=1; i<=recordCountToBeAdded; i++)
        {
            server = generateServer("host" + i);
            serverService.add(server);
        }
        Long addedTime = System.currentTimeMillis();
        int recordCount = serverService.listAll().size();
        Long countedTime = System.currentTimeMillis();
        assertEquals(recordCountToBeAdded + " records should have been added.",
                recordCountToBeAdded, recordCount-initialRecordCount);

        System.out.format("Added in %d ms, listed in %d ms, %d records/s.\n",
                addedTime-startTime, countedTime-addedTime, Math.round(recordCountToBeAdded*1000/(addedTime-startTime)));

    }

    @AfterClass
    public static void afterClass()
    {
        ctx.close();
    }

}