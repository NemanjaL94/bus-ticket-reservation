package rs.ac.singidunum.dto;

import java.sql.Time;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import rs.ac.singidunum.utility.SqlTimeDeserializer;

public class UpdateRouteDto {
	
	private Long routeStopId;

	@JsonFormat(pattern = "HH:mm")
	@JsonDeserialize(using = SqlTimeDeserializer.class)
	private Time arrivalTime;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date arrivalDate;
	
	private Double price;

	public UpdateRouteDto() {
		
	}

	public UpdateRouteDto(Long routeStopId, Time arrivalTime, Date arrivalDate, Double price) {
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

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

}
