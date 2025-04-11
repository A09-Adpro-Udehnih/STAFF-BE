package com.example.staffbe.service;

import com.example.staffbe.model.Staff;
import com.example.staffbe.repository.StaffRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StaffService {

    private final StaffRepository staffRepository;

    public StaffService(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    public List<Staff> getAllStaff() {
        return staffRepository.findAll();
    }

    public Optional<Staff> getStaffById(UUID id) {
        return staffRepository.findById(id);
    }

    public Staff createStaff(Staff staff) {
        // Paksa role menjadi STAFF agar aman
        staff.setRole(com.example.staffbe.model.Role.STAFF);
        return staffRepository.save(staff);
    }

    public Optional<Staff> updateStaff(UUID id, Staff updatedStaff) {
        return staffRepository.findById(id).map(staff -> {
            staff.setEmail(updatedStaff.getEmail());
            staff.setFullName(updatedStaff.getFullName());
            staff.setPassword(updatedStaff.getPassword());
            staff.setUpdatedAt(updatedStaff.getUpdatedAt());
            return staffRepository.save(staff);
        });
    }

    public void deleteStaff(UUID id) {
        staffRepository.deleteById(id);
    }
}
