package kz.inflation.InflationApp.controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.http.HttpStatus;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorsController implements ErrorController {

    @RequestMapping("/error")
    public String error(HttpServletRequest request){
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null && Integer.parseInt(status.toString()) == HttpStatus.SC_NOT_FOUND){
            return "errors/404";
        }

        return "error";
    }
}
