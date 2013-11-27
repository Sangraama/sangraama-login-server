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


    //create a new user
    public void create(User user){
        prop = new Properties();
        try {
            prop.load(getClass().getResourceAsStream("/conf/loginserver.properties"));
        } catch (IOException e) {
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
        mutator.insert(rowKey, USER,
                HFactory.createStringColumn("username", user.getUserName()));
        mutator.insert(rowKey, USER,
                HFactory.createStringColumn("password", user.getPassword()));
        mutator.insert(rowKey, USER,
                HFactory.createStringColumn("x", String.valueOf(user.getX())));
        mutator.insert(rowKey, USER,
                HFactory.createStringColumn("y", String.valueOf(user.getY())));
        mutator.insert(rowKey, USER,
                HFactory.createStringColumn("angle", String.valueOf(user.getAngle())));
        mutator.insert(rowKey, USER,
                HFactory.createStringColumn("health", String.valueOf(user.getHealth())));
        mutator.insert(rowKey, USER,
                HFactory.createStringColumn("score", String.valueOf(user.getScore())));
        mutator.insert(rowKey, USER,
                HFactory.createStringColumn("bullettype", String.valueOf(user.getBulletType())));
        mutator.insert(rowKey, USER,
                HFactory.createStringColumn("shiptype", String.valueOf(user.getShipType())));



        //for the purpose of validating the created user
        SliceQuery<String, String, String> query = HFactory.createSliceQuery(keyspace, stringSerializer, stringSerializer, stringSerializer);
        query.setColumnFamily(USER).setKey(rowKey).setColumnNames("id","username", "password","x","y","angle","health","score","bullettype","shiptype");
        query.setRange("", "", false, 10);
        List<HColumn<String, String>> columns = query.execute().get().getColumns();
        System.out.println("############################################################");
        System.out.println("Angle="+columns.get(0).getValue());
        System.out.println("Bullet="+columns.get(1).getValue());
        System.out.println("Health="+columns.get(2).getValue());
        System.out.println("Userid="+columns.get(3).getValue());
        System.out.println("Password="+columns.get(4).getValue());
        System.out.println("Score="+columns.get(5).getValue());
        System.out.println("Ship="+columns.get(6).getValue());
        System.out.println("UserName="+columns.get(7).getValue());
        System.out.println("X="+columns.get(8).getValue());
        System.out.println("Y="+columns.get(9).getValue());

        //System.out.println("inside create user, cassandra");


    }

    //check whether a user already exists or not
    public boolean isUserExists(String username, String password){
        prop = new Properties();
        try {
            prop.load(getClass().getResourceAsStream("/conf/loginserver.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.println("Inside is userexists");
        Cluster cluster = CassandraConnection.createCluster();
        Keyspace keySpace = HFactory.createKeyspace("LoginK", cluster);
        SliceQuery<String, String, String> query = HFactory.createSliceQuery(keySpace, stringSerializer, stringSerializer, stringSerializer);
        query.setColumnFamily(USER).setKey(username).setColumnNames("id","username", "password","x","y","angle","health","score","bullettype","shiptype");
        query.setRange("", "", false, 10);
        List<HColumn<String, String>> columns = query.execute().get().getColumns();

        if(columns.size()>0){

        if(username.equals(columns.get(7).getValue())&& password.equals(columns.get(4).getValue())){
           // System.out.println("user exists, cassandra checked.");
            /*UserImpl testuser=new UserImpl();
            testuser.setAngle(10);
            testuser.setBulletType(1);
            testuser.setHealth(200);
            testuser.setUserId(Integer.parseInt(columns.get(3).getValue()));
            testuser.setPassword(columns.get(4).getValue());
            testuser.setScore(100);
            testuser.setShipType(2);
            testuser.setUserName(columns.get(7).getValue());
            testuser.setX(2500);
            testuser.setY(500);
            updateUser(testuser);*/
            return true;
        }
        
        
        else{

            return false;
        }
        }
        else return false;
       
    }

    //retrieve a user by username and password
    public UserImpl getUserByUserNameAndPassWord(String username, String password){
        prop = new Properties();
        try {
            prop.load(getClass().getResourceAsStream("/conf/loginserver.properties"));
        } catch (IOException e) {
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


    /*update existing user details*/
    public void updateUser(User user){

        Cluster cluster = CassandraConnection.createCluster();
        Keyspace keySpace = HFactory.createKeyspace("LoginK", cluster);
        Mutator<String> mutator = HFactory.createMutator(keySpace, new StringSerializer());
        String rowKey=user.getUserName();

       mutator.insert(rowKey, USER,
               HFactory.createStringColumn("id", String.valueOf(user.getUserId())));
       mutator.insert(rowKey, USER,
                HFactory.createStringColumn("username", user.getUserName()));
       mutator.insert(rowKey, USER,
                HFactory.createStringColumn("password", user.getPassword()));
       mutator.insert(rowKey, USER,
                HFactory.createStringColumn("x", String.valueOf(user.getX())));
       mutator.insert(rowKey, USER,
                HFactory.createStringColumn("y", String.valueOf(user.getY())));
       mutator.insert(rowKey, USER,
                HFactory.createStringColumn("angle", String.valueOf(user.getAngle())));
       mutator.insert(rowKey, USER,
                HFactory.createStringColumn("health", String.valueOf(user.getHealth())));
       mutator.insert(rowKey, USER,
                HFactory.createStringColumn("score", String.valueOf(user.getScore())));
       mutator.insert(rowKey, USER,
                HFactory.createStringColumn("bullettype", String.valueOf(user.getBulletType())));
       mutator.insert(rowKey, USER,
                HFactory.createStringColumn("shiptype", String.valueOf(user.getShipType())));

        ////for the purpose of validating the created user
        SliceQuery<String, String, String> query = HFactory.createSliceQuery(keySpace, stringSerializer, stringSerializer, stringSerializer);
        query.setColumnFamily(USER).setKey(rowKey).setColumnNames("id","username", "password","x","y","angle","health","score","bullettype","shiptype");
        query.setRange("", "", false, 10);
        List<HColumn<String, String>> columns = query.execute().get().getColumns();
        System.out.println("############################################################");
        System.out.println("Angle="+columns.get(0).getValue());
        System.out.println("Bullet="+columns.get(1).getValue());
        System.out.println("Health="+columns.get(2).getValue());
        System.out.println("Userid="+columns.get(3).getValue());
        System.out.println("Password="+columns.get(4).getValue());
        System.out.println("Score="+columns.get(5).getValue());
        System.out.println("Ship="+columns.get(6).getValue());
        System.out.println("UserName="+columns.get(7).getValue());
        System.out.println("X="+columns.get(8).getValue());
        System.out.println("Y="+columns.get(9).getValue());


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
