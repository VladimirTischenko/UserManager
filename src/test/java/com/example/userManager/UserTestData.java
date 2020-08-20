package com.example.userManager;

import com.example.userManager.dao.User;
import com.example.userManager.to.UserDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserTestData {
    public static final int USER_TO_1_ID = 1;
    public static final int USER_TO_10_ID = 10;

    public static final UserDTO USER_TO_1 = new UserDTO(1, "V", "T", true,
            LocalDateTime.of(2020, 8, 13, 10, 14), null);
    public static final UserDTO USER_TO_2 = new UserDTO(2, "K", "V", false,
            LocalDateTime.of(2020, 8, 13, 15, 2),
            LocalDateTime.of(2020, 8, 18, 16, 42, 43));
    public static final UserDTO USER_TO_3 = new UserDTO(3, "D", "UpdatedLastName", true,
            LocalDateTime.of(2020, 8, 13, 14, 36, 23),
            LocalDateTime.of(2020, 8, 13, 14, 53, 32));
    public static final UserDTO USER_TO_4 = new UserDTO(4, "K", "Tis", false,
            LocalDateTime.of(2020, 8, 13, 15, 14, 33), null);
    public static final UserDTO USER_TO_5 = new UserDTO(5, "Jul", "Korol", true,
            LocalDateTime.of(2020, 8, 14, 10, 36, 47),
            LocalDateTime.of(2020, 8, 14, 10, 41, 6));
    public static final UserDTO USER_TO_6 = new UserDTO(6, "Pavel", "Skripnik", true,
            LocalDateTime.of(2020, 8, 16, 16, 43, 8),
            LocalDateTime.of(2020, 8, 16, 16, 43, 31));
    public static final UserDTO USER_TO_7 = new UserDTO(7, "Igor", "S", false,
            LocalDateTime.of(2020, 8, 19, 6, 44, 5), null);

    public static final List<UserDTO> USER_TOS = Arrays.asList(USER_TO_1, USER_TO_2, USER_TO_3, USER_TO_4, USER_TO_5,
            USER_TO_6, USER_TO_7);

    public static List<UserDTO> getUserTosByFirstName(String s) {
        ArrayList<UserDTO> userDTOs = new ArrayList<>();
        for (UserDTO userDTO : USER_TOS) {
            if (s.equals(userDTO.getFirstName())) {
                userDTOs.add(userDTO);
            }
        }
        return userDTOs;
    }

    public static List<UserDTO> getUserTosByLastName(String s) {
        ArrayList<UserDTO> userDTOs = new ArrayList<>();
        for (UserDTO userDTO : USER_TOS) {
            if (s.equals(userDTO.getLastName())) {
                userDTOs.add(userDTO);
            }
        }
        return userDTOs;
    }

    public static List<UserDTO> getUserTosByFirstNameAndLastName(String firstName, String lastName) {
        ArrayList<UserDTO> userDTOs = new ArrayList<>();
        for (UserDTO userDTO : USER_TOS) {
            if (firstName.equals(userDTO.getFirstName()) && lastName.equals(userDTO.getLastName())) {
                userDTOs.add(userDTO);
            }
        }
        return userDTOs;
    }

    public static User getNew() {
        return new User(null, "newUserFN", "newUserLN", "somePass",
                false, null, null);
    }

    public static User getForUpdate() {
        User user = getNew();
        user.setFirstName("UpdatedFirstName");
        user.setLastName("UpdatedLastName");
        user.setPassword("1a1dc91c907325c69271ddf0c944bc72");
        return user;
    }
}
