package rs.ac.singidunum.dto;

public class SimpleRouteDto {

	private Long busCompanyId;
	private Integer availableTickets;

	public SimpleRouteDto() {

	}

	public SimpleRouteDto(Long busCompanyId, Integer availableTickets) {
		this.busCompanyId = busCompanyId;
		this.availableTickets = availableTickets;
	}

	public Long getBusCompanyId() {
		return busCompanyId;
	}

	public void setBusCompanyId(Long busCompany) {
		this.busCompanyId = busCompany;
	}

	public Integer getAvailableTickets() {
		return availableTickets;
	}

	public void setAvailableTickets(Integer availableTickets) {
		this.availableTickets = availableTickets;
	}

}
