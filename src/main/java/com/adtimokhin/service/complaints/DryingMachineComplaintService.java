package com.adtimokhin.service.complaints;

import com.adtimokhin.aspect.NotEmptyArguments;
import com.adtimokhin.model.complaints.DryingMachineComplaint;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author adtimokhin
 * 24.10.2021
 **/

@Service
public interface DryingMachineComplaintService {

    @Transactional
    @NotEmptyArguments
    void save(DryingMachineComplaint complaint);

    List<DryingMachineComplaint> findAll();

    @Transactional
    void deleteAll();
}
