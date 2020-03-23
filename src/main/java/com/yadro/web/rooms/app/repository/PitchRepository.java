package com.yadro.web.rooms.app.repository;

import java.io.Serializable;
import java.util.List;

import com.yadro.web.rooms.app.model.Pitch;
import com.yadro.web.rooms.app.model.Stadium;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PitchRepository extends JpaRepository<Pitch, Serializable> {
	
	Pitch findById(Long id);
	Pitch findByName(String name);
	List<Pitch> findByStadium(Stadium stadium);
	
	@Query("select ro from Pitch ro " +
	         "where ro.name = :name and ro.stadium = :stadium")
    Pitch findByNameAndStadium(
			@Param("name") String name,  
			@Param("stadium") Stadium stadium);
    
    @Modifying
    @Transactional
    @Query("update Pitch r set r.name = :name, r.ball = :ball, "
    		+ "r.manish = :manish, r.grass = :grass, r.projector = :projector, r.changingRoom = :changingRoom, "
    		+ "r.shower = :shower, r.wc = :wc, r.stadium = :stadium "
            + "where r.id = :id")
    int updatePitch(
            @Param("id") Long id,
            @Param("name") String name,
            @Param("ball") int ball,
            @Param("manish") int manish,
            @Param("grass") String grass,
            @Param("projector") String projector,
            @Param("changingRoom") String changingRoom,
            @Param("shower") String shower,
            @Param("wc") String wc,
            @Param("stadium") Stadium stadium);
}
