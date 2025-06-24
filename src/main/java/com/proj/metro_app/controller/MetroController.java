package com.proj.metro_app.controller;
import com.proj.metro_app.dto.DijkstraResult;
import com.proj.metro_app.dto.Request;
import com.proj.metro_app.service.MetroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MetroController {

    @Autowired
    private MetroService metroService;

    @GetMapping("/")
    public String metroFront(Model model) {
        model.addAttribute("stations", metroService.getAllStationNames());
        return "metro_front";
    }

    @PostMapping("/process")
    public String processRoute(@ModelAttribute Request request, Model model) {
        String source = request.getSource();
        String dest = request.getDest();

        DijkstraResult distanceResult = metroService.getShortestPath(source, dest, false);
        DijkstraResult timeResult = metroService.getShortestPath(source, dest, true);

        model.addAttribute("stations", metroService.getAllStationNames());
        model.addAttribute("source", source);
        model.addAttribute("dest", dest);
        model.addAttribute("distance", "Estimated distance: " + distanceResult.cost() + " KM");
        model.addAttribute("time", "Expected time: " + (timeResult.cost() / 60) + " minutes");
        model.addAttribute("path", distanceResult.path());

        return "metro_front";
    }

}
