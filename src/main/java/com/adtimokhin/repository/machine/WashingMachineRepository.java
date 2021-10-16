package com.adtimokhin.repository.machine;

import com.adtimokhin.model.machine.WashingMachine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author adtimokhin
 * 16.10.2021
 **/
@Repository
public interface WashingMachineRepository extends JpaRepository<WashingMachine, Integer> {
//    WashingMachine findById(int id);
}
