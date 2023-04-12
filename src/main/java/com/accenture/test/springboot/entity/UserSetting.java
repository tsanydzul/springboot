package com.accenture.test.springboot.entity;

import jakarta.persistence.*;

import javax.validation.constraints.Size;

@Entity
public class UserSetting {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    @Size(min = 3, max = 100)
    private String key;

    @Column(nullable = false)
    @Size(min = 3, max = 100)
    private String value;

    private Long user_id;

    public Long getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }
}
