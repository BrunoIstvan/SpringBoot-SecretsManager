package br.com.bicmsystems.test_sm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.bicmsystems.test_sm.model.State;

@Repository
public interface StateRepository extends JpaRepository<State, String> {

}
