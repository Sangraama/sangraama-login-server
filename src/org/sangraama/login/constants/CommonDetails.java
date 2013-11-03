package org.sangraama.login.constants;

public enum CommonDetails {
    INSTANCE;
    private String host;

    public void setHost(String host) {
        this.host = host;
    }

    public String getHost() {
        return this.host;
    }

}
