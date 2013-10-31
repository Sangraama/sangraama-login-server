package org.sangraama.login.database.cassandra;

import me.prettyprint.hector.api.Cluster;

public class TestMain {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

       // Cluster cluster=CassandraConnection.createCluster("localhost");
       // CassandraAPI.generateKeySpace(cluster);
       // CassandraAPI.generateColumnFamily("UserDetails");
      /* CassandraAPI.insert(
                "id:username:password:x:y:angle:health:score:bullettype:shiptype",
                "1:denuwanthi:denu123:100:100:30:100:120:1:2");
     CassandraAPI.insert(
               "id:username:password:x:y:angle:health:score:bullettype:shiptype",
               "2:amila:amila123:100:100:30:100:120:1:2");*/
        //CassandraAPI.retrieve("localhost", "SangraamaLoginKey", "userdetails", "username");
        CassandraAPI c=new CassandraAPI();
       boolean b= c.isUserExists("denuwanthi", "denu123");
       if(b==true){
           System.out.println("User exists!!");
       }
       else
           System.out.println("No user!!");

    }

}
