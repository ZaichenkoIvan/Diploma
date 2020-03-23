package com.yadro.web.rooms.app.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.yadro.web.rooms.app.model.Stadium;
import com.yadro.web.rooms.app.model.University;
import com.yadro.web.rooms.app.repository.StadiumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StadiumService {
	@Autowired
	private StadiumRepository stadiumRepository;
	
	@Autowired
	private PitchService pitchService;
	
	public Stadium findById(Long id) {
		Stadium stadium = stadiumRepository.findById(id);
		return stadium;
	}
	
	public Stadium findByName(String name) {
		Stadium stadium = stadiumRepository.findByName(name);
		return stadium;
	}
	
	public List<Stadium> list() {
		return stadiumRepository.findAll();
	}
	
    public Boolean delete(Long id) {
        this.stadiumRepository.delete(id);
        return true;
    }

    public Stadium add(Stadium stadium) {
    	if (this.stadiumRepository.findByNameAndUniversity(stadium.getName(), stadium.getUniversity()) == null) {
    		this.stadiumRepository.save(stadium);
    		return stadium;
    	} else {
    		return null;
    	}
    }
    
    public Stadium update(Stadium stadium) {
        return update(stadium.getId(), stadium);
    }
    
    public Stadium update(Long id, Stadium newData) {
    	
    	String checkName = newData.getName();
    	Long checkId = id;
    	
    	Stadium check = this.stadiumRepository.findByNameAndUniversity(newData.getName(), newData.getUniversity());
    	if (check != null) {
    		checkName = check.getName();
    		checkId = check.getId();
    	}

    	if ( checkName.equals(newData.getName()) || checkId.equals(id)) {
            this.stadiumRepository.updateStadium(
                    id, 
                    newData.getName(),
                    newData.getAddress(),
                    newData.getUniversity());
            return newData;
    	} else {
    		return null;
    	}
    }
	
	public List<List<String>> listTable() {
		List<Stadium> stadiums = stadiumRepository.findAll();
	    List<List<String>> stadiumsList = new LinkedList<List<String>>();
	    
	    Comparator<Stadium> comparator = new Comparator<Stadium>() {
		    @Override
		    public int compare(Stadium left, Stadium right) {
		        return (int) (left.getId() - right.getId());
		    }
		};
		Collections.sort(stadiums, comparator);
	    
	    for (Stadium stadium : stadiums) {
	    	List<String> stadiumData = new ArrayList<String>();
	    	
	    	stadiumData.add("<a style=\"color: #f9b012\" href=\"/stadium/edit/" + stadium.getId() + "\"><svg xmlns=\"http://www.w3.org/2000/svg\" viewBox=\"0 0 24 24\" width=\"24\" height=\"24\"><path d=\"M3 17.25V21h3.75L17.81 9.94l-3.75-3.75L3 17.25z\"></path><path d=\"M20.71 7.04c.39-.39.39-1.02 0-1.41l-2.34-2.34c-.39-.39-1.02-.39-1.41 0l-1.83 1.83 3.75 3.75 1.83-1.83z\"></path></svg></a>");
	    	stadiumData.add(stadium.getName());
	    	
	    	stadiumData.add(stadium.getUniversity().getName());
	    	
	    	int count = pitchService.countInStadium(stadium);
	    	stadiumData.add(String.valueOf(count));
	    	
	    	if (count != 0) {
	    		stadiumData.add("<svg xmlns=\"http://www.w3.org/2000/svg\" viewBox=\"0 0 24 24\" width=\"24\" height=\"24\"><path d=\"M6 19c0 1.1.9 2 2 2h8c1.1 0 2-.9 2-2V7H6v12z\" fill=\"#E4E4E4\" ></path><path d=\"M19 4h-3.5l-1-1h-5l-1 1H5v2h14V4z\" fill=\"#E4E4E4\"></path></svg>");
	    	}else{
	    		stadiumData.add("<a style=\"color: #f9b012\" href=\"/stadium/delete?id=" + stadium.getId() + "\"><svg xmlns=\"http://www.w3.org/2000/svg\" viewBox=\"0 0 24 24\" width=\"24\" height=\"24\"><path d=\"M6 19c0 1.1.9 2 2 2h8c1.1 0 2-.9 2-2V7H6v12z\"></path><path d=\"M19 4h-3.5l-1-1h-5l-1 1H5v2h14V4z\"></path></svg></a>");
	    	}
	    	
	    	stadiumsList.add(stadiumData);
	    }
		return stadiumsList;
	}
	
	public List<List<String>> listTable(University university) {
		List<Stadium> stadiums = stadiumRepository.findAll();
	    List<List<String>> stadiumsList = new LinkedList<List<String>>();
	    
	    Comparator<Stadium> comparator = new Comparator<Stadium>() {
		    @Override
		    public int compare(Stadium left, Stadium right) {
		        return (int) (left.getId() - right.getId());
		    }
		};
		Collections.sort(stadiums, comparator);
	    
	    for (Stadium stadium : stadiums) {
	    	List<String> stadiumData = new ArrayList<String>();
	    	if (stadium.getUniversity().getId().equals(university.getId())) {
	    		stadiumData.add("<a style=\"color: #000000\" href=\"/book/stadium/" + stadium.getId() + "\"><svg fill=\"#000000\" height=\"24\" viewBox=\"0 0 24 24\" width=\"24\" xmlns=\"http://www.w3.org/2000/svg\"><path d=\"M8.59 16.34l4.58-4.59-4.58-4.59L10 5.75l6 6-6 6z\"/><path d=\"M0-.25h24v24H0z\" fill=\"none\"/></svg></a>");
		    	stadiumData.add(stadium.getName());
		    	stadiumsList.add(stadiumData);
	    	}
	    }
		return stadiumsList;
	}
	
	public Map<String, Object> listExport() {
		List<Stadium> stadiums = stadiumRepository.findAll();
		
	    Comparator<Stadium> comparator = new Comparator<Stadium>() {
		    @Override
		    public int compare(Stadium left, Stadium right) {
		        return (int) (left.getId() - right.getId());
		    }
		};
		Collections.sort(stadiums, comparator);
		
		Map<String, Object> linkedHashMap = new LinkedHashMap<String, Object>();
		for (Stadium stadium : stadiums) {
			Map<String, Object> linkedHashMapMap = new LinkedHashMap<String, Object>();
			linkedHashMapMap.put("id", stadium.getId());
			linkedHashMapMap.put("name", stadium.getName());
			linkedHashMapMap.put("address", stadium.getName());
			linkedHashMapMap.put("pitchesCount", pitchService.countInStadium(stadium));
			linkedHashMap.put("stadium " + String.valueOf(stadium.getId()), linkedHashMapMap);
		}
		return linkedHashMap;
	}
	
	public int countInUniversity(University university) {
		return this.stadiumRepository.findByUniversity(university).size();
	}
	
	public List<Stadium> universityStadiumList(University university) {
		return this.stadiumRepository.findByUniversity(university);
	}
}
