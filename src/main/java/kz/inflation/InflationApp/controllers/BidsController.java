package kz.inflation.InflationApp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/bids")
public class BidsController {

    @GetMapping()
    public String getBids(){
        return "bids/bids";
    }
}
