package com.yadro.web.rooms.app.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.yadro.web.rooms.app.model.Hostel;
import com.yadro.web.rooms.app.model.University;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yadro.web.rooms.app.service.UniversityService;
import com.yadro.web.rooms.app.service.HostelService;

@Controller
public class HostelController {
	
	private Logger log = LoggerFactory.getLogger(HostelController.class);
	
	@Autowired
	private HostelService hostelService;
	
	@Autowired
	private UniversityService universityService;
	
	@RequestMapping("/hostel/list")
	public String list(ModelMap map) {
	   map.addAttribute("hostels", hostelService.listTable());
	   return "hostel/list";
	}
	
	@RequestMapping(value = "/hostel/list/export")
	public @ResponseBody String listDownload(HttpServletResponse response) {
		String fileName = "hostelList.json";
		response.setContentType("application/force-download");
	  response.setHeader("Content-Disposition", "attachment; filename="+fileName);
	  try {
			response.flushBuffer();
		} catch (IOException e) {
			e.printStackTrace();
		}
	  Gson gson = new GsonBuilder().setPrettyPrinting().create();
	  String content = gson.toJson(hostelService.listExport());
	  return content;
	}
	
	@RequestMapping(value = "/hostel/add", method = RequestMethod.GET)
	public String add(Hostel hostel, Model model) {
		List<University> universityList = universityService.list();
		model.addAttribute("universityList", universityList);
	   return "/hostel/add";
	}
	
	@RequestMapping(value = "/hostel/add", method = RequestMethod.POST)
	public String addPost(@Valid Hostel hostel, BindingResult result, Model model) {
		List<University> universityList = universityService.list();
		model.addAttribute("universityList", universityList);
	   if (result.hasErrors()) {
	       return "hostel/add";
	   }
	   
	   Hostel registeredHostel = hostelService.add(hostel);
	   if (registeredHostel != null) {
	      return "redirect:/hostel/list";
	   } else {
	       log.error("Hostel already exists: " + hostel.getName());
	       //result.rejectValue("university", "error.alreadyExists", "This university already exists, please try another name.");
	       return "redirect:/hostel/add" + "?error";
	   }
	}
	
	@RequestMapping("/hostel/edit/{id}")
	public String edit(@PathVariable("id") Long id, Hostel hostel, Model model) {
		Hostel u = hostelService.findById(id);
		hostel.setId(u.getId());
		hostel.setName(u.getName());
		hostel.setAddress(u.getAddress());
		hostel.setUniversity(u.getUniversity());
		
		List<University> universityList = universityService.list();
		model.addAttribute("universityList", universityList);
	   
	   return "/hostel/edit";
	}
	
	@RequestMapping(value = "/hostel/edit", method = RequestMethod.POST)
	public String editPost(@Valid Hostel hostel, BindingResult result, Model model) {
		
		List<University> universityList = universityService.list();
		model.addAttribute("universityList", universityList);
		 
	   if (result.hasFieldErrors("name")) {
	       return "/hostel/edit";
	   }

		Hostel uni = hostelService.update(hostel);
		
		if (uni == null) {
			return "redirect:/hostel/edit/" + hostel.getId() + "?error";
		} else {
			return "redirect:/hostel/edit/" + hostel.getId() + "?updated";
		}
	}
	
	@RequestMapping("/hostel/delete")
	public String delete(Long id) {
		hostelService.delete(id);
		return "redirect:/hostel/list";
	}
	
}
