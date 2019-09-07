package br.com.bicmsystems.test_sm.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.bicmsystems.test_sm.dto.CategoryDto;
import br.com.bicmsystems.test_sm.model.Category;
import br.com.bicmsystems.test_sm.repository.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository repository;
	
	public List<CategoryDto> listAll() {
		
		List<Category> models = repository.findAll();
		return convertListModelToDto(models);
		
	}
	
	public CategoryDto save(CategoryDto dto) {
		
		Category model = convertDtoToModel(dto);
		model = repository.save(model);
		return convertModelToDto(model);
		
	}
	
	public void deleteById(Integer id) {
		
		repository.deleteById(id);
		
	}
	
	private List<CategoryDto> convertListModelToDto(List<Category> models) {
		
		return models.stream().map(c -> 
			CategoryDto.builder().code(c.getCode()).name(c.getName()).build())
				.collect(Collectors.toList());
		
	}
	
	private CategoryDto convertModelToDto(Category model) {
		
		return CategoryDto.builder().code(model.getCode()).name(model.getName()).build();
		
	}
	
	private Category convertDtoToModel(CategoryDto dto) {
		
		return Category.builder().code(dto.getCode()).name(dto.getName()).build();
		
	}
	
}
