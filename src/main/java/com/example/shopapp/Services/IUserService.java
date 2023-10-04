package com.example.shopapp.Services;

import com.example.shopapp.DTOS.UserDTO;
import com.example.shopapp.Exceptions.DataNotFoundException;
import com.example.shopapp.Models.User;

public interface IUserService {
    User createUser(UserDTO userDTO) throws DataNotFoundException;
    String Login(String phoneNumber, String password);
}
