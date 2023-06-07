package com.rent.repo;

import com.rent.model.Cars;
import com.rent.model.enums.BodyType;
import com.rent.model.enums.Fuel;
import com.rent.model.enums.Transmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarsRepo extends JpaRepository<Cars, Long> {
    List<Cars> findAllByDescription_BodyTypeAndDescription_FuelAndDescription_TransmissionOrderByFreeDesc(BodyType bodyType, Fuel fuel, Transmission transmission);
    List<Cars> findAllByOrderByFreeDesc();
}
