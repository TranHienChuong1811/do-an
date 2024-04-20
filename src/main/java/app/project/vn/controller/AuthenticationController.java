package app.project.vn.controller;

import lombok.Data;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin("*")
public class AuthenticationController {

    @PostMapping("/login")
    public Object login(@RequestBody LoginPayload payload) {
        var msg = payload.getUsername().equals("admin") && payload.getPassword().equals("admin") ? "OK" : "ERROR";
        return Map.of("status", msg);
    }

    @Data
    public static class LoginPayload {
        private String username;
        private String password;
    }
}
