package com.peterbuki.bookingtool.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@JsonIgnoreProperties({ "id" })
@JsonPropertyOrder(value = { "Type", "Host", "IP Address", "Contact", "Team", "Start", "End", "Cluster", "Component", "Release", "Usage"})
public class Server extends Dto{

    @Id
    private Integer id;

    @JsonProperty(value = "Type")
    private String type;
    @JsonProperty(value = "Host")
    private String hostname;
    @JsonProperty(value = "IP Address")
    private String ip;
    @JsonProperty(value = "Contact")
    private String contact;
    @JsonProperty(value = "Team")
    private String team;
    @JsonProperty(value = "Start")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime start;
    @JsonProperty(value = "End")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime end;
    @JsonProperty(value = "Cluster")
    private String cluster;
    @JsonProperty(value = "Component")
    private String component;
    @JsonProperty(value = "Release")
    private String release;
    @JsonProperty(value = "Usage")
    private String usage;

    //Type, Host, IP Address, Contact, Team, Start, End,
    //Apollo 4200 Gen9, seliics01196, 10.216.186.225, Red Tribe, Red Tribe (Ishy), 2017-09-01 00:00:00, 2018-11-30 23:59:00,

    // Cluster, Component, Release, Usage
    // Red: Red/Orange Tribe CI, ARK, 17.0.1, shared Red Tribe/Orange CI w/vmx-eea208; ARK; 45+ ppl; 17.0.1 GA 11/30/17

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getCluster() {
        return cluster;
    }

    public void setCluster(String cluster) {
        this.cluster = cluster;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return String.format("Server id=%d, type='%s', hostname='%s', ip='%s', contact='%s', team='%s', " +
                "cluster='%s', component='%s', release='%s', usage='%s'",
                id, type, hostname, ip, contact, team, cluster, component, release, usage);
    }

    @Override
    public boolean equals(Object object) {
        Server server = (Server) object;
        return type.equals(server.type) && hostname.equals(server.hostname) && ip.equals(server.ip) &&
                contact.equals(server.contact) && team.equals(server.team) && cluster.equals(server.cluster) &&
                component.equals(server.component) && release.equals(server.release) && usage.equals(server.usage);
    }
}
