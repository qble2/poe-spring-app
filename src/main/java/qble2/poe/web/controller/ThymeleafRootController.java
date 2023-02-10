package qble2.poe.web.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import qble2.poe.web.ThymeleafLoginForm;

@Controller
@RequestMapping
public class ThymeleafRootController {

  @GetMapping(path = "/")
  public String index(Model model) {
    return "index";
  }

  @GetMapping(path = "/login")
  public String getLogin(@RequestParam(name = "error", required = false) String error,
      @RequestParam(name = "logout", required = false) String logout,
      @RequestParam(name = "expired", required = false) String expired,
      @RequestParam(name = "invalid", required = false) String invalid, Model model) {
    ThymeleafLoginForm thymeleafLoginForm = new ThymeleafLoginForm();
    model.addAttribute("loginForm", thymeleafLoginForm);

    return "login";
  }

  @GetMapping(path = "/logout")
  public String logout(Model model, HttpServletRequest request) throws ServletException {
    request.logout();

    return "redirect:/login?logout";
  }

}
