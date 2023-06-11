package kz.inflation.InflationApp.controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorsController implements ErrorController {

    @RequestMapping("/error")
    public String error(HttpServletRequest request, Model model){
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if(status != null){
            int code = Integer.parseInt(status.toString());
            if (code == HttpStatus.NOT_FOUND.value()){
                model.addAttribute("mainTitle", "404. NOT FOUND");
                model.addAttribute("textTitle", "Oops! Страница не найдена.");
                model.addAttribute("textDescription", "Страница которую вы искали, не найдена");
                model.addAttribute("code", HttpStatus.NOT_FOUND.value());
                return "errors/404";
            } else if (code == HttpStatus.BAD_REQUEST.value()) {
                model.addAttribute("mainTitle", "400. BAD REQUEST");
                model.addAttribute("textTitle", "Oops! Неверный запрос.");
                model.addAttribute("textDescription", "Передан неверный запрос");
                model.addAttribute("code", HttpStatus.BAD_REQUEST.value());
                return "errors/404";
            }
        }

        return "errors/404";
    }
}
