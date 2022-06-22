package it.valeriovaudi.onlyoneportal.accountservice.web.endpoint;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebSiteController {

    private final String rpUiHost;
    private final String vauthenticatorHost;

    public WebSiteController(@Value("${vauthenticator.session-management.rp-iframe.host}") String rpUiHost,
                             @Value("${vauthenticator.host}") String vauthenticatorHost) {
        this.rpUiHost = rpUiHost;
        this.vauthenticatorHost = vauthenticatorHost;
    }


    @GetMapping("/site/index.html")
    public String view(Model model) {
        model.addAttribute("rpSessionManagementIFrame", rpUiHost + "/session/management");
        model.addAttribute("opSessionManagementIFrame", vauthenticatorHost + "/session/management");

        return "site/index";
    }
}
