package br.com.bicmsystems.test_sm.model;

import javax.persistence.Table;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Table(name = "Category")
@Data
@Builder
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer code;
	
	private String name;
	
}
