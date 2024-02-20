package me.zuif.rean.api.config.wrapper;

import java.util.List;

public class ConfigList<T> {
    private List<T> list;
    private String path;

    public ConfigList(String path, List<T> list) {
        this.list = list;
        this.path = path;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
