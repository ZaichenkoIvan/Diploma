package com.yadro.web.rooms.app.repository;

import java.io.Serializable;
import java.util.List;

import com.yadro.web.rooms.app.model.Room;
import com.yadro.web.rooms.app.model.Hostel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Serializable> {
	
	Room findById(Long id);
	Room findByName(String name);
	List<Room> findByHostel(Hostel hostel);
	
	@Query("select ro from Room ro " +
	         "where ro.name = :name and ro.hostel = :hostel")
	Room findByNameAndHostel(
			@Param("name") String name,  
			@Param("hostel") Hostel hostel);
    
    @Modifying
    @Transactional
    @Query("update Room r set r.name = :name, r.ball = :ball, "
    		+ "r.manish = :manish, r.grass = :grass, r.projector = :projector, r.changingRoom = :changingRoom, "
    		+ "r.shower = :shower, r.wc = :wc, r.hostel = :hostel "
            + "where r.id = :id")
    int updateRoom(
            @Param("id") Long id,
            @Param("name") String name,
            @Param("ball") int ball,
            @Param("manish") int manish,
            @Param("grass") String grass,
            @Param("projector") String projector,
            @Param("changingRoom") String changingRoom,
            @Param("shower") String shower,
            @Param("wc") String wc,
            @Param("hostel") Hostel hostel);
}
