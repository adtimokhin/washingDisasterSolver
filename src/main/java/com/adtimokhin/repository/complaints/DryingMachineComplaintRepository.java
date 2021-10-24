package com.adtimokhin.repository.complaints;

import com.adtimokhin.model.complaints.DryingMachineComplaint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author adtimokhin
 * 24.10.2021
 **/

@Repository
public interface DryingMachineComplaintRepository extends JpaRepository<DryingMachineComplaint, Integer> {
}
