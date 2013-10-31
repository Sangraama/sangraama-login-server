package org.sangraama.login.database.cassandra.dao;

public interface User {

    public int getId();

    public String getUserName();

    public String getPassword();

    public float getX();

    public float getY();

    public float getAngle();

    public int getScore();

    public int getHealth();

    public int getBulletType();

    public int getShipType();
}