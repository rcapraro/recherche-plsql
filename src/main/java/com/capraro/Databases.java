package com.capraro;

public enum Databases {

    REPLICAT("jdbc:oracle:thin:@10.1.1.109:1521:DBREP", "REPLICAT (ancienne infra)"),
    NI_DEV1("jdbc:oracle:thin:@10.1.1.88:1521:DBD01", "NI-DEV1 (nouvelle infra)"),
    NI_DEV2("jdbc:oracle:thin:@10.1.1.88:1521:DBD02", "NI-DEV2 (nouvelle infra)");

    private String url;

    private String alias;

    Databases(final String url, final String alias) {
        this.url = url;
        this.alias = alias;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(final String alias) {
        this.alias = alias;
    }
}
