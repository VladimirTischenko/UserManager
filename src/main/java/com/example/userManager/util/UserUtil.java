package com.example.userManager.util;

import com.example.userManager.dao.User;
import com.example.userManager.to.UserTo;

import java.util.ArrayList;
import java.util.List;

public class UserUtil {
    public static UserTo convertToTo(User user) {
        return new UserTo(user);
    }

    public static Iterable<UserTo> convertCollectionToTo(Iterable<User> users) {
        List<UserTo> userTos = new ArrayList<>();
        for (User user : users) {
            userTos.add(convertToTo(user));
        }
        return userTos;
    }
}
