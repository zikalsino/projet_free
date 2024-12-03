//package com.example.demo.controller;
//
//import com.example.demo.entity.User;
//import com.example.demo.service.Impl.UserService;
//import lombok.Data;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@Data
//@RestController
//@RequestMapping("/api/users")
//@RequiredArgsConstructor
//public class UserController {
//
//
//    final private UserService userService;
//
//
//
//
//    @GetMapping("/{id}")
//    public ResponseEntity<User> getUserById(@PathVariable Long id) {
//        return userService.findById(id)
//                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
//                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
//    }
//
//
//
//    @GetMapping
//    public ResponseEntity<List<User>> getAllUsers() {
//        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
//        userService.deleteUser(id);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
//        try {
//            User updatedUser = userService.updateUser(id, userDetails);
//            return ResponseEntity.ok(updatedUser);
//        } catch (Exception e) {
//            return ResponseEntity.notFound().build();
//        }
//
//    }
//}
