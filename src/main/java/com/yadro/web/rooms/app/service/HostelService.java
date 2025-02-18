package com.yadro.web.rooms.app.service;

import com.yadro.web.rooms.app.model.Hostel;
import com.yadro.web.rooms.app.model.University;
import com.yadro.web.rooms.app.repository.HostelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class HostelService {
    @Autowired
    private HostelRepository hostelRepository;

    @Autowired
    private RoomService roomService;

    public Hostel findById(Long id) {
        Hostel hostel = hostelRepository.findById(id);
        return hostel;
    }

    public Hostel findByName(String name) {
        Hostel hostel = hostelRepository.findByName(name);
        return hostel;
    }

    public List<Hostel> list() {
        return hostelRepository.findAll();
    }

    public Boolean delete(Long id) {
        this.hostelRepository.delete(id);
        return true;
    }

    public Hostel add(Hostel hostel) {
        if (this.hostelRepository.findByNameAndUniversity(hostel.getName(), hostel.getUniversity()) == null) {
            this.hostelRepository.save(hostel);
            return hostel;
        } else {
            return null;
        }
    }

    public Hostel update(Hostel hostel) {
        return update(hostel.getId(), hostel);
    }

    public Hostel update(Long id, Hostel newData) {

        String checkName = newData.getName();
        Long checkId = id;

        Hostel check = this.hostelRepository.findByNameAndUniversity(newData.getName(), newData.getUniversity());
        if (check != null) {
            checkName = check.getName();
            checkId = check.getId();
        }

        if (checkName.equals(newData.getName()) || checkId.equals(id)) {
            this.hostelRepository.updateHostel(
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
        List<Hostel> hostels = hostelRepository.findAll();
        List<List<String>> hostelsList = new LinkedList<List<String>>();

        Comparator<Hostel> comparator = new Comparator<Hostel>() {
            @Override
            public int compare(Hostel left, Hostel right) {
                return (int) (left.getId() - right.getId());
            }
        };
        Collections.sort(hostels, comparator);

        for (Hostel hostel : hostels) {
            List<String> hostelData = new ArrayList<String>();

            hostelData.add("<a style=\"color: #f9b012\" href=\"/hostel/edit/" + hostel.getId() + "\"><svg xmlns=\"http://www.w3.org/2000/svg\" viewBox=\"0 0 24 24\" width=\"24\" height=\"24\"><path d=\"M3 17.25V21h3.75L17.81 9.94l-3.75-3.75L3 17.25z\"></path><path d=\"M20.71 7.04c.39-.39.39-1.02 0-1.41l-2.34-2.34c-.39-.39-1.02-.39-1.41 0l-1.83 1.83 3.75 3.75 1.83-1.83z\"></path></svg></a>");
            hostelData.add(hostel.getName());

            hostelData.add(hostel.getUniversity().getName());

            int count = roomService.countInHostel(hostel);
            hostelData.add(String.valueOf(count));

            if (count != 0) {
                hostelData.add("<svg xmlns=\"http://www.w3.org/2000/svg\" viewBox=\"0 0 24 24\" width=\"24\" height=\"24\"><path d=\"M6 19c0 1.1.9 2 2 2h8c1.1 0 2-.9 2-2V7H6v12z\" fill=\"#E4E4E4\" ></path><path d=\"M19 4h-3.5l-1-1h-5l-1 1H5v2h14V4z\" fill=\"#E4E4E4\"></path></svg>");
            } else {
                hostelData.add("<a style=\"color: #f9b012\" href=\"/hostel/delete?id=" + hostel.getId() + "\"><svg xmlns=\"http://www.w3.org/2000/svg\" viewBox=\"0 0 24 24\" width=\"24\" height=\"24\"><path d=\"M6 19c0 1.1.9 2 2 2h8c1.1 0 2-.9 2-2V7H6v12z\"></path><path d=\"M19 4h-3.5l-1-1h-5l-1 1H5v2h14V4z\"></path></svg></a>");
            }

            hostelsList.add(hostelData);
        }
        return hostelsList;
    }

    public List<List<String>> listTable(University university) {
        List<Hostel> hostels = hostelRepository.findAll();
        List<List<String>> hostelsList = new LinkedList<List<String>>();

        Comparator<Hostel> comparator = new Comparator<Hostel>() {
            @Override
            public int compare(Hostel left, Hostel right) {
                return (int) (left.getId() - right.getId());
            }
        };
        Collections.sort(hostels, comparator);

        for (Hostel hostel : hostels) {
            List<String> hostelData = new ArrayList<String>();
            if (hostel.getUniversity().getId().equals(university.getId())) {
                hostelData.add("<a style=\"color: #000000\" href=\"/book/hostel/" + hostel.getId() + "\"><svg fill=\"#000000\" height=\"24\" viewBox=\"0 0 24 24\" width=\"24\" xmlns=\"http://www.w3.org/2000/svg\"><path d=\"M8.59 16.34l4.58-4.59-4.58-4.59L10 5.75l6 6-6 6z\"/><path d=\"M0-.25h24v24H0z\" fill=\"none\"/></svg></a>");
                hostelData.add(hostel.getName());
                hostelsList.add(hostelData);
            }
        }
        return hostelsList;
    }

    public Map<String, Object> listExport() {
        List<Hostel> hostels = hostelRepository.findAll();

        Comparator<Hostel> comparator = new Comparator<Hostel>() {
            @Override
            public int compare(Hostel left, Hostel right) {
                return (int) (left.getId() - right.getId());
            }
        };
        Collections.sort(hostels, comparator);

        Map<String, Object> linkedHashMap = new LinkedHashMap<String, Object>();
        for (Hostel hostel : hostels) {
            Map<String, Object> linkedHashMapMap = new LinkedHashMap<String, Object>();
            linkedHashMapMap.put("id", hostel.getId());
            linkedHashMapMap.put("name", hostel.getName());
            linkedHashMapMap.put("address", hostel.getName());
            linkedHashMapMap.put("roomsCount", roomService.countInHostel(hostel));
            linkedHashMap.put("hostel " + String.valueOf(hostel.getId()), linkedHashMapMap);
        }
        return linkedHashMap;
    }

    public int countInUniversity(University university) {
        return this.hostelRepository.findByUniversity(university).size();
    }

    public List<Hostel> universityHostelList(University university) {
        return this.hostelRepository.findByUniversity(university);
    }
}
