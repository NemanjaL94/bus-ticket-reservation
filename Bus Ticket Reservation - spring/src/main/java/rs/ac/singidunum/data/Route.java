package rs.ac.singidunum.data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Route {

	@Id
	@GeneratedValue
	@Column(name = "route_id")
	private Long routeId;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "bus_company_id")
	private BusCompany busCompany;

	@Column(name = "available_tickets")
	private Integer availableTickets;

	public Route() {

	}

	public Route(BusCompany busCompany, Integer availableTickets) {
		this.busCompany = busCompany;
		this.availableTickets = availableTickets;
	}

	public Long getRouteId() {
		return routeId;
	}

	public void setRouteId(Long routeId) {
		this.routeId = routeId;
	}

	public BusCompany getBusCompany() {
		return busCompany;
	}

	public void setBusCompany(BusCompany busCompany) {
		this.busCompany = busCompany;
	}

	public Integer getAvailableTickets() {
		return availableTickets;
	}

	public void setAvailableTickets(Integer availableTickets) {
		this.availableTickets = availableTickets;
	}

}
