package org.sangraama.login.database.cassandra.dao;

public class ClientEvent {

    /**
     * @param args
     */
    private int type;
    private String username;
    private String password;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
