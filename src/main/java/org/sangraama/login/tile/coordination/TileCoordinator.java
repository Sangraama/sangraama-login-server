package org.sangraama.login.tile.coordination;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.sangraama.login.constants.CommonDetails;

import java.util.Map;

public enum TileCoordinator {
    INSTANCE;
    private HazelcastInstance hazelcastInstance;
    SangraamaMap sangraamaMap;

    Map<String, String> subtileMap;

    TileCoordinator() {
        sangraamaMap = SangraamaMap.INSTANCE;
        hazelcastInstance = Hazelcast.newHazelcastInstance(new Config());
        this.subtileMap = hazelcastInstance.getMap("subtile");
    }

    public String getSubTileHost(float x, float y) {
        x = x / CommonDetails.INSTANCE.getScale();
        y = y / CommonDetails.INSTANCE.getScale();

        String host = "";
        float subTileOriginX = x - (x % sangraamaMap.getSubTileWidth());
        float subTileOriginY = y - (y % sangraamaMap.getSubTileHeight());
        host = subtileMap.get(Float.toString(subTileOriginX) + ":"
                + Float.toString(subTileOriginY));

        return host;
    }
}
