package com.controller;



import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.model.Role;
import com.model.User;
import com.service.RoleService;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.service.EmailService;


@Controller
public class HomeController {
	
	
	@Autowired
	private UserService userserv;
	
	@Autowired
	private RoleService roleserv;
	
	 @Autowired
	 private EmailService emailService;

	 @RequestMapping("/contactus")
	 public ModelAndView showEmailForm() {
	     return new ModelAndView("contactus");
	 }

	    @PostMapping("/sendEmail")
	    public String sendEmail(@RequestParam String to, @RequestParam String subject, @RequestParam String message) {
	        emailService.sendEmail(to, subject, message);
	        return "redirect:/contactus";
	    }

	@GetMapping("/")
	public String index(Model m)
	{
		
		m.addAttribute("title","Home");
		return "home";
	}
	
	
	
	@GetMapping("/signin")
	public String signin(Model m)
	{
		m.addAttribute("title","Signin");
		return "signin";
	}
	
	
	
	
	@GetMapping("/register")
	public String register(Model m)
	{
		m.addAttribute("title","Registration");
		return "register";
	}
	
	
	@GetMapping("/about")
	public String about(Model m)
	{
		m.addAttribute("title","About");
		return "about";
	}

	/*@GetMapping("/contactus")
	public String contactus(Model m)
	{
		m.addAttribute("title","Contactus");
		return "contactus";
	}*/
	
	@PostMapping("/createuser")
	public String createuser(@ModelAttribute User user,HttpSession session)
	{		
		 try {
		        String email = user.getEmail();
		        
		        if (userserv.getUserByEmail(email) != null) {
		            session.setAttribute("msg", "Registration Failed, Please try a different email id");
		            return "redirect:/register";
		        }
		        
		        // Check if the user's age is greater than or equal to 18
		        int userAge = user.getAge(); // Assuming there's an "age" field in the User class
		        if (userAge < 18) {
		            session.setAttribute("msg", "Registration Failed, User must be 18 or older");
		            return "redirect:/register";
		        }

		        List<Role> r = new ArrayList<>();
		        r.add(roleserv.getRoleByName("ROLE_USER"));
		        
		        user.setRoles(r);
		        userserv.addUser(user);

		        session.setAttribute("msg", "Registration Successful...");
		        return "redirect:/register";
		    } catch (Exception e) {
		        System.out.println(e);
		        session.setAttribute("msg", "Registration Failed, An error occurred.");
		        return "redirect:/register";
		    }
	}	
	
}

