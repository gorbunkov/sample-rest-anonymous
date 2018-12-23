package com.company.demo.web.controllers;

import com.company.demo.service.MyService;
import com.haulmont.cuba.security.entity.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping("/api/{tenant}")
public class CustomerController {

    @Inject
    protected MyService myService;

    /**
     * This method requires OAuth authentication
     */
    @PostMapping(path = "/changePassword")
    public String changePassword(@PathVariable("tenant") String tenant) {
        return "changePassword - " + tenant;
    }

    /**
     * This method can be accessed with the REST API without authentication. An anonymous session is set to the security
     * context in the {@link com.company.demo.web.filter.MyAnonymousAuthenticationFilter}. After that middleware services
     * can be invoked within the method
     */
    @PostMapping(path = "/cart")
    public String cart(@PathVariable("tenant") String tenant) {
        List<User> users = myService.loadAllUsers();
        return String.valueOf(users.size());
    }

}
