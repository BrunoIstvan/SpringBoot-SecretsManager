package br.com.bicmsystems.test_sm.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Entity
@Table(name = "State")
@Data
public class State {

	@Id
	@Length(min = 2, max = 2)
	private String initials;
	
	private String name;
	
}
