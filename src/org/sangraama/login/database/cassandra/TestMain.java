package org.sangraama.login.database.cassandra;

import me.prettyprint.hector.api.Cluster;

public class TestMain {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Cluster cluster=CassandraConnection.createCluster("denuwanthi", "denu123", "localhost");
        CassandraAPI.generateKeySpace(cluster);
        CassandraAPI.generateColumnFamily("logindetails");
        CassandraAPI.insert("logindetails", "username", "gamer1");
        CassandraAPI.retrieve("denuwanthi", "denu123", "localhost", "SangraamaLoginKeyspace", "logindetails", "username");
    }

}
