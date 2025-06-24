package com.proj.metro_app.dto;

import java.util.List;

public record DijkstraResult(int cost, List<String> path) {}