package com.example.electricity.controller.electric;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.electricity.config.HasPermission;
import com.example.electricity.dto.request.HistoryElectricityRequest;
import com.example.electricity.dto.response.ApiResponse;
import com.example.electricity.dto.response.HistoryElectricityResponse;
import com.example.electricity.service.electricity.HistoryElectricityService;




@RestController
@RequestMapping("/history")

public class ElectricityHistoryController {
    @Autowired
    private HistoryElectricityService historyElectricityService;

    @PostMapping("/calculate")
    @HasPermission("UPDATE_EMPLOYEE")
    ApiResponse< HistoryElectricityResponse> calculated( @RequestBody HistoryElectricityRequest electric) {
        ApiResponse< HistoryElectricityResponse> response = new ApiResponse<>();
        response.setResult(historyElectricityService.calculate ( electric));
        return response;
    }

    @GetMapping("/userHistory")
    @HasPermission("VIEW_USER")
    ApiResponse< List<HistoryElectricityResponse>> getUserHistory() {
        ApiResponse< List<HistoryElectricityResponse>> response = new ApiResponse<>();
        response.setResult(historyElectricityService.getUserHistory());
        return response;
    }
    // @GetMapping("/userHistory/{id}")
    // @HasPermission("UPDATE_EMPLOYEE")
    // public List<HistoryElectricityDto> getAllHistoryUser(@PathVariable Long id){
    //     return historyElectricityService.getAllHistoryUser(id);
    // }

    @GetMapping("/getall")
    @HasPermission("UPDATE_EMPLOYEE")
    ApiResponse< List<HistoryElectricityResponse>> getAll() {
        ApiResponse<List< HistoryElectricityResponse>> response = new ApiResponse<>();
        response.setResult(historyElectricityService.getAll());
        return response;
    }
    
}
