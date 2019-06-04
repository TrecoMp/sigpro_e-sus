package com.sistema.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfWriter;
import com.sistema.model.Ficha;
import com.sistema.model.MsgAdd;
import com.sistema.model.MsgRespAdd;
import com.sistema.model.Pessoa;
import com.sistema.model.Profissional;
import com.sistema.model.Registro;
import com.sistema.model.Relatorio;
import com.sistema.service.FichaService;
import com.sistema.service.PessoaService;
import com.sistema.service.ProfissionalService;
import com.sistema.service.RegistroService;


@Controller
@RequestMapping("/registro")
public class RegistroController {
	
	@Autowired
	ProfissionalService profissionalService;
	
	@Autowired
	PessoaService pessoaService;
	
	@Autowired
	RegistroService registroService;
	
	@Autowired
	FichaService fichaService;
	
	@RequestMapping("/adicionar")
	public ModelAndView adicionarRegistro() {
		Object auth = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails user = (UserDetails) auth;
		Pessoa logado= pessoaService.buscaPorLogin(user.getUsername());
		
		Calendar c = Calendar.getInstance();
		//SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
    	
		
		Registro newRegistro = new Registro();
		newRegistro.setDigitador(logado.getNome());
		newRegistro.setData(new Timestamp(c.getTimeInMillis()));
		newRegistro.setPessoa("temp");
		newRegistro.setTipo("temp");
		newRegistro.setId(registroService.adicionarRegistro(newRegistro));
		
		ModelAndView mv = new ModelAndView("form-registro");
		
		mv.addObject("registro", newRegistro);
		mv.addObject("logado", logado);
		
		return mv;

	}
	
	@PostMapping("/addficha")
	public ResponseEntity<List<MsgRespAdd>> addficha(@RequestBody MsgAdd msg) {
		try {
			
			//Primeiro converte de String para Date
			//DateFormat formatUS = new SimpleDateFormat("dd-mm-yyy");
			Calendar c = Calendar.getInstance();
					
			c.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(msg.getData()));
			Date date = c.getTime();
			
			
			Ficha ficha = new Ficha();
			
			ficha.setProfissional(profissionalService.buscaPorCns(msg.getId_prof()));
			ficha.setRegistro(registroService.buscaRegistro(msg.getId_registro()));
			ficha.setData(date);
			
			fichaService.adicionarFicha(ficha);
				
			Registro registro = registroService.buscaRegistro(msg.getId_registro());
			
			List<Ficha> fichas = registro.getFichas();
			
			List<MsgRespAdd> resposta = new ArrayList<MsgRespAdd>();
			
			
			for (Ficha f : fichas) {
				MsgRespAdd m = new MsgRespAdd(f.getProfissional().getNome(), f.getData());
				resposta.add(m);
			}
			
			ResponseEntity<List<MsgRespAdd>> responseEntity = 
					new ResponseEntity<List<MsgRespAdd>>(resposta, HttpStatus.OK);
			return responseEntity;
		} catch (Exception e) {
			return new ResponseEntity<List<MsgRespAdd>>(HttpStatus.BAD_REQUEST);

		}	
	}
	
	@PostMapping("/salvar")
	public ResponseEntity<String> salvarRegistro(@RequestBody MsgAdd msg) {
		try {
			// utilizei a classe MsgAdd para realizar a passagem de dados
			//pessoa veio dentro de msg.data
			//tipo veio dentro de msg.id_prof
			long tipo = msg.getId_prof();
			String pessoa = msg.getPessoa();
			
			Registro registro = registroService.buscaRegistro(msg.getId_registro());
			
			registro.setPessoa(pessoa);
			if (tipo == 0) {
				registro.setTipo("Recebimento");
			}else {
				registro.setTipo("Devolução");
			}
			
			registroService.adicionarRegistro(registro);
						
			
			String resposta = "OK";
			
			ResponseEntity<String> responseEntity = 
					new ResponseEntity<String>(resposta, HttpStatus.OK);
			return responseEntity;
		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);

		}	
	}
	
	@RequestMapping("/buscar")
	public ModelAndView buscaRegistro(String id_registro) {
		Object auth = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails user = (UserDetails) auth;
		Pessoa logado= pessoaService.buscaPorLogin(user.getUsername());
		
		Registro registro = new Registro();
		ModelAndView mv = new ModelAndView("listar-registro");
		
		if (id_registro != null) {
			registro = registroService.buscaRegistro(Long.parseLong(id_registro));
			mv.addObject("registro", registro);
		}
		
		mv.addObject("logado", logado);
		
		return mv;
	}
	
	@RequestMapping("/remover")
	public ModelAndView removerRegistro(String id_registro) {
		Object auth = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails user = (UserDetails) auth;
		Pessoa logado= pessoaService.buscaPorLogin(user.getUsername());
		
		Registro registro = new Registro();
		ModelAndView mv = new ModelAndView("listar-registro-rem");
		
		if (id_registro != null) {
			registro = registroService.buscaRegistro(Long.parseLong(id_registro));
			mv.addObject("registro", registro);
		}
		
		mv.addObject("logado", logado);
		
		return mv;
	}
	
	@RequestMapping("/excluir/{id}")
	public ModelAndView excluirRegistro(@PathVariable("id") long id) {
		registroService.removerRegistro(id);
		ModelAndView mv = new ModelAndView("redirect:/registro/remover");
		return mv;
		
	}
	
	@RequestMapping("/limpador")
	public ResponseEntity<String> limpaRegistro() {
		try {
			registroService.removerRegistroTipo("temp");
			String resposta = "OK";
			
			ResponseEntity<String> responseEntity = 
					new ResponseEntity<String>(resposta, HttpStatus.OK);
			return responseEntity;
		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);

		}	
	}
	
	
	@RequestMapping("/formrelatorio")
	public ModelAndView formRelatorio() {
		Object auth = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails user = (UserDetails) auth;
		Pessoa logado= pessoaService.buscaPorLogin(user.getUsername());
		
		ModelAndView mv = new ModelAndView("form-relatorio");
		mv.addObject("logado", logado);
		
		return mv;
	}
	
	public ByteArrayInputStream montaPdf(List<Profissional> profissionais, Date dataInicio, Date dataFim, String equipe) throws DocumentException {
		Document document = new Document();
		ByteArrayOutputStream saida = new ByteArrayOutputStream();
		PdfWriter.getInstance(document, saida);
		
		try {
			
			Calendar c = Calendar.getInstance();
			Timestamp ts = new Timestamp(c.getTimeInMillis());
			DateFormat fdata = new SimpleDateFormat("dd-MM-yyyy-HH:mm:ss");
			String time = fdata.format(ts);
						
			
			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			
			document.open();
			document.setPageSize(PageSize.A4);
			Paragraph ptitulo = new Paragraph(new Phrase(20F , "Relatório de dias não entregues" + "\n" + equipe + "\n" +  dateFormat.format(dataInicio) + " - " + dateFormat.format(dataFim), FontFactory.getFont(FontFactory.HELVETICA, 18F)));
            ptitulo.setAlignment(Element.ALIGN_CENTER);
            ptitulo.add("\n" + "\n");
            document.add( ptitulo );
			
			List<Date> datas = new ArrayList<Date>();
            datas.add(dataInicio);
            Calendar inicio = Calendar.getInstance();
            inicio.setTime(dataInicio);
            
            while(inicio.getTime().before(dataFim)) {
            	inicio.add(Calendar.DAY_OF_YEAR,1);
                datas.add(inicio.getTime());
            }
            
            GregorianCalendar gc = new GregorianCalendar();
            Paragraph p = new Paragraph();
            
			for (Profissional profissional : profissionais) {
				p.add(new Phrase("Profissional: " + profissional.getNome() + "\n"));
				
				List<Date> datasFichas = new ArrayList<Date>();
				for (Ficha f : profissional.getFichas()) {
					datasFichas.add(f.getData());
				}
				
				for (Date d : datas) {
					gc.setTime(d);
					
					if ( gc.get(Calendar.DAY_OF_WEEK) > 1 && gc.get(Calendar.DAY_OF_WEEK) < 7) {
						if ( !datasFichas.contains(d)) {
							p.add(dateFormat.format(d) + "\n");
						}
					}
				}
			}
			
			document.add(p);
			Paragraph datafim = new Paragraph();
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
			
			datafim.add("Gerado dia: " + formato.format(new Timestamp(cal.getTimeInMillis())));
			datafim.setAlignment(Element.ALIGN_RIGHT);
			document.add(datafim);
			document.close();
			
		 } catch (DocumentException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		
		 return new ByteArrayInputStream(saida.toByteArray());
	}
	
	@RequestMapping(value="gerarelatorio",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_PDF_VALUE)
		public ResponseEntity<InputStreamResource> gerarRelatorio(Relatorio relatorio) throws ParseException, DocumentException {
		
		String equipe = relatorio.getEquipe();
		
		Calendar c = Calendar.getInstance();
		//Timestamp ts = new Timestamp(c.getTimeInMillis());
		
		c.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(relatorio.getDatainicio()));
		Date di = c.getTime();
		
		c.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(relatorio.getDatafim()));
		Date df = c.getTime();
		
		List<Profissional> profissionais = profissionalService.buscarPorUbs(equipe);
		
		ByteArrayInputStream arquivo = montaPdf(profissionais, di, df, equipe);
		
		HttpHeaders headers = new HttpHeaders();
		
		headers.add("Content-Disposition", "inline; filename=relatorio.pdf");
		
		return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(arquivo));
		
	}
}
