package rs.ac.singidunum.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import rs.ac.singidunum.data.Reservation;
import rs.ac.singidunum.data.Route;
import rs.ac.singidunum.data.User;
import rs.ac.singidunum.dto.ReservationDto;
import rs.ac.singidunum.repository.ReservationRepository;
import rs.ac.singidunum.repository.RouteRepository;
import rs.ac.singidunum.repository.UserRepository;

@Service
public class ReservationService {

	@Autowired
	private ReservationRepository reservationRepository;

	@Autowired
	private RouteRepository routeRepository;

	@Autowired
	private UserRepository userRepository;

	public List<ReservationDto> getAllReservations() {
		List<ReservationDto> reservationDtos = new ArrayList<>();

		for (Reservation reservation : reservationRepository.findAll()) {
			reservationDtos.add(new ReservationDto(reservation));
		}

		return reservationDtos;
	}

	@Transactional
	public ReservationDto makeReservation(String token, ReservationDto reservationDto) {
		User foundUser = userRepository.findByToken(token);

		reservationDto.setUserId(foundUser.getUserId());
		Route foundRoute = routeRepository.findOne(reservationDto.getRouteId());
		int tickets = foundRoute.getAvailableTickets() - reservationDto.getNumberOfTickets();
		if (reservationDto.getNumberOfTickets() <= 0) {
			return null;
		}
		if (foundUser != null && foundRoute != null && tickets >= 0) {
			reservationRepository.save(reservationDto.getUserId(), reservationDto.getRouteId(),
					reservationDto.getNumberOfTickets(), reservationDto.getPrice());

			foundRoute.setAvailableTickets(tickets);
			routeRepository.save(foundRoute);
			return reservationDto;
		} else {
			return null;
		}
	}

	@Transactional
	public Reservation updateReservation(Long reservationId, Integer tickets, Double price) {
		Reservation foundReservation = reservationRepository.findOne(reservationId);
		int bonusTickets = foundReservation.getNumberOfTickets() - tickets;
		int availableTickets = foundReservation.getRoute().getAvailableTickets() + bonusTickets;
		if (foundReservation != null && availableTickets >= 0) {

			foundReservation.getRoute().setAvailableTickets(availableTickets);
			routeRepository.save(foundReservation.getRoute());
			foundReservation.setNumberOfTickets(tickets);
			foundReservation.setPrice(price);
			return reservationRepository.save(foundReservation);
		} else {
			return null;
		}
	}

	public boolean deleteReservation(Long reservationId) {
		Reservation foundReservation = reservationRepository.findOne(reservationId);
		if (foundReservation != null) {
			Route reservationRoute = foundReservation.getRoute();
			reservationRoute.setAvailableTickets(reservationRoute.getAvailableTickets() + foundReservation.getNumberOfTickets());
			routeRepository.save(reservationRoute);
			reservationRepository.delete(foundReservation);
			return true;
		}
		return false;

	}
}
