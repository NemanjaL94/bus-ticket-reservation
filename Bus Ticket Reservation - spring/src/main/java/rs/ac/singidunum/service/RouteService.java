package rs.ac.singidunum.service;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import rs.ac.singidunum.data.BusCompany;
import rs.ac.singidunum.data.Route;
import rs.ac.singidunum.data.RouteStop;
import rs.ac.singidunum.data.Stop;
import rs.ac.singidunum.dto.FullRouteDto;
import rs.ac.singidunum.dto.RouteDto;
import rs.ac.singidunum.dto.RouteStopDto;
import rs.ac.singidunum.dto.SimpleRouteDto;
import rs.ac.singidunum.dto.TemporaryRouteDto;
import rs.ac.singidunum.dto.UpdateRouteDto;
import rs.ac.singidunum.repository.BusCompanyRepository;
import rs.ac.singidunum.repository.RouteRepository;
import rs.ac.singidunum.repository.RouteStopRepository;
import rs.ac.singidunum.repository.StopRepository;

@Service
public class RouteService {

	@Autowired
	private RouteRepository routeRepository;

	@Autowired
	private BusCompanyRepository busCompanyRepository;

	@Autowired
	private StopRepository stopRepository;

	@Autowired
	private RouteStopRepository routeStopRepository;

	public List<RouteDto> getRoutes(Date arrivalDate, String fromCity, String toCity) {
		List<Object[]> result = routeRepository.getRoutes(arrivalDate, fromCity, toCity);
		List<TemporaryRouteDto> temporaryRoutes = addTemporaryRoutes(result);

		return getValidRoutes(temporaryRoutes, fromCity, toCity);
	}

	public List<TemporaryRouteDto> addTemporaryRoutes(List<Object[]> result) {

		List<TemporaryRouteDto> temporaryRoutes = new ArrayList<>();
		fillTemporaryRoutes(temporaryRoutes, result);
		return temporaryRoutes;
	}

	public void fillTemporaryRoutes(List<TemporaryRouteDto> temporaryRoutes, List<Object[]> result) {

		for (Object[] row : result) {
			Integer routeId = (Integer) row[0];
			String busCompanyName = (String) row[1];
			Time arrivalTime = (Time) row[2];
			String cityName = (String) row[3];
			Double fare = (Double) row[4];
			Integer availableTickets = (Integer) row[5];

			temporaryRoutes
					.add(new TemporaryRouteDto(routeId, busCompanyName, arrivalTime, cityName, fare, availableTickets));

		}
	}

	public List<RouteDto> getValidRoutes(List<TemporaryRouteDto> temporaryRoutes, String fromCity, String toCity) {
		List<RouteDto> routes = new ArrayList<>();
		for (int i = 0; i < temporaryRoutes.size() - 1; i++) {

			TemporaryRouteDto t1 = (TemporaryRouteDto) temporaryRoutes.get(i);
			TemporaryRouteDto t2 = (TemporaryRouteDto) temporaryRoutes.get(i + 1);

			checkTemporaryRouteDtos(routes, t1, t2, fromCity, toCity);

		}
		return routes;
	}

	public void checkTemporaryRouteDtos(List<RouteDto> routes, TemporaryRouteDto t1, TemporaryRouteDto t2,
			String fromCity, String toCity) {

		Integer routeId = 0;
		String busCompanyName = t1.getBusCompanyName();
		StringBuilder route = new StringBuilder();
		StringBuilder schedule = new StringBuilder();
		route.append(fromCity + " - " + toCity);

		if (t1.getRouteId().intValue() == t2.getRouteId().intValue()) {
			routeId = t1.getRouteId();
			if (t1.getFare() == 0 && t1.getCityName().equals(fromCity) && t2.getCityName().equals(toCity)) {
				schedule.append(t1.getArrivalTime() + " - " + t2.getArrivalTime());
				routes.add(new RouteDto(routeId, busCompanyName, route.toString(), schedule.toString(), t2.getFare(),
						t1.getAvailableTickets()));

			} else if (t2.getFare() == 0 && t2.getCityName().equals(fromCity) && t1.getCityName().equals(toCity)) {
				schedule.append(t2.getArrivalTime() + " - " + t1.getArrivalTime());
				routes.add(new RouteDto(routeId, busCompanyName, route.toString(), schedule.toString(), t1.getFare(),
						t2.getAvailableTickets()));
			}

		}

	}

	public List<FullRouteDto> getFullRoutes() {
		List<Object[]> result = routeRepository.getRouteStops();
		List<FullRouteDto> fullRouteDtos = new ArrayList<>();
		fillFullRoutes(fullRouteDtos, result);
		return fullRouteDtos;
	}

	public void fillFullRoutes(List<FullRouteDto> fullRouteDtos, List<Object[]> result) {

		for (Object[] row : result) {
			Integer routeStopId = (Integer) row[0];
			Integer routeId = (Integer) row[1];
			String cityName = (String) row[2];
			Time arrivalTime = (Time) row[3];
			Date arrivalDate = (Date) row[4];
			Double fare = (Double) row[5];
			Integer availableTickets = (Integer) row[6];

			fullRouteDtos.add(
					new FullRouteDto(routeStopId, routeId, cityName, arrivalTime, arrivalDate, fare, availableTickets));

		}
	}

	public Stop addStop(Stop stop) {
		return stopRepository.save(stop);
	}

	public BusCompany addBusCompany(BusCompany busCompany) {
		return busCompanyRepository.save(busCompany);
	}

	public List<Stop> getAllStops() {
		return stopRepository.findAll();
	}

	public List<BusCompany> getAllBusCompanies() {
		return busCompanyRepository.findAll();
	}

	public Route addRoute(SimpleRouteDto simpleRouteDto) {
		if (simpleRouteDto != null) {
			BusCompany foundBusCompany = busCompanyRepository.findOne(simpleRouteDto.getBusCompanyId());
			if (foundBusCompany != null) {
				return routeRepository.save(new Route(foundBusCompany, simpleRouteDto.getAvailableTickets()));
			}
		}
		return null;
	}

	@Transactional
	public List<RouteStop> addRouteStops(List<RouteStopDto> routeStopDtos, Long routeId) {
		Route route = routeRepository.findOne(routeId);
		if (routeStopDtos != null && route != null) {
			List<RouteStop> routeStops = new ArrayList<>();
			for (RouteStopDto routeStopDto : routeStopDtos) {
				BusCompany foundBusCompany = busCompanyRepository.findOne(routeStopDto.getBusCompanyId());
				Stop foundStop = stopRepository.findOne(routeStopDto.getStopId());
				if (foundBusCompany == null || foundStop == null) {
					return null;
				} else {
					routeStops.add(new RouteStop(route, foundStop, routeStopDto.getArrivalTime(),
							routeStopDto.getArrivalDate(), routeStopDto.getPrice()));
				}
			}
			return (List<RouteStop>) routeStopRepository.save(routeStops);
		}
		return null;
	}

	public BusCompany updateBusCompany(Long busCompanyId, String busCompanyName) {
		BusCompany foundBusCompany = busCompanyRepository.findOne(busCompanyId);
		if (foundBusCompany != null) {
			foundBusCompany.setBusCompanyName(busCompanyName);
			return busCompanyRepository.save(foundBusCompany);
		} else {
			return null;
		}
	}

	public boolean deleteBusCompany(Long busCompanyId) {
		BusCompany foundBusCompany = busCompanyRepository.findOne(busCompanyId);
		if (foundBusCompany != null) {
			busCompanyRepository.delete(busCompanyId);
			return true;
		} else {
			return false;
		}
	}

	public Stop updateStop(Long stopId, String cityName) {
		Stop foundStop = stopRepository.findOne(stopId);
		if (foundStop != null) {
			foundStop.setCityName(cityName);
			return stopRepository.save(foundStop);
		} else {
			return null;
		}
	}

	public boolean deleteStop(Long stopId) {
		Stop foundStop = stopRepository.findOne(stopId);
		if (foundStop != null) {
			stopRepository.delete(stopId);
			return true;
		} else {
			return false;
		}
	}

	public RouteStop updateRouteStop(UpdateRouteDto updateRouteDto) {
		RouteStop foundRouteStop = routeStopRepository.findOne(updateRouteDto.getRouteStopId());
		if (foundRouteStop != null) {
			foundRouteStop.setArrivalTime(updateRouteDto.getArrivalTime());
			foundRouteStop.setArrivalDate(updateRouteDto.getArrivalDate());
			foundRouteStop.setFare(updateRouteDto.getPrice());
			return routeStopRepository.save(foundRouteStop);
		} else {
			return null;
		}
	}

	public boolean deleteRouteStop(Long routeStopId) {
		RouteStop foundRouteStop = routeStopRepository.findOne(routeStopId);
		if (foundRouteStop != null) {
			routeStopRepository.delete(routeStopId);
			return true;
		} else {
			return false;
		}
	}

}
