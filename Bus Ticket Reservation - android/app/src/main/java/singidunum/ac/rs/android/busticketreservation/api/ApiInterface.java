package singidunum.ac.rs.android.busticketreservation.api;


import java.util.List;

import retrofit2.Call;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import singidunum.ac.rs.android.busticketreservation.data.ReservationDto;
import singidunum.ac.rs.android.busticketreservation.data.RouteDto;
import singidunum.ac.rs.android.busticketreservation.data.SignInDto;
import singidunum.ac.rs.android.busticketreservation.data.StopDto;
import singidunum.ac.rs.android.busticketreservation.data.TokenDto;
import singidunum.ac.rs.android.busticketreservation.data.UserDto;


public interface ApiInterface {

    @POST("register")
    Call<UserDto> register(@Body UserDto userDto);


    @POST("login")
    Call<TokenDto> login(@Body SignInDto signInDto);

    @GET("stops")
    Call<List<StopDto>> getAllStops();

    @GET("routes")
    Call<List<RouteDto>> getRoutes(@Header("token") String token, @Query("arrivalDate") String arrivalDate,
                                   @Query("fromCity") String fromCity,
                                   @Query("toCity") String toCity);

    @POST("reservation")
    Call<ReservationDto> makeReservation(@Header("token") String token, @Body ReservationDto reservationDto);

    @POST("logout")
    Call<Void> logout(@Header("token") String token);
}
