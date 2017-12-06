package com.marcskow.visca;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class KotyController {

	@Autowired
	KotDAO dao;

	@RequestMapping("/lista")
	public String listaKotow(Model model) {
		model.addAttribute("koty", dao.getKoty());
		return "lista";
	}
	
	@RequestMapping("/dodaj")
	public String dodajKota(Model model) {
		return "dodaj";
	}
	
	@RequestMapping("/kot-{id}")
	public String szczegolyKota(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("kot", dao.getKotById(id));
		return "szczegoly";
	}
	
}
