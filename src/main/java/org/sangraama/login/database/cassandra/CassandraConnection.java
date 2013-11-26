package org.sangraama.login.database.cassandra;

import java.util.HashMap;
import java.util.Map;

import org.sangraama.login.constants.CommonDetails;

import me.prettyprint.cassandra.service.CassandraHostConfigurator;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.factory.HFactory;

public class CassandraConnection {
   
    private static final String USERNAME_KEY = "username";
    private static final String PASSWORD_KEY = "password";
    
    //create a cluster to store data on the host in port 9160
    public static Cluster createCluster() {

        Map<String, String> credentials =
                new HashMap<String, String>();
        credentials.put(USERNAME_KEY, "admin");
        credentials.put(PASSWORD_KEY, "admin");
        return HFactory.createCluster("SangraamaCluster2",
                new CassandraHostConfigurator(CommonDetails.INSTANCE.getHost()+":"+"9160"), credentials);
      
    }
   
}
