package com.adtimokhin.service.complaints.impl;

import com.adtimokhin.model.complaints.DryingMachineComplaint;
import com.adtimokhin.repository.complaints.DryingMachineComplaintRepository;
import com.adtimokhin.service.complaints.DryingMachineComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author adtimokhin
 * 24.10.2021
 **/

@Component
public class DryingMachineComplaintServiceImpl implements DryingMachineComplaintService {

    @Autowired
    private DryingMachineComplaintRepository dryingMachineComplaintRepository;

    @Override
    public void save(DryingMachineComplaint complaint) {
        dryingMachineComplaintRepository.save(complaint);
    }

    @Override
    public List<DryingMachineComplaint> findAll() {
        return dryingMachineComplaintRepository.findAll();
    }
}
