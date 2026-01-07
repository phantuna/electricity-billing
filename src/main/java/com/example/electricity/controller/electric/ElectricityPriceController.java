package com.example.electricity.controller.electric;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.electricity.config.HasPermission;
import com.example.electricity.dto.response.ApiResponse;
import com.example.electricity.dto.response.ElectricityPriceResponse;
import com.example.electricity.entity.ElectricityPrice;
import com.example.electricity.service.electricity.ElectricityPriceService;




@RestController
@RequestMapping("/price")
public class ElectricityPriceController {
    @Autowired
    private ElectricityPriceService electricityPriceService;

    @PostMapping("/create")
    @HasPermission("UPDATE_EMPLOYEE")
    ApiResponse< ElectricityPriceResponse> create(@RequestBody ElectricityPriceResponse dto) {
        ApiResponse< ElectricityPriceResponse> response = new ApiResponse<>();
        response.setResult(electricityPriceService.create(dto));
        return response;
    }
    
    @GetMapping("/view")
    ApiResponse< List<ElectricityPriceResponse>> getAll(){
        ApiResponse< List<ElectricityPriceResponse>> response = new ApiResponse<>();
        response.setResult(electricityPriceService.getAll());
        return response;
    }
    @DeleteMapping("/delete/{id}")
    @HasPermission("UPDATE_EMPLOYEE")
    ApiResponse <String> delete(@PathVariable @NonNull Long id){
        electricityPriceService.delete(id);
        ApiResponse <String> response = new ApiResponse<>();
        response.setResult("Delete success");;
        return response;
    }
    @PutMapping("update/{id}")
    @HasPermission("UPDATE_EMPLOYEE")
    ApiResponse< ElectricityPrice> update(@PathVariable Long id, @RequestBody ElectricityPriceResponse dto) {    
        ApiResponse< ElectricityPrice> response = new ApiResponse<>();   
        response.setResult(electricityPriceService.update(id, dto)); 
        return response;
    }
}
