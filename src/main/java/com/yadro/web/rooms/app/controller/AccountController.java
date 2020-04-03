package com.yadro.web.rooms.app.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.yadro.web.rooms.app.model.University;
import com.yadro.web.rooms.app.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yadro.web.rooms.app.model.Account;
import com.yadro.web.rooms.app.service.AccountService;
import com.yadro.web.rooms.app.service.UniversityService;

@Controller
public class AccountController {
	private Logger log = LoggerFactory.getLogger(AccountController.class);
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	protected AuthenticationManager authenticationManager;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private UniversityService universityService;
	
	@RequestMapping("/welcome")
	public String login(Account account) {
	   return "welcome";
	}

	@RequestMapping("/registration")
	public String registration() {
		return "registration";
	}
	
	@RequestMapping("/")
	public String home(Map<String, Object> model) {
        model.put("date", new Date());
		return "home";
	}
	
	@RequestMapping("/account/list")
	public String list(ModelMap map) {
	   map.addAttribute("accounts", accountService.listTable());
	   return "account/list";
	}
	
	@RequestMapping("/account/list/group")
	public String listGroup(ModelMap map) {
	   map.addAttribute("accounts", accountService.listTableGroup());
	   return "account/listgroup";
	}
	
	@ResponseBody
	@RequestMapping(value ="/account/list/export")
	public String listDownload(HttpServletResponse response) {
		
		String fileName = "userList.json";
		response.setContentType("application/force-download");
		response.setHeader("Content-Disposition", "attachment; filename="+fileName);
		try {
			response.flushBuffer();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String content = gson.toJson(accountService.listExport());
		return content;
	}
	
	@RequestMapping(value = "/account/register", method = RequestMethod.GET)
	public String register(Account account, Model model) {
		
		List<University> universityList = universityService.list();
		model.addAttribute("universityList", universityList);
			
	   return "account/register";
	}
	
	@RequestMapping(value = "/account/register", method = RequestMethod.POST)
	public String registerPost(@Valid Account account, BindingResult result, Model model) {
		
		List<University> universityList = universityService.list();
		model.addAttribute("universityList", universityList);
		
	   if (result.hasErrors()) {
	       return "account/register";
	   }
	   
	   Account registeredAccount = accountService.register(account);
	   if (registeredAccount != null) {
	       return "redirect:/account/list";
	   } else {
	       log.error("Account already exists: " + account.getUserName());
	       result.rejectValue("email", "error.alreadyExists", "This username or email already exists, please try to reset password instead.");
	       return "account/register";
	   }
	}
	
	@RequestMapping("/account/delete")
	public String delete(Long id) {
		accountService.delete(id);
	   return "redirect:/account/list";
	}
	
	@RequestMapping("/account/autologin")
	public String autoLogin(Account account) {
		accountService.autoLogin(account.getUserName());
	   return "redirect:/";
	}
	
	@RequestMapping("/account/edit/{id}")
	public String edit(@PathVariable("id") Long id, Account account, Model model) {
	   Account a;
	   Account loggedInUser = accountService.getLoggedInAccount();
	   if(id == 0) {
	       id = loggedInUser.getId();
	   }
	   if(loggedInUser.getId() != id && !loggedInUser.isAdmin()) {
	       return "account/premission-denied";
	   } else if (loggedInUser.isAdmin()) {
	       a = accountRepository.findOne(id);
	   } else {
	       a = loggedInUser;
	   }
	   account.setId(a.getId());
	   account.setUserName(a.getUserName());
	   account.setAddress(a.getAddress());
	   account.setGroupName(a.getGroupName());
	   account.setEmail(a.getEmail());
	   account.setFirstName(a.getFirstName());
	   account.setLastName(a.getLastName());
	   account.setRole(a.getRole());
	   account.setUniversity(a.getUniversity());

	   
		List<University> universityList = universityService.list();
		model.addAttribute("universityList", universityList);
	   
	   return "/account/edit";
	}
	
	@RequestMapping(value = "/account/edit", method = RequestMethod.POST)
	public String editPost(@Valid Account account, BindingResult result) {
		 
	   if (result.hasFieldErrors("email")) {
	       return "/account/edit";
	   }
	   
	   if(accountService.getLoggedInAccount().isAdmin()) {
		   accountService.updateAccount(account);
	   } else {
		   account.setUniversity(accountService.getLoggedInAccount().getUniversity());
		   accountService.updateAccount(accountService.getLoggedInAccount().getUserName(), account);
	   }
	
	   if (accountService.getLoggedInAccount().getId().equals(account.getId())) {
	       // put updated user to session
		   accountService.getLoggedInAccount(true);
	   }
	
	   return "redirect:/account/edit/" + account.getId() + "?updated";
	}

}
