package com.sistema.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sistema.model.Pessoa;
import com.sistema.model.Role;
import com.sistema.service.PessoaService;

@Controller
@RequestMapping("/pessoa")
public class PessoaController {

	@Autowired
	private PessoaService pessoaService;
	
	@RequestMapping("/formpessoa")
	public ModelAndView formularioPessoa() {
		Object auth = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails user = (UserDetails) auth;
		
		Pessoa logado = pessoaService.buscaPorLogin(user.getUsername());
		
		ModelAndView mv = new ModelAndView("formulario-pessoa");
		mv.addObject("pessoa", new Pessoa());
		mv.addObject("logado", logado);
		return mv;
	}

	@PostMapping("/adicionar")
	public ModelAndView adicionarPessoa(Pessoa pessoa) {
		//todo usuario adicionado ja entra como usuario
		
		Object auth = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails user = (UserDetails) auth;
		
		Pessoa logado = pessoaService.buscaPorLogin(user.getUsername());
		
		Pessoa p1 = pessoaService.buscaPorLogin(pessoa.getLogin());
		
		if(p1==null) {
		
			Role role = new Role();
			List<Pessoa> pessoas = new ArrayList<Pessoa>();
			List<Role> roles = new ArrayList<Role>();
			role.setPapel("ROLE_USER");
			pessoas.add(pessoa);
			roles.add(role);
			role.setPessoas(pessoas);
			//fim
			pessoa.setRoles(roles);
			pessoaService.adicionarPessoa(pessoa);
			ModelAndView mv = new ModelAndView("redirect:/inicio");
			return mv;
		}else {
			int resposta = 1;
			ModelAndView mv = new ModelAndView("formulario-pessoa");
			mv.addObject("pessoa", pessoa);
			mv.addObject("resposta", resposta);
			mv.addObject("logado", logado);
			return mv;
		}
	}

	@PostMapping("/atualizar")
	public ModelAndView atualuzarPessoa(Pessoa pessoa) {
		
		Pessoa p = pessoaService.buscarPorId(pessoa.getId());
		//pega o papel del no banco pois o mesmo não vem do html		
		List<Role> roles = p.getRoles();
		
		pessoa.setRoles(roles);
		
		pessoaService.adicionarPessoa(pessoa);
		ModelAndView mv = new ModelAndView("redirect:/inicio");
		return mv;

	}
	
	
	@GetMapping("/listarpessoas")
	public ModelAndView listarPessoas() {
		Object auth = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails user = (UserDetails) auth;
		
		Pessoa logado = pessoaService.buscaPorLogin(user.getUsername());
		List<Pessoa> pessoas = pessoaService.listarPessoas();
		
		ModelAndView mv = new ModelAndView("listar-pessoas");
		mv.addObject("todasAsPessoas", pessoas);
		mv.addObject("logado", logado);
		return mv;
	}
	
	@GetMapping("/attpessoas")
	public ModelAndView attPessoas() {
		Object auth = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails user = (UserDetails) auth;
		
		Pessoa logado = pessoaService.buscaPorLogin(user.getUsername());
		List<Pessoa> pessoas = pessoaService.listarPessoas();
		
		ModelAndView mv = new ModelAndView("att-pessoa");
		mv.addObject("todasAsPessoas", pessoas);
		mv.addObject("logado", logado);
		return mv;
	}
	
	@RequestMapping("/autoatt")
	public ModelAndView autoAttPessoa() {
		Object auth = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails user = (UserDetails) auth;
		
		Pessoa logado = pessoaService.buscaPorLogin(user.getUsername());
		
		ModelAndView mv = new ModelAndView("formatt-pessoa");
		mv.addObject("pessoa", logado);
		mv.addObject("logado", logado);
		
		return mv;
	}
	
	@RequestMapping("/att/{id}")
	public ModelAndView atualizarPessoa(@PathVariable Long id) {
		Object auth = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails user = (UserDetails) auth;
		
		Pessoa logado = pessoaService.buscaPorLogin(user.getUsername());
		Pessoa pessoa = pessoaService.buscarPorId(id);
		
		ModelAndView mv = new ModelAndView("formatt-pessoa");
		mv.addObject("pessoa", pessoa);
		mv.addObject("logado", logado);
		
		return mv;
	}
	
	@RequestMapping("/rempessoas")
	public ModelAndView removerPessoas() {
		Object auth = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails user = (UserDetails) auth;
		
		Pessoa logado = pessoaService.buscaPorLogin(user.getUsername());
		List<Pessoa> pessoas = pessoaService.listarPessoas();
		
		ModelAndView mv = new ModelAndView("rem-pessoas");
		mv.addObject("todasAsPessoas", pessoas);
		mv.addObject("logado", logado);
		return mv;
	}
	
	@RequestMapping("/excluir/{id}")
	public ModelAndView excluirPessoa(@PathVariable Long id) {
		pessoaService.removerPessoa(id);
		
		ModelAndView mv = new ModelAndView("redirect:/pessoa/rempessoas");
		
		return mv;
	}
	
	@RequestMapping("/logar")
	public ModelAndView logar() {
		ModelAndView mv = new ModelAndView("login");
		return mv;
	}

	
	@RequestMapping("/tornaadm/{id}")
	public ModelAndView tornarAdm(@PathVariable Long id) {
		Pessoa pessoa = pessoaService.buscarPorId(id);
		Role role = new Role();
		role.setPapel("ROLE_ADMIN");
		List<Role> roles = new ArrayList<Role>();
		roles.add(role);
		pessoa.setRoles(roles);
		pessoaService.atualizarPessoa(pessoa);
		ModelAndView mv = new ModelAndView("redirect:/pessoa/attpessoas");
		return mv;
	}
}
