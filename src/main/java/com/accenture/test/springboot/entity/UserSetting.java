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
    private String _key;

    @Column(nullable = false)
    @Size(min = 3, max = 100)
    private String _value;

    private Long user_id;

    public Long getId() {
        return id;
    }

    public String get_key() {
        return _key;
    }

    public void set_key(String key) {
        this._key = key;
    }

    public String get_value() {
        return _value;
    }

    public void set_value(String _value) {
        this._value = _value;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }
}
