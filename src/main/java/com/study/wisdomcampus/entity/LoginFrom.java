package com.study.wisdomcampus.entity;

import lombok.Data;

/**
 * @description: 用户登录表单信息
 */
@Data
public class LoginFrom {
    private String username;
    private String password;
    private String verifiCode;
    private Integer userType;
}
