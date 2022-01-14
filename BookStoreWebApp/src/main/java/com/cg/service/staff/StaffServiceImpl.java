package com.cg.service.staff;

import com.cg.model.Staff;
import com.cg.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class StaffServiceImpl implements StaffService {

    @Autowired
    private StaffRepository staffRepository;

    @Override
    public List<Staff> findAll() {
        return staffRepository.findAll();
    }

    @Override
    public Optional<Staff> findById(Long id) {
        return staffRepository.findById(id);
    }

    @Override
    public Staff getById(Long id) {
        return staffRepository.getById(id);
    }

    @Override
    public Staff save(Staff staff) {
        return staffRepository.save(staff);
    }

    @Override
    public void remove(Long id) {
        staffRepository.deleteById(id);
    }

    @Override
    public List<Staff> findAllByDeletedIsFalse() {
        return staffRepository.findAllByDeletedIsFalse();
    }

    @Override
    public List<Staff> findAllByDeletedIsFalseAndUserIdIsNull() {
        return staffRepository.findAllByDeletedIsFalseAndUserIdIsNull();
    }
}
