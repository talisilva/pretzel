package com.devoliga.ifood.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devoliga.ifood.Repository.ProdutosRepository;
import com.devoliga.ifood.model.Produto;



@RestController
@RequestMapping("/produtos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProdutosController {
	@Autowired
	private ProdutosRepository produtosRepository;
	
	@GetMapping
	public ResponseEntity<List<Produto>> getAll(){
		return ResponseEntity.ok(produtosRepository.findAll());
		
	} 
	
	@GetMapping("/{id}")
	public ResponseEntity<Produto> getById(@PathVariable long id){
		return produtosRepository.findById(id)
			 .map(resp -> ResponseEntity.ok(resp))
			.orElse(ResponseEntity.notFound().build());
		
	}
	
	@GetMapping("/nome/{nome}")
	public ResponseEntity<List<Produto>> getByNome(@PathVariable String nome){
		return ResponseEntity.ok(produtosRepository.findAllByNomeContainingIgnoreCase(nome));
	}
	

	
	@PostMapping
	public ResponseEntity<Produto> postCategoria(@RequestBody Produto produto){
		return ResponseEntity.status(HttpStatus.CREATED).body(produtosRepository.save(produto));
	}
	
	@PutMapping
	public ResponseEntity<Produto> putCategoria(@RequestBody Produto produto){
		return ResponseEntity.status(HttpStatus.OK).body(produtosRepository.save(produto));		
	}
	
	@DeleteMapping("/{id}")
	public void deletePostagem(@PathVariable long id) {
		produtosRepository.deleteById(id);
	}
	
	
}
