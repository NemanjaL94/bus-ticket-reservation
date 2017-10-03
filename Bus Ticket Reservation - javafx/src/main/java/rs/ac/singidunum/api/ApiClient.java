package rs.ac.singidunum.api;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient {
	
	   public static final String BASE_URL = "http://127.0.0.1:8080/admin/";
	    private static Retrofit retrofitClient = null;

	    public static Retrofit getClient() {
	        if (retrofitClient==null) {
	            retrofitClient = new Retrofit.Builder()
	                    .baseUrl(BASE_URL)
	                    .addConverterFactory(GsonConverterFactory.create())
	                    .build();
	        }
	        return retrofitClient;
	    }

}
