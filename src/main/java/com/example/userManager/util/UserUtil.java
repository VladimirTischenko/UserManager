package com.example.userManager.util;

import com.example.userManager.dao.User;
import com.example.userManager.to.UserDTO;

import java.util.ArrayList;
import java.util.List;

public class UserUtil {
    public static UserDTO convertToTo(User user) {
        return new UserDTO(user);
    }

    public static Iterable<UserDTO> convertCollectionToTo(Iterable<User> users) {
        List<UserDTO> userDTOs = new ArrayList<>();
        for (User user : users) {
            userDTOs.add(convertToTo(user));
        }
        return userDTOs;
    }
}
