package com.proj.metro_app.service;

import com.proj.metro_app.dto.DijkstraResult;
import com.proj.metro_app.model.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MetroService {
    public MetroGraph g;

    public MetroService() {
        this.g = new MetroGraph();
        createMetroMap();
    }
    public void createMetroMap() {
        g.addNode("Airport~B");
        g.addNode("Meenambakkam~B");
        g.addNode("Alandur~BG");
        g.addNode("Guindy~B");
        g.addNode("Little Mount~B");
        g.addNode("Saidapet~B");
        g.addNode("Teynampet~B");
        g.addNode("AG DMS~B");
        g.addNode("Thousand Lights~B");
        g.addNode("LIC~B");
        g.addNode("Chennai Central~BGR");
        g.addNode("High Court~B");
        g.addNode("Mannadi~B");
        g.addNode("Washermanpet~B");
        g.addNode("St Thomas Mount~G");
        g.addNode("Ekkattuthangal~G");
        g.addNode("Ashok Nagar~G");
        g.addNode("Vadapalani~G");
        g.addNode("CMBT~G");
        g.addNode("Koyambedu~G");
        g.addNode("Thirumangalam~G");
        g.addNode("Anna Nagar Tower~G");
        g.addNode("Anna Nagar East~G");
        g.addNode("Shenoy Nagar~G");
        g.addNode("Kilpauk~G");
        g.addNode("Egmore~G");
        g.addNode("Velachery~R");
        g.addNode("Taramanni~R");
        g.addNode("Thiruvanmyur~RO");
        g.addNode("Adayar~R");
        g.addNode("Light House~R");
        g.addNode("Chepauk~R");
        g.addNode("Park Town~R");
        g.addNode("Medavakkam~O");
        g.addNode("Perumbakkam~O");
        g.addNode("Shollinganallur~O");
        g.addNode("Perungudi~O");
        g.addNode("Thoraipakkam~O");

        //Blue Line
        g.addEdge("Airport~B", "Meenambakkam~B", 8);
        g.addEdge("Meenambakkam~B", "Alandur~BG", 10);
        g.addEdge("Alandur~BG", "Guindy~B", 7);
        g.addEdge("Guindy~B", "Little Mount~B", 6);
        g.addEdge("Little Mount~B", "Saidapet~B", 5);
        g.addEdge("Saidapet~B", "Teynampet~B", 7);
        g.addEdge("Teynampet~B", "AG DMS~B", 4);
        g.addEdge("AG DMS~B", "Thousand Lights~B", 8);
        g.addEdge("Thousand Lights~B", "LIC~B", 4);
        g.addEdge("LIC~B", "Chennai Central~BGR", 8);

        //Green line
        g.addEdge("St Thomas Mount~G", "Alandur~BG", 7);
        g.addEdge("Alandur~BG", "Ekkattuthangal~G", 7);
        g.addEdge("Ekkattuthangal~G", "Ashok Nagar~G", 7);
        g.addEdge("Ashok Nagar~G", "Vadapalani~G", 7);
        g.addEdge("Vadapalani~G", "CMBT~G", 7);
        g.addEdge("CMBT~G", "Koyambedu~G", 7);
        g.addEdge("Koyambedu~G", "Thirumangalam~G", 7);
        g.addEdge("Thirumangalam~G", "Anna Nagar Tower~G", 7);
        g.addEdge("Anna Nagar Tower~G", "Anna Nagar East~G", 7);
        g.addEdge("Anna Nagar East~G", "Shenoy Nagar~G", 7);
        g.addEdge("Shenoy Nagar~G", "Kilpauk~G", 7);
        g.addEdge("Kilpauk~G", "Egmore~G", 7);
        g.addEdge("Egmore~G", "Chennai Central~BGR", 7);

        //Red line
        g.addEdge("Velachery~R", "Taramani~R", 7);
        g.addEdge("Taramani~R", "Thiruvanmyur~RO", 7);
        g.addEdge("Thiruvanmyur~RO", "Adayar~R", 7);
        g.addEdge("Adayar~R", "Light House~R", 7);
        g.addEdge("Light House~R", "Chepauk~R", 7);
        g.addEdge("Chepauk~R", "Park Town~R", 7);
        g.addEdge("Park Town~R", "Chennai Central~BGR", 7);

        //orange line
        g.addEdge("Medavakkam~O", "Perumbakkam~O", 7);
        g.addEdge("Perumbakkam~O", "Shollinganallur~O", 7);
        g.addEdge("Shollinganallur~O", "Perungudi~O", 7);
        g.addEdge("Perungudi~O", "Thoraipakkam~O", 7);
        g.addEdge("Thoraipakkam~O", "Thiruvanmyur~RO", 7);
    }

    public String displayStations() {
        List<String> stationNames = new ArrayList<>(g.nodes.keySet());
        Collections.sort(stationNames);
        StringBuilder sb = new StringBuilder();
        int i = 1;
        for (String name : stationNames) {
            sb.append(i++).append(". ").append(name).append("<br>");
        }
        return sb.toString();
    }

    public String displayMap() {
        StringBuilder sb = new StringBuilder();
        for (String key : g.nodes.keySet()) {
            sb.append(key).append(" =><br>");
            for (Map.Entry<String, Integer> entry : g.nodes.get(key).neighbours.entrySet()) {
                sb.append("&emsp;").append(entry.getKey()).append(" : ").append(entry.getValue()).append("<br>");
            }
            sb.append("<br>");
        }
        return sb.toString();
    }

    public List<String> getAllStationNames() {
        List<String> names = new ArrayList<>(g.nodes.keySet());
        Collections.sort(names);
        return names;
    }

    public String getDistance(String src, String dst) {
        DijkstraResult result = getShortestPath(src, dst, false);
        if (result.cost() == -1)
            return "No path from " + src + " to " + dst + "<br>";
        return "Shortest distance from " + src + " to " + dst + " is " + result.cost() + " KM<br>Path: " + result.path() + "<br>";
    }

    public String getTime(String src, String dst) {
        DijkstraResult result = getShortestPath(src, dst, true);
        if (result.cost() == -1)
            return "No path from " + src + " to " + dst + "<br>";
        return "Shortest time from " + src + " to " + dst + " is " + (result.cost() / 60) + " minutes<br>Path: " + result.path() + "<br>";
    }


    public DijkstraResult getShortestPath(String src, String dst, boolean timeMode) {
        class Node implements Comparable<Node> {
            String name;
            int cost;
            List<String> path;

            Node(String name, int cost, List<String> path) {
                this.name = name;
                this.cost = cost;
                this.path = path;
            }

            public int compareTo(Node other) {
                return Integer.compare(this.cost, other.cost);
            }
        }

        Map<String, Integer> dist = new HashMap<>();
        Set<String> visited = new HashSet<>();
        PriorityQueue<Node> pq = new PriorityQueue<>();

        for (String v : g.nodes.keySet()) {
            dist.put(v, Integer.MAX_VALUE);
        }

        dist.put(src, 0);
        pq.add(new Node(src, 0, new ArrayList<>(List.of(src))));

        while (!pq.isEmpty()) {
            Node curr = pq.poll();

            if (visited.contains(curr.name)) continue;
            visited.add(curr.name);

            if (curr.name.equals(dst)) {
                return new DijkstraResult(curr.cost, curr.path);
            }

            for (Map.Entry<String, Integer> nbr : g.nodes.get(curr.name).neighbours.entrySet()) {
                String neighbor = nbr.getKey();
                int edgeCost = timeMode ? (120 + 40 * nbr.getValue()) : nbr.getValue();
                int newCost = curr.cost + edgeCost;

                if (newCost < dist.get(neighbor)) {
                    dist.put(neighbor, newCost);
                    List<String> newPath = new ArrayList<>(curr.path);
                    newPath.add(neighbor);
                    pq.add(new Node(neighbor, newCost, newPath));
                }
            }
        }

        return new DijkstraResult(-1, List.of("No path found"));
    }


}
