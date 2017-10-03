package rs.ac.singidunum.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class BusCompany {

	@Id
	@GeneratedValue
	@Column(name = "bus_company_id")
	private Long busCompanyId;

	@Column(name = "bus_company_name")
	private String busCompanyName;

	public BusCompany() {

	}
	
	public BusCompany(String busCompanyName) {
		this.busCompanyName = busCompanyName;
	}
	
	public BusCompany(Long busCompanyId) {
		this.busCompanyId = busCompanyId;
	}

	public Long getBusCompanyId() {
		return busCompanyId;
	}

	public void setBusCompanyId(Long busCompanyId) {
		this.busCompanyId = busCompanyId;
	}


	public String getBusCompanyName() {
		return busCompanyName;
	}

	public void setBusCompanyName(String busCompanyName) {
		this.busCompanyName = busCompanyName;
	}

}
