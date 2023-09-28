package com.clearsolution.testjob.Controller;

import com.clearsolution.testjob.DTO.UserDTO;
import com.clearsolution.testjob.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        List<UserDTO> userDTOS = userService.getAllUsers();

        return new ResponseEntity<>(userDTOS, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("id") int id){
        UserDTO user = userService.getUserById(id);
        System.out.println("here");
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/birthDateRage")
    public ResponseEntity<List<UserDTO>> getUsersByBirthDateRange(
            @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to
    ) throws IllegalAccessException {

        List<UserDTO> userDTOS = userService.getUsersByBirthDateRange(from, to);

        return new ResponseEntity<>(userDTOS, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<UserDTO> postUser(@Valid @RequestBody UserDTO userDTO) throws IllegalAccessException {
        UserDTO userResponse = userService.postUser(userDTO);

        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(
            @PathVariable("id") int id,
            @Valid @RequestBody UserDTO userDTO
    ){
        UserDTO userResponse = userService.updateUser(id, userDTO);

        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @DeleteMapping()
    public ResponseEntity<String> deleteAllUsers(){
        userService.deleteAll();

        return new ResponseEntity<>("All users were deleted", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserDTO> deleteUserById(@PathVariable("id") int id){
        UserDTO userDTO = userService.deleteUserById(id);

        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }
}
