package org.pranay.creatorstore.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "🚀 CreatorStore Backend is Running!";
    }

    @GetMapping("/health")
    public String health() {
        return "OK";
    }
}