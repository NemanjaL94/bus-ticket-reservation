package rs.ac.singidunum.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rs.ac.singidunum.data.AdminDto;
import rs.ac.singidunum.data.BusCompanyDto;
import rs.ac.singidunum.data.FullRouteDto;
import rs.ac.singidunum.data.ReservationDto;
import rs.ac.singidunum.data.RouteDto;
import rs.ac.singidunum.data.RouteStopDto;
import rs.ac.singidunum.data.SignInDto;
import rs.ac.singidunum.data.StopDto;
import rs.ac.singidunum.data.UpdateRouteDto;
import rs.ac.singidunum.data.UserDto;

public interface ApiInterface {

	@POST("login")
	Call<AdminDto> login(@Body SignInDto signInDto);

	@POST("logout")
	Call<Void> logout(@Header(value = "token") String token);

	@GET("users")
	Call<List<UserDto>> getUsers(@Header(value = "token") String token);

	@POST("stop")
	Call<StopDto> addStop(@Header(value = "token") String token, @Body StopDto stopDto);

	@GET("busCompany")
	Call<List<BusCompanyDto>> getBusCompanies(@Header(value = "token") String token);

	@POST("busCompany")
	Call<BusCompanyDto> addBusCompany(@Header(value = "token") String token, @Body BusCompanyDto busCompanyDto);

	@PUT("busCompany/{busCompanyId}")
	Call<BusCompanyDto> updateBusCompany(@Header(value = "token") String token, @Path("busCompanyId") Integer busCompanyId,
			@Query(value = "busCompanyName") String busCompanyName);

	@DELETE("busCompany/{busCompanyId}")
	Call<Void> deleteBusCompany(@Header(value = "token") String token, @Path("busCompanyId") Integer busCompanyId);

	@GET("stops")
	Call<List<StopDto>> getStops(@Header(value = "token") String token);

	@POST("routes")
	Call<Long> createRoute(@Header(value = "token") String token, @Body RouteDto routeDto);

	@POST("routeStops/{routeId}")
	Call<List<RouteStopDto>> createRouteStops(@Header(value = "token") String token,
			@Body List<RouteStopDto> routeStopDtos, @Path("routeId") Long routeId);

	@PUT("stops/{stopId}")
	Call<StopDto> updateStop(@Header(value = "token") String token, @Path("stopId") Integer stopId,
			@Query(value = "cityName") String cityName);

	@DELETE("stops/{stopId}")
	Call<Void> deleteStop(@Header(value = "token") String token, @Path("stopId") Integer stopId);

	@GET("reservations")
	Call<List<ReservationDto>> getReservations(@Header(value = "token") String token);

	@PUT("reservations/{reservationId}")
	Call<ReservationDto> updateReservation(@Header(value = "token") String token,
			@Path("reservationId") Long reservationId, @Query(value = "tickets") Integer tickets,
			@Query(value = "price") Double price);

	@DELETE("reservations/{reservationId}")
	Call<Void> deleteReservation(@Header(value = "token") String token, @Path("reservationId") Long reservationId);

	@GET("routes")
	Call<List<FullRouteDto>> getFullRoutes(@Header(value = "token") String token);

	@PUT("routeStops")
	Call<UpdateRouteDto> updateRouteStop(@Header(value = "token") String token, @Body UpdateRouteDto updateRouteDto);

	@DELETE("routeStops/{routeStopId}")
	Call<Void> deleteRouteStop(@Header(value = "token") String token, @Path("routeStopId") Integer routeStopId);
}
