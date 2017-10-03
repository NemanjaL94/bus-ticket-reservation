package rs.ac.singidunum.dto;

public class RouteDto {

	private Integer routeId;
	private String busCompanyName;
	private String route;
	private String schedule;
	private Double fare;
	private Integer availableTickets;

	

	public RouteDto(Integer routeId, String busCompanyName, String route, String schedule, Double fare,
			Integer availableTickets) {
		this.routeId = routeId;
		this.busCompanyName = busCompanyName;
		this.route = route;
		this.schedule = schedule;
		this.fare = fare;
		this.availableTickets = availableTickets;
	}


	public Integer getRouteId() {
		return routeId;
	}

	public void setRouteId(Integer routeId) {
		this.routeId = routeId;
	}

	public String getBusCompanyName() {
		return busCompanyName;
	}

	public void setBusCompanyName(String busCompanyName) {
		this.busCompanyName = busCompanyName;
	}

	public String getRoute() {
		return route;
	}

	public void setRoute(String route) {
		this.route = route;
	}

	public String getSchedule() {
		return schedule;
	}

	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}


	public Double getFare() {
		return fare;
	}

	public void setFare(Double fare) {
		this.fare = fare;
	}

	public Integer getAvailableTickets() {
		return availableTickets;
	}

	public void setAvailableTickets(Integer availableTickets) {
		this.availableTickets = availableTickets;
	}
}
