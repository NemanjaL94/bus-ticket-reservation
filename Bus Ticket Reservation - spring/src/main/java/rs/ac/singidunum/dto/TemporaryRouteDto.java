package rs.ac.singidunum.dto;

import java.sql.Time;

public class TemporaryRouteDto {
	private Integer routeId;
	private String busCompanyName;
	private Time arrivalTime;
	private String cityName;
	private Double fare;
	private Integer availableTickets;

	public TemporaryRouteDto(Integer routeId, String busCompanyName, Time arrivalTime, String cityName, Double fare,
			Integer availableTickets) {
		this.routeId = routeId;
		this.busCompanyName = busCompanyName;
		this.arrivalTime = arrivalTime;
		this.cityName = cityName;
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

	public Time getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(Time arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
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

	@Override
	public String toString() {
		return "TemporaryRouteDto [routeId=" + routeId + ", busCompanyName=" + busCompanyName + ", arrivalTime="
				+ arrivalTime + ", cityName=" + cityName + ", fare=" + fare + ", availableTickets=" + availableTickets
				+ "]";
	}

}
