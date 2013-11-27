package org.sangraama.login.database.cassandra.dao;

public interface UserRepository {
    public boolean isUserExists(String username, String password);

    public User getUserByUserNameAndPassWord(String username, String password);

    public void create(User user);

    public void updateUser(User user);

}
