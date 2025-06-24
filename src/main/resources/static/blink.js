function startBlinking(stationId) {
    const stationDot = document.getElementById(stationId);
    if (stationDot) stationDot.style.animation = 'blink 1s infinite';
}

function stopBlinking(stationId) {
    const stationDot = document.getElementById(stationId);
    if (stationDot) stationDot.style.animation = 'none';
}
