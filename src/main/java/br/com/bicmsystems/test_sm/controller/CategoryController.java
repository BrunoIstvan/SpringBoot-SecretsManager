package br.com.bicmsystems.test_sm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.bicmsystems.test_sm.dto.CategoryDto;
import br.com.bicmsystems.test_sm.service.CategoryService;

@RestController
@RequestMapping("/category")
public class CategoryController {

	@Autowired
	private CategoryService service;
	
	@GetMapping
	public List<CategoryDto> listAll() {
		
		return service.listAll();
		
	}
	
	@PostMapping
	public CategoryDto insert(@RequestBody CategoryDto dto) {
		
		return service.save(dto);
		
	}
	
	@PutMapping("/{id}")
	public CategoryDto update(@RequestBody CategoryDto dto,
							  @PathVariable Integer id) {
		
		return service.save(dto);
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteById(@PathVariable Integer id) {
		
		service.deleteById(id);
		return ResponseEntity.noContent().build();
				
	}
	
}
