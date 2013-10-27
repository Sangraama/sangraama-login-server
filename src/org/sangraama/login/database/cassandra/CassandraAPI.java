package org.sangraama.login.database.cassandra;


import me.prettyprint.cassandra.serializers.StringSerializer;
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

public class CassandraAPI {
    private static Cluster cluster;
    private static Keyspace keyspace;
    
    public static void generateKeySpace(Cluster cluster){
       createKeyspace(cluster); 
    }
    public static void generateColumnFamily(String columnFamilyName){
        createColumnFamily(columnFamilyName);
     }
    
    private static void createKeyspace(Cluster cluster) {
        CassandraAPI.cluster=cluster;
        KeyspaceDefinition definition = new ThriftKsDef("SangraamaLoginKeyspace");
        cluster.addKeyspace(definition);
        keyspace = HFactory.createKeyspace("SangraamaLoginKeyspace", cluster);
    }
    
    private static void createColumnFamily(String columnFamilyName){
        ColumnFamilyDefinition familyDefinition = new ThriftCfDef(keyspace.getKeyspaceName(), columnFamilyName);
        cluster.addColumnFamily(familyDefinition);
    }
    
    public static void insert(String columnFamilyName,String columnName, String value){
   
         Mutator<String> mutator = HFactory.createMutator(keyspace, new StringSerializer());
         
         mutator.insert("keyone", columnFamilyName, HFactory.createStringColumn(columnName, value));
        
     }
    
    public static void retrieve(String username, String password, String host, String keySpace, String columnFamily, String ColumnName){
        
        Cluster cluster = CassandraConnection.createCluster(username,password, host);
        Keyspace keyspace = HFactory.createKeyspace(keySpace, cluster);
        ColumnQuery<String, String, String> columnQuery =
                HFactory.createStringColumnQuery(keyspace);
        columnQuery.setColumnFamily(columnFamily).setKey("keyone").setName(ColumnName);
        QueryResult<HColumn<String, String>> result = columnQuery.execute();
        HColumn<String, String> hColumn = result.get();
        System.out.println("Value : " + hColumn.getValue());
        System.out.println(cluster.getName());
        
        
    }
}
