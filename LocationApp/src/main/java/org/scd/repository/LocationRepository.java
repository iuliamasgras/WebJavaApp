package org.scd.repository;

import org.scd.model.Location;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface LocationRepository extends CrudRepository<Location,Long> {

    Location save(Location location);

    Optional<Location> findById(final Long id);

    @Override
    void deleteById(Long aLong);

    @Query(value = "SELECT location FROM Location location WHERE location.date <= :endDate AND location.date >= :startDate AND location.user.id = :user_id")
    List<Location> getLocations(@Param("startDate") Date startDate,@Param("endDate") Date endDate,@Param("user_id") Long userId);

}
