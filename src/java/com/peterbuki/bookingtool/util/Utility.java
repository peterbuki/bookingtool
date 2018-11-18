package com.peterbuki.bookingtool.util;

import com.peterbuki.bookingtool.model.ServerDto;
import org.apache.commons.lang.StringUtils;

public class Utility {


    public static String serverFormatter(ServerDto server, int columns) {
        StringBuilder serverString = new StringBuilder();
        appendHeader(serverString, "Test v1.0", columns);

        if (server != null) {
            appendLine(serverString,
                    String.format("IP: %s", server.getIp()),
                    String.format("Hostname: %s", server.getHostname()), columns);
            appendLine(serverString,
                    String.format("Team: %s", server.getTeam()),
                    String.format("Contact: %s", server.getContact()), columns);
            appendLine(serverString,
                    String.format("Cluster: %s", server.getCluster()),
                    String.format("Usage: %s", server.getUsage()), columns);
            appendLine(serverString,
                    String.format("Component: %s", server.getComponent()),
                    String.format("Release: %s", server.getRelease()), columns);
            appendLine(serverString,
                    String.format("Booking from %1tF to %2tF", server.getStart(), server.getEnd()), columns);
        } else {
            appendLine(serverString, "No information was found in the database.", columns);
        }
        appendHeader(serverString, "", columns);
        return serverString.toString();
    }


    private static StringBuilder appendHeader(StringBuilder builder, String data, int columns) {
        return builder.append("+").append(StringUtils.center(data, columns - 2, "-")).append("+\n");
    }

    private static StringBuilder appendLine(StringBuilder builder, String data1, String data2, int columns) {
        if (data1.length() + data2.length() < columns - 2) {
            return appendLine(builder, data1 + " " + data2, columns);
        } else {
            appendLine(builder, data1, columns);
            return appendLine(builder, data2, columns);
        }
    }

    private static StringBuilder appendLine(StringBuilder builder, String data, int columns) {
        return builder.append("|").append(StringUtils.center(data, columns - 2)).append("|\n");
    }

    private static Integer id = 0;

    public static ServerDto generateServer(String hostname) {
        ServerDto server = new ServerDto();

        server.setId(++id);
        server.setType("HP DL380 G7");
        server.setHostname(hostname);
        server.setIp("10.11.12.0");
        server.setContact("info@contact.com");
        server.setTeam("A-Team");
        server.setCluster("Main Cluster");
        server.setComponent("Acme");
        server.setRelease("v12.0.1");
        server.setUsage("CI cluster");

        return server;
    }

}
