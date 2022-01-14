package com.cg.service.staff;

import com.cg.model.Staff;
import com.cg.service.IGeneralService;

import java.util.List;

public interface StaffService extends IGeneralService<Staff> {
    List<Staff> findAllByDeletedIsFalse();

    List<Staff> findAllByDeletedIsFalseAndUserIdIsNull();
}
