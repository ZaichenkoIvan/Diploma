package com.yadro.web.rooms.app.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.yadro.web.rooms.app.model.University;
import com.yadro.web.rooms.app.repository.UniversityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UniversityService {
	@Autowired
	private UniversityRepository universityRepository;
	
	@Autowired
	private AccountService  accountService;
	
	@Autowired
	private HostelService hostelService;
	
	public University findById(Long id) {
		University university = universityRepository.findById(id);
		return university;
	}
	
	public List<University> list() {
		return universityRepository.findAll();
	}
	
    public Boolean delete(Long id) {
        this.universityRepository.delete(id);
        return true;
    }
    
    public University add(University university) {
    	if (this.universityRepository.findByName(university.getName()) == null) {
    		this.universityRepository.save(university);
    		return university;
    	} else {
    		return null;
    	}
    }
    
    public University update(University university) {
        return update(university.getId(), university);
    }
    
    public University update(Long id, University newData) {
    	if (this.universityRepository.findByName(newData.getName()) == null) {
            this.universityRepository.updateUniversity(
                    id, 
                    newData.getName());
            return newData;
    	} else {
    		return null;
    	}
    }
	
	public List<List<String>> listTable() {
		List<University> universities = universityRepository.findAll();
	    List<List<String>> universitiesList = new LinkedList<List<String>>();
	    
	    Comparator<University> comparator = new Comparator<University>() {
		    @Override
		    public int compare(University left, University right) {
		        return (int) (left.getId() - right.getId());
		    }
		};
		Collections.sort(universities, comparator);
	    
	    for (University university : universities) {
	    	List<String> universityData = new ArrayList<String>();
	    	
	    	universityData.add("<a style=\"color: #f9b012\" href=\"/university/edit/" + university.getId() + "\"><svg xmlns=\"http://www.w3.org/2000/svg\" viewBox=\"0 0 24 24\" width=\"24\" height=\"24\"><path d=\"M3 17.25V21h3.75L17.81 9.94l-3.75-3.75L3 17.25z\"></path><path d=\"M20.71 7.04c.39-.39.39-1.02 0-1.41l-2.34-2.34c-.39-.39-1.02-.39-1.41 0l-1.83 1.83 3.75 3.75 1.83-1.83z\"></path></svg></a>");
	    	universityData.add(university.getName());
	    	
	    	int countMembers = accountService.countInUniversity(university);
	    	universityData.add(String.valueOf(countMembers));
	    	
	    	int countHostels = hostelService.countInUniversity(university);
	    	universityData.add(String.valueOf(countHostels));
	    	
	    	if (countMembers != 0 || countHostels != 0) {
	    		universityData.add("<svg xmlns=\"http://www.w3.org/2000/svg\" viewBox=\"0 0 24 24\" width=\"24\" height=\"24\"><path d=\"M6 19c0 1.1.9 2 2 2h8c1.1 0 2-.9 2-2V7H6v12z\" fill=\"#E4E4E4\" ></path><path d=\"M19 4h-3.5l-1-1h-5l-1 1H5v2h14V4z\" fill=\"#E4E4E4\"></path></svg>");
	    	}else{
	    		universityData.add("<a style=\"color: #f9b012\" href=\"/university/delete?id=" + university.getId() + "\"><svg xmlns=\"http://www.w3.org/2000/svg\" viewBox=\"0 0 24 24\" width=\"24\" height=\"24\"><path d=\"M6 19c0 1.1.9 2 2 2h8c1.1 0 2-.9 2-2V7H6v12z\"></path><path d=\"M19 4h-3.5l-1-1h-5l-1 1H5v2h14V4z\"></path></svg></a>");
	    	}
	    	
	    	universitiesList.add(universityData);
	    }
		return universitiesList;
	}
    
	public Map<String, Object> listExport() {
		List<University> universities = universityRepository.findAll();
	    Comparator<University> comparator = new Comparator<University>() {
		    @Override
		    public int compare(University left, University right) {
		        return (int) (left.getId() - right.getId());
		    }
		};
		Collections.sort(universities, comparator);
		
		Map<String, Object> linkedHashMap = new LinkedHashMap<String, Object>();
		for (University university : universities) {
			Map<String, Object> linkedHashMapMap = new LinkedHashMap<String, Object>();
			linkedHashMapMap.put("id", university.getId());
			linkedHashMapMap.put("name", university.getName());
			linkedHashMapMap.put("accountCount", accountService.countInUniversity(university));
			linkedHashMap.put("university " + String.valueOf(university.getId()), linkedHashMapMap);
		}
		return linkedHashMap;
	}
    
}
