package com.JSCode.DTO;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificacionAsignacionDTO implements Serializable{
    private Long orderId;
    private Long userId;
}