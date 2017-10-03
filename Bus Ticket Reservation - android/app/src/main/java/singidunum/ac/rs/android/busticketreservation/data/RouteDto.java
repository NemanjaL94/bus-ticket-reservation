package singidunum.ac.rs.android.busticketreservation.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


/**
 * Created by Galiks on 7/4/2017.
 */

public class RouteDto implements Parcelable, Serializable {
    @SerializedName("routeId")
    @Expose
    private Integer routeId;
    @SerializedName("busCompanyName")
    @Expose
    private String busCompanyName;
    @SerializedName("route")
    @Expose
    private String route;
    @SerializedName("schedule")
    @Expose
    private String schedule;
    @SerializedName("fare")
    @Expose
    private Double fare;
    @SerializedName("availableTickets")
    @Expose
    private Integer availableTickets;

    public RouteDto(Integer routeId, String busCompanyName, String route, String schedule, Double fare, Integer availableTickets) {
        this.routeId = routeId;
        this.busCompanyName = busCompanyName;
        this.route = route;
        this.schedule = schedule;
        this.fare = fare;
        this.availableTickets = availableTickets;
    }

    public RouteDto(Parcel input) {
        this.routeId = input.readInt();
        this.busCompanyName = input.readString();
        this.route = input.readString();
        this.schedule = input.readString();
        this.fare = input.readDouble();
        this.availableTickets = input.readInt();

    }

    public Integer getRouteId() {
        return routeId;
    }

    public void setRouteId(Integer routeId) {
        this.routeId = routeId;
    }

    public String getBusCompanyName() {
        return busCompanyName;
    }

    public void setBusCompanyName(String busCompanyName) {
        this.busCompanyName = busCompanyName;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public Double getFare() {
        return fare;
    }

    public void setFare(Double fare) {
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
        return "RouteDto{" +
                "routeId=" + routeId +
                ", busCompanyName='" + busCompanyName + '\'' +
                ", route='" + route + '\'' +
                ", schedule='" + schedule + '\'' +
                ", fare=" + fare +
                ", availableTickets=" + availableTickets +
                '}';
    }

    public static final Parcelable.Creator<RouteDto> CREATOR = new Creator<RouteDto>() {
        @Override
        public RouteDto createFromParcel(Parcel source) {
            return new RouteDto(source);
        }

        @Override
        public RouteDto[] newArray(int size) {
            return new RouteDto[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(routeId);
        dest.writeString(busCompanyName);
        dest.writeString(route);
        dest.writeString(schedule);
        dest.writeDouble(fare);
        dest.writeInt(availableTickets);
    }
}
