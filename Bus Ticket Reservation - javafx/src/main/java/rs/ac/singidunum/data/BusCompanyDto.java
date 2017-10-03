package rs.ac.singidunum.data;

public class BusCompanyDto {

	private Integer busCompanyId;
	private String busCompanyName;
	

	public BusCompanyDto(Integer busCompanyId, String busCompanyName) {
		this.busCompanyId = busCompanyId;
		this.busCompanyName = busCompanyName;
	}

	public BusCompanyDto(String busCompanyName) {
		this.busCompanyName = busCompanyName;
	}

	public Integer getBusCompanyId() {
		return busCompanyId;
	}

	public void setBusCompanyId(Integer busCompanyId) {
		this.busCompanyId = busCompanyId;
	}

	public String getBusCompanyName() {
		return busCompanyName;
	}

	public void setBusCompanyName(String busCompanyName) {
		this.busCompanyName = busCompanyName;
	}

	@Override
	public String toString() {
		return busCompanyName;
	}

}
