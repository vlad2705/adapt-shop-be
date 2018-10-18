package cz.cvut.fel.web.administration;

import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller
@Scope(value = "session")
@ELBeanName(value = "loginController")
@Join(path = "/admin/login", to = "/shop/login.jsf")
public class LoginController {}
