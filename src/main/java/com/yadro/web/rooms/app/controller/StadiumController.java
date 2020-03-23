package com.yadro.web.rooms.app.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.yadro.web.rooms.app.model.Stadium;
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
import com.yadro.web.rooms.app.service.StadiumService;

@Controller
public class StadiumController {
	
	private Logger log = LoggerFactory.getLogger(StadiumController.class);
	
	@Autowired
	private StadiumService stadiumService;
	
	@Autowired
	private UniversityService universityService;
	
	@RequestMapping("/stadium/list")
	public String list(ModelMap map) {
	   map.addAttribute("stadiums", stadiumService.listTable());
	   return "stadium/list";
	}
	
	@RequestMapping(value = "/stadium/list/export")
	public @ResponseBody String listDownload(HttpServletResponse response) {
		String fileName = "stadiumList.json";
		response.setContentType("application/force-download");
	  response.setHeader("Content-Disposition", "attachment; filename="+fileName);
	  try {
			response.flushBuffer();
		} catch (IOException e) {
			e.printStackTrace();
		}
	  Gson gson = new GsonBuilder().setPrettyPrinting().create();
	  String content = gson.toJson(stadiumService.listExport());
	  return content;
	}
	
	@RequestMapping(value = "/stadium/add", method = RequestMethod.GET)
	public String add(Stadium stadium, Model model) {
		List<University> universityList = universityService.list();
		model.addAttribute("universityList", universityList);
	   return "/stadium/add";
	}
	
	@RequestMapping(value = "/stadium/add", method = RequestMethod.POST)
	public String addPost(@Valid Stadium stadium, BindingResult result, Model model) {
		List<University> universityList = universityService.list();
		model.addAttribute("universityList", universityList);
	   if (result.hasErrors()) {
	       return "stadium/add";
	   }
	   
	   Stadium registeredStadium = stadiumService.add(stadium);
	   if (registeredStadium != null) {
	      return "redirect:/stadium/list";
	   } else {
	       log.error("Stadium already exists: " + stadium.getName());
	       //result.rejectValue("university", "error.alreadyExists", "This university already exists, please try another name.");
	       return "redirect:/stadium/add" + "?error";
	   }
	}
	
	@RequestMapping("/stadium/edit/{id}")
	public String edit(@PathVariable("id") Long id, Stadium stadium, Model model) {
		Stadium u = stadiumService.findById(id);
		stadium.setId(u.getId());
		stadium.setName(u.getName());
		stadium.setAddress(u.getAddress());
		stadium.setUniversity(u.getUniversity());
		
		List<University> universityList = universityService.list();
		model.addAttribute("universityList", universityList);
	   
	   return "/stadium/edit";
	}
	
	@RequestMapping(value = "/stadium/edit", method = RequestMethod.POST)
	public String editPost(@Valid Stadium stadium, BindingResult result, Model model) {
		
		List<University> universityList = universityService.list();
		model.addAttribute("universityList", universityList);
		 
	   if (result.hasFieldErrors("name")) {
	       return "/stadium/edit";
	   }

		Stadium uni = stadiumService.update(stadium);
		
		if (uni == null) {
			return "redirect:/stadium/edit/" + stadium.getId() + "?error";
		} else {
			return "redirect:/stadium/edit/" + stadium.getId() + "?updated";
		}
	}
	
	@RequestMapping("/stadium/delete")
	public String delete(Long id) {
		stadiumService.delete(id);
		return "redirect:/stadium/list";
	}
	
}
