package com.clearsolution.testjob.service;

import com.clearsolution.testjob.DTO.UserDTO;
import com.clearsolution.testjob.Entity.User;
import com.clearsolution.testjob.Exception.UserException;
import com.clearsolution.testjob.Mapper.UserMapper;
import com.clearsolution.testjob.Repository.UserRepository;
import com.clearsolution.testjob.Service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;

    private User user;
    private UserDTO userDTO;

    @BeforeEach
    public void init() {
        user = User
                .builder()
                .firstName("Erik")
                .lastName("EEEE")
                .email("erik@mail.com")
                .birthDate(LocalDateTime.of(1999, 9, 3, 0, 0, 0))
                .address("street")
                .phoneNumber("123456789")
                .build();

        userDTO = UserMapper.userToDto(user);
    }

    @Test
    public void userSaveTest() throws IllegalAccessException {
        when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

        UserDTO savedUserDto = userService.postUser(userDTO);

        assertThat(savedUserDto).isNotNull();
        assertThat(savedUserDto).isEqualTo(userDTO);
    }

    @Test
    public void userFindByIdTest() {

        when(userRepository.findById(1)).thenReturn(Optional.ofNullable(user));

        UserDTO returnedUserDto = userService.getUserById(1);

        assertThat(returnedUserDto).isNotNull();
        assertThat(returnedUserDto).isEqualTo(userDTO);
    }

    @Test
    public void userFindAllTest() {

        when(userRepository.findAll()).thenReturn(List.of(user));

        List<UserDTO> returnedUserDTOs = userService.getAllUsers();

        assertThat(returnedUserDTOs).isNotNull();
        assertThat(returnedUserDTOs.size()).isEqualTo(1);
        assertThat(returnedUserDTOs.contains(userDTO)).isTrue();
    }

    @Test
    public void userExceptionTest() {

        Assertions.assertThrows(UserException.class, () -> userService.getUserById(1));
        Assertions.assertThrows(UserException.class, () -> userService.updateUser(1, new UserDTO()));
    }

    @Test
    public void illegalArgumentExceptionTest() {

        Assertions.assertThrows(IllegalAccessException.class, () -> userService.getUsersByBirthDateRange(
                LocalDateTime.of(2002, 2, 2, 0, 0, 0),
                LocalDateTime.of(2001, 1, 1, 0, 0, 0)
        ));
    }
}
