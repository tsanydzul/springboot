package com.accenture.test.springboot.repo;

import com.accenture.test.springboot.entity.User;
import com.accenture.test.springboot.util.PageOffsetAndSize;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User, Long>{
    @Query(nativeQuery = true, value = "Select * from _USER where is_active=true limit ?1 offset ?2")
    List<User> findAll(int max_records, int offset);

    @Query(nativeQuery = true, value = "Select count(*) from _USER where ssn=?1 and is_active=true")
    Integer findUserWithSSN(String ssn);

    @Query(nativeQuery = true, value = "Select * from _USER where is_active=true and ?1")
    User findByIdAndActive(Long id);
}
