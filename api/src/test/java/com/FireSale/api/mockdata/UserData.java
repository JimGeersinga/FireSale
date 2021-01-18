package com.FireSale.api.mockdata;

import com.FireSale.api.model.Address;
import com.FireSale.api.model.Image;
import com.FireSale.api.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserData {
    public static List<User> getUsers()
    {
        Address address = new Address();
        address.setCity("Rotterdam");
        address.setCountry("Nederland");
        address.setHouseNumber("20");
        address.setPostalCode("0000AA");
        address.setStreet("Wijnhaven");
        address.setId(1L);
        address.setModified(LocalDateTime.now());
        Image avatar = new Image();
        //avatar.setPath("/avatar/test");
        avatar.setType(".jpg");
        avatar.setId(1L);
        List<User> users = new ArrayList<>();
        for(int i = 1; i <= 10; i++)
        {
            User user = new User();
            user.setId((long)i);
            user.setFirstName("First name " + i);
            user.setLastName("Last name " + i);
            user.setAddress(address);
            user.setIsLocked(false);
            user.setAvatar(avatar);
            users.add(user);
        }
        return users;
    }



}
