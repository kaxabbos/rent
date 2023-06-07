package com.rent.repo;

import com.rent.model.Cars;
import com.rent.model.Statistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatisticsRepo extends JpaRepository<Statistics, Long> {
}
