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

import com.devoliga.ifood.Repository.CategoriaRepository;
import com.devoliga.ifood.model.Categoria;





@RestController
@RequestMapping("/categoria")
@CrossOrigin(origins = "*", allowedHeaders = "*" )
public class CategoriaController {
	
	@Autowired
	private CategoriaRepository categoryRepository;
	
	@GetMapping
	public ResponseEntity <List<Categoria>> getAll(){
		return ResponseEntity.ok(categoryRepository.findAll());
		
	} 
	
	@GetMapping("/{id}")
	public ResponseEntity<Categoria> getById(@PathVariable long id){
		return categoryRepository.findById(id)
			 .map(resp -> ResponseEntity.ok(resp))
			.orElse(ResponseEntity.notFound().build());
		
	}
	
	@GetMapping("/setor/{setor}")
	public ResponseEntity<List<Categoria>> getBySetor(@PathVariable String setor){
		return ResponseEntity.ok(categoryRepository.findAllBySetorContainingIgnoreCase(setor));
	}
	
	@PostMapping
	public ResponseEntity<Categoria> postCategoria(@RequestBody Categoria categoria){
		return ResponseEntity.status(HttpStatus.CREATED).body(categoryRepository.save(categoria));
	}
	
	@PutMapping
	public ResponseEntity<Categoria> putCategoria(@RequestBody Categoria categoria){
		return ResponseEntity.status(HttpStatus.OK).body(categoryRepository.save(categoria));		
	}
	
	@DeleteMapping("/{id}")
	public void deletePostagem(@PathVariable long id) {
		 categoryRepository.deleteById(id);
	}
	
	
	
	
	




}
