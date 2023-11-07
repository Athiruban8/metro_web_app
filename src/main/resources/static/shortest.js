const metroStations = {
    'Airport': {'Meenambakkam': 3},
    'Meenambakkam': {
        'Nanganallur Road': 1,
        'Airport': 3
    },
    'Nanganallur Road': {
        'Alandur': 1,
        'Meenambakkam': 1
    },
    'Alandur': {
        'Nanganallur Road': 1,
        'Ekkaduthangal': 1,
        'Guindy': 1
    },
    'Guindy': {
        'Little Mount': 1,
        'Alandur': 1
    },
    'Little Mount': {
        'Saidapet': 1,
        'Guindy': 1
    },
    'Saidapet': {
        'Nandanam': 2,
        'Little Mount': 1
    },
    'Nandanam': {
        'Teynampet': 1,
        'Saidapet': 2
    },
    'Teynampet': {
        'Nandanam': 1,
        'AG-DMS': 1
    },
    'AG-DMS': {
        'Thousand Lights': 2,
        'Teynampet': 1
    },
    'Thousand Lights': {
        'LIC': 1,
        'AG-DMS': 2
    },
    'LIC': {
        'Govt. Estate': 1,
        'Thousand Lights': 1
    },
    'Govt. Estate': {
        'Chennai Central': 2,
        'LIC': 1
    },
    'Chennai Central': {
        'High Court': 3,
        'Govt. Estate': 2
    },
    'High Court': {
        'Mannadi': 1,
        'Chennai Central': 3
    },
    'Mannadi': {
        'Washermenpet': 2,
        'High Court': 1
    }
};
// Function to calculate the shortest route and fare
function calculate() {
    // Get the source and destination stations from the dropdowns
    const sourceStation = document.getElementById('source').value;
    const destinationStation = document.getElementById('dest').value;

    // Check if source and destination are selected
    if (sourceStation === '' || destinationStation === '') {
        alert('Please select source and destination stations.');
        return;
    }

    // Dijkstra's algorithm implementation to find the shortest route and fare
    const stations = Object.keys(metroStations);
    const INF = Number.MAX_SAFE_INTEGER;

    // Create a distance matrix and initialize with Infinity
    const distances = {};
    stations.forEach((station) => (distances[station] = INF));
    distances[sourceStation] = 0;

    const visited = {};
    const path = {};

    while (true) {
        let currentStation = null;

        // Find the nearest station
        stations.forEach((station) => {
            if (
                !visited[station] &&
                (currentStation === null ||
                    distances[station] < distances[currentStation])
            ) {
                currentStation = station;
            }
        });

        if (currentStation === null || distances[currentStation] === INF) {
            break;
        }

        visited[currentStation] = true;

        // Update distances to adjacent stations
        for (const neighbor in metroStations[currentStation]) {
            const distance =
                distances[currentStation] + metroStations[currentStation][neighbor];
            if (distance < distances[neighbor]) {
                distances[neighbor] = distance;
                path[neighbor] = currentStation;
            }
        }
    }

    // Build the route and calculate the fare
    const route = [];
    let current = destinationStation;
    while (current !== sourceStation) {
        route.unshift(current);
        current = path[current];
    }
    route.unshift(sourceStation);

    const fare = distances[destinationStation];

    // Display the results
    return route.join(' -> ');
    //document.getElementById('route').textContent = route.join(' -> ');
    //document.getElementById('fare').textContent = fare + ' units'; // You can replace 'units' with your currency
}