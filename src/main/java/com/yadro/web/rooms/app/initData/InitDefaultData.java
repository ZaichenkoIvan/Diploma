package com.yadro.web.rooms.app.initData;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import com.yadro.web.rooms.app.model.Hostel;
import com.yadro.web.rooms.app.model.Room;
import com.yadro.web.rooms.app.model.University;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yadro.web.rooms.app.model.Account;
import com.yadro.web.rooms.app.service.AccountService;
import com.yadro.web.rooms.app.service.UniversityService;
import com.yadro.web.rooms.app.service.RoomService;
import com.yadro.web.rooms.app.service.HostelService;

import lombok.extern.slf4j.Slf4j;

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

		for(University o : universities) {
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

		for(Account a : accounts) {
			accountService.register(a);
		}
		try {
			List<Hostel> hostels = new ArrayList<Hostel>();
			Hostel hostel;

			hostel = new Hostel();
			hostel.setName("Yadro");
			hostel.setAddress("7 building KPI");
			hostel.setUniversity(universities.get(0));
			hostels.add(hostel);

			hostel = new Hostel();
			hostel.setName("24 building of KPI");
			hostel.setAddress("24 building of KPI");
			hostel.setUniversity(universities.get(0));
			hostels.add(hostel);

			for(Hostel u : hostels) {
				hostelService.add(u);
			}

			List<Room> rooms = new ArrayList<Room>();
			Room room;

			room = new Room();
			room.setName("Left Room");
			room.setBall(2);
			room.setManish(22);
			room.setWc("YES");
			room.setShower("YES");
			room.setGrass("NO");
			room.setProjector("YES");
			room.setChangingRoom("YES");
			room.setHostel(hostelService.findByName("Yadro"));
			rooms.add(room);

			room = new Room();
			room.setName("Right room");
			room.setBall(2);
			room.setManish(20);
			room.setWc("YES");
			room.setShower("YES");
			room.setGrass("NO");
			room.setProjector("NO");
			room.setChangingRoom("YES");
			room.setHostel(hostelService.findByName("Yadro"));
			rooms.add(room);

			room = new Room();
			room.setName("Full room");
			room.setBall(4);
			room.setManish(45);
			room.setWc("YES");
			room.setShower("YES");
			room.setGrass("YES");
			room.setProjector("YES");
			room.setChangingRoom("YES");
			room.setHostel(hostelService.findByName("24 building of KPI"));
			rooms.add(room);

			for(Room r : rooms) {
				roomService.add(r);
			}
		}
		catch(Exception e){
			log.debug("Section works only on first run on db creation");
		}
		//*/
		log.debug("Init default done");
	}
}
