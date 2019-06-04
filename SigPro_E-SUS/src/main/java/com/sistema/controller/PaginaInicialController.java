package com.sistema.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sistema.model.Pessoa;
import com.sistema.service.PessoaService;
@Controller
public class PaginaInicialController {

	@Autowired
	private PessoaService pessoaService;
	
	@RequestMapping("/")
	public ModelAndView index() {
		ModelAndView mv = new ModelAndView("redirect:/inicio");
		return mv;
	}
	
	@RequestMapping("/inicio")
	public ModelAndView paginaInicial() {
		Object auth = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails user = (UserDetails) auth;
		
		Pessoa pessoa = pessoaService.buscaPorLogin(user.getUsername());
						
		ModelAndView mv = new ModelAndView("index");
		mv.addObject("pessoa", pessoa);
		return mv;
	}
	
}