const metroStations = {
    'Airport~B': {'Meenambakkam~B': 3},
    'Meenambakkam~B': {
        //'Nanganallur Road': 1,
        'Airport~B': 3,
        'Alandur~BG': 3
    },
    /*'Nanganallur Road': {
        'Alandur~BG': 1,
        'Meenambakkam~B': 1
    },*/
    'Alandur~BG': {
        'Meenambakkam~B': 3,
        //'Nanganallur Road': 1,
        'Ekkattuthangal~G': 1,
        'Guindy~B': 1,
        'St Thomas Mount~G': 3
    },
    'Guindy~B': {
        'Little Mount~B': 1,
        'Alandur~BG': 1
    },
    'Little Mount~B': {
        'Saidapet~B': 1,
        'Guindy~B': 1
    },
    'Saidapet~B': {
        //'Nandanam~B': 2,
        'Teynampet~B': 3,
        'Little Mount~B': 1
    },
    /*'Nandanam~B': {
        'Teynampet~B': 1,
        'Saidapet~B': 2
    },*/
    'Teynampet~B': {
        //'Nandanam~B': 1,
        'Saidapet~B': 3,
        'AG DMS~B': 1
    },
    'AG DMS~B': {
        'Thousand Lights~B': 2,
        'Teynampet~B': 1
    },
    'Thousand Lights~B': {
        'LIC~B': 1,
        'AG DMS~B': 2
    },
    'LIC~B': {
        //'Govt. Estate~B': 1,
        'Chennai Central~BGR': 3,
        'Thousand Lights~B': 1
    },
    /*'Govt. Estate~B': {
        'Chennai Central~B': 2,
        'LIC~B': 1
    },*/
    'Chennai Central~BGR': {
        'High Court~B': 3,
        //'Govt. Estate~B': 2
        'LIC~B': 3,
        'Egmore~G': 2,
        'Park Town~R': 4
    },
    'High Court~B': {
        'Mannadi~B': 1,
        'Chennai Central~BGR': 3
    },
    'Mannadi~B': {
        'Washermanpet~B': 2,
        'High Court~B': 1
    },
    'Washermanpet~B': {
        'Mannadi~B': 2
    },
    'St Thomas Mount~G': {
        'Alandur~BG': 3
    },
    'Ekkattuthangal~G': {
        'Alandur~BG': 2,
        'Ashok Nagar~G': 2
    },
    'Ashok Nagar~G': {
        'Ekkattuthangal~G': 2,
        'Vadapalani~G': 1
    },
    'Vadapalani~G': {
        'Ashok Nagar~G': 1,
        'CMBT~G': 3
    },
    'CMBT~G': {
        'Koyambedu~G': 2,
        'Vadapalani~G': 3,
    },
    'Koyambedu~G': {
        'Thirumangalam~G': 2,
        'CMBT~G': 2
    },
    'Thirumangalam~G':{
        'Anna Nagar Tower~G': 3,
        'Koyambedu~G': 2
    },
    'Anna Nagar Tower~G':{
        'Thirumangalam~G': 2,
        'Anna Nagar East~G': 2,
    },
    'Anna Nagar East~G':{
        'Anna Nagar Tower~G': 2,
        'Shenoy Nagar~G': 1
    },
    'Shenoy Nagar~G':{
        'Kilpauk~G': 2,
        'Anna Nagar East~G': 1
    },
    'Kilpauk~G':{
        'Egmore~G': 3,
        'Shenoy Nagar': 2
    },
    'Egmore~G': {
        'Chennai Central~BGR': 2,
        'Kilpauk~G': 3
    },
    'Park Town~R': {
        'Chepauk~R': 3,
        'Chennai Central~BGR': 4
    },
    'Chepauk~R': {
        'Park Town~R': 3,
        'Light House~R': 4
    },
    'Light House~R': {
        'Chepauk~R': 3,
        'Adyar~R': 4
    },
    'Adyar~R': {
        'Light House~R': 4,
        'Thiruvanmyur~RO': 3
    },
    'Thiruvanmyur~RO': {
        'Taramani~R': 3,
        'Thoraipakkam~O': 4,
        'Adyar~R': 3
    },
    'Taramani~R': {
        'Velachery~R': 2,
        'Thiruvanmyur~RO': 3
    },
    'Velachery~R':{'Taramani~R':2},
    'Thoraipakkam~O':{
        'Thiruvanmyur~RO': 4,
        'Perungudi~O': 5
    },
    'Perungudi~O':{
        'Thoraipakkam~O':5,
        'Shollinganallur~O': 4
    },
    'Shollinganallur~O':{
        'Perungudi~O': 4,
        'Perumbakkam~O': 3
    },
    'Perumbakkam~O': {
        'Shollinganallur~O': 3,
        'Medavakkam~O': 3
    },
    'Medavakkam~O':{
        'Perumbakkam~O': 3
    }
};

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

    const route = [];
    let current = destinationStation;
    while (current !== sourceStation) {
        route.unshift(current);
        current = path[current];
    }
    route.unshift(sourceStation);
    return route;
}