package com.accenture.test.springboot.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name="_USER")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    @Size(min = 16, max = 16)
    private String ssn;

    @Column(nullable = false)
    @Size(min = 3, max = 100)
    private String first_name;
    private String middle_name;
    @Column(nullable = false)
    @Size(min = 3, max = 100)
    private String family_name;

    private Date birth_date;
    @Column(nullable = false)
    private Instant created_time;
    @Column(nullable = false)
    private Instant updated_time;
    @Column(nullable = false)
    @Size(max = 100)
    private String created_by = "SYSTEM";
    @Column(nullable = false)
    @Size(max = 100)
    private String updated_by = "SYSTEM";
    private Boolean is_active = true;
    private Instant delete_time;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private Set<UserSetting> userSetting;

    public List<Map<String,String>>fetchUserSettings(){
        if(userSetting != null){
            List<Map<String,String>> result = new ArrayList<>();
            userSetting.forEach(it -> result.add(it.getMap()));
            return result;
        }else{
            return null;
        }
    }

}
