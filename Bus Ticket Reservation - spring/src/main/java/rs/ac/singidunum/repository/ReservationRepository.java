package rs.ac.singidunum.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import rs.ac.singidunum.data.Reservation;

public interface ReservationRepository extends CrudRepository<Reservation, Long> {

	@Modifying
	@Query(value = "insert into reservation (user_id, route_id, number_of_tickets, price) values (?1, ?2, ?3, ?4)", nativeQuery = true)
	public int save(Long userId, Long routeId, Integer numberOfTickets, Double price);
	
	
}
