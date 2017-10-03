package rs.ac.singidunum.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.format.annotation.DateTimeFormat;

import rs.ac.singidunum.data.Route;

public interface RouteRepository extends CrudRepository<Route, Long> {

	List<Route> findAll();

	@Query(value = "select route_stop.route_id, bus_company_name, arrival_time, city_name, fare, available_tickets from bus_company inner join route on bus_company.bus_company_id = route.bus_company_id inner join route_stop on route.route_id = route_stop.route_id inner join stop on route_stop.stop_id = stop.stop_id where arrival_date = ?1 and city_name in (?2, ?3) order by route_id;", nativeQuery = true)
	List<Object[]> getRoutes(@DateTimeFormat(pattern = "yyyy-MM-dd") Date arrivalDate, String fromCity, String toCity);

	@Query(value = "select route_stop_id, route.route_id, city_name, arrival_time, arrival_date, fare, available_tickets from route_stop inner join stop on route_stop.stop_id= stop.stop_id inner join route on route_stop.route_id = route.route_id order by route.route_id asc, fare asc;", nativeQuery = true)
	List<Object[]> getRouteStops();

}