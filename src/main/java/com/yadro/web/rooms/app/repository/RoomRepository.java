package com.yadro.web.rooms.app.repository;

import com.yadro.web.rooms.app.model.Hostel;
import com.yadro.web.rooms.app.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

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
    @Query("update Room r set r.name = :name, r.floor = :floor, "
            + "r.capacity = :capacity, r.sportsEquipment = :sportsEquipment, r.recreationArea = :recreationArea, r.lunchZone = :lunchZone, "
            + "r.lighting = :lighting, r.description = :description, r.hostel = :hostel "
            + "where r.id = :id")
    int updateRoom(
            @Param("id") Long id,
            @Param("name") String name,
            @Param("floor") int floor,
            @Param("capacity") int capacity,
            @Param("sportsEquipment") String sportsEquipment,
            @Param("recreationArea") String recreationArea,
            @Param("lunchZone") String lunchZone,
            @Param("lighting") String lighting,
            @Param("description") String description,
            @Param("hostel") Hostel hostel);
}
