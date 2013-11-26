package org.sangraama.login.tile.coordination;

import org.sangraama.login.constants.CommonDetails;

public enum SangraamaMap {
    INSTANCE;
    private float subTileWidth;
    private float subTileHeight;

    public float getSubTileWidth() {
        return subTileWidth;
    }

    public void setSubTileWidth(float subTileWidth) {
        this.subTileWidth = subTileWidth / CommonDetails.INSTANCE.getScale();
    }

    public float getSubTileHeight() {
        return subTileHeight;
    }

    public void setSubTileHeight(float subTileHeight) {
        this.subTileHeight = subTileHeight / CommonDetails.INSTANCE.getScale();
    }
}
