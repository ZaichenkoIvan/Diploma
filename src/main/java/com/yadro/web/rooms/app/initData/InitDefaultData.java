package com.yadro.web.rooms.app.initData;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import com.yadro.web.rooms.app.model.Pitch;
import com.yadro.web.rooms.app.model.Stadium;
import com.yadro.web.rooms.app.model.University;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yadro.web.rooms.app.model.Account;
import com.yadro.web.rooms.app.service.AccountService;
import com.yadro.web.rooms.app.service.UniversityService;
import com.yadro.web.rooms.app.service.PitchService;
import com.yadro.web.rooms.app.service.StadiumService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class InitDefaultData {

	@Autowired
	private AccountService accountService;

	@Autowired
	private UniversityService universityService;

	@Autowired
	private StadiumService stadiumService;

	@Autowired
	private PitchService pitchService;

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
			List<Stadium> stadiums = new ArrayList<Stadium>();
			Stadium stadium;

			stadium = new Stadium();
			stadium.setName("Yadro");
			stadium.setAddress("7 building KPI");
			stadium.setUniversity(universities.get(0));
			stadiums.add(stadium);

			stadium = new Stadium();
			stadium.setName("24 building of KPI");
			stadium.setAddress("24 building of KPI");
			stadium.setUniversity(universities.get(0));
			stadiums.add(stadium);

			for(Stadium u : stadiums) {
				stadiumService.add(u);
			}

			List<Pitch> pitches = new ArrayList<Pitch>();
			Pitch pitch;

			pitch = new Pitch();
			pitch.setName("Left Pitch");
			pitch.setBall(2);
			pitch.setManish(22);
			pitch.setWc("YES");
			pitch.setShower("YES");
			pitch.setGrass("NO");
			pitch.setProjector("YES");
			pitch.setChangingRoom("YES");
			pitch.setStadium(stadiumService.findByName("Yadro"));
			pitches.add(pitch);

			pitch = new Pitch();
			pitch.setName("Right Pitch");
			pitch.setBall(2);
			pitch.setManish(20);
			pitch.setWc("YES");
			pitch.setShower("YES");
			pitch.setGrass("NO");
			pitch.setProjector("NO");
			pitch.setChangingRoom("YES");
			pitch.setStadium(stadiumService.findByName("Yadro"));
			pitches.add(pitch);

			pitch = new Pitch();
			pitch.setName("Full Pitch");
			pitch.setBall(4);
			pitch.setManish(45);
			pitch.setWc("YES");
			pitch.setShower("YES");
			pitch.setGrass("YES");
			pitch.setProjector("YES");
			pitch.setChangingRoom("YES");
			pitch.setStadium(stadiumService.findByName("24 building of KPI"));
			pitches.add(pitch);

			for(Pitch r : pitches) {
				pitchService.add(r);
			}
		}
		catch(Exception e){
			log.debug("Section works only on first run on db creation");
		}
		//*/
		log.debug("Init default done");
	}
}
