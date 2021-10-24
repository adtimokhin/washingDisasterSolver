package com.adtimokhin.repository.complaints;

import com.adtimokhin.model.complaints.WashingMachineComplaint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author adtimokhin
 * 23.10.2021
 **/

@Repository
public interface WashingMachineComplaintRepository extends JpaRepository<WashingMachineComplaint, Integer> {
}
