package org.sangraama.login.serverstart;

import java.util.Properties;
import javax.servlet.ServletContextEvent;

import javax.servlet.ServletContextListener;

import me.prettyprint.hector.api.Cluster;

import org.sangraama.login.constants.CommonDetails;
import org.sangraama.login.database.cassandra.CassandraAPI;
import org.sangraama.login.database.cassandra.CassandraConnection;

public class ServerStarter implements ServletContextListener {
    private Properties prop;

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
    }

    @Override
    public void contextInitialized(ServletContextEvent arg0) {

        this.prop = new Properties();
        try {
            this.prop.load(getClass().getResourceAsStream("/conf/loginserver.properties"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        CommonDetails.INSTANCE.setHost(prop.getProperty("host"));
//        Cluster cluster = CassandraConnection.createCluster();
//        CassandraAPI.generateKeySpace(cluster);
//        CassandraAPI.generateColumnFamily("UserDetails");

    }
}
