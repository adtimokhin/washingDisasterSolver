package com.adtimokhin.service.complaints.impl;

import com.adtimokhin.model.complaints.WashingMachineComplaint;
import com.adtimokhin.repository.complaints.WashingMachineComplaintRepository;
import com.adtimokhin.service.complaints.WashingMachineComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author adtimokhin
 * 23.10.2021
 **/

@Component
public class WashingMachineComplaintServiceImpl implements WashingMachineComplaintService {


    @Autowired
    private WashingMachineComplaintRepository washingMachineComplaintRepository;

    @Override
    public void save(WashingMachineComplaint complaint) {
        washingMachineComplaintRepository.save(complaint);
    }

    @Override
    public List<WashingMachineComplaint> findAll() {
        return washingMachineComplaintRepository.findAll();
    }
}
