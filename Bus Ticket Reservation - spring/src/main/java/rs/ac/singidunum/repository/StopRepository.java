package rs.ac.singidunum.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import rs.ac.singidunum.data.Stop;

public interface StopRepository extends CrudRepository<Stop, Long> {
	
	List<Stop> findAll();
}
