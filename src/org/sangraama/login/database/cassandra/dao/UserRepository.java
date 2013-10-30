package org.sangraama.login.database.cassandra.dao;

import java.util.List;

public interface UserRepository {
    public List<LoginDetails> getAllLoginDetails();
    public LoginDetails getUserName(String userName);
 
    
    public void updateLoginDetails(LoginDetails loginDetails);
    public void deleteLoginDetails(LoginDetails loginDetails);
    
}
