package rs.ac.singidunum.dto;

import rs.ac.singidunum.data.Reservation;

public class ReservationDto {

	private Long reservationId;
	private Long userId;
	private Long routeId;
	private Integer numberOfTickets;
	private Double price;

	public ReservationDto() {

	}
	
	public ReservationDto(Long routeId, Integer numberOfTickets, Double price) {
		this.routeId = routeId;
		this.numberOfTickets = numberOfTickets;
		this.price = price;
	}

	public ReservationDto(Long reservationId, Long userId, Long routeId, Integer numberOfTickets, Double price) {
		this.reservationId = reservationId;
		this.userId = userId;
		this.routeId = routeId;
		this.numberOfTickets = numberOfTickets;
		this.price = price;
	}
	
	public ReservationDto(Reservation reservation) {
		this.reservationId = reservation.getReservationId();
		this.userId = reservation.getUser().getUserId();
		this.routeId = reservation.getRoute().getRouteId();
		this.numberOfTickets = reservation.getNumberOfTickets();
		this.price = reservation.getPrice();
	}
	

	public Long getReservationId() {
		return reservationId;
	}

	public void setReservationId(Long reservationId) {
		this.reservationId = reservationId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getRouteId() {
		return routeId;
	}

	public void setRouteId(Long routeId) {
		this.routeId = routeId;
	}

	public Integer getNumberOfTickets() {
		return numberOfTickets;
	}

	public void setNumberOfTickets(Integer numberOfTickets) {
		this.numberOfTickets = numberOfTickets;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

}
