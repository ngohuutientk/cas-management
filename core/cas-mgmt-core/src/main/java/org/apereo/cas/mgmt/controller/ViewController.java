package org.apereo.cas.mgmt.controller;

import org.apereo.cas.authentication.principal.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import org.pac4j.core.context.J2EContext;
import org.pac4j.core.profile.ProfileManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

/**
 * Contoller for rendered views.
 *
 * @author Travis Schmidt
 * @since 6.0
 */
@Controller("viewController")
@RequiredArgsConstructor
@Slf4j
public class ViewController {

    private static final String STATUS = "status";

    private final Service defaultService;

    /**
     * Mapped method to return the manage.html.
     *
     * @return - ModelAndView
     */
    @GetMapping({"management/", "management", "management/index.html"})
    public ModelAndView manage() {
        val model = new HashMap<String, Object>();
        model.put(STATUS, HttpServletResponse.SC_OK);
        model.put("defaultServiceUrl", this.defaultService.getId());
        return new ModelAndView("management/index", model);
    }

    /**
     * Mapped method to return the dashboard entry point.
     *
     * @return - ModelAndView
     */
    @GetMapping({"dashboard/index.html", "dashboard/", "dashboard"})
    public ModelAndView dashboard() {
        val model = new HashMap<String, Object>();
        model.put(STATUS, HttpServletResponse.SC_OK);
        return new ModelAndView("dashboard/index", model);
    }

    /**
     * Root mapping that navigates to managment or register depending on user role.
     *
     * @param request - the request
     * @param response - the response
     * @return - ModelAndView
     */
    @GetMapping({"cas-management", "/", "index.html"})
    public ModelAndView root(final HttpServletRequest request, final HttpServletResponse response) {
        var url = request.getContextPath() + "/management";
        return new ModelAndView(new RedirectView(url));
    }


    /**
     * Authorization failure handling. Simply returns the view name.
     *
     * @return the view name.
     */
    @GetMapping(value = "/authorizationFailure")
    public String authorizationFailureView() {
        return "authorizationFailure";
    }

    /**
     * Logout handling. Simply returns the view name.
     *
     * @param request  the request
     * @param response the response
     * @return the view name.
     */
    @GetMapping(value = "/logout.html")
    public String logoutView(final HttpServletRequest request, final HttpServletResponse response) {
        LOGGER.debug("Invalidating application session...");
        new ProfileManager(new J2EContext(request, response)).logout();
        request.getSession(false).invalidate();
        return "logout";
    }
}
