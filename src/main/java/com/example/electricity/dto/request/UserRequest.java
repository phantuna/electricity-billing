package com.example.electricity.dto.request;




import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Size;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    private Long id;
    @Size(min = 4,message= "username must be at least 4 charactors")
    private String username;
    @Size(min = 6,message= "password must be at least 6 charactors")
    private String password;
     
}
