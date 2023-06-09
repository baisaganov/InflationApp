package kz.inflation.InflationApp.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("")
public class MainContorller {

    @GetMapping
    public String index(){
        return "index";
    }

    @GetMapping("/inflation-report")
    public String inflationView(){
        return "inflation";
    }

    @ResponseBody
    @GetMapping("/loaderio-cde0d7d9e356aae02237dd2d08b73072/")
    public String verify(){
        return "loaderio-cde0d7d9e356aae02237dd2d08b73072";
    }
}
