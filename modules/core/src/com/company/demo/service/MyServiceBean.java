package com.company.demo.service;

import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.security.entity.User;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service(MyService.NAME)
public class MyServiceBean implements MyService {

    @Inject
    protected DataManager dataManager;

    @Override
    public List<User> loadAllUsers() {
        return dataManager.load(User.class)
                .query("select u from sec$User u")
                .list();
    }
}