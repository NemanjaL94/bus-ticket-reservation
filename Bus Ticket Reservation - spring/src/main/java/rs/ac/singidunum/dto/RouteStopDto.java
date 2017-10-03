package rs.ac.singidunum.dto;

import java.sql.Time;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import rs.ac.singidunum.utility.SqlTimeDeserializer;

public class RouteStopDto {

	private Long routeStopId;
	private Long busCompanyId;
	private Long stopId;

	@JsonFormat(pattern = "HH:mm")
	@JsonDeserialize(using = SqlTimeDeserializer.class)
	private Time arrivalTime;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date arrivalDate;

	private Double price;

	public RouteStopDto() {

	}

	public RouteStopDto(Long busCompanyId, Long stopId, Time arrivalTime, Date arrivalDate, Double price) {
		this.busCompanyId = busCompanyId;
		this.stopId = stopId;
		this.arrivalTime = arrivalTime;
		this.arrivalDate = arrivalDate;
		this.price = price;
	}

	public RouteStopDto(Long routeStopId, Time arrivalTime, Date arrivalDate, Double price) {
		this.routeStopId = routeStopId;
		this.arrivalTime = arrivalTime;
		this.arrivalDate = arrivalDate;
		this.price = price;
	}

	public Long getRouteStopId() {
		return routeStopId;
	}

	public void setRouteStopId(Long routeStopId) {
		this.routeStopId = routeStopId;
	}

	public Long getBusCompanyId() {
		return busCompanyId;
	}

	public void setBusCompanyId(Long busCompanyId) {
		this.busCompanyId = busCompanyId;
	}

	public Long getStopId() {
		return stopId;
	}

	public void setStopId(Long stopId) {
		this.stopId = stopId;
	}

	public Date getArrivalTime() {
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

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "RouteStopDto [busCompanyId=" + busCompanyId + ", stopId=" + stopId + ", arrivalTime=" + arrivalTime
				+ ", arrivalDate=" + arrivalDate + ", price=" + price + "]";
	}

}
