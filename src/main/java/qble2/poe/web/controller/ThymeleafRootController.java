package qble2.poe.web.controller;

import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import qble2.poe.web.ThymeleafInitForm;

// This application serves as a proxy to an external API
// There is no user to register, nor a credential to save
// Essential user information are simply stored in session during the session's lifetime
@Controller
@RequestMapping("/")
public class ThymeleafRootController {

  @GetMapping
  public String index(Model model, HttpSession session) {
    String poeSessionId = (String) session.getAttribute("poeSessionId");
    if (StringUtils.isNotBlank(poeSessionId)) {
      return "index-signedin";
    }

    ThymeleafInitForm thymeleafInitForm = new ThymeleafInitForm();
    model.addAttribute("initForm", thymeleafInitForm);

    return "index";
  }

  @PostMapping
  public String index(@ModelAttribute(value = "initForm") ThymeleafInitForm thymeleafInitForm,
      Model model, HttpSession session) {
    if (!isValid(thymeleafInitForm)) {
      // TODO BKE errors/validation
      return "index";
    }

    session.setAttribute("accountName", thymeleafInitForm.getAccountName());
    session.setAttribute("poeSessionId", thymeleafInitForm.getPoeSessionId());

    return "index-signedin";
  }

  @GetMapping("/exit")
  public String exit(Model model, HttpSession session) {
    session.invalidate();

    ThymeleafInitForm thymeleafInitForm = new ThymeleafInitForm();
    model.addAttribute("initForm", thymeleafInitForm);

    return "redirect:/";
  }

  /////
  /////
  /////

  // TODO BEK check valid information by pinging GGG (ex: get stash tabs)
  private boolean isValid(ThymeleafInitForm thymeleafInitForm) {
    return StringUtils.isNotBlank(thymeleafInitForm.getAccountName())
        && StringUtils.isNotBlank(thymeleafInitForm.getPoeSessionId());
  }

}
