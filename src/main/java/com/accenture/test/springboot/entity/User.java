package com.accenture.test.springboot.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;

import javax.validation.constraints.Size;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name="_USER")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

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

    public Long getId() {
        return id;
    }
    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getMiddle_name() {
        return middle_name;
    }

    public void setMiddle_name(String middle_name) {
        this.middle_name = middle_name;
    }

    public String getFamily_name() {
        return family_name;
    }

    public void setFamily_name(String family_name) {
        this.family_name = family_name;
    }

    public Date getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(Date birth_date) {
        this.birth_date = birth_date;
    }

    public Instant getCreated_time() {
        return created_time;
    }

    public void setCreated_time(Instant created_time) {
        this.created_time = created_time;
    }

    public Instant getUpdated_time() {
        return updated_time;
    }

    public void setUpdated_time(Instant updated_time) {
        this.updated_time = updated_time;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public String getUpdated_by() {
        return updated_by;
    }

    public void setUpdated_by(String updated_by) {
        this.updated_by = updated_by;
    }

    public Boolean getIs_active() {
        return is_active;
    }

    public void setIs_active(Boolean is_active) {
        this.is_active = is_active;
    }

    public Instant getDelete_time() {
        return delete_time;
    }

    public void setDelete_time(Instant delete_time) {
        this.delete_time = delete_time;
    }

    public Set<UserSetting> getUserSetting() {
        return userSetting;
    }

    public void setUserSetting(Set<UserSetting> userSetting) {
        this.userSetting = userSetting;
    }

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
