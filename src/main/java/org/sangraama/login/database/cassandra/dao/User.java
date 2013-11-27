package org.sangraama.login.database.cassandra.dao;

public interface User {

    public int getType();

    public int getUserId();

    public String getUserName();

    public String getPassword();

    public float getX();

    public float getY();

    public float getAngle();

    public int getScore();

    public int getHealth();

    public int getBulletType();

    public int getShipType();

    public String getServerUrl();

    public void setServerUrl(String serverUrl);
}
