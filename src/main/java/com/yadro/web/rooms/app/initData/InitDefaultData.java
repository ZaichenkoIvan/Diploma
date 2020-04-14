package com.yadro.web.rooms.app.initData;

import com.yadro.web.rooms.app.model.Account;
import com.yadro.web.rooms.app.model.Hostel;
import com.yadro.web.rooms.app.model.Room;
import com.yadro.web.rooms.app.model.University;
import com.yadro.web.rooms.app.service.AccountService;
import com.yadro.web.rooms.app.service.HostelService;
import com.yadro.web.rooms.app.service.RoomService;
import com.yadro.web.rooms.app.service.UniversityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class InitDefaultData {

    @Autowired
    private AccountService accountService;

    @Autowired
    private UniversityService universityService;

    @Autowired
    private HostelService hostelService;

    @Autowired
    private RoomService roomService;

    @PostConstruct
    private void initDefaultData() {
        log.debug("Init default start");
        ///*
        List<University> universities = new ArrayList<University>();
        University university;

        university = new University();
        university.setName("KPI");
        universities.add(university);

        university = new University();
        university.setName("Other University");
        universities.add(university);

        for (University o : universities) {
            universityService.add(o);
        }

        List<Account> accounts = new ArrayList<Account>();
        Account account;

        account = new Account();
        account.setUniversity(universities.get(0));
        account.setUserName("admin");
        account.setPassword("admin");
        account.setEmail("fgzf.fgzf.1999@gmail.com");
        account.setFirstName("Vasyl");
        account.setLastName("Zaichenko");
        account.setRole("ROLE_ADMIN");
        account.setAddress("Str. Borsch 20");
        account.setGroupName("IP-64");
        accounts.add(account);

        account = new Account();
        account.setUniversity(universities.get(0));
        account.setUserName("user");
        account.setPassword("user");
        account.setEmail("i.v.zaichenko70@gmail.com");
        account.setFirstName("Ivan");
        account.setLastName("Zaichenko");
        account.setRole("ROLE_USER");
        account.setAddress("Obshaga 20");
        account.setGroupName("IP-64");
        accounts.add(account);

        for (Account a : accounts) {
            accountService.register(a);
        }
        try {
            List<Hostel> hostels = getHostels(universities);

            for (Hostel u : hostels) {
                hostelService.add(u);
            }

            List<Room> rooms = getRooms();

            for (Room r : rooms) {
                roomService.add(r);
            }
        } catch (Exception e) {
            log.debug("Section works only on first run on db creation");
        }
        //*/
        log.debug("Init default done");
    }

    private List<Room> getRooms() {
        List<Room> rooms = new ArrayList<>();

        Room room = new Room();
        room.setName("Ping-pong room");
        room.setFloor(5);
        room.setCapacity(10);
        room.setDescription("Ping-pong room");
        room.setLighting("YES");
        room.setSportsEquipment("YES");
        room.setRecreationArea("YES");
        room.setLunchZone("NO");
        room.setHostel(hostelService.findByName("Hostel 1"));
        rooms.add(room);

        room = new Room();
        room.setName("Gym");
        room.setFloor(12);
        room.setCapacity(50);
        room.setDescription("Gym");
        room.setLighting("YES");
        room.setSportsEquipment("YES");
        room.setRecreationArea("YES");
        room.setLunchZone("YES");
        room.setHostel(hostelService.findByName("Hostel 1"));
        rooms.add(room);

        room = new Room();
        room.setName("Dance room");
        room.setFloor(2);
        room.setCapacity(30);
        room.setDescription("Dance room");
        room.setLighting("YES");
        room.setSportsEquipment("NO");
        room.setRecreationArea("NO");
        room.setLunchZone("NO");
        room.setHostel(hostelService.findByName("Hostel 1"));
        rooms.add(room);

        room = new Room();
        room.setName("Music room");
        room.setFloor(6);
        room.setCapacity(7);
        room.setDescription("Music room");
        room.setLighting("YES");
        room.setSportsEquipment("NO");
        room.setRecreationArea("NO");
        room.setLunchZone("NO");
        room.setHostel(hostelService.findByName("Hostel 1"));
        rooms.add(room);

        room = new Room();
        room.setName("Ping-pong room");
        room.setFloor(5);
        room.setCapacity(10);
        room.setDescription("Ping-pong room");
        room.setLighting("YES");
        room.setSportsEquipment("YES");
        room.setRecreationArea("YES");
        room.setLunchZone("NO");
        room.setHostel(hostelService.findByName("Hostel 3"));
        rooms.add(room);

        room = new Room();
        room.setName("Gym");
        room.setFloor(12);
        room.setCapacity(50);
        room.setDescription("Gym");
        room.setLighting("YES");
        room.setSportsEquipment("YES");
        room.setRecreationArea("YES");
        room.setLunchZone("YES");
        room.setHostel(hostelService.findByName("Hostel 3"));
        rooms.add(room);

        room = new Room();
        room.setName("Dance room");
        room.setFloor(2);
        room.setCapacity(30);
        room.setDescription("Dance room");
        room.setLighting("YES");
        room.setSportsEquipment("NO");
        room.setRecreationArea("NO");
        room.setLunchZone("NO");
        room.setHostel(hostelService.findByName("Hostel 3"));
        rooms.add(room);

        room = new Room();
        room.setName("Music room");
        room.setFloor(6);
        room.setCapacity(7);
        room.setDescription("Music room");
        room.setLighting("YES");
        room.setSportsEquipment("NO");
        room.setRecreationArea("NO");
        room.setLunchZone("NO");
        room.setHostel(hostelService.findByName("Hostel 3"));
        rooms.add(room);

        room = new Room();
        room.setName("Ping-pong room");
        room.setFloor(5);
        room.setCapacity(10);
        room.setDescription("Ping-pong room");
        room.setLighting("YES");
        room.setSportsEquipment("YES");
        room.setRecreationArea("YES");
        room.setLunchZone("NO");
        room.setHostel(hostelService.findByName("Hostel 4"));
        rooms.add(room);

        room = new Room();
        room.setName("Gym");
        room.setFloor(12);
        room.setCapacity(50);
        room.setDescription("Gym");
        room.setLighting("YES");
        room.setSportsEquipment("YES");
        room.setRecreationArea("YES");
        room.setLunchZone("YES");
        room.setHostel(hostelService.findByName("Hostel 4"));
        rooms.add(room);

        room = new Room();
        room.setName("Dance room");
        room.setFloor(2);
        room.setCapacity(30);
        room.setDescription("Dance room");
        room.setLighting("YES");
        room.setSportsEquipment("NO");
        room.setRecreationArea("NO");
        room.setLunchZone("NO");
        room.setHostel(hostelService.findByName("Hostel 4"));
        rooms.add(room);

        room = new Room();
        room.setName("Music room");
        room.setFloor(6);
        room.setCapacity(7);
        room.setDescription("Music room");
        room.setLighting("YES");
        room.setSportsEquipment("NO");
        room.setRecreationArea("NO");
        room.setLunchZone("NO");
        room.setHostel(hostelService.findByName("Hostel 4"));
        rooms.add(room);

        room = new Room();
        room.setName("Ping-pong room");
        room.setFloor(5);
        room.setCapacity(10);
        room.setDescription("Ping-pong room");
        room.setLighting("YES");
        room.setSportsEquipment("YES");
        room.setRecreationArea("YES");
        room.setLunchZone("NO");
        room.setHostel(hostelService.findByName("Hostel 6"));
        rooms.add(room);

        room = new Room();
        room.setName("Gym");
        room.setFloor(12);
        room.setCapacity(50);
        room.setDescription("Gym");
        room.setLighting("YES");
        room.setSportsEquipment("YES");
        room.setRecreationArea("YES");
        room.setLunchZone("YES");
        room.setHostel(hostelService.findByName("Hostel 6"));
        rooms.add(room);

        room = new Room();
        room.setName("Dance room");
        room.setFloor(2);
        room.setCapacity(30);
        room.setDescription("Dance room");
        room.setLighting("YES");
        room.setSportsEquipment("NO");
        room.setRecreationArea("NO");
        room.setLunchZone("NO");
        room.setHostel(hostelService.findByName("Hostel 6"));
        rooms.add(room);

        room = new Room();
        room.setName("Music room");
        room.setFloor(6);
        room.setCapacity(7);
        room.setDescription("Music room");
        room.setLighting("YES");
        room.setSportsEquipment("NO");
        room.setRecreationArea("NO");
        room.setLunchZone("NO");
        room.setHostel(hostelService.findByName("Hostel 6"));
        rooms.add(room);

        room = new Room();
        room.setName("Ping-pong room");
        room.setFloor(5);
        room.setCapacity(10);
        room.setDescription("Ping-pong room");
        room.setLighting("YES");
        room.setSportsEquipment("YES");
        room.setRecreationArea("YES");
        room.setLunchZone("NO");
        room.setHostel(hostelService.findByName("Hostel 7"));
        rooms.add(room);

        room = new Room();
        room.setName("Gym");
        room.setFloor(12);
        room.setCapacity(50);
        room.setDescription("Gym");
        room.setLighting("YES");
        room.setSportsEquipment("YES");
        room.setRecreationArea("YES");
        room.setLunchZone("YES");
        room.setHostel(hostelService.findByName("Hostel 7"));
        rooms.add(room);

        room = new Room();
        room.setName("Dance room");
        room.setFloor(2);
        room.setCapacity(30);
        room.setDescription("Dance room");
        room.setLighting("YES");
        room.setSportsEquipment("NO");
        room.setRecreationArea("NO");
        room.setLunchZone("NO");
        room.setHostel(hostelService.findByName("Hostel 7"));
        rooms.add(room);

        room = new Room();
        room.setName("Music room");
        room.setFloor(6);
        room.setCapacity(7);
        room.setDescription("Music room");
        room.setLighting("YES");
        room.setSportsEquipment("NO");
        room.setRecreationArea("NO");
        room.setLunchZone("NO");
        room.setHostel(hostelService.findByName("Hostel 7"));
        rooms.add(room);

        room = new Room();
        room.setName("Ping-pong room");
        room.setFloor(5);
        room.setCapacity(10);
        room.setDescription("Ping-pong room");
        room.setLighting("YES");
        room.setSportsEquipment("YES");
        room.setRecreationArea("YES");
        room.setLunchZone("NO");
        room.setHostel(hostelService.findByName("Hostel 8"));
        rooms.add(room);

        room = new Room();
        room.setName("Gym");
        room.setFloor(12);
        room.setCapacity(50);
        room.setDescription("Gym");
        room.setLighting("YES");
        room.setSportsEquipment("YES");
        room.setRecreationArea("YES");
        room.setLunchZone("YES");
        room.setHostel(hostelService.findByName("Hostel 8"));
        rooms.add(room);

        room = new Room();
        room.setName("Dance room");
        room.setFloor(2);
        room.setCapacity(30);
        room.setDescription("Dance room");
        room.setLighting("YES");
        room.setSportsEquipment("NO");
        room.setRecreationArea("NO");
        room.setLunchZone("NO");
        room.setHostel(hostelService.findByName("Hostel 8"));
        rooms.add(room);

        room = new Room();
        room.setName("Music room");
        room.setFloor(6);
        room.setCapacity(7);
        room.setDescription("Music room");
        room.setLighting("YES");
        room.setSportsEquipment("NO");
        room.setRecreationArea("NO");
        room.setLunchZone("NO");
        room.setHostel(hostelService.findByName("Hostel 8"));
        rooms.add(room);

        room = new Room();
        room.setName("Ping-pong room");
        room.setFloor(5);
        room.setCapacity(10);
        room.setDescription("Ping-pong room");
        room.setLighting("YES");
        room.setSportsEquipment("YES");
        room.setRecreationArea("YES");
        room.setLunchZone("NO");
        room.setHostel(hostelService.findByName("Hostel 10"));
        rooms.add(room);

        room = new Room();
        room.setName("Gym");
        room.setFloor(12);
        room.setCapacity(50);
        room.setDescription("Gym");
        room.setLighting("YES");
        room.setSportsEquipment("YES");
        room.setRecreationArea("YES");
        room.setLunchZone("YES");
        room.setHostel(hostelService.findByName("Hostel 10"));
        rooms.add(room);

        room = new Room();
        room.setName("Dance room");
        room.setFloor(2);
        room.setCapacity(30);
        room.setDescription("Dance room");
        room.setLighting("YES");
        room.setSportsEquipment("NO");
        room.setRecreationArea("NO");
        room.setLunchZone("NO");
        room.setHostel(hostelService.findByName("Hostel 10"));
        rooms.add(room);

        room = new Room();
        room.setName("Music room");
        room.setFloor(6);
        room.setCapacity(7);
        room.setDescription("Music room");
        room.setLighting("YES");
        room.setSportsEquipment("NO");
        room.setRecreationArea("NO");
        room.setLunchZone("NO");
        room.setHostel(hostelService.findByName("Hostel 10"));
        rooms.add(room);

        room = new Room();
        room.setName("Ping-pong room");
        room.setFloor(5);
        room.setCapacity(10);
        room.setDescription("Ping-pong room");
        room.setLighting("YES");
        room.setSportsEquipment("YES");
        room.setRecreationArea("YES");
        room.setLunchZone("NO");
        room.setHostel(hostelService.findByName("Hostel 11"));
        rooms.add(room);

        room = new Room();
        room.setName("Gym");
        room.setFloor(12);
        room.setCapacity(50);
        room.setDescription("Gym");
        room.setLighting("YES");
        room.setSportsEquipment("YES");
        room.setRecreationArea("YES");
        room.setLunchZone("YES");
        room.setHostel(hostelService.findByName("Hostel 11"));
        rooms.add(room);

        room = new Room();
        room.setName("Dance room");
        room.setFloor(2);
        room.setCapacity(30);
        room.setDescription("Dance room");
        room.setLighting("YES");
        room.setSportsEquipment("NO");
        room.setRecreationArea("NO");
        room.setLunchZone("NO");
        room.setHostel(hostelService.findByName("Hostel 11"));
        rooms.add(room);

        room = new Room();
        room.setName("Music room");
        room.setFloor(6);
        room.setCapacity(7);
        room.setDescription("Music room");
        room.setLighting("YES");
        room.setSportsEquipment("NO");
        room.setRecreationArea("NO");
        room.setLunchZone("NO");
        room.setHostel(hostelService.findByName("Hostel 11"));
        rooms.add(room);

        room = new Room();
        room.setName("Ping-pong room");
        room.setFloor(5);
        room.setCapacity(10);
        room.setDescription("Ping-pong room");
        room.setLighting("YES");
        room.setSportsEquipment("YES");
        room.setRecreationArea("YES");
        room.setLunchZone("NO");
        room.setHostel(hostelService.findByName("Hostel 12"));
        rooms.add(room);

        room = new Room();
        room.setName("Gym");
        room.setFloor(12);
        room.setCapacity(50);
        room.setDescription("Gym");
        room.setLighting("YES");
        room.setSportsEquipment("YES");
        room.setRecreationArea("YES");
        room.setLunchZone("YES");
        room.setHostel(hostelService.findByName("Hostel 12"));
        rooms.add(room);

        room = new Room();
        room.setName("Dance room");
        room.setFloor(2);
        room.setCapacity(30);
        room.setDescription("Dance room");
        room.setLighting("YES");
        room.setSportsEquipment("NO");
        room.setRecreationArea("NO");
        room.setLunchZone("NO");
        room.setHostel(hostelService.findByName("Hostel 12"));
        rooms.add(room);

        room = new Room();
        room.setName("Music room");
        room.setFloor(6);
        room.setCapacity(7);
        room.setDescription("Music room");
        room.setLighting("YES");
        room.setSportsEquipment("NO");
        room.setRecreationArea("NO");
        room.setLunchZone("NO");
        room.setHostel(hostelService.findByName("Hostel 12"));
        rooms.add(room);

        room = new Room();
        room.setName("Ping-pong room");
        room.setFloor(5);
        room.setCapacity(10);
        room.setDescription("Ping-pong room");
        room.setLighting("YES");
        room.setSportsEquipment("YES");
        room.setRecreationArea("YES");
        room.setLunchZone("NO");
        room.setHostel(hostelService.findByName("Hostel 13"));
        rooms.add(room);

        room = new Room();
        room.setName("Gym");
        room.setFloor(12);
        room.setCapacity(50);
        room.setDescription("Gym");
        room.setLighting("YES");
        room.setSportsEquipment("YES");
        room.setRecreationArea("YES");
        room.setLunchZone("YES");
        room.setHostel(hostelService.findByName("Hostel 13"));
        rooms.add(room);

        room = new Room();
        room.setName("Dance room");
        room.setFloor(2);
        room.setCapacity(30);
        room.setDescription("Dance room");
        room.setLighting("YES");
        room.setSportsEquipment("NO");
        room.setRecreationArea("NO");
        room.setLunchZone("NO");
        room.setHostel(hostelService.findByName("Hostel 13"));
        rooms.add(room);

        room = new Room();
        room.setName("Music room");
        room.setFloor(6);
        room.setCapacity(7);
        room.setDescription("Music room");
        room.setLighting("YES");
        room.setSportsEquipment("NO");
        room.setRecreationArea("NO");
        room.setLunchZone("NO");
        room.setHostel(hostelService.findByName("Hostel 13"));
        rooms.add(room);

        room = new Room();
        room.setName("Ping-pong room");
        room.setFloor(5);
        room.setCapacity(10);
        room.setDescription("Ping-pong room");
        room.setLighting("YES");
        room.setSportsEquipment("YES");
        room.setRecreationArea("YES");
        room.setLunchZone("NO");
        room.setHostel(hostelService.findByName("Hostel 14"));
        rooms.add(room);

        room = new Room();
        room.setName("Gym");
        room.setFloor(12);
        room.setCapacity(50);
        room.setDescription("Gym");
        room.setLighting("YES");
        room.setSportsEquipment("YES");
        room.setRecreationArea("YES");
        room.setLunchZone("YES");
        room.setHostel(hostelService.findByName("Hostel 14"));
        rooms.add(room);

        room = new Room();
        room.setName("Dance room");
        room.setFloor(2);
        room.setCapacity(30);
        room.setDescription("Dance room");
        room.setLighting("YES");
        room.setSportsEquipment("NO");
        room.setRecreationArea("NO");
        room.setLunchZone("NO");
        room.setHostel(hostelService.findByName("Hostel 14"));
        rooms.add(room);

        room = new Room();
        room.setName("Music room");
        room.setFloor(6);
        room.setCapacity(7);
        room.setDescription("Music room");
        room.setLighting("YES");
        room.setSportsEquipment("NO");
        room.setRecreationArea("NO");
        room.setLunchZone("NO");
        room.setHostel(hostelService.findByName("Hostel 14"));
        rooms.add(room);

        room = new Room();
        room.setName("Ping-pong room");
        room.setFloor(5);
        room.setCapacity(10);
        room.setDescription("Ping-pong room");
        room.setLighting("YES");
        room.setSportsEquipment("YES");
        room.setRecreationArea("YES");
        room.setLunchZone("NO");
        room.setHostel(hostelService.findByName("Hostel 15"));
        rooms.add(room);

        room = new Room();
        room.setName("Gym");
        room.setFloor(12);
        room.setCapacity(50);
        room.setDescription("Gym");
        room.setLighting("YES");
        room.setSportsEquipment("YES");
        room.setRecreationArea("YES");
        room.setLunchZone("YES");
        room.setHostel(hostelService.findByName("Hostel 15"));
        rooms.add(room);

        room = new Room();
        room.setName("Dance room");
        room.setFloor(2);
        room.setCapacity(30);
        room.setDescription("Dance room");
        room.setLighting("YES");
        room.setSportsEquipment("NO");
        room.setRecreationArea("NO");
        room.setLunchZone("NO");
        room.setHostel(hostelService.findByName("Hostel 15"));
        rooms.add(room);

        room = new Room();
        room.setName("Music room");
        room.setFloor(6);
        room.setCapacity(7);
        room.setDescription("Music room");
        room.setLighting("YES");
        room.setSportsEquipment("NO");
        room.setRecreationArea("NO");
        room.setLunchZone("NO");
        room.setHostel(hostelService.findByName("Hostel 15"));
        rooms.add(room);

        room = new Room();
        room.setName("Ping-pong room");
        room.setFloor(5);
        room.setCapacity(10);
        room.setDescription("Ping-pong room");
        room.setLighting("YES");
        room.setSportsEquipment("YES");
        room.setRecreationArea("YES");
        room.setLunchZone("NO");
        room.setHostel(hostelService.findByName("Hostel 16"));
        rooms.add(room);

        room = new Room();
        room.setName("Gym");
        room.setFloor(12);
        room.setCapacity(50);
        room.setDescription("Gym");
        room.setLighting("YES");
        room.setSportsEquipment("YES");
        room.setRecreationArea("YES");
        room.setLunchZone("YES");
        room.setHostel(hostelService.findByName("Hostel 16"));
        rooms.add(room);

        room = new Room();
        room.setName("Dance room");
        room.setFloor(2);
        room.setCapacity(30);
        room.setDescription("Dance room");
        room.setLighting("YES");
        room.setSportsEquipment("NO");
        room.setRecreationArea("NO");
        room.setLunchZone("NO");
        room.setHostel(hostelService.findByName("Hostel 16"));
        rooms.add(room);

        room = new Room();
        room.setName("Music room");
        room.setFloor(6);
        room.setCapacity(7);
        room.setDescription("Music room");
        room.setLighting("YES");
        room.setSportsEquipment("NO");
        room.setRecreationArea("NO");
        room.setLunchZone("NO");
        room.setHostel(hostelService.findByName("Hostel 16"));
        rooms.add(room);

        room = new Room();
        room.setName("Ping-pong room");
        room.setFloor(5);
        room.setCapacity(10);
        room.setDescription("Ping-pong room");
        room.setLighting("YES");
        room.setSportsEquipment("YES");
        room.setRecreationArea("YES");
        room.setLunchZone("NO");
        room.setHostel(hostelService.findByName("Hostel 17"));
        rooms.add(room);

        room = new Room();
        room.setName("Gym");
        room.setFloor(12);
        room.setCapacity(50);
        room.setDescription("Gym");
        room.setLighting("YES");
        room.setSportsEquipment("YES");
        room.setRecreationArea("YES");
        room.setLunchZone("YES");
        room.setHostel(hostelService.findByName("Hostel 17"));
        rooms.add(room);

        room = new Room();
        room.setName("Dance room");
        room.setFloor(2);
        room.setCapacity(30);
        room.setDescription("Dance room");
        room.setLighting("YES");
        room.setSportsEquipment("NO");
        room.setRecreationArea("NO");
        room.setLunchZone("NO");
        room.setHostel(hostelService.findByName("Hostel 17"));
        rooms.add(room);

        room = new Room();
        room.setName("Music room");
        room.setFloor(6);
        room.setCapacity(7);
        room.setDescription("Music room");
        room.setLighting("YES");
        room.setSportsEquipment("NO");
        room.setRecreationArea("NO");
        room.setLunchZone("NO");
        room.setHostel(hostelService.findByName("Hostel 17"));
        rooms.add(room);

        room = new Room();
        room.setName("Ping-pong room");
        room.setFloor(5);
        room.setCapacity(10);
        room.setDescription("Ping-pong room");
        room.setLighting("YES");
        room.setSportsEquipment("YES");
        room.setRecreationArea("YES");
        room.setLunchZone("NO");
        room.setHostel(hostelService.findByName("Hostel 18"));
        rooms.add(room);

        room = new Room();
        room.setName("Gym");
        room.setFloor(12);
        room.setCapacity(50);
        room.setDescription("Gym");
        room.setLighting("YES");
        room.setSportsEquipment("YES");
        room.setRecreationArea("YES");
        room.setLunchZone("YES");
        room.setHostel(hostelService.findByName("Hostel 18"));
        rooms.add(room);

        room = new Room();
        room.setName("Dance room");
        room.setFloor(2);
        room.setCapacity(30);
        room.setDescription("Dance room");
        room.setLighting("YES");
        room.setSportsEquipment("NO");
        room.setRecreationArea("NO");
        room.setLunchZone("NO");
        room.setHostel(hostelService.findByName("Hostel 18"));
        rooms.add(room);

        room = new Room();
        room.setName("Music room");
        room.setFloor(6);
        room.setCapacity(7);
        room.setDescription("Music room");
        room.setLighting("YES");
        room.setSportsEquipment("NO");
        room.setRecreationArea("NO");
        room.setLunchZone("NO");
        room.setHostel(hostelService.findByName("Hostel 18"));
        rooms.add(room);

        room = new Room();
        room.setName("Ping-pong room");
        room.setFloor(5);
        room.setCapacity(10);
        room.setDescription("Ping-pong room");
        room.setLighting("YES");
        room.setSportsEquipment("YES");
        room.setRecreationArea("YES");
        room.setLunchZone("NO");
        room.setHostel(hostelService.findByName("Hostel 19"));
        rooms.add(room);

        room = new Room();
        room.setName("Gym");
        room.setFloor(12);
        room.setCapacity(50);
        room.setDescription("Gym");
        room.setLighting("YES");
        room.setSportsEquipment("YES");
        room.setRecreationArea("YES");
        room.setLunchZone("YES");
        room.setHostel(hostelService.findByName("Hostel 19"));
        rooms.add(room);

        room = new Room();
        room.setName("Dance room");
        room.setFloor(2);
        room.setCapacity(30);
        room.setDescription("Dance room");
        room.setLighting("YES");
        room.setSportsEquipment("NO");
        room.setRecreationArea("NO");
        room.setLunchZone("NO");
        room.setHostel(hostelService.findByName("Hostel 19"));
        rooms.add(room);

        room = new Room();
        room.setName("Music room");
        room.setFloor(6);
        room.setCapacity(7);
        room.setDescription("Music room");
        room.setLighting("YES");
        room.setSportsEquipment("NO");
        room.setRecreationArea("NO");
        room.setLunchZone("NO");
        room.setHostel(hostelService.findByName("Hostel 19"));
        rooms.add(room);

        room = new Room();
        room.setName("Ping-pong room");
        room.setFloor(5);
        room.setCapacity(10);
        room.setDescription("Ping-pong room");
        room.setLighting("YES");
        room.setSportsEquipment("YES");
        room.setRecreationArea("YES");
        room.setLunchZone("NO");
        room.setHostel(hostelService.findByName("Hostel 20"));
        rooms.add(room);

        room = new Room();
        room.setName("Gym");
        room.setFloor(12);
        room.setCapacity(50);
        room.setDescription("Gym");
        room.setLighting("YES");
        room.setSportsEquipment("YES");
        room.setRecreationArea("YES");
        room.setLunchZone("YES");
        room.setHostel(hostelService.findByName("Hostel 20"));
        rooms.add(room);

        room = new Room();
        room.setName("Dance room");
        room.setFloor(2);
        room.setCapacity(30);
        room.setDescription("Dance room");
        room.setLighting("YES");
        room.setSportsEquipment("NO");
        room.setRecreationArea("NO");
        room.setLunchZone("NO");
        room.setHostel(hostelService.findByName("Hostel 20"));
        rooms.add(room);

        room = new Room();
        room.setName("Music room");
        room.setFloor(6);
        room.setCapacity(7);
        room.setDescription("Music room");
        room.setLighting("YES");
        room.setSportsEquipment("NO");
        room.setRecreationArea("NO");
        room.setLunchZone("NO");
        room.setHostel(hostelService.findByName("Hostel 20"));
        rooms.add(room);

        room = new Room();
        room.setName("Ping-pong room");
        room.setFloor(5);
        room.setCapacity(10);
        room.setDescription("Ping-pong room");
        room.setLighting("YES");
        room.setSportsEquipment("YES");
        room.setRecreationArea("YES");
        room.setLunchZone("NO");
        room.setHostel(hostelService.findByName("Hostel 21"));
        rooms.add(room);

        room = new Room();
        room.setName("Gym");
        room.setFloor(12);
        room.setCapacity(50);
        room.setDescription("Gym");
        room.setLighting("YES");
        room.setSportsEquipment("YES");
        room.setRecreationArea("YES");
        room.setLunchZone("YES");
        room.setHostel(hostelService.findByName("Hostel 21"));
        rooms.add(room);

        room = new Room();
        room.setName("Dance room");
        room.setFloor(2);
        room.setCapacity(30);
        room.setDescription("Dance room");
        room.setLighting("YES");
        room.setSportsEquipment("NO");
        room.setRecreationArea("NO");
        room.setLunchZone("NO");
        room.setHostel(hostelService.findByName("Hostel 21"));
        rooms.add(room);

        room = new Room();
        room.setName("Music room");
        room.setFloor(6);
        room.setCapacity(7);
        room.setDescription("Music room");
        room.setLighting("YES");
        room.setSportsEquipment("NO");
        room.setRecreationArea("NO");
        room.setLunchZone("NO");
        room.setHostel(hostelService.findByName("Hostel 21"));
        rooms.add(room);

        room = new Room();
        room.setName("Ping-pong room");
        room.setFloor(5);
        room.setCapacity(10);
        room.setDescription("Ping-pong room");
        room.setLighting("YES");
        room.setSportsEquipment("YES");
        room.setRecreationArea("YES");
        room.setLunchZone("NO");
        room.setHostel(hostelService.findByName("Hostel 22"));
        rooms.add(room);

        room = new Room();
        room.setName("Gym");
        room.setFloor(12);
        room.setCapacity(50);
        room.setDescription("Gym");
        room.setLighting("YES");
        room.setSportsEquipment("YES");
        room.setRecreationArea("YES");
        room.setLunchZone("YES");
        room.setHostel(hostelService.findByName("Hostel 22"));
        rooms.add(room);

        room = new Room();
        room.setName("Dance room");
        room.setFloor(2);
        room.setCapacity(30);
        room.setDescription("Dance room");
        room.setLighting("YES");
        room.setSportsEquipment("NO");
        room.setRecreationArea("NO");
        room.setLunchZone("NO");
        room.setHostel(hostelService.findByName("Hostel 22"));
        rooms.add(room);

        room = new Room();
        room.setName("Music room");
        room.setFloor(6);
        room.setCapacity(7);
        room.setDescription("Music room");
        room.setLighting("YES");
        room.setSportsEquipment("NO");
        room.setRecreationArea("NO");
        room.setLunchZone("NO");
        room.setHostel(hostelService.findByName("Hostel 22"));
        rooms.add(room);

        return rooms;
    }

    private List<Hostel> getHostels(List<University> universities) {
        List<Hostel> hostels = new ArrayList<Hostel>();

        Hostel hostel1 = new Hostel();
        hostel1.setName("Hostel 1");
        hostel1.setAddress("");
        hostel1.setUniversity(universities.get(0));
        hostels.add(hostel1);

        Hostel hostel3 = new Hostel();
        hostel3.setName("Hostel 3");
        hostel3.setAddress("");
        hostel3.setUniversity(universities.get(0));
        hostels.add(hostel3);

        Hostel hostel4 = new Hostel();
        hostel4.setName("Hostel 4");
        hostel4.setAddress("");
        hostel4.setUniversity(universities.get(0));
        hostels.add(hostel4);

        Hostel hostel6 = new Hostel();
        hostel6.setName("Hostel 6");
        hostel6.setAddress("");
        hostel6.setUniversity(universities.get(0));
        hostels.add(hostel6);

        Hostel hostel7 = new Hostel();
        hostel7.setName("Hostel 7");
        hostel7.setAddress("");
        hostel7.setUniversity(universities.get(0));
        hostels.add(hostel7);

        Hostel hostel8 = new Hostel();
        hostel8.setName("Hostel 8");
        hostel8.setAddress("");
        hostel8.setUniversity(universities.get(0));
        hostels.add(hostel8);

        Hostel hostel10 = new Hostel();
        hostel10.setName("Hostel 10");
        hostel10.setAddress("");
        hostel10.setUniversity(universities.get(0));
        hostels.add(hostel10);

        Hostel hostel11 = new Hostel();
        hostel11.setName("Hostel 11");
        hostel11.setAddress("");
        hostel11.setUniversity(universities.get(0));
        hostels.add(hostel11);

        Hostel hostel12 = new Hostel();
        hostel12.setName("Hostel 12");
        hostel12.setAddress("");
        hostel12.setUniversity(universities.get(0));
        hostels.add(hostel12);

        Hostel hostel13 = new Hostel();
        hostel13.setName("Hostel 13");
        hostel13.setAddress("");
        hostel13.setUniversity(universities.get(0));
        hostels.add(hostel13);

        Hostel hostel14 = new Hostel();
        hostel14.setName("Hostel 14");
        hostel14.setAddress("");
        hostel14.setUniversity(universities.get(0));
        hostels.add(hostel14);

        Hostel hostel15 = new Hostel();
        hostel15.setName("Hostel 15");
        hostel15.setAddress("");
        hostel15.setUniversity(universities.get(0));
        hostels.add(hostel15);

        Hostel hostel16 = new Hostel();
        hostel16.setName("Hostel 16");
        hostel16.setAddress("");
        hostel16.setUniversity(universities.get(0));
        hostels.add(hostel16);

        Hostel hostel17 = new Hostel();
        hostel17.setName("Hostel 17");
        hostel17.setAddress("");
        hostel17.setUniversity(universities.get(0));
        hostels.add(hostel17);

        Hostel hostel18 = new Hostel();
        hostel18.setName("Hostel 18");
        hostel18.setAddress("");
        hostel18.setUniversity(universities.get(0));
        hostels.add(hostel18);

        Hostel hostel19 = new Hostel();
        hostel19.setName("Hostel 19");
        hostel19.setAddress("");
        hostel19.setUniversity(universities.get(0));
        hostels.add(hostel19);

        Hostel hostel20 = new Hostel();
        hostel20.setName("Hostel 20");
        hostel20.setAddress("");
        hostel20.setUniversity(universities.get(0));
        hostels.add(hostel20);

        Hostel hostel21 = new Hostel();
        hostel21.setName("Hostel 21");
        hostel21.setAddress("");
        hostel21.setUniversity(universities.get(0));
        hostels.add(hostel21);

        Hostel hostel22 = new Hostel();
        hostel22.setName("Hostel 22");
        hostel22.setAddress("");
        hostel22.setUniversity(universities.get(0));
        hostels.add(hostel22);

        return hostels;
    }
}
