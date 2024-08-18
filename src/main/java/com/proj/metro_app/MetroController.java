package com.proj.metro_app;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MetroController {
    private Graph_M g;

    public MetroController() {
        g = new Graph_M();
        com.proj.metro_app.Graph_M.Create_Metro_Map(g);
    }
    @GetMapping("/")
    public String metro_front(Model model){
        model.addAttribute("stations", "");
        return "metro_front";
    }
    @PostMapping("/process")
    public String op(@ModelAttribute Request request, Model model)
    {
        String choice = request.getChoice();
        String source = request.getSource();
        String dest = request.getDest();
        String result = "";
        if ("1".equals(choice)) {
            result = g.display_Stations();
            model.addAttribute("stations",result);
        }
        if("2".equals(choice)){
            result = g.display_Map();
            model.addAttribute("map",result);
        }
        if("3".equals(choice)){
            result = g.distance(g,source,dest);
            model.addAttribute("distance",result);
        }
        if("4".equals(choice)){
            result = g.time(g,source,dest);
            model.addAttribute("time",result);
        }
            /*if ("5".equals(choice)){
                result = g.shortest_path(source,dest,g);
                model.addAttribute("path",result);
            }*/
        return "metro_front";
    }
}
