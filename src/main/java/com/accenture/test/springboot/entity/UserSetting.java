package com.accenture.test.springboot.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import javax.validation.constraints.Size;
import java.util.HashMap;
import java.util.Map;

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

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonIgnore
    private User user;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user_id) {
        this.user = user_id;
    }
    public Map<String,String> getMap(){
        Map<String, String> result = new HashMap<>();
        result.put(this._key, this._value);
        return result;
    }
}
