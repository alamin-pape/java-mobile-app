package com.kasucsc.cscmessenger;

public class Upload {
    public String name;
    public String url;

    public Upload(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return this.name;
    }

    public String getUrl() {
        return this.url;
    }
}
