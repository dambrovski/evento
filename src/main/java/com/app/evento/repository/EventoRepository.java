package com.app.evento.repository;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;

import com.app.evento.models.Evento;

public interface EventoRepository extends CrudRepository<Evento, String>{
	Evento findByCodigo(long codigo);
	Collection<Evento> findAll();
	

}
