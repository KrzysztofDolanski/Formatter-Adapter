package com.example.formatter_adapter.entities;

import java.util.Optional;

public interface UserDao {

    Optional<User> selectUserByUsername(String username);
}
