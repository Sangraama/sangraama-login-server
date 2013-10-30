package org.sangraama.login.database.cassandra;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.service.CassandraHostConfigurator;
import me.prettyprint.cassandra.service.ThriftCfDef;
import me.prettyprint.cassandra.service.ThriftKsDef;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.ddl.ColumnFamilyDefinition;
import me.prettyprint.hector.api.ddl.KeyspaceDefinition;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.mutation.Mutator;
import me.prettyprint.hector.api.query.ColumnQuery;
import me.prettyprint.hector.api.query.QueryResult;

final class CassandraConnection {
   
    private static final String USERNAME_KEY = "username";
    private static final String PASSWORD_KEY = "password";
 
    
    //create a cluster to store data on the host in port 9160
    public static Cluster createCluster(String host) {

        Map<String, String> credentials =
                new HashMap<String, String>();
        credentials.put(USERNAME_KEY, "admin");
        credentials.put(PASSWORD_KEY, "admin");
        return HFactory.createCluster("SangraamaClusterTwo",
                new CassandraHostConfigurator(host+":"+"9160"), credentials);
      
    }

}
