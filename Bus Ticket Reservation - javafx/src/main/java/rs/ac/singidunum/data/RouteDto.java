package rs.ac.singidunum.data;

public class RouteDto {

	private int busCompanyId;
	private int availableTickets;

	public RouteDto(int busCompanyId, int availableTickets) {
		this.busCompanyId = busCompanyId;
		this.availableTickets = availableTickets;
	}

	public int getBusCompanyId() {
		return busCompanyId;
	}

	public void setBusCompanyId(int busCompanyId) {
		this.busCompanyId = busCompanyId;
	}

	public int getAvailableTickets() {
		return availableTickets;
	}

	public void setAvailableTickets(int availableTickets) {
		this.availableTickets = availableTickets;
	}

	@Override
	public String toString() {
		return "RouteDto [busCompanyId=" + busCompanyId + ", availableTickets=" + availableTickets + "]";
	}
	
	

}
