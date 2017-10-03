package rs.ac.singidunum.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.singidunum.data.BusCompany;
import rs.ac.singidunum.data.Route;
import rs.ac.singidunum.data.RouteStop;
import rs.ac.singidunum.data.Stop;
import rs.ac.singidunum.dto.FullRouteDto;
import rs.ac.singidunum.dto.RouteDto;
import rs.ac.singidunum.dto.RouteStopDto;
import rs.ac.singidunum.dto.SimpleRouteDto;
import rs.ac.singidunum.dto.UpdateRouteDto;
import rs.ac.singidunum.service.RouteService;
import rs.ac.singidunum.service.UserService;

@RestController
public class RouteController {

	@Autowired
	private RouteService routeService;

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/routes", method = RequestMethod.GET)
	public ResponseEntity<?> getRoutes(@RequestHeader(value = "token") String token,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date arrivalDate, @RequestParam String fromCity,
			@RequestParam String toCity) {
		if (userService.authentication(token)) {
			List<RouteDto> routes = routeService.getRoutes(arrivalDate, fromCity, toCity);
			if (routes.size() != 0) {
				return new ResponseEntity<>(routes, HttpStatus.OK);
			} else {
				return new ResponseEntity<>("No routes were found", HttpStatus.BAD_REQUEST);
			}
		}
		return new ResponseEntity<>(HttpStatus.FORBIDDEN);
	}

