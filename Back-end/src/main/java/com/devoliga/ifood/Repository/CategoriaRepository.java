package com.devoliga.ifood.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devoliga.ifood.model.Categoria;


public interface CategoriaRepository extends JpaRepository<Categoria,Long > {


	public List<Categoria> findAllBySetorContainingIgnoreCase(String setor);

}
