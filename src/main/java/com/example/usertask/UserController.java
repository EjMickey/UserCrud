package com.example.usertask;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/users")
    String getUsers(){
        Iterable<User> users = userService.getUsers();
        StringBuilder html = new StringBuilder("<table style=\"border: 1px solid\"><tr><td>Id</td><td>Name</td><td>Email</td><td>Age</td></tr>");
        users.forEach(u -> html.append(
                "<tr><td>"+u.getUserId()+"</td><td>"+u.getName()+"</td><td>"+u.getEmail()+"</td><td>"+u.getAge()+"</td></tr>"
        ));
        html.append("</table>");
        return html.toString();
    }

    @PostMapping("/user")
    String postUser(@RequestBody User user){
        userService.saveUser(user);
        return "Uploaded new user data: "+ user.toString();
    }

    @PostMapping("/users")
    public String postUsers(@RequestBody Iterable<User> users) {
        userService.saveUsers(users);

        String uploadedUsers = StreamSupport.stream(users.spliterator(), false)
                .map(User::toString)
                .collect(Collectors.joining(", "));

        return "Uploaded new user data: " + uploadedUsers;
    }

    @GetMapping("/user/{id}")
    String getUserById(@PathVariable Long id){
        try{
            User u = userService.getUserById(id);
            return String.valueOf(u.getUserId());
        }
        catch(UserNotFoundException e){
            System.err.println(e.getMessage());
            return e.getMessage();
        }
    }

    @GetMapping("/user/email/{email}")
    String getUserByEmail(@PathVariable String email){
        try{
            User u = userService.getUserByEmail(email);
            return u.getEmail();
        }
        catch (UserNotFoundException e){
            System.err.println(e.getMessage());
            return e.getMessage();
        }
    }

    @GetMapping("/user/name/{name}")
    String getUserByName(@PathVariable String name){
        try{
            User u = userService.getUserByName(name);
            return u.getName();
        }
        catch (UserNotFoundException e){
            System.err.println(e.getMessage());
            return e.getMessage();
        }
    }

    @GetMapping("/users/adults")
    String getAdults(){
        Iterable<User> users = userService.getAdults();
        if(!users.iterator().hasNext()){
            System.err.println("There is no saved adult in db");
            return "";
        }
        else{
            StringBuilder html = new StringBuilder();
            users.forEach(u -> html.append("<p>"+u.toString()+"</p>"));
            return html.toString();
        }
    }

    @GetMapping("users/count")
    String countUsers(){
        return "You've got "+userService.countUsers()+" users in db";
    }
}
