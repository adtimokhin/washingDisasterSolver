package com.adtimokhin.service.complaints;

import com.adtimokhin.model.complaints.WashingMachineComplaint;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author adtimokhin
 * 23.10.2021
 **/

@Service
public interface WashingMachineComplaintService {

    @Transactional
    void save(WashingMachineComplaint complaint);

    List<WashingMachineComplaint> findAll();
}
