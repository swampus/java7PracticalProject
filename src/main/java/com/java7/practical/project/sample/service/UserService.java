package com.java7.practical.project.sample.service;

import com.java7.practical.project.sample.model.User;
import com.java7.practical.project.sample.repository.UserRepository;
import org.h2.util.StringUtils;

import java.util.Date;
import java.util.List;

public class UserService {
    private final UserRepository userRepository = new UserRepository();

    public List<User> getAllUser(){
        return userRepository.getlAllUser();
    }

    public String addUser(String userName, String firstname,
                          String lastname, String email,
                          Date birth, String addrres) {

        //user validator
        if (StringUtils.isNullOrEmpty(userName)
                || StringUtils.isNullOrEmpty(firstname)
                || StringUtils.isNullOrEmpty(lastname)
                || StringUtils.isNullOrEmpty(addrres)
                || birth == null) {
            return "WRONG DATA, Correct input";
        }

       //UserFactory :: Factory pattern
        User user = new User();
        user.setAddress(addrres);
        user.setDateOfBirth(birth);
        user.setEmail(email);
        user.setFirstname(firstname);
        user.setUsername(userName);
        user.setLastname(lastname);

        long insertedUserId = userRepository.saveUser(user).getId();
        return "USER with ID: " + insertedUserId + " CREATED !";

    }

}
