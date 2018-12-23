package com.company.demo.service;


import com.haulmont.cuba.security.entity.User;

import java.util.List;

public interface MyService {
    String NAME = "demo_MyService";

    List<User> loadAllUsers();
}