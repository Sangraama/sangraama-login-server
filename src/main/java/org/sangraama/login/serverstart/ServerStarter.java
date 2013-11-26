package org.sangraama.login.serverstart;

import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.sangraama.login.constants.CommonDetails;
import org.sangraama.login.port.handle.PortLoader;
import org.sangraama.login.tile.coordination.SangraamaMap;

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
            CommonDetails.INSTANCE.setHost(this.prop.getProperty("host"));
            CommonDetails.INSTANCE.setScale(Integer.valueOf(this.prop.getProperty("scalingFactor")));
            SangraamaMap.INSTANCE.setSubTileWidth(Float.valueOf(this.prop.getProperty("subtilewidth")));
            SangraamaMap.INSTANCE.setSubTileHeight(Float.valueOf(this.prop.getProperty("subtileheight")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        PortLoader portLoader = new PortLoader();
        portLoader.ParsePortFile();
        // Cluster cluster = CassandraConnection.createCluster();
        // CassandraAPI.generateKeySpace(cluster);
        // CassandraAPI.generateColumnFamily("UserDetails");
        System.out.println("Login Server Started ... ");
    }
}
