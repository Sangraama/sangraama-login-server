package org.sangraama.login.database.cassandra;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

//import org.apache.cassandra.utils.avro.UUID;
import java.util.UUID;

import org.sangraama.login.database.cassandra.dao.User;
import org.sangraama.login.database.cassandra.dao.UserImpl;

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
    private static Keyspace keyspace=null;

    private static StringSerializer stringSerializer = StringSerializer.get();
    private static List<String> keyList = new ArrayList<String>();
    private final static String USER = "UserDetails";
    
    private static Properties prop;

    // create the keyspace for cluster
    public static void generateKeySpace(Cluster cluster) {
        createKeyspace(cluster);
    }

    // create the desired columnfamily to store the data.
    public static void generateColumnFamily(String columnFamilyName) {
        createColumnFamily(columnFamilyName);
    }

    // create the keyspace for cluster
    private static void createKeyspace(Cluster cluster) {
        CassandraAPI.cluster = cluster;
        KeyspaceDefinition definition = new ThriftKsDef("LoginK");

        cluster.addKeyspace(definition);
        keyspace = HFactory.createKeyspace("LoginK", cluster);
    }

    // create a column family definition and add the column family to the cluster
    private static void createColumnFamily(String columnFamilyName) {
        ColumnFamilyDefinition familyDefinition = new ThriftCfDef(keyspace.getKeyspaceName(),
                columnFamilyName);
        cluster.addColumnFamily(familyDefinition);
    }

    // insert data into the columnfamily
    /*public  void insert(
            String columnList, String valueList) {
        prop = new Properties();
        try {
            prop.load(getClass().getResourceAsStream("/conf/loginserver.properties"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Cluster cluster = CassandraConnection.createCluster(prop.getProperty("host"));
        Keyspace keyspace = HFactory.createKeyspace("LoginK", cluster);
        Mutator<String> mutator = HFactory.createMutator(keyspace, new StringSerializer());

        String rowKey = null;
        // for (int i = 0; i < Integer.parseInt(rowCount); i++) {
      //  rowKey = UUID.randomUUID().toString();
       
        System.out.println("\nInserting Key " + rowKey + "To Column Family " + USER
                + "\n");
        String[] value = valueList.split(":");
        rowKey=value[1];
        keyList.add(rowKey);
        int i = 0;
        for (String columnName : columnList.split(":")) {
            mutator.insert(rowKey, USER,
                    HFactory.createStringColumn(columnName, value[i]));

            System.out.println("Column Name: " + columnName + " Value: " + value[i] + "\n");
            i++;
        }
        // }

    }*/
    
    public void create(User user){
        prop = new Properties();
        try {
            prop.load(getClass().getResourceAsStream("/conf/loginserver.properties"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Cluster cluster = CassandraConnection.createCluster();
        Keyspace keyspace = HFactory.createKeyspace("LoginK", cluster);
        Mutator<String> mutator = HFactory.createMutator(keyspace, new StringSerializer());
        String rowKey = null;
        System.out.println("\nInserting Key " + rowKey + "To Column Family " + USER
                + "\n");
        //String[] value = valueList.split(":");
        rowKey=user.getUserName();
        keyList.add(rowKey);
        mutator.insert(rowKey, USER,
                HFactory.createStringColumn("id", String.valueOf(user.getUserId())));
        System.out.println("Column Name: " + "id" + " Value: " + String.valueOf(user.getUserId()) + "\n");
        mutator.insert(rowKey, USER,
                HFactory.createStringColumn("username", user.getUserName()));
        System.out.println("Column Name: " + "username" + " Value: " + user.getUserName() + "\n");
        mutator.insert(rowKey, USER,
                HFactory.createStringColumn("password", user.getPassword()));
        System.out.println("Column Name: " + "password" + " Value: " + user.getPassword() + "\n");
        mutator.insert(rowKey, USER,
                HFactory.createStringColumn("x", String.valueOf(user.getX())));
        System.out.println("Column Name: " + "x" + " Value: " + String.valueOf(user.getX()) + "\n");
        mutator.insert(rowKey, USER,
                HFactory.createStringColumn("y", String.valueOf(user.getY())));
        System.out.println("Column Name: " + "y" + " Value: " + String.valueOf(user.getY()) + "\n");
        mutator.insert(rowKey, USER,
                HFactory.createStringColumn("angle", String.valueOf(user.getAngle())));
        System.out.println("Column Name: " + "angle" + " Value: " + String.valueOf(user.getAngle()) + "\n");
        mutator.insert(rowKey, USER,
                HFactory.createStringColumn("health", String.valueOf(user.getHealth())));
        System.out.println("Column Name: " + "health" + " Value: " + String.valueOf(user.getHealth()) + "\n");
        mutator.insert(rowKey, USER,
                HFactory.createStringColumn("score", String.valueOf(user.getScore())));
        System.out.println("Column Name: " + "score" + " Value: " + String.valueOf(user.getScore()) + "\n");
        mutator.insert(rowKey, USER,
                HFactory.createStringColumn("bullettype", String.valueOf(user.getBulletType())));
        System.out.println("Column Name: " + "bullettype" + " Value: " + String.valueOf(user.getBulletType()) + "\n");
        mutator.insert(rowKey, USER,
                HFactory.createStringColumn("shiptype", String.valueOf(user.getShipType())));
        System.out.println("Column Name: " + "shiptype" + " Value: " + String.valueOf(user.getShipType()) + "\n");
    }

  /*  // retrieve data from the column family.
    public static void retrieve(String host, String keySpace, String columnFamily, String ColumnName) {

        Cluster cluster = CassandraConnection.createCluster(host);
        Keyspace keyspace = HFactory.createKeyspace(keySpace, cluster);

        ColumnQuery<String, String, String> columnQuery = HFactory
                .createStringColumnQuery(keyspace);
        for (String key : keyList) {
            System.out.println("\nretrieving Key " + key + "From Column Family " + columnFamily
                    + "\n");
            columnQuery.setColumnFamily(columnFamily).setKey(key).setName(ColumnName);
            QueryResult<HColumn<String, String>> result = columnQuery.execute();
            HColumn<String, String> hColumn = result.get();
            System.out.println("Column: " + hColumn.getName() + " Value : " + hColumn.getValue()
                    + "\n");
        }

        // System.out.println("Value : " + hColumn.getValue());
        // System.out.println(cluster.getName());

    }*/
    
    public boolean isUserExists(String username, String password){
        prop = new Properties();
        try {
            prop.load(getClass().getResourceAsStream("/conf/loginserver.properties"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        Cluster cluster = CassandraConnection.createCluster();
        Keyspace keySpace = HFactory.createKeyspace("LoginK", cluster);
        SliceQuery<String, String, String> query = HFactory.createSliceQuery(keySpace, stringSerializer, stringSerializer, stringSerializer);
        query.setColumnFamily(USER).setKey(username).setColumnNames("id","username", "password","x","y","angle","health","score","bullettype","shiptype");
        query.setRange("", "", false, 10);
        List<HColumn<String, String>> columns = query.execute().get().getColumns();
        //System.out.println("**"+columns.get(2).getValue());
        if(columns.size()>0){
            
        
        if(username.equals(columns.get(7).getValue())&& password.equals(columns.get(4).getValue())){
            return true;
        }
        
        
        else{
            //System.out.println(columns.get(4).getValue()+":"+columns.get(7).getValue());
            return false;
        }
        }
        else return false;
       
    }
    
    public UserImpl getUserByUserNameAndPassWord(String username, String password){
        prop = new Properties();
        try {
            prop.load(getClass().getResourceAsStream("/conf/loginserver.properties"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Cluster cluster = CassandraConnection.createCluster();
        Keyspace keySpace = HFactory.createKeyspace("LoginK", cluster);
        SliceQuery<String, String, String> query = HFactory.createSliceQuery(keySpace, stringSerializer, stringSerializer, stringSerializer);
        query.setColumnFamily(USER).setKey(username).setColumnNames("id","username", "password","x","y","angle","health","score","bullettype","shiptype");
        query.setRange("", "", false, 10);
        List<HColumn<String, String>> columns = query.execute().get().getColumns();
        UserImpl user=new UserImpl();
        user.setAngle(Float.parseFloat(columns.get(0).getValue()));
        user.setBulletType(Integer.parseInt(columns.get(1).getValue()));
        user.setHealth(Integer.parseInt(columns.get(2).getValue()));
        user.setUserId(Integer.parseInt(columns.get(3).getValue()));
        user.setPassword(columns.get(4).getValue());
        user.setScore(Integer.parseInt(columns.get(5).getValue()));
        user.setShipType(Integer.parseInt(columns.get(6).getValue()));
        user.setUserName(columns.get(7).getValue());
        user.setX(Float.parseFloat(columns.get(8).getValue()));
        user.setY(Float.parseFloat(columns.get(9).getValue()));
        user.setType(1);
        
        return user;
        
    }
    
    

   /* public static void delete(String username, String password, String host, String keySpace,
            String key, String columnFamily, String ColumnName) {
        Cluster cluster = CassandraConnection.createCluster(host);
        Keyspace keyspace = HFactory.createKeyspace(keySpace, cluster);
        Mutator<String> mutator = HFactory.createMutator(keyspace, new StringSerializer());
        mutator.delete(key, columnFamily, ColumnName, stringSerializer);
        System.out.println("Data Deleted!!");
        ColumnQuery<String, String, String> columnQuery = HFactory
                .createStringColumnQuery(keyspace);
        columnQuery.setColumnFamily(columnFamily).setKey(key).setName(ColumnName);
        QueryResult<HColumn<String, String>> result = columnQuery.execute();
        HColumn<String, String> hColumn = result.get();

    }*/

}
