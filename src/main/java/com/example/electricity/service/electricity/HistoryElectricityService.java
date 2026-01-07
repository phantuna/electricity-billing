package com.example.electricity.service.electricity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.electricity.dto.request.HistoryElectricityRequest;
import com.example.electricity.dto.response.HistoryElectricityResponse;
import com.example.electricity.entity.ElectricityPrice;
import com.example.electricity.entity.HistoryElectricity;
import com.example.electricity.entity.User;
import com.example.electricity.exception.AppException;
import com.example.electricity.exception.ErrorCode;
import com.example.electricity.repository.ElectricityPriceRepository;
import com.example.electricity.repository.HistoryElectricityRepository;
import com.example.electricity.repository.UserRepository;

@Service
public class HistoryElectricityService {
    @Autowired
    private HistoryElectricityRepository historyElectricityRepository;
    @Autowired
    private ElectricityPriceRepository electricityPriceRepository;
    @Autowired
    private UserRepository userRepository;


    public HistoryElectricityResponse calculate(HistoryElectricityRequest dto){
        User user = userRepository.findById(dto.getUserId())
         .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        
        LocalDate now = LocalDate.now();
        int month = now.getMonthValue();
        int year  = now.getYear();

        List<HistoryElectricity> existing = 
                historyElectricityRepository.findByUserIdAndMonth(dto.getUserId(), month, year);

        if (!existing.isEmpty()) {
            throw new AppException(ErrorCode.ELECTRICITY_EXISTED);
        }
         HistoryElectricity last = historyElectricityRepository.findTopByUserIdOrderByCreatedDateDesc(dto.getUserId());

        int previousKwh = (last == null) ? 0 : last.getCurrentKwh();
          if(dto.getCurrentKwh() < previousKwh){
            throw new AppException(ErrorCode.CURRENTkWH_NOT_GREATER_THAN_PREVIOUSkWH);
        }
        // Tính mức tiêu thụ
        int consumption = dto.getCurrentKwh() - previousKwh;

        // Lấy bảng giá
        List<ElectricityPrice> tiers = electricityPriceRepository.findAll();

        long totalAmount = (long) calulaterElectricity(consumption, tiers);

        // Lưu lịch sử mới
        HistoryElectricity history = new HistoryElectricity();
        history.setUser(user);
        history.setPreviousKwh(previousKwh);
        history.setCurrentKwh(dto.getCurrentKwh());
        history.setConsumption((long)consumption);
        historyElectricityRepository.save(history);

        // Trả về DTO
        HistoryElectricityResponse response = new HistoryElectricityResponse();
        response.setUsername(user.getUsername());
        response.setPreviousKwh(previousKwh);
        response.setCurrentKwh(dto.getCurrentKwh());
        response.setConsumption(consumption);
        response.setDate(history.getCreatedDate());
        response.setTotalAmount(totalAmount);

        return response;
        
        }
    public double calulaterElectricity(int Kwh,List<ElectricityPrice> electric){
        double total =0;
        int remain = Kwh;

        for(ElectricityPrice e: electric){
            int min = e.getFromKwh();
            Integer maxVal = e.getToKwh();
            int max = (maxVal == null) ? Integer.MAX_VALUE : maxVal;
            int tierRange = max - min +1;

            int usage = Math.min(remain, tierRange);
            if(usage >0){
                total += usage * e.getPrice();
                remain -= usage;
            }
            

            if(remain <=0) break;

        }
        return total;

    }
   
    public Long getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        return user.getId();
    }

     public List<HistoryElectricityResponse> getUserHistory() {
        Long userId = getCurrentUserId();
        List<HistoryElectricity> list = historyElectricityRepository.findByUserId(userId);
        List<ElectricityPrice> tiers = electricityPriceRepository.findAll();

        List<HistoryElectricityResponse> dtos = new ArrayList<>();

        for (HistoryElectricity h : list) {
            HistoryElectricityResponse dto = new HistoryElectricityResponse();
            dto.setUsername(h.getUser().getUsername());
            dto.setPreviousKwh(h.getPreviousKwh());
            dto.setCurrentKwh(h.getCurrentKwh());
            dto.setConsumption(h.getConsumption().intValue());
            dto.setDate(h.getCreatedDate());

            long totalAmount = (long) calulaterElectricity(h.getConsumption().intValue(), tiers);
            dto.setTotalAmount(totalAmount);

            dtos.add(dto);
        }

        return dtos;
    }

    // public List<HistoryElectricityDto> getAllHistoryUser(Long userId) {

    //     List<historyElectricity> list = historyElectricityRepository.findByUserId(userId);

    //     List<HistoryElectricityDto> dtos = new ArrayList<>();

    //     for (historyElectricity h : list) {
    //         HistoryElectricityDto dto = new HistoryElectricityDto();
    //         dto.setUsername(h.getUser().getUsername());
    //         dto.setPreviousKwh(h.getPreviousKwh());
    //         dto.setCurrentKwh(h.getCurrentKwh());
    //         dto.setConsumption(h.getConsumption().intValue());
    //         dto.setDate(h.getCreatedDate());

    //         List<electricityPrice> tiers = electricityPriceRepository.findAll();
    //         long totalAmount = (long) calulaterElectricity(h.getConsumption().intValue(), tiers);
    //         dto.setTotalAmount(totalAmount);

    //         dtos.add(dto);
    //     }

    //     return dtos;
    // }

    public List<HistoryElectricityResponse> getAll() {
        
        List<ElectricityPrice> tiers = electricityPriceRepository.findAll();

        return historyElectricityRepository.findAll()
                .stream()
                .map(h -> toDto(h, (long) calulaterElectricity(h.getConsumption().intValue(), tiers)))
                .toList();    
        }
    public HistoryElectricityResponse toDto(HistoryElectricity h, long totalAmount) {
        
        return HistoryElectricityResponse.builder()
            .username(h.getUser().getUsername())
            .previousKwh(h.getPreviousKwh())
            .currentKwh(h.getCurrentKwh())
            .consumption(h.getConsumption().intValue())
            .totalAmount(totalAmount)
            .Date(h.getCreatedDate())
            .build();
        }

}
