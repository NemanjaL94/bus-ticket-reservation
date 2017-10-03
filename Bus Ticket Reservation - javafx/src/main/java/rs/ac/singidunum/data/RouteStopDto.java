package rs.ac.singidunum.data;

public class RouteStopDto {
	private Integer busCompanyId;

	private Integer stopId;

	private String arrivalTime;

	private String arrivalDate;

	private Integer price;

	public RouteStopDto() {

	}

	public RouteStopDto(Integer busCompanyId, Integer stopId, String arrivalTime, String arrivalDate, Integer price) {
		this.busCompanyId = busCompanyId;
		this.stopId = stopId;
		this.arrivalTime = arrivalTime;
		this.arrivalDate = arrivalDate;
		this.price = price;
	}

	public Integer getBusCompanyId() {
		return busCompanyId;
	}

	public void setBusCompanyId(Integer busCompanyId) {
		this.busCompanyId = busCompanyId;
	}

	public Integer getStopId() {
		return stopId;
	}

	public void setStopId(Integer stopId) {
		this.stopId = stopId;
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

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "RouteStopDto [busCompanyId=" + busCompanyId + ", stopId=" + stopId + ", arrivalTime=" + arrivalTime
				+ ", arrivalDate=" + arrivalDate + ", price=" + price + "]";
	}
	
	

}
