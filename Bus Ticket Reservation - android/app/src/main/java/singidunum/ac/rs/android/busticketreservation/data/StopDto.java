package singidunum.ac.rs.android.busticketreservation.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Galiks on 7/5/2017.
 */

public class StopDto {

    @SerializedName("stopId")
    @Expose
    private Integer stopId;

    @SerializedName("cityName")
    @Expose
    private String cityName;

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

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    @Override
    public String toString() {
        return "StopDto{" +
                "stopId=" + stopId +
                ", cityName='" + cityName + '\'' +
                '}';
    }
}
