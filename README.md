# Chennai Metro Navigator

A full-stack web application for travelers to plan metro journeys in Chennai using shortest path algorithms. Users can view a static metro map and calculate the shortest route between two stations with a detailed path display. Built with **Spring Boot**, **Thymeleaf**, and **Vanilla JavaScript**.

## Features

- Display of Chennai Metro static map with highlighted stations
- Input source and destination stations using dropdown
- Automatically shows:
  - Expected **distance** (in kilometers)
  - Estimated **time** (in minutes)
  - Full **route** with interchanges between lines
- Dijkstra’s algorithm used for accurate and efficient routing
- Blinking effect on stations included in the selected route
- Fully backend-driven (Spring MVC + Thymeleaf)
- Graph data structure implementation for storing the map and routing

## Tech Stack

- **Backend**: Spring Boot, Java
- **Frontend**: Thymeleaf, HTML, CSS, Vanilla JavaScript
- **Algorithms**: Dijkstra's shortest path
- **Templating**: Thymeleaf (server-rendered HTML)

## How It Works

1. On application startup, the Chennai Metro network is loaded as a graph using hardcoded data.
2. Users select source and destination stations from a dropdown form.
3. Upon form submission:
   - Shortest **distance** is calculated using Dijkstra’s algorithm.
   - Shortest **time** is calculated including fixed transfer time and segment travel time.
   - The full **route** is shown with interchanges and endpoint labels.
   - Route stations are visually highlighted (blinking effect) on the static map.

## Running the Application

### Prerequisites

- Java 17+
- Maven

### Run Locally

```bash
git clone https://github.com/Athiruban8/metro-route-planner.git
cd metro-route-planner
mvn spring-boot:run
```
Then open your browser and visit:
http://localhost:8080

## Future Improvements

- Add a ticket fare calculator
- Expected train timings and scheduling integration
- Add travel history tracking
