package org.sangraama.login.database.cassandra;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

//import org.apache.cassandra.utils.avro.UUID;
import java.util.UUID;

import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.serializers.UUIDSerializer;
import me.prettyprint.cassandra.service.ThriftCfDef;
import me.prettyprint.cassandra.service.ThriftKsDef;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.beans.ColumnSlice;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.beans.OrderedRows;
import me.prettyprint.hector.api.beans.Row;
import me.prettyprint.hector.api.ddl.ColumnFamilyDefinition;
import me.prettyprint.hector.api.ddl.KeyspaceDefinition;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.mutation.Mutator;
import me.prettyprint.hector.api.query.ColumnQuery;
import me.prettyprint.hector.api.query.QueryResult;
import me.prettyprint.hector.api.query.RangeSlicesQuery;
import me.prettyprint.hector.api.query.SliceQuery;

public class CassandraAPI {
    private static Cluster cluster;
    private static Keyspace keyspace;
    private static StringSerializer stringSerializer = StringSerializer.get();
    private static List<String> keyList = new ArrayList<String>();
    //private static  String rowKey = null;
  //create the keyspace for cluster
    public static void generateKeySpace(Cluster cluster){
       createKeyspace(cluster); 
    }
    
    //create the desired columnfamily to store the data.
    public static void generateColumnFamily(String columnFamilyName){
        createColumnFamily(columnFamilyName);
     }
    
    //create the keyspace for cluster
    private static void createKeyspace(Cluster cluster) {
        CassandraAPI.cluster=cluster;
        KeyspaceDefinition definition = new ThriftKsDef("SangraamaLoginKey");
        cluster.addKeyspace(definition);
        keyspace = HFactory.createKeyspace("SangraamaLoginKey", cluster);
    }
    
    //create a column family definition and add the column family to the cluster
    private static void createColumnFamily(String columnFamilyName){
        ColumnFamilyDefinition familyDefinition = new ThriftCfDef(keyspace.getKeyspaceName(), columnFamilyName);
        cluster.addColumnFamily(familyDefinition);
    }
    
    //insert data into the columnfamily
    public static void insert( String host,String keySpace,String columnFamilyName,String columnList, String valueList,String rowCount){
        Cluster cluster = CassandraConnection.createCluster(host);
        Keyspace keyspace = HFactory.createKeyspace(keySpace, cluster);
        Mutator<String> mutator = HFactory.createMutator(keyspace, new StringSerializer());
        
        
        String rowKey = null;
       // for (int i = 0; i < Integer.parseInt(rowCount); i++) {
            rowKey = UUID.randomUUID().toString();
            keyList.add(rowKey);
            System.out.println("\nInserting Key " + rowKey + "To Column Family " + columnFamilyName + "\n");
            String[] value=valueList.split(":");
            int i=0;
            for (String columnName : columnList.split(":")) {
                mutator.insert(rowKey, columnFamilyName, HFactory.createStringColumn(columnName, value[i]));
                
                System.out.println("Column Name: " + columnName + " Value: " + value[i] + "\n");
                i++;
            }
      //  }
        
        
        
     }
    
    //retrieve data from the column family.
    public static void retrieve( String host, String keySpace, String columnFamily, String ColumnName){
        
        Cluster cluster = CassandraConnection.createCluster( host);
        Keyspace keyspace = HFactory.createKeyspace(keySpace, cluster);
       
                
        ColumnQuery<String, String, String> columnQuery =
                HFactory.createStringColumnQuery(keyspace);
        for (String key : keyList) {
            System.out.println("\nretrieving Key " + key + "From Column Family " + columnFamily + "\n");  
            columnQuery.setColumnFamily(columnFamily).setKey(key).setName(ColumnName);
            QueryResult<HColumn<String, String>> result = columnQuery.execute();
            HColumn<String, String> hColumn = result.get();
            System.out.println("Column: " + hColumn.getName() + " Value : " + hColumn.getValue() + "\n");
        }
        
        
        
        
       // System.out.println("Value : " + hColumn.getValue());
       // System.out.println(cluster.getName());
        
        
    }
    
    
    public static void delete(String username, String password, String host, String keySpace, String key, String columnFamily, String ColumnName){
        Cluster cluster = CassandraConnection.createCluster(host);
        Keyspace keyspace = HFactory.createKeyspace(keySpace, cluster);
        Mutator<String> mutator = HFactory.createMutator(keyspace, new StringSerializer());
        mutator.delete(key,columnFamily,ColumnName, stringSerializer);
        System.out.println("Data Deleted!!");
        ColumnQuery<String, String, String> columnQuery =
                HFactory.createStringColumnQuery(keyspace);
        columnQuery.setColumnFamily(columnFamily).setKey(key).setName(ColumnName);
        QueryResult<HColumn<String, String>> result = columnQuery.execute();
        HColumn<String, String> hColumn = result.get();
    }
    
    
    
    
    
}