	@RequestMapping(value = "/admin/stop", method = RequestMethod.POST)
	public ResponseEntity<?> addCity(@RequestHeader(value = "token") String token, @RequestBody Stop stop) {
		if (userService.authentication(token)) {
			Stop createdStop = routeService.addStop(stop);
			if (createdStop != null) {
				return new ResponseEntity<>(createdStop, HttpStatus.CREATED);
			} else {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		}
		return new ResponseEntity<>(HttpStatus.FORBIDDEN);

	}

	@RequestMapping(value = "/admin/busCompany", method = RequestMethod.POST)
	public ResponseEntity<?> addBusCompany(@RequestHeader(value = "token") String token,
			@RequestBody BusCompany busCompany) {
		if (userService.authentication(token)) {
			BusCompany createdBusCompany = routeService.addBusCompany(busCompany);
			if (createdBusCompany != null) {
				return new ResponseEntity<>(createdBusCompany, HttpStatus.CREATED);
			} else {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		}
		return new ResponseEntity<>(HttpStatus.FORBIDDEN);

	}

	@RequestMapping(value = "/admin/busCompany", method = RequestMethod.GET)
	public ResponseEntity<?> addBusCompany(@RequestHeader(value = "token") String token) {
		if (userService.authentication(token)) {
			List<BusCompany> busCompanies = routeService.getAllBusCompanies();
			if (busCompanies != null) {
				return new ResponseEntity<>(busCompanies, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		}
		return new ResponseEntity<>(HttpStatus.FORBIDDEN);
	}

	@RequestMapping(value = "/admin/busCompany/{busCompanyId}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateBusCompany(@RequestHeader(value = "token") String token,
			@PathVariable Long busCompanyId, @RequestParam String busCompanyName) {
		if (userService.authentication(token)) {
			BusCompany updatedBusComapny = routeService.updateBusCompany(busCompanyId, busCompanyName);
			if (updatedBusComapny != null) {
				return new ResponseEntity<>(updatedBusComapny, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		}
		return new ResponseEntity<>(HttpStatus.FORBIDDEN);

	}

	@RequestMapping(value = "/admin/busCompany/{busCompanyId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteBusCompany(@RequestHeader(value = "token") String token,
			@PathVariable Long busCompanyId) {
		if (userService.authentication(token)) {
			boolean deleted = routeService.deleteBusCompany(busCompanyId);
			if (deleted) {
				return new ResponseEntity<>(HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		}
		return new ResponseEntity<>(HttpStatus.FORBIDDEN);
	}

	@RequestMapping(value = "/stops", method = RequestMethod.GET)
	public ResponseEntity<?> getUserStops() {
		return new ResponseEntity<>(routeService.getAllStops(), HttpStatus.OK);
	}

	@RequestMapping(value = "/admin/stops", method = RequestMethod.GET)
	public ResponseEntity<?> getStops(@RequestHeader(value = "token") String token) {
		if (userService.authentication(token)) {
			return new ResponseEntity<>(routeService.getAllStops(), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.FORBIDDEN);

	}

	@RequestMapping(value = "/admin/routes", method = RequestMethod.POST)
	public ResponseEntity<?> addRoute(@RequestHeader(value = "token") String token,
			@RequestBody SimpleRouteDto simpleRouteDto) {
		if (userService.authentication(token)) {
			Route createdRoute = routeService.addRoute(simpleRouteDto);
			if (createdRoute != null) {
				return new ResponseEntity<>(createdRoute.getRouteId(), HttpStatus.CREATED);
			} else {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		}
		return new ResponseEntity<>(HttpStatus.FORBIDDEN);

	}

	@RequestMapping(value = "/admin/routeStops/{routeId}", method = RequestMethod.POST)
	public ResponseEntity<?> addRouteStops(@RequestHeader(value = "token") String token,
			@RequestBody List<RouteStopDto> routeStopDtos, @PathVariable Long routeId) {
		if (userService.authentication(token)) {
			List<RouteStop> createdRouteStops = routeService.addRouteStops(routeStopDtos, routeId);
			if (createdRouteStops != null) {
				return new ResponseEntity<>(routeStopDtos, HttpStatus.CREATED);
			} else {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		}
		return new ResponseEntity<>(HttpStatus.FORBIDDEN);

	}

	@RequestMapping(value = "/admin/stops/{stopId}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateStop(@RequestHeader(value = "token") String token, @PathVariable Long stopId,
			@RequestParam String cityName) {
		if (userService.authentication(token)) {
			Stop updatedStop = routeService.updateStop(stopId, cityName);
			if (updatedStop != null) {
				return new ResponseEntity<>(updatedStop, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		}
		return new ResponseEntity<>(HttpStatus.FORBIDDEN);

	}

	@RequestMapping(value = "/admin/stops/{stopId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteStop(@RequestHeader(value = "token") String token, @PathVariable Long stopId) {
		if (userService.authentication(token)) {
			boolean deleted = routeService.deleteStop(stopId);
			if (deleted) {
				return new ResponseEntity<>(HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		}
		return new ResponseEntity<>(HttpStatus.FORBIDDEN);
	}

	@RequestMapping(value = "/admin/routes", method = RequestMethod.GET)
	public ResponseEntity<?> getFullRoutes(@RequestHeader(value = "token") String token) {
		if (userService.authentication(token)) {
			List<FullRouteDto> routes = routeService.getFullRoutes();
			if (routes.size() != 0) {
				return new ResponseEntity<>(routes, HttpStatus.OK);
			} else {
				return new ResponseEntity<>("No routes were found", HttpStatus.BAD_REQUEST);
			}
		}
		return new ResponseEntity<>(HttpStatus.FORBIDDEN);
	}

	@RequestMapping(value = "/admin/routeStops", method = RequestMethod.PUT)
	public ResponseEntity<?> updateStop(@RequestHeader(value = "token") String token,
			@RequestBody UpdateRouteDto updateRouteDto) {
		if (userService.authentication(token)) {
			RouteStop updatedRouteStop = routeService.updateRouteStop(updateRouteDto);
			if (updatedRouteStop != null) {
				return new ResponseEntity<>(updateRouteDto, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		}
		return new ResponseEntity<>(HttpStatus.FORBIDDEN);

	}

	@RequestMapping(value = "/admin/routeStops/{routeStopId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteRouteStop(@RequestHeader(value = "token") String token,
			@PathVariable Long routeStopId) {
		if (userService.authentication(token)) {
			boolean deleted = routeService.deleteRouteStop(routeStopId);
			if (deleted) {
				return new ResponseEntity<>(HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		}
		return new ResponseEntity<>(HttpStatus.FORBIDDEN);

	}
}
