package com.clearsolution.testjob.repository;

import com.clearsolution.testjob.Entity.User;
import com.clearsolution.testjob.Repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    public void init() {
        user = User
                .builder()
                .firstName("Erik")
                .lastName("EEEE")
                .email("erik@mail.com")
                .birthDate(LocalDateTime.of(2003, 9, 3, 0, 0, 0))
                .address("street")
                .phoneNumber("123456789")
                .build();
    }

    @Test
    public void userSaveTest(){

        User savedUser = userRepository.save(user);

        Assertions.assertNotNull(savedUser);
        Assertions.assertTrue(savedUser.getId() > 0);

    }

    @Test
    public void userFindAllTest() {

        User user2 = User
                .builder()
                .firstName("Erik2")
                .lastName("EEEE2")
                .email("erik2@mail.com")
                .birthDate(LocalDateTime.now())
                .address("street2")
                .phoneNumber("0987654321")
                .build();

        userRepository.save(user);
        userRepository.save(user2);

        List<User> userList = userRepository.findAll();

        Assertions.assertNotNull(userList);
        Assertions.assertEquals(2, userList.size());
        Assertions.assertTrue(userList.contains(user));
        Assertions.assertTrue(userList.contains(user2));
    }

    @Test
    public void userFindById() {

        User savedUser = userRepository.save(user);

        Optional<User> returnedUser = userRepository.findById(savedUser.getId());

        Assertions.assertTrue(returnedUser.isPresent());
        Assertions.assertSame(user, returnedUser.get());
    }

    @Test
    public void userUpdateTest() {

        User savedUser = userRepository.save(user);

        savedUser.setFirstName("newName");
        userRepository.save(user);
        Optional<User> returnedUser = userRepository.findById(savedUser.getId());

        Assertions.assertTrue(returnedUser.isPresent());
        Assertions.assertSame(user, returnedUser.get());
    }

    @Test
    public void UserRepository_DeleteById() {

        User savedUser = userRepository.save(user);

        userRepository.deleteById(savedUser.getId());
        Optional<User> returnedUser = userRepository.findById(user.getId());

        Assertions.assertFalse(returnedUser.isPresent());
    }

    @Test
    public void UserRepository_FindByBirthDateBetween_ReturnsListOfUsersIfExist() {

        userRepository.save(user);

        List<User> userList = userRepository.findByBirthDateBetween(
                LocalDateTime.of(2003, 1, 1, 0, 0, 0),
                LocalDateTime.of(2004, 1, 1, 0, 0, 0)
        );

        Assertions.assertNotNull(userList);
        Assertions.assertEquals(1, userList.size());
        Assertions.assertTrue(userList.contains(user));
    }

}
