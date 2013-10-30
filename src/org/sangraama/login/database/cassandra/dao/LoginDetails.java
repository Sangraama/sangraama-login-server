package org.sangraama.login.database.cassandra.dao;

public class LoginDetails {

    /**
     * @param args
     */
    private String username;
    private String password;
    //private String id;
   
   
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
