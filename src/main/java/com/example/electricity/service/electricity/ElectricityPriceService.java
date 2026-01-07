package com.example.electricity.service.electricity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.example.electricity.dto.response.ElectricityPriceResponse;
import com.example.electricity.entity.ElectricityPrice;
import com.example.electricity.exception.AppException;
import com.example.electricity.exception.ErrorCode;
import com.example.electricity.repository.ElectricityPriceRepository;

@Service
public class ElectricityPriceService {
    
    @Autowired
    private ElectricityPriceRepository electricityPriceRepo;

    public ElectricityPriceResponse create(ElectricityPriceResponse dto){

        if(electricityPriceRepo.existsByFromKwhAndToKwh(dto.getFromKwh(),dto.getToKwh()))
            throw new AppException(ErrorCode.ELECTRICITY_EXISTED);

        if(dto.getFromKwh()> dto.getToKwh()) 
            throw new AppException(ErrorCode.CURRENTkWH_NOT_GREATER_THAN_PREVIOUSkWH);
        
        ElectricityPrice electricPrice = new ElectricityPrice();
        electricPrice.setFromKwh(dto.getFromKwh());
        electricPrice.setToKwh(dto.getToKwh());
        electricPrice.setPrice(dto.getPrice());
        ElectricityPrice saved =  electricityPriceRepo.save(electricPrice);
    
        ElectricityPriceResponse response = new ElectricityPriceResponse();
        response.setFromKwh(saved.getFromKwh());
        response.setToKwh(saved.getToKwh());
        response.setPrice(saved.getPrice());
        return response;
    }

    public List<ElectricityPriceResponse> getAll(){
        List<ElectricityPrice> list = electricityPriceRepo.findAll();
        List<ElectricityPriceResponse> response = new ArrayList<>();
    
        for (ElectricityPrice electricityPrice : list) {
            ElectricityPriceResponse dto = new ElectricityPriceResponse();
            dto.setFromKwh(electricityPrice.getFromKwh());
            dto.setToKwh(electricityPrice.getToKwh());
            dto.setPrice(electricityPrice.getPrice());
            response.add(dto);
        }
        return response;
    }
    public void delete(@NonNull Long id){
        electricityPriceRepo.deleteById(id);
    }

    public ElectricityPrice update(Long id , ElectricityPriceResponse dto){

        ElectricityPrice electricPrice = new ElectricityPrice();
        electricPrice.setFromKwh(dto.getFromKwh());
        electricPrice.setToKwh(dto.getToKwh());
        electricPrice.setPrice(dto.getPrice());
        return electricityPriceRepo.save(electricPrice);
    }
}
