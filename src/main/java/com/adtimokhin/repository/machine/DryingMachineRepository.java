package com.adtimokhin.repository.machine;

import com.adtimokhin.model.machine.DryingMachine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author adtimokhin
 * 16.10.2021
 **/
@Repository
public interface DryingMachineRepository extends JpaRepository<DryingMachine, Integer> {

}
