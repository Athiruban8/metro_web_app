function path(route) {
    let text = "";
    for (let i = 0; i < route.length - 1; i++) {
        if (route[i].localeCompare('Alandur~BG') === 0 || route[i].localeCompare('Chennai Central~BGR') === 0 || route[i].localeCompare('Thiruvanmyur~RO') === 0) {
            if (i - 1>=0 && i + 1 < route.length) {
                const p1 = route[i - 1].charAt(route[i - 1].length - 1);
                const p2 = route[i + 1].charAt(route[i + 1].length - 1);
                if (p1 != p2) {
                    text += "<br>Switch from ";
                    switch (p1) {
                        case 'R':
                            text += "Red Line to ";
                            break;
                        case 'B':
                            text += "Blue Line to ";
                            break;
                        case 'G':
                            text += "Green Line to ";
                            break;
                        case 'O':
                            text += "Orange Line to ";
                            break;
                    }
                    switch (p2) {
                        case 'R':
                            text += "Red Line";
                            break;
                        case 'B':
                            text += "Blue Line";
                            break;
                        case 'G':
                            text += "Green Line";
                            break;
                        case 'O':
                            text += "Orange Line";
                            break;
                    }
                    text += " at " + route[i] + " -> <br>";
                }
                else
                    text += route[i] + " -> ";
            }
            else
                text += route[i] + " -> ";
        }
        else
            text += route[i] + " -> ";
    }
    text += route[route.length - 1];
    return text;
}