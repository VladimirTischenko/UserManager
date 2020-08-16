package com.example.userManager.to;

import com.example.userManager.dao.User;
import lombok.Data;

import java.util.Date;

@Data
public class UserTo {
    private Integer id;
    private String firstName;
    private String lastName;
    private boolean active;
    private Date created;
    private Date updated;

    public UserTo(User user) {
        id = user.getId();
        firstName = user.getFirstName();
        lastName = user.getLastName();
        active = user.isActive();
        created = user.getCreated();
        updated = user.getUpdated();
    }
}
