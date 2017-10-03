package rs.ac.singidunum.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Stop {

	@Id
	@GeneratedValue
	@Column(name = "stop_id")
	private Long stopId;

	@Column(name = "city_name")
	private String cityName;

	public Stop() {

	}

	public Stop(String cityName) {
		this.cityName = cityName;
	}

	public Long getStopId() {
		return stopId;
	}

	public void setStopId(Long stopId) {
		this.stopId = stopId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

}
