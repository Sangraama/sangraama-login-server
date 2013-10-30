package org.sangraama.login.database.cassandra.dao;

import java.util.List;

import org.sangraama.login.database.cassandra.CassandraAPI;

public class UserRepositoryImpl implements UserRepository{

    public UserRepositoryImpl(String userName,String password){
        LoginDetails loginDetails= new LoginDetails();
        loginDetails.setUsername(userName);
        loginDetails.setPassword(password);
        //CassandraAPI.insert(usernameCluster, passwordCluster, host, keySpace, columnFamilyName, columnName, loginDetails.getUsername());
    }
    
    
   


    @Override
    public List<LoginDetails> getAllLoginDetails() {
        return null;
    }


   


    @Override
    public void updateLoginDetails(LoginDetails loginDetails) {
        
    }





    @Override
    public void deleteLoginDetails(LoginDetails loginDetails) {
       //CassandraAPI.delete(usernameCluster, passwordCluster, host, keySpace, key, columnFamilyName, columnName);
        
    }





    @Override
    public LoginDetails getUserName(String userName) {
        // TODO Auto-generated method stub
        return null;
    }
}
