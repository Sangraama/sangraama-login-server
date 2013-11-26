package org.sangraama.login.database.cassandra.dao;

import org.sangraama.login.database.cassandra.CassandraAPI;

public class UserRepositoryImpl implements UserRepository {
    private CassandraAPI cassandraAPI;

    public UserRepositoryImpl() {
        cassandraAPI = new CassandraAPI();
    }

    @Override
    public boolean isUserExists(String username, String password) {
        return cassandraAPI.isUserExists(username, password);

    }

    @Override
    public User getUserByUserNameAndPassWord(String username, String password) {
        return cassandraAPI.getUserByUserNameAndPassWord(username, password);
    }

    @Override
    public void create(User user) {
        cassandraAPI.create(user);

    }
}
