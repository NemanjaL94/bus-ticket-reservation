package rs.ac.singidunum.dto;

import java.sql.Time;
import java.util.Date;

public class FullRouteDto {

	private Integer routeStopId;
	private Integer routeId;
	private String cityName;
	private Time arrivalTime;
	private Date arrivalDate;
	private Double fare;
	private Integer availableTickets;

	public FullRouteDto(Integer routeStopId, Integer routeId, String cityName, Time arrivalTime, Date arrivalDate,
			Double fare, Integer availableTickets) {
		this.routeStopId = routeStopId;
		this.routeId = routeId;
		this.cityName = cityName;
		this.arrivalTime = arrivalTime;
		this.arrivalDate = arrivalDate;
		this.fare = fare;
		this.availableTickets = availableTickets;
	}

	public Integer getRouteStopId() {
		return routeStopId;
	}

	public void setRouteStopId(Integer routeStopId) {
		this.routeStopId = routeStopId;
	}

	public Integer getRouteId() {
		return routeId;
	}

	public void setRouteId(Integer routeId) {
		this.routeId = routeId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Time getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(Time arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public Date getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(Date arrivalDate) {
		this.arrivalDate = arrivalDate;
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
