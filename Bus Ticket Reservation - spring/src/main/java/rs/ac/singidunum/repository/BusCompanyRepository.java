package rs.ac.singidunum.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import rs.ac.singidunum.data.BusCompany;

public interface BusCompanyRepository extends CrudRepository<BusCompany, Long> {
	
	List<BusCompany> findAll();
}
