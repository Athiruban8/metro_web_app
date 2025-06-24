function display_path(route) {
    let html = '<div style="display: flex; flex-wrap: wrap; gap: 8px; align-items: center; font-family: Arial;">';
    const junctions = ['Alandur~BG', 'Chennai Central~BGR', 'Thiruvanmyur~RO'];

    function getLineColor(code) {
        const colors = {
            'R': '#e74c3c', // Red
            'B': '#3498db', // Blue
            'G': '#27ae60', // Green
            'O': '#e67e22'  // Orange
        };
        return colors[code] || '#7f8c8d';
    }

    function getLineName(code) {
        const names = {
            'R': 'Red Line',
            'B': 'Blue Line',
            'G': 'Green Line',
            'O': 'Orange Line'
        };
        return names[code] || 'Unknown Line';
    }

    for (let i = 0; i < route.length; i++) {
        const current = route[i];
        const next = route[i + 1];
        const prev = route[i - 1];
        const [name, lineCodes] = current.split('~');
        const currentLine = lineCodes.slice(-1); // Get last line code

        // Determine if this is a junction
        const isJunction = junctions.includes(current);
        let displayColor = getLineColor(currentLine);
        let label = name;

        // Junction handling
        if (isJunction) {
            if (prev && next) {
                const prevLine = prev.split('~')[1].slice(-1);
                const nextLine = next.split('~')[1].slice(-1);

                if (prevLine !== nextLine) {
                    label += ` (Switch from ${getLineName(prevLine)} to ${getLineName(nextLine)})`;
                }
                displayColor = getLineColor(nextLine);
            }
            else if (next) {
                const nextLine = next.split('~')[1].slice(-1);
                displayColor = getLineColor(nextLine);
            }
            else{
                const prevLine = prev.split('~')[1].slice(-1);
                displayColor = getLineColor(prevLine);
            }
        }

        html += `<div style="background:${displayColor}; color:white; padding:4px 12px; border-radius:16px;">
            ${label}
        </div>`;

        // Add arrow if not last station
        if (i < route.length - 1) {
            html += '<div style="color:#666; padding:0 4px;">→</div>';
        }
    }

    html += '</div>';
    return html;
}