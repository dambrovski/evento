package com.app.evento.controllers;

import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.json.HTTP;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.app.evento.models.Convidado;
import com.app.evento.models.Evento;
import com.app.evento.repository.ConvidadoRepository;
import com.app.evento.repository.EventoRepository;

@Controller
public class EventoController {

	@Autowired
	private EventoRepository er;
	
	@Autowired
	private ConvidadoRepository cr;

	@RequestMapping(value = "/cadastrarEvento", method = RequestMethod.GET)
	public String form() {

		return "evento/formEvento";
	}
	
	@RequestMapping(value = "/cadastrarEvento", method = RequestMethod.POST)
	public String form(Evento evento) {

		er.save(evento);
		return "redirect:/cadastrarEvento";
	}





	@RequestMapping("/eventos")
	public ResponseEntity<Collection<Evento>> listaEventos() {
		return new ResponseEntity<>(er.findAll(), HttpStatus.OK);
	}


	
	
	
	
	/*
	 * @RequestMapping(value="/{codigo}", method=RequestMethod.GET) public
	 * ModelAndView detalhesEvento(@PathVariable("codigo") long codigo){ Evento
	 * evento = er.findByCodigo(codigo); 
	 * ModelAndView mv = new ModelAndView("evento/detalhesEvento"); mv.addObject("evento", evento);
	 * Iterable<Convidado> convidados = cr.findByEvento(evento);
	 * mv.addObject("convidados", convidados); return mv;
	 * 
	 * }
	 */
	
	
	
	@RequestMapping(value = "/{codigo}", method = RequestMethod.GET)
	public ResponseEntity<Evento> detalhesEvento(@PathVariable long codigo) {
		if (er.findByCodigo(codigo) != null) {
			Evento evento = er.findByCodigo(codigo);
			Iterable<Convidado> convidados = cr.findByEvento(evento);
			
			evento = (Evento) convidados;
			JSONObject json = new JSONObject();
			JSONArray jsonArr = new JSONArray();
			json.put("evento", evento);
			json.put("convidados", convidados);
			jsonArr.put(json);
			//return new ResponseEntity<Evento>((evento), (MultiValueMap<String, String>) convidados, HttpStatus.OK);
			
			return new ResponseEntity<Evento>(evento,HttpStatus.OK);
			//return new ResponseEntity<>(jsonArr, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}




	
	
	
	@RequestMapping("/deletarEvento")
	public String deletarEvento(long codigo) {
		Evento evento = er.findByCodigo(codigo);
		er.delete(evento);
		return "redirect:/eventos";
	}
	
	
	@RequestMapping(value="/{codigo}", method=RequestMethod.POST)
	public String detalhesEventoPost(@PathVariable("codigo") long codigo, @Valid Convidado convidado, BindingResult result, RedirectAttributes attributest){
		if (result.hasErrors()) {
			attributest.addFlashAttribute("mensagem", "Verifique os campos!");
			return "redirect:/{codigo}";
			
		}
		Evento evento = er.findByCodigo(codigo);
		convidado.setEvento(evento);
		cr.save(convidado);
		attributest.addFlashAttribute("mensagem", "Convidado adicionado com Sucesso!");
		return "redirect:/{codigo}";
		
	}
	
	@RequestMapping("/deletarConvidado")
	public String deletarConvidado(String rg) {
		Convidado convidado = cr.findByRg(rg);
		cr.delete(convidado);
		
		Evento evento = convidado.getEvento();
		long codigoLong = evento.getCodigo();
		String codigo = "" + codigoLong;
		return "redirect:/" + codigo;
		
	}
	
	
	
	
	
	
	
	
	
	
}
