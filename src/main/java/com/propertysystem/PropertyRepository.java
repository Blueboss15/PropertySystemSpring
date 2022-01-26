package com.propertysystem;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PropertyRepository extends CrudRepository<PropertyEntity, String>
{
    @Query("from PropertyEntity p where p.region=:region and p.areaRange=:size and p.type in :types and p.dateAdded between :startDate and :endDate")
    public List<PropertyEntity> findByRegionSizeTypeAndDate(@Param("region") String region, @Param("size") String size, @Param("types") String[] types, @Param("startDate") LocalDate startDate, @Param("endDate")LocalDate endDate);
}