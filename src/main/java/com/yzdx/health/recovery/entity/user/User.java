package com.yzdx.health.recovery.entity.user;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "user")
public class User {
    @Id
    private String userId;
    private String password;
    private String name;
    private String gender;
    private Date birthday;
    private String phoneNumber;
    private String email;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private Date createdDate;
}
