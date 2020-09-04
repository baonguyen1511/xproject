/**
 * 
 */
package com.xproject.demo.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xproject.demo.models.ERole;
import com.xproject.demo.models.Role;
import com.xproject.demo.models.User;
import com.xproject.demo.payload.request.UpdateRequest;
import com.xproject.demo.repository.RoleRepository;
import com.xproject.demo.repository.UserRepository;

/**
 * @author bao.nguyentx
 *
 */
@CrossOrigin(origins = "${xproject.app.xURL}", maxAge = 3600)
@RestController
@RequestMapping("/api/rbaa")
public class UserController {
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> getAllUsers() {
        try {
            List<User> users = new ArrayList<User>();
            userRepository.findAll().forEach(users::add);
            if (users.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("users/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> updateUser(@PathVariable("id") long id,
            @Valid @RequestBody UpdateRequest updateRequest) {
        Optional<User> userData = userRepository.findById(id);

        if (userData.isPresent()) {
            User _user = userData.get();
            _user.setEmail(updateRequest.getEmail());
            _user.setPassword(encoder.encode(updateRequest.getPassword()));
            _user.setUsername(updateRequest.getUsername());
            Set<String> strRoles = updateRequest.getRole();
            Set<Role> roles = new HashSet<>();
            strRoles.forEach(role -> {
                switch (role) {
                case "admin":
                    Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                            .orElseThrow(() -> new RuntimeException(
                                    "Error: Role is not found."));
                    roles.add(adminRole);

                    break;
                case "mod":
                    Role modRole = roleRepository
                            .findByName(ERole.ROLE_MODERATOR)
                            .orElseThrow(() -> new RuntimeException(
                                    "Error: Role is not found."));
                    roles.add(modRole);

                    break;
                default:
                    Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                            .orElseThrow(() -> new RuntimeException(
                                    "Error: Role is not found."));
                    roles.add(userRole);
                }
            });
            _user.setRoles(roles);
            return new ResponseEntity<>(userRepository.save(_user),
                    HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/users/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HttpStatus> deleteUser(
            @PathVariable("id") long id) {
        try {
            userRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
