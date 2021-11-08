package com.devoliga.ifood.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devoliga.ifood.model.Produto;


public interface ProdutosRepository extends JpaRepository<Produto, Long> {
	
	public List<Produto> findAllByNomeContainingIgnoreCase(String nome);

}
