package rs.ac.singidunum.data;

public class ReservationDto {

	private Long reservationId;
	private Long userId;
	private Long routeId;
	private Integer numberOfTickets;
	private Integer price;

	public ReservationDto() {

	}

	public ReservationDto(Long reservationId, Long userId, Long routeId, Integer numberOfTickets, Integer price) {
		this.reservationId = reservationId;
		this.userId = userId;
		this.routeId = routeId;
		this.numberOfTickets = numberOfTickets;
		this.price = price;
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

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

}
