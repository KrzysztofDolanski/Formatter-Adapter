package com.example.formatter_adapter.services;

import com.example.formatter_adapter.entities.User;
import com.example.formatter_adapter.entities.UserDao;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.formatter_adapter.security.ApplicationUserRole.*;

@Repository("fake")
public class FakeUserDaoService implements UserDao {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public FakeUserDaoService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<User> selectUserByUsername(String username) {
        return getUsers()
                .stream()
                .filter(user -> username.equals(user.getUsername())).findFirst();
    }


    private List<User> getUsers() {
        List<User> users = new ArrayList<>();
        users.add(new User("krzysztof", passwordEncoder.encode("password"), true, true, true, true, STUDENT.getGrantedAuthorities()));
        users.add(new User("monika", passwordEncoder.encode("password"), true, true, true, true, ADMIN.getGrantedAuthorities()));
        users.add(new User("pawel", passwordEncoder.encode("password"), true, true, true, true, ADMINTREINEE.getGrantedAuthorities()));
        return users;
    }
}
