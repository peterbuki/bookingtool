package com.peterbuki.bookingtool.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Server {

    @Id
    private Integer id;

    private String type;
    private String hostname;
    private String ip;
    private String contact;
    private String team;
    private String cluster;
    private String component;
    private String release;
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

    public String toString() {
        return String.format("Server id=%d, type='%s', hostname='%s', ip='%s', contact='%s', team='%s', " +
                "cluster='%s', component='%s', release='%s', usage='%s'",
                id, type, hostname, ip, contact, team, cluster, component, release, usage);
    }
}