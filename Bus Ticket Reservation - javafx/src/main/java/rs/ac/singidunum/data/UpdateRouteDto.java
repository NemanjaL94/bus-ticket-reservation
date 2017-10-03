package rs.ac.singidunum.data;

public class UpdateRouteDto {
	private Integer routeStopId;
	private String arrivalTime;
	private String arrivalDate;
	private Integer price;
	
	
	public UpdateRouteDto(Integer routeStopDto, String arrivalTime, String arrivalDate, Integer price) {
		this.routeStopId = routeStopDto;
		this.arrivalTime = arrivalTime;
		this.arrivalDate = arrivalDate;
		this.price = price;
	}
	public Integer getRouteStopId() {
		return routeStopId;
	}
	public void setRouteStopId(Integer routeStopId) {
		this.routeStopId = routeStopId;
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
	
}
