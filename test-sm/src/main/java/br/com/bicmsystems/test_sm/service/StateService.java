package br.com.bicmsystems.test_sm.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.bicmsystems.test_sm.dto.StateDto;
import br.com.bicmsystems.test_sm.model.State;
import br.com.bicmsystems.test_sm.repository.StateRepository;

@Service
public class StateService {

	@Autowired
	private StateRepository repository;
	
	public List<StateDto> listAll() {
		
		List<State> models = repository.findAll();
		return convertListModelToDto(models);
		
	}
	
	private List<StateDto> convertListModelToDto(List<State> models) {
		
		return models.stream().map(c -> 
		StateDto.builder().initials(c.getInitials()).name(c.getName()).build())
				.collect(Collectors.toList());
		
	}
		
}
