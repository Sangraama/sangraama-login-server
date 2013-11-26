package org.sangraama.login.constants;

public enum CommonDetails {
    INSTANCE;
    private String host;
    private int scale;

    private CommonDetails(){
        this.host = "localhost";
        this.scale = 32;
    }
    public void setHost(String host) {
        this.host = host;
    }

    public String getHost() {
        return this.host;
    }

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }
}
