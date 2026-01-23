package com.workorderhub.core.entity;

/**
 * Represents the access credentials associated with en employee.
 */
public class Credentials {

    private int accessId;
    private String name;
    private String accessKey;

    public Credentials() {
    }

    public Credentials(String name, String key) {
        this.name = name;
        this.accessKey = key;
    }

    public int getAccessId() {
        return accessId;
    }

    public void setAccessId(int accessId) {
        this.accessId = accessId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }
}
