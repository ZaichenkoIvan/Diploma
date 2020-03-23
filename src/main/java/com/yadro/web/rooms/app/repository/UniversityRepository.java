package com.yadro.web.rooms.app.repository;

import java.io.Serializable;

import com.yadro.web.rooms.app.model.University;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UniversityRepository extends JpaRepository<University, Serializable> {
	
	University findById(Long id);
	University findByName(String name);
    
    @Modifying
    @Transactional
    @Query("update University o set o.name = :name "
            + "where o.id = :id")
    int updateUniversity(
            @Param("id") Long id,
            @Param("name") String name);
	
}
