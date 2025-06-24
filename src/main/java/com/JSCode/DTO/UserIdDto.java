package com.JSCode.DTO;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserIdDto implements Serializable {
    private Long userId;
    private String token; 
}
