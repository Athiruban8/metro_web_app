package com.proj.metro_app;

public class Request {
    private String source;
    private String dest;
    private String choice;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }
    @Override
    public String toString() {
        return "Request{" +
                "source='" + source + '\'' +
                ", dest='" + dest + '\'' +
                ", choice='" + choice + '\'' +
                '}';
    }
}
