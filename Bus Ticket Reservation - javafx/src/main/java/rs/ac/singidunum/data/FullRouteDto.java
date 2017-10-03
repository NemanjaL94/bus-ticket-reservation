package rs.ac.singidunum.data;

public class FullRouteDto {

	private Integer routeStopId;
	private Integer routeId;
	private String cityName;
	private String arrivalTime;
	private String arrivalDate;
	private Integer fare;
	private Integer availableTickets;

	public FullRouteDto(Integer routeStopId, Integer routeId, String cityName, String arrivalTime, String arrivalDate,
			Integer fare, Integer availableTickets) {
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

	public String getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public String getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(String arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	public Integer getFare() {
		return fare;
	}

	public void setFare(Integer fare) {
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
		return "FullRouteDto [routeStopId=" + routeStopId + ", routeId=" + routeId + ", cityName=" + cityName
				+ ", arrivalTime=" + arrivalTime + ", arrivalDate=" + arrivalDate + ", fare=" + fare
				+ ", availableTickets=" + availableTickets + "]";
	}

	

}
