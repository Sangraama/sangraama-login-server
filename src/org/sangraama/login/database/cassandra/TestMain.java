package org.sangraama.login.database.cassandra;

import me.prettyprint.hector.api.Cluster;

public class TestMain {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
   //  Cluster cluster=CassandraConnection.createCluster("localhost");
    // CassandraAPI.generateKeySpace(cluster);
     //CassandraAPI.generateColumnFamily("userdetails");
        CassandraAPI.insert("localhost", "SangraamaLoginKey", "userdetails", "id:username:password:x:y:angle:health:score:bullettype:shiptype","1:denuwanthi:denu123:100:100:30:100:120:1:2","1");
        CassandraAPI.retrieve("localhost", "SangraamaLoginKey", "userdetails", "username");
    }

}
