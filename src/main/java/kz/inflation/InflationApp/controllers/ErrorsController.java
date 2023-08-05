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
            } else if (code == HttpStatus.UNAUTHORIZED.value()) {
                model.addAttribute("mainTitle", "401. Unauthorized");
                model.addAttribute("textTitle", "Oops! Вы не авторизованы");
                model.addAttribute("textDescription", "Авторизуйтесь и попробуйте снова");
                model.addAttribute("code", HttpStatus.UNAUTHORIZED.value());
                return "errors/404";
            } else if (code == HttpStatus.FORBIDDEN.value()) {
                model.addAttribute("mainTitle", "403. Forbidden");
                model.addAttribute("textTitle", "Oops! Запрос устарел");
                model.addAttribute("textDescription", "Повторите попытку или обратитесь к Администратору");
                model.addAttribute("code", HttpStatus.FORBIDDEN.value());
                return "errors/404";
            } else if (code == HttpStatus.CONFLICT.value()) {
                model.addAttribute("mainTitle", "409. Conflict");
                model.addAttribute("textTitle", "Oops! Конфликт запроса");
                model.addAttribute("textDescription", "Обнаружен конфликт версий/запроса");
                model.addAttribute("code", HttpStatus.CONFLICT.value());
                return "errors/404";
            } else if (code == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                model.addAttribute("mainTitle", "500. Internal Server Error");
                model.addAttribute("textTitle", "Oops! Что-то пошло не так, но мы не знаем, что именно");
                model.addAttribute("textDescription", "Повторите попытку или обратитесь к Администратору");
                model.addAttribute("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
                return "errors/404";
            } else if (code == HttpStatus.SERVICE_UNAVAILABLE.value()) {
                model.addAttribute("mainTitle", "503. Unavailable");
                model.addAttribute("textTitle", "Oops! Что-то пошло не так");
                model.addAttribute("textDescription", "Сервер временно не отвечает");
                model.addAttribute("code", HttpStatus.SERVICE_UNAVAILABLE.value());
                return "errors/404";
            }
        }

        return "errors/404";
    }
}
