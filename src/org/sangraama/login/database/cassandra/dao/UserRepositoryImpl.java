package org.sangraama.login.database.cassandra.dao;

public class UserRepositoryImpl implements UserRepository {

    @Override
    public boolean isUserExists(String username, String password) {
        return false;

    }

    @Override
    public User getUserByUserNameAndPassWord(String username, String password) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void create(User user) {
        // TODO Auto-generated method stub

    }
}
