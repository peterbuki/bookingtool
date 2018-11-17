package com.peterbuki.bookingtool.util;

import com.peterbuki.bookingtool.model.Server;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class UtilityTest {


    private Server server;

    @Before
    public void setUp() {
        server = new Server();
        server.setIp("1.2.3.4");
        server.setHostname("myserver");

        server.setTeam("ACS team");
        server.setContact("info@acs-team.none");

        server.setCluster("Main Tribe CI");
        server.setUsage("Jenkins master node");
        server.setComponent("Apache");
        server.setRelease("2.6.1");

        server.setStart(LocalDateTime.of(1990, 03, 31, 12, 0));
        server.setEnd(LocalDateTime.of(2020, 03, 31, 12, 0));
    }

    @Test
    public void testServerFormatter_ServerIsNull() {
        server = null;
        String result = Utility.serverFormatter(server, 80);

        String expectedLines =
                        "+----------------------------------Test v1.0-----------------------------------+\n" +
                        "|                  No information was found in the database.                   |\n" +
                        "+------------------------------------------------------------------------------+\n";

        assertEquals(expectedLines, result);
    }

    @Test
    public void testServerFormatter_ServerWithShortLines() {
        String resultLines = Utility.serverFormatter(server, 40);
        System.out.println(resultLines);

        String expectedLines =
                        "+--------------Test v1.0---------------+\n" +
                        "|    IP: 1.2.3.4 Hostname: myserver    |\n" +
                        "|            Team: ACS team            |\n" +
                        "|     Contact: info@acs-team.none      |\n" +
                        "|        Cluster: Main Tribe CI        |\n" +
                        "|      Usage: Jenkins master node      |\n" +
                        "|   Component: Apache Release: 2.6.1   |\n" +
                        "|Booking from 1990-03-31 to 2020-03-31 |\n" +
                        "+--------------------------------------+\n";
        assertEquals(expectedLines, resultLines);
    }


}