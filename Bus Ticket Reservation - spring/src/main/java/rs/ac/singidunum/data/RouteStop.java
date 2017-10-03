package rs.ac.singidunum.data;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class RouteStop {

	@Id
	@GeneratedValue
	@Column(name = "route_stop_id")
	private Long routeStopId;
	
	@ManyToOne
	@JoinColumn(name = "route_id")
	private Route route;
	
	@ManyToOne
	@JoinColumn(name = "stop_id")
	private Stop stop;

	@Temporal(TemporalType.TIME)
	@Column(name = "arrival_time")
	private Date arrivalTime;

	@Temporal(TemporalType.DATE)
	@Column(name = "arrival_date")
	private Date arrivalDate;

	@Column(name = "fare")
	private Double fare;

	public RouteStop() {

	}

	public RouteStop(Route route, Stop stop, Date arrivalTime, Date arrivalDate, Double fare) {
		this.route = route;
		this.stop = stop;
		this.arrivalTime = arrivalTime;
		this.arrivalDate = arrivalDate;
		this.fare = fare;
	}

	public Long getRouteStopId() {
		return routeStopId;
	}

	public void setRouteStopId(Long routeStopId) {
		this.routeStopId = routeStopId;
	}

	public Route getRoute() {
		return route;
	}

	public void setRoute(Route route) {
		this.route = route;
	}

	public Stop getStop() {
		return stop;
	}

	public void setStop(Stop stop) {
		this.stop = stop;
	}

	public Date getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(Date arrivalTime) {
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

}
