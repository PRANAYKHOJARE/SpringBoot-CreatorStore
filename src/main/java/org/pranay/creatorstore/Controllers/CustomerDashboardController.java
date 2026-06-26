package org.pranay.creatorstore.Controllers;

import lombok.RequiredArgsConstructor;
import org.pranay.creatorstore.dto.CustomerDashboardResponse;
import org.pranay.creatorstore.services.CustomerDashboardService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CustomerDashboardController {

    private final CustomerDashboardService customerDashboardService;

    @GetMapping("/dashboard/{customerId}")
    public CustomerDashboardResponse getDashboard(@PathVariable Long customerId) {
        return customerDashboardService.getDashboard(customerId);
    }
}