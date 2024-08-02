package com.example.drawscribeio.dto;

import lombok.*;

@Setter@Getter
@AllArgsConstructor@NoArgsConstructor
@Data
public class UserDto {

    private Long userId;
    private String username;
    private String email;
    private String avatarUrl;
    private Integer score;
}
