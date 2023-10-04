package com.example.shopapp.Services;

import com.example.shopapp.DTOS.UserDTO;
import com.example.shopapp.Exceptions.DataNotFoundException;
import com.example.shopapp.Models.Role;
import com.example.shopapp.Models.User;
import com.example.shopapp.Repositories.RoleRepository;
import com.example.shopapp.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    @Override
    public User createUser(UserDTO userDTO) throws DataNotFoundException {
        String phoneNumber = userDTO.getPhoneNumber();
        if (userRepository.existsByPhoneNumber(phoneNumber)) {
            throw new DataIntegrityViolationException("Phone number already exists");
        }

        User newUser = User.
                builder().
                fullName(userDTO.getFullName()).
                phoneNumber(userDTO.getPhoneNumber()).
                password(userDTO.getPassword()).
                address(userDTO.getAddress()).
                dateOfBirth(userDTO.getDateOfBirth()).
                facebookAccountId(userDTO.getFacebookAccountId()).
                googleAccountId(userDTO.getGoogleAccountId()).
                build();
        Role role = roleRepository.findById(userDTO.getRoleId())
                .orElseThrow(() -> new DataNotFoundException("Role not found !"));
        newUser.setRoleId(role);
        // Kiem tra neu co accountId, khong yeu cau password
        if (userDTO.getFacebookAccountId() == 0 && userDTO.getGoogleAccountId() == 0) {
            //String password = userDTO.getPassword();
            //newUser.setPassword(encodedPassword);
        }
        return userRepository.save(newUser);
    }

    @Override
    public String Login(String phoneNumber, String password) {

        return null;
    }
}
