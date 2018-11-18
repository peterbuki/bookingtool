package com.peterbuki.bookingtool.model;

import org.junit.Test;

import static com.peterbuki.bookingtool.util.Utility.generateServer;
import static org.junit.Assert.*;

public class ServerDtoTest {


    @Test
    public void testToString() {
        ServerDto server = new ServerDto();

        server.setId(123);
        server.setType("HP DL380 G7");
        server.setHostname("myserver1");
        server.setIp("10.11.12.13");
        server.setContact("info@contact.com");
        server.setTeam("A-Team");
        server.setCluster("Main Cluster");
        server.setComponent("Acme");
        server.setRelease("v12.0.1");
        server.setUsage("CI cluster");

        String serverString = server.toString();
        String expected = "ServerDto id=123, type='HP DL380 G7', hostname='myserver1', ip='10.11.12.13', " +
                "contact='info@contact.com', team='A-Team', cluster='Main Cluster', component='Acme', " +
                "release='v12.0.1', usage='CI cluster'";
        assertEquals(expected, serverString);

        // "ServerDto id=%d, type='%s', hostname='%s', ip='%s', contact='%s', team='%s', " +
        //                "cluster='%s', component='%s', release='%s', usage='%s'"
    }

    @Test
    public void equalsTest() {
        ServerDto s1 = generateServer("alma");
        ServerDto s2 = generateServer("alma");

        assertEquals(s1, s2);
        s2.setUsage("ho-ho-ho");
        assertNotEquals(s1, s2);
    }

}