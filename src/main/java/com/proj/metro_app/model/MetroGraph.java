package com.proj.metro_app.model;

import java.util.*;

public class MetroGraph {
    public HashMap<String, Node> nodes = new HashMap<>();

    public void addNode(String name) {
        nodes.put(name, new Node());
    }

    public void addEdge(String src, String dest, int weight) {
        if (nodes.containsKey(src) && nodes.containsKey(dest)) {
            nodes.get(src).neighbours.put(dest, weight);
            nodes.get(dest).neighbours.put(src, weight);
        }
    }

}
