package rs.ac.singidunum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.singidunum.data.Reservation;
import rs.ac.singidunum.dto.ReservationDto;
import rs.ac.singidunum.service.ReservationService;
import rs.ac.singidunum.service.UserService;

@RestController
public class ReservationController {

	@Autowired
	private ReservationService reservationService;

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/admin/reservations", method = RequestMethod.GET)
	public ResponseEntity<?> getReservations(@RequestHeader(value = "token") String token) {
		if (userService.authentication(token)) {
			return new ResponseEntity<>(reservationService.getAllReservations(), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.FORBIDDEN);
	}

	@RequestMapping(value = "/reservation", method = RequestMethod.POST)
	public ResponseEntity<?> makeReservation(@RequestHeader(value = "token") String token,
			@RequestBody ReservationDto reservationDto) {
		if (userService.authentication(token)) {
			ReservationDto createdReservation = reservationService.makeReservation(token, reservationDto);
			if (createdReservation != null) {
				return new ResponseEntity<>(createdReservation, HttpStatus.CREATED);
			} else {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}

		}
		return new ResponseEntity<>(HttpStatus.FORBIDDEN);
	}
	
	@RequestMapping(value = "/admin/reservations/{reservationId}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateStop(@RequestHeader(value = "token") String token, @PathVariable Long reservationId,
			@RequestParam Integer tickets, @RequestParam Double price) {
		if (userService.authentication(token)) {
			Reservation updatedReservation = reservationService.updateReservation(reservationId, tickets, price);
			if (updatedReservation != null) {
				return new ResponseEntity<>(new ReservationDto(updatedReservation), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		}
		return new ResponseEntity<>(HttpStatus.FORBIDDEN);

	}

	@RequestMapping(value = "/admin/reservations/{reservationId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteReservation(@RequestHeader(value = "token") String token,
			@PathVariable Long reservationId) {
		if (userService.authentication(token)) {
			boolean deleted = reservationService.deleteReservation(reservationId);
			if (deleted) {
				return new ResponseEntity<>(HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		}
		return new ResponseEntity<>(HttpStatus.FORBIDDEN);
	}
}
