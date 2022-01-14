package com.cg.repository;

import com.cg.model.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {

    List<Staff> findAllByDeletedIsFalse() ;

    List<Staff> findAllByDeletedIsFalseAndUserIdIsNull() ;


}
