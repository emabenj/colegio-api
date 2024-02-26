package pe.colegio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import pe.colegio.service.UsuarioServ;

@Controller @RequestMapping("/home")
public class HomeController {
	@Autowired
	private UsuarioServ service;
	
	public HomeController() {}

	@GetMapping("")
	public String home0_GET() {return "redirect:/home/";}
	
	@GetMapping("/")
	public String home_GET() {
		return "Welcome";
	}
	
	
}