package com.sistema.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sistema.model.Ficha;
import com.sistema.model.Pessoa;
import com.sistema.model.Profissional;
import com.sistema.service.FichaService;
import com.sistema.service.PessoaService;
import com.sistema.service.ProfissionalService;

@Controller
@RequestMapping("/profissional")
public class ProfissionalController {

	@Autowired
	private ProfissionalService profissionalService;
	
	@Autowired
	private PessoaService pessoaService;
	@RequestMapping("/formprofissional")
	public ModelAndView formularioProfissional() {
		Object auth = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails user = (UserDetails) auth;
		
		Pessoa logado = pessoaService.buscaPorLogin(user.getUsername());
		
		ModelAndView mv = new ModelAndView("formulario-profissional");
		mv.addObject("profissional", new Profissional());
		mv.addObject("logado", logado);
		return mv;
	}

	@PostMapping("/adicionar")
	public ModelAndView adicionarProfissional(Profissional profissional) {
				
		profissionalService.adicionarProfissional(profissional);
		ModelAndView mv = new ModelAndView("redirect:/inicio");
		return mv;
	}
	
	@RequestMapping("/listar")
	public ModelAndView listarProfissionais(String equipe) {
		Object auth = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails user = (UserDetails) auth;
		
		Pessoa logado = pessoaService.buscaPorLogin(user.getUsername());
		
		List<Profissional> profissionais= profissionalService.buscarPorUbs(equipe);
		
		ModelAndView mv = new ModelAndView("listar-profissionais");
		mv.addObject("logado", logado);
		mv.addObject("todosOsProfissionais", profissionais);
		return mv;
	}
	
	@RequestMapping(value = "/listar/{equipe}", 
			method = RequestMethod.GET,
			produces = {MimeTypeUtils.APPLICATION_JSON_VALUE})
	public ResponseEntity<List<Profissional>> teste4(@PathVariable("equipe") String equipe) {
		try {
			List<Profissional> profissionais = profissionalService.buscarPorUbs(equipe);
			
			ResponseEntity<List<Profissional>> responseEntity = 
					new ResponseEntity<List<Profissional>>(profissionais, HttpStatus.OK);
			return responseEntity;
		} catch (Exception e) {
			return new ResponseEntity<List<Profissional>>(HttpStatus.BAD_REQUEST);
		}
		
	}
	
	
	@RequestMapping("/listarem")
	public ModelAndView listaremoverProfissionais(String equipe) {
		Object auth = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails user = (UserDetails) auth;
		
		Pessoa logado = pessoaService.buscaPorLogin(user.getUsername());
		
		List<Profissional> profissionais= profissionalService.buscarPorUbs(equipe);
		
		ModelAndView mv = new ModelAndView("listar-rem-profissionais");
		mv.addObject("logado", logado);
		mv.addObject("todosOsProfissionais", profissionais);
		return mv;
	}
	
	@RequestMapping("/excluir/{cns}")
	public ModelAndView excluirPessoa(@PathVariable long cns) {
		profissionalService.removerProfissional(cns);
		
		ModelAndView mv = new ModelAndView("redirect:/profissional/listarem");
		
		return mv;
	}
	
	@RequestMapping("/listaredt")
	public ModelAndView listareditarProfissionais(String equipe) {
		Object auth = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails user = (UserDetails) auth;
		
		Pessoa logado = pessoaService.buscaPorLogin(user.getUsername());
		
		List<Profissional> profissionais= profissionalService.buscarPorUbs(equipe);
		
		ModelAndView mv = new ModelAndView("listar-edt-profissionais");
		mv.addObject("logado", logado);
		mv.addObject("todosOsProfissionais", profissionais);
		return mv;
	}
	
	@RequestMapping("/editar/{cns}")
	public ModelAndView editarProfissional(@PathVariable long cns) {
		Object auth = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails user = (UserDetails) auth;
		
		Pessoa logado = pessoaService.buscaPorLogin(user.getUsername());
		Profissional profissional = profissionalService.buscaPorCns(cns);
		
		
		ModelAndView mv = new ModelAndView("formulario-profissional");
		mv.addObject("profissional", profissional);
		mv.addObject("logado", logado);
		return mv;
	}
	
	@RequestMapping("/fichas/{cns}")
	public ModelAndView fichasProfissional(@PathVariable("cns") long cns) {
		Object auth = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails user = (UserDetails) auth;
		
		Pessoa logado = pessoaService.buscaPorLogin(user.getUsername());
		Profissional profissional = profissionalService.buscaPorCns(cns);
		List<Ficha> fichas = profissional.getFichas();
		
		
		ModelAndView mv = new ModelAndView("fichas-profissional");
		mv.addObject("todasAsFichas", fichas);
		mv.addObject("profissional", profissional);
		mv.addObject("logado", logado);
		
		return mv;
		
	}
}
