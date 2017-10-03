package singidunum.ac.rs.android.busticketreservation.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Galiks on 7/8/2017.
 */

public class ReservationDto {

    @SerializedName("userId")
    @Expose
    private Integer userId;
    @SerializedName("routeId")
    @Expose
    private Integer routeId;
    @SerializedName("numberOfTickets")
    @Expose
    private Integer numberOfTickets;
    @SerializedName("price")
    @Expose
    private Double price;

    public ReservationDto(Integer routeId, Integer numberOfTickets, Double price) {
        this.routeId = routeId;
        this.numberOfTickets = numberOfTickets;
        this.price = price;
    }


    public ReservationDto(Integer userId, Integer routeId, Integer numberOfTickets, Double price) {
        this.userId = userId;
        this.routeId = routeId;
        this.numberOfTickets = numberOfTickets;
        this.price = price;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRouteId() {
        return routeId;
    }

    public void setRouteId(Integer routeId) {
        this.routeId = routeId;
    }

    public Integer getNumberOfTickets() {
        return numberOfTickets;
    }

    public void setNumberOfTickets(Integer numberOfTickets) {
        this.numberOfTickets = numberOfTickets;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
