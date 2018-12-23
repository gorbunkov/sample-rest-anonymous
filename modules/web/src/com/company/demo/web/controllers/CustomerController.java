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

    @PostMapping(path = "/changePassword")
    public String changePassword(@PathVariable("tenant") String tenant) {
        return "changePassword - " + tenant;
    }

    @PostMapping(path = "/cart")
    public String cart(@PathVariable("tenant") String tenant) {
        List<User> users = myService.loadAllUsers();
        return String.valueOf(users.size());
    }

}
