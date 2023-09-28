package com.clearsolution.testjob.Service;

import com.clearsolution.testjob.DTO.UserDTO;
import com.clearsolution.testjob.Entity.User;
import com.clearsolution.testjob.Exception.UserException;
import com.clearsolution.testjob.Mapper.UserMapper;
import com.clearsolution.testjob.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    @Value("${user.minAge}")
    private int userMinAge;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO getUserById(int id) {
         Optional<User> user = userRepository.findById(id);

         if(user.isEmpty()){
             throw new UserException("user was not found");
         }

         UserDTO userDTO = UserMapper.userToDto(user.get());

         return userDTO;

    }

    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();

        List<UserDTO> userDTOS = users
                .stream()
                .map(UserMapper::userToDto)
                .toList();

        return userDTOS;
    }

    public UserDTO postUser(UserDTO userDTO) throws IllegalAccessException {
        if(userDTO.getBirthDate().isAfter(LocalDateTime.now().minusYears(userMinAge))){
            throw new IllegalAccessException("user must be older 18 years old");
        }

        User user = UserMapper.dtoToUser(userDTO);

        user = userRepository.save(user);

        return UserMapper.userToDto(user);
    }

    public List<UserDTO> getUsersByBirthDateRange(LocalDateTime from, LocalDateTime to) throws IllegalAccessException {
        if(from.isAfter(to)){
            throw new IllegalAccessException("bat range format");
        }

        List<User> users = userRepository.findByBirthDateBetween(from, to);

        List<UserDTO> userDTOS = users
                .stream()
                .map(UserMapper::userToDto)
                .toList();

        return userDTOS;
    }

    public UserDTO updateUser(int id, UserDTO userDTO) {
        Optional<User> optionalUser = userRepository.findById(id);

        if(optionalUser.isEmpty()){
            throw new UserException("user was not found");
        }

        User user = optionalUser.get();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setBirthDate(userDTO.getBirthDate());
        user.setAddress(userDTO.getAddress());
        user.setPhoneNumber(userDTO.getPhoneNumber());

        userRepository.save(user);

        UserDTO userResponse = UserMapper.userToDto(user);

        return userResponse;
    }

    public void deleteAll() {
        userRepository.deleteAll();
    }

    public UserDTO deleteUserById(int id) {
        Optional<User> user = userRepository.findById(id);

        if(user.isEmpty()){
            throw new UserException("user was not found");
        }

        userRepository.delete(user.get());

        UserDTO userDTO = UserMapper.userToDto(user.get());

        return userDTO;
    }
}
