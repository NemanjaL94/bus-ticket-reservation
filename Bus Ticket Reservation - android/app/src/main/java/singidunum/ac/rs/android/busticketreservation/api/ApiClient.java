package singidunum.ac.rs.android.busticketreservation.api;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public static final String BASE_URL = "http://10.0.2.2:8080/";
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
