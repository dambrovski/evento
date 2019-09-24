package com.app.evento.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.app.evento.models.Convidado;
import com.app.evento.models.Evento;

public interface ConvidadoRepository extends CrudRepository<Convidado, String>{
	List<Convidado> findByEvento(Evento evento);
	Convidado findByRg(String rg);

}
