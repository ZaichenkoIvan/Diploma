package com.yadro.web.rooms.app.repository;

import java.io.Serializable;
import java.util.List;

import com.yadro.web.rooms.app.model.Hostel;
import com.yadro.web.rooms.app.model.University;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface HostelRepository extends JpaRepository<Hostel, Serializable> {
	
	Hostel findById(Long id);
	Hostel findByName(String name);
	List<Hostel> findByUniversity(University university);
	
	@Query("select un from Hostel un " +
	         "where un.name = :name and un.university = :university")
    Hostel findByNameAndUniversity(
			@Param("name") String name,  
			@Param("university") University university);
    
    @Modifying
    @Transactional
    @Query("update Hostel u set u.name = :name, u.address = :address, u.university = :university  "
            + "where u.id = :id")
    int updateHostel(
            @Param("id") Long id,
            @Param("name") String name,
            @Param("address") String address,
            @Param("university") University university);
}