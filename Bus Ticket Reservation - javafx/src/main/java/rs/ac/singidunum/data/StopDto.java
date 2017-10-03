package rs.ac.singidunum.data;

public class StopDto {

	private Integer stopId;
	private String cityName;

	public StopDto() {

	}

	public StopDto(String cityName) {
		this.cityName = cityName;
	}
	public StopDto(Integer stopId, String cityName) {
		this.stopId = stopId;
		this.cityName = cityName;
	}

	public Integer getStopId() {
		return stopId;
	}

	public void setStopId(Integer stopId) {
		this.stopId = stopId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCity(String cityName) {
		this.cityName = cityName;
	}

	@Override
	public String toString() {
		return cityName;
	}
	
	

}
