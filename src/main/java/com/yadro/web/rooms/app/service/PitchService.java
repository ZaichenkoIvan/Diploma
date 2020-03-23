package com.yadro.web.rooms.app.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import com.yadro.web.rooms.app.model.Pitch;
import com.yadro.web.rooms.app.model.Stadium;
import com.yadro.web.rooms.app.repository.PitchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PitchService {
	@Autowired
	private PitchRepository pitchRepository;
	
	@Autowired
	private EventService eventService;

	public Pitch findById(Long id) {
		return pitchRepository.findById(id);
	}
	
	public Pitch findByName(String name) {
		return pitchRepository.findByName(name);
	}
	
	public List<Pitch> findByStadium(Stadium stadium) {
		return pitchRepository.findByStadium(stadium);
	}
	
	public List<Pitch> list() {
		return pitchRepository.findAll();
	}
	
    public Boolean delete(Long id) {
        this.pitchRepository.delete(id);
        return true;
    }
    
    public Pitch add(Pitch pitch) {
    	if (this.pitchRepository.findByNameAndStadium(pitch.getName(), pitch.getStadium()) == null) {
    		this.pitchRepository.save(pitch);
    		return pitch;
    	} else {
    		return null;
    	}
    }
    
    public Pitch update(Pitch pitch) {
        return update(pitch.getId(), pitch);
    }
    
    public Pitch update(Long id, Pitch newData) {
    	
    	String checkName = newData.getName();
    	Long checkId = id;
    	
    	Pitch check = this.pitchRepository.findByNameAndStadium(newData.getName(), newData.getStadium());
    	if (check != null) {
    		checkName = check.getName();
    		checkId = check.getId();
    	}

    	if ( checkName.equals(newData.getName()) || checkId.equals(id)) {
            this.pitchRepository.updatePitch(
                    id, 
                    newData.getName(),
                    newData.getBall(),
                    newData.getManish(),
                    newData.getGrass(),
                    newData.getProjector(),
                    newData.getChangingRoom(),
                    newData.getShower(),
                    newData.getWc(),
                    newData.getStadium());
            return newData;
    	} else {
    		return null;
    	}
    }
    
	public List<List<String>> listTable() {
		List<Pitch> pitches = pitchRepository.findAll();
	    List<List<String>> pitchList = new LinkedList<List<String>>();
	    
	    Comparator<Pitch> comparator = new Comparator<Pitch>() {
		    @Override
		    public int compare(Pitch left, Pitch right) {
		        return (int) (left.getId() - right.getId());
		    }
		};
		Collections.sort(pitches, comparator);
	    
	    for (Pitch pitch : pitches) {
	    	List<String> pitchData = new ArrayList<String>();
	    	
	    	pitchData.add("<a style=\"color: #f9b012\" href=\"/pitch/edit/" + pitch.getId() + "\"><svg xmlns=\"http://www.w3.org/2000/svg\" viewBox=\"0 0 24 24\" width=\"24\" height=\"24\"><path d=\"M3 17.25V21h3.75L17.81 9.94l-3.75-3.75L3 17.25z\"></path><path d=\"M20.71 7.04c.39-.39.39-1.02 0-1.41l-2.34-2.34c-.39-.39-1.02-.39-1.41 0l-1.83 1.83 3.75 3.75 1.83-1.83z\"></path></svg></a>");
	    	pitchData.add(pitch.getName());
	    	pitchData.add(pitch.getStadium().getName());
	    	pitchData.add(String.valueOf(pitch.getBall()));
	    	pitchData.add(String.valueOf(pitch.getManish()));
	    	
	    	if (pitch.getGrass().equals("YES")) {
	    		pitchData.add("<span style=\"font-size:1px; color: #fffff\">YES</span> <svg fill=\"#5CA830\" height=\"24\" viewBox=\"0 0 24 24\" width=\"24\" xmlns=\"http://www.w3.org/2000/svg\"> <path d=\"M0 0h24v24H0z\" fill=\"none\"/> <path d=\"M9 16.2L4.8 12l-1.4 1.4L9 19 21 7l-1.4-1.4L9 16.2z\"/> </svg>");
	    	}else{
	    		pitchData.add("<span style=\"font-size:1px; color: #fffff\">NO</span> <svg fill=\"#BC0000\" height=\"24\" viewBox=\"0 0 24 24\" width=\"24\" xmlns=\"http://www.w3.org/2000/svg\"> <path d=\"M19 6.41L17.59 5 12 10.59 6.41 5 5 6.41 10.59 12 5 17.59 6.41 19 12 13.41 17.59 19 19 17.59 13.41 12z\"/> <path d=\"M0 0h24v24H0z\" fill=\"none\"/> </svg>");
	    	}
	    	
	    	if (pitch.getProjector().equals("YES")) {
	    		pitchData.add("<span style=\"font-size:1px; color: #fffff\">YES</span> <svg fill=\"#5CA830\" height=\"24\" viewBox=\"0 0 24 24\" width=\"24\" xmlns=\"http://www.w3.org/2000/svg\"> <path d=\"M0 0h24v24H0z\" fill=\"none\"/> <path d=\"M9 16.2L4.8 12l-1.4 1.4L9 19 21 7l-1.4-1.4L9 16.2z\"/> </svg>");
	    	}else{
	    		pitchData.add("<span style=\"font-size:1px; color: #fffff\">NO</span> <svg fill=\"#BC0000\" height=\"24\" viewBox=\"0 0 24 24\" width=\"24\" xmlns=\"http://www.w3.org/2000/svg\"> <path d=\"M19 6.41L17.59 5 12 10.59 6.41 5 5 6.41 10.59 12 5 17.59 6.41 19 12 13.41 17.59 19 19 17.59 13.41 12z\"/> <path d=\"M0 0h24v24H0z\" fill=\"none\"/> </svg>");
	    	}
	    	
	    	if (pitch.getChangingRoom().equals("YES")) {
	    		pitchData.add("<span style=\"font-size:1px; color: #fffff\">YES</span> <svg fill=\"#5CA830\" height=\"24\" viewBox=\"0 0 24 24\" width=\"24\" xmlns=\"http://www.w3.org/2000/svg\"> <path d=\"M0 0h24v24H0z\" fill=\"none\"/> <path d=\"M9 16.2L4.8 12l-1.4 1.4L9 19 21 7l-1.4-1.4L9 16.2z\"/> </svg>");
	    	}else{
	    		pitchData.add("<span style=\"font-size:1px; color: #fffff\">NO</span> <svg fill=\"#BC0000\" height=\"24\" viewBox=\"0 0 24 24\" width=\"24\" xmlns=\"http://www.w3.org/2000/svg\"> <path d=\"M19 6.41L17.59 5 12 10.59 6.41 5 5 6.41 10.59 12 5 17.59 6.41 19 12 13.41 17.59 19 19 17.59 13.41 12z\"/> <path d=\"M0 0h24v24H0z\" fill=\"none\"/> </svg>");
	    	}
	    	
	    	if (pitch.getShower().equals("YES")) {
	    		pitchData.add("<span style=\"font-size:1px; color: #fffff\">YES</span> <svg fill=\"#5CA830\" height=\"24\" viewBox=\"0 0 24 24\" width=\"24\" xmlns=\"http://www.w3.org/2000/svg\"> <path d=\"M0 0h24v24H0z\" fill=\"none\"/> <path d=\"M9 16.2L4.8 12l-1.4 1.4L9 19 21 7l-1.4-1.4L9 16.2z\"/> </svg>");
	    	}else{
	    		pitchData.add("<span style=\"font-size:1px; color: #fffff\">NO</span><svg fill=\"#BC0000\" height=\"24\" viewBox=\"0 0 24 24\" width=\"24\" xmlns=\"http://www.w3.org/2000/svg\"> <path d=\"M19 6.41L17.59 5 12 10.59 6.41 5 5 6.41 10.59 12 5 17.59 6.41 19 12 13.41 17.59 19 19 17.59 13.41 12z\"/> <path d=\"M0 0h24v24H0z\" fill=\"none\"/> </svg>");
	    	}
	    	
	    	if (pitch.getWc().equals("YES")) {
	    		pitchData.add("<span style=\"font-size:1px; color: #fffff\">YES</span> <svg fill=\"#5CA830\" height=\"24\" viewBox=\"0 0 24 24\" width=\"24\" xmlns=\"http://www.w3.org/2000/svg\"> <path d=\"M0 0h24v24H0z\" fill=\"none\"/> <path d=\"M9 16.2L4.8 12l-1.4 1.4L9 19 21 7l-1.4-1.4L9 16.2z\"/> </svg>");
	    	}else{
	    		pitchData.add("<span style=\"font-size:1px; color: #fffff\">NO</span> <svg fill=\"#BC0000\" height=\"24\" viewBox=\"0 0 24 24\" width=\"24\" xmlns=\"http://www.w3.org/2000/svg\"> <path d=\"M19 6.41L17.59 5 12 10.59 6.41 5 5 6.41 10.59 12 5 17.59 6.41 19 12 13.41 17.59 19 19 17.59 13.41 12z\"/> <path d=\"M0 0h24v24H0z\" fill=\"none\"/> </svg>");
	    	}
	    	
	    	int count = eventService.countInPitch(pitch);
	    	pitchData.add(String.valueOf(count));
	    	
	    	if (count != 0) {
	    		pitchData.add("<svg xmlns=\"http://www.w3.org/2000/svg\" viewBox=\"0 0 24 24\" width=\"24\" height=\"24\"><path d=\"M6 19c0 1.1.9 2 2 2h8c1.1 0 2-.9 2-2V7H6v12z\" fill=\"#E4E4E4\" ></path><path d=\"M19 4h-3.5l-1-1h-5l-1 1H5v2h14V4z\" fill=\"#E4E4E4\"></path></svg>");
	    	}else{
	    		pitchData.add("<a style=\"color: #f9b012\" href=\"/pitch/delete?id=" + pitch.getId() + "\"><svg xmlns=\"http://www.w3.org/2000/svg\" viewBox=\"0 0 24 24\" width=\"24\" height=\"24\"><path d=\"M6 19c0 1.1.9 2 2 2h8c1.1 0 2-.9 2-2V7H6v12z\"></path><path d=\"M19 4h-3.5l-1-1h-5l-1 1H5v2h14V4z\"></path></svg></a>");
	    	}
	    	
	    	pitchList.add(pitchData);
	    }
		return pitchList;
	}
	
	public List<List<String>> listTable(Long id) {
		List<Pitch> pitches = pitchRepository.findAll();
	    List<List<String>> pitchList = new LinkedList<List<String>>();
	    
	    Comparator<Pitch> comparator = new Comparator<Pitch>() {
		    @Override
		    public int compare(Pitch left, Pitch right) {
		        return (int) (left.getId() - right.getId());
		    }
		};
		Collections.sort(pitches, comparator);
	    
	    for (Pitch pitch : pitches) {
	    	List<String> pitchData = new ArrayList<String>();
	    	
	    	if (pitch.getStadium().getId().equals(id)) {
	    	
		    	pitchData.add("<a style=\"color: #f9b012\" href=\"/book/pitch/" + pitch.getId() + "\"><svg fill=\"#000000\" height=\"32\" viewBox=\"0 0 32 32\" width=\"32\" xmlns=\"http://www.w3.org/2000/svg\"><path d=\"M8.59 16.34l4.58-4.59-4.58-4.59L10 5.75l6 6-6 6z\"/><path d=\"M0-.25h24v24H0z\" fill=\"none\"/></svg></a>");
		    	pitchData.add(pitch.getName());
		    	pitchData.add(String.valueOf(pitch.getBall()));
		    	pitchData.add(String.valueOf(pitch.getManish()));
		    	
		    	if (pitch.getGrass().equals("YES")) {
		    		pitchData.add("<span style=\"font-size:1px; color: #fffff\">YES</span> <svg fill=\"#5CA830\" height=\"24\" viewBox=\"0 0 24 24\" width=\"24\" xmlns=\"http://www.w3.org/2000/svg\"> <path d=\"M0 0h24v24H0z\" fill=\"none\"/> <path d=\"M9 16.2L4.8 12l-1.4 1.4L9 19 21 7l-1.4-1.4L9 16.2z\"/> </svg>");
		    	}else{
		    		pitchData.add("<span style=\"font-size:1px; color: #fffff\">NO</span> <svg fill=\"#BC0000\" height=\"24\" viewBox=\"0 0 24 24\" width=\"24\" xmlns=\"http://www.w3.org/2000/svg\"> <path d=\"M19 6.41L17.59 5 12 10.59 6.41 5 5 6.41 10.59 12 5 17.59 6.41 19 12 13.41 17.59 19 19 17.59 13.41 12z\"/> <path d=\"M0 0h24v24H0z\" fill=\"none\"/> </svg>");
		    	}
		    	
		    	if (pitch.getProjector().equals("YES")) {
		    		pitchData.add("<span style=\"font-size:1px; color: #fffff\">YES</span> <svg fill=\"#5CA830\" height=\"24\" viewBox=\"0 0 24 24\" width=\"24\" xmlns=\"http://www.w3.org/2000/svg\"> <path d=\"M0 0h24v24H0z\" fill=\"none\"/> <path d=\"M9 16.2L4.8 12l-1.4 1.4L9 19 21 7l-1.4-1.4L9 16.2z\"/> </svg>");
		    	}else{
		    		pitchData.add("<span style=\"font-size:1px; color: #fffff\">NO</span> <svg fill=\"#BC0000\" height=\"24\" viewBox=\"0 0 24 24\" width=\"24\" xmlns=\"http://www.w3.org/2000/svg\"> <path d=\"M19 6.41L17.59 5 12 10.59 6.41 5 5 6.41 10.59 12 5 17.59 6.41 19 12 13.41 17.59 19 19 17.59 13.41 12z\"/> <path d=\"M0 0h24v24H0z\" fill=\"none\"/> </svg>");
		    	}
		    	
		    	if (pitch.getChangingRoom().equals("YES")) {
		    		pitchData.add("<span style=\"font-size:1px; color: #fffff\">YES</span> <svg fill=\"#5CA830\" height=\"24\" viewBox=\"0 0 24 24\" width=\"24\" xmlns=\"http://www.w3.org/2000/svg\"> <path d=\"M0 0h24v24H0z\" fill=\"none\"/> <path d=\"M9 16.2L4.8 12l-1.4 1.4L9 19 21 7l-1.4-1.4L9 16.2z\"/> </svg>");
		    	}else{
		    		pitchData.add("<span style=\"font-size:1px; color: #fffff\">NO</span> <svg fill=\"#BC0000\" height=\"24\" viewBox=\"0 0 24 24\" width=\"24\" xmlns=\"http://www.w3.org/2000/svg\"> <path d=\"M19 6.41L17.59 5 12 10.59 6.41 5 5 6.41 10.59 12 5 17.59 6.41 19 12 13.41 17.59 19 19 17.59 13.41 12z\"/> <path d=\"M0 0h24v24H0z\" fill=\"none\"/> </svg>");
		    	}
		    	
		    	if (pitch.getShower().equals("YES")) {
		    		pitchData.add("<span style=\"font-size:1px; color: #fffff\">YES</span> <svg fill=\"#5CA830\" height=\"24\" viewBox=\"0 0 24 24\" width=\"24\" xmlns=\"http://www.w3.org/2000/svg\"> <path d=\"M0 0h24v24H0z\" fill=\"none\"/> <path d=\"M9 16.2L4.8 12l-1.4 1.4L9 19 21 7l-1.4-1.4L9 16.2z\"/> </svg>");
		    	}else{
		    		pitchData.add("<span style=\"font-size:1px; color: #fffff\">NO</span><svg fill=\"#BC0000\" height=\"24\" viewBox=\"0 0 24 24\" width=\"24\" xmlns=\"http://www.w3.org/2000/svg\"> <path d=\"M19 6.41L17.59 5 12 10.59 6.41 5 5 6.41 10.59 12 5 17.59 6.41 19 12 13.41 17.59 19 19 17.59 13.41 12z\"/> <path d=\"M0 0h24v24H0z\" fill=\"none\"/> </svg>");
		    	}
		    	
		    	if (pitch.getWc().equals("YES")) {
		    		pitchData.add("<span style=\"font-size:1px; color: #fffff\">YES</span> <svg fill=\"#5CA830\" height=\"24\" viewBox=\"0 0 24 24\" width=\"24\" xmlns=\"http://www.w3.org/2000/svg\"> <path d=\"M0 0h24v24H0z\" fill=\"none\"/> <path d=\"M9 16.2L4.8 12l-1.4 1.4L9 19 21 7l-1.4-1.4L9 16.2z\"/> </svg>");
		    	}else{
		    		pitchData.add("<span style=\"font-size:1px; color: #fffff\">NO</span> <svg fill=\"#BC0000\" height=\"24\" viewBox=\"0 0 24 24\" width=\"24\" xmlns=\"http://www.w3.org/2000/svg\"> <path d=\"M19 6.41L17.59 5 12 10.59 6.41 5 5 6.41 10.59 12 5 17.59 6.41 19 12 13.41 17.59 19 19 17.59 13.41 12z\"/> <path d=\"M0 0h24v24H0z\" fill=\"none\"/> </svg>");
		    	}
		    	
		    	//int count = eventService.countInPitch(pitch);
		    	pitchData.add("");
		    	
		    	
		    	pitchList.add(pitchData);
	    	}
	    }
		return pitchList;
	}
	
	public List<List<String>> listTablePublic(String name) {
		List<Pitch> pitches = pitchRepository.findAll();
	    List<List<String>> pitchList = new LinkedList<List<String>>();
	    
	    Comparator<Pitch> comparator = new Comparator<Pitch>() {
		    @Override
		    public int compare(Pitch left, Pitch right) {
		        return (int) (left.getId() - right.getId());
		    }
		};
		Collections.sort(pitches, comparator);
	    
	    for (Pitch pitch : pitches) {
	    	List<String> pitchData = new ArrayList<String>();
	    	if (pitch.getStadium().getUniversity().getName().startsWith(name)) {
	    	pitchData.add("<a style=\"color: #f9b012\" href=\"/calendar/pitch/" + pitch.getId() + "\"><svg fill=\"#000000\" height=\"32\" viewBox=\"0 0 32 32\" width=\"32\" xmlns=\"http://www.w3.org/2000/svg\"><path d=\"M8.59 16.34l4.58-4.59-4.58-4.59L10 5.75l6 6-6 6z\"/><path d=\"M0-.25h24v24H0z\" fill=\"none\"/></svg></a>");
	    	pitchData.add(pitch.getName());
	    	pitchData.add(String.valueOf(pitch.getStadium().getName()));
	    	//int count = eventService.countInPitch(pitch);
	    	pitchData.add("");
	    	
	    	pitchList.add(pitchData);
	    	}
	    }
		return pitchList;
	}
	
	public List<Pitch> listExport() {
		List<Pitch> pitches = pitchRepository.findAll();
		
	    Comparator<Pitch> comparator = new Comparator<Pitch>() {
		    @Override
		    public int compare(Pitch left, Pitch right) {
		        return (int) (left.getId() - right.getId());
		    }
		};
		Collections.sort(pitches, comparator);
		
		return pitches;
	}
	
	public int countInStadium(Stadium stadium) {
		return this.pitchRepository.findByStadium(stadium).size();
	}
	
	public List<Pitch> pitchStadiumList(Stadium stadium) {
		return this.pitchRepository.findByStadium(stadium);
	}
}
