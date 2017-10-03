package singidunum.ac.rs.android.busticketreservation;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import singidunum.ac.rs.android.busticketreservation.api.ApiClient;
import singidunum.ac.rs.android.busticketreservation.api.ApiInterface;
import singidunum.ac.rs.android.busticketreservation.data.RouteDto;
import singidunum.ac.rs.android.busticketreservation.data.StopDto;

public class SearchActivity extends AppCompatActivity {


    private ImageButton datePickerButton;

    static final int DIALOG_ID = 0;
    private String date = "";
    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    public static String KEY = "key";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/Pickle Pushing.otf");

        final ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Button searchButton = (Button) findViewById(R.id.searchButton);
        searchButton.setTypeface(tf);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animAlpha = AnimationUtils.loadAnimation(v.getContext(), R.anim.anim_alpha);
                v.startAnimation(animAlpha);

                AutoCompleteTextView cityFromTextView = (AutoCompleteTextView) findViewById(R.id.cityFromTextView);
                AutoCompleteTextView cityToTextView = (AutoCompleteTextView) findViewById(R.id.cityToTextView);


                Call<List<RouteDto>> call = apiInterface.getRoutes(LoginActivity.getToken(), date, cityFromTextView.getText().toString(),
                        cityToTextView.getText().toString());


                call.enqueue(new Callback<List<RouteDto>>() {
                    @Override
                    public void onResponse(Call<List<RouteDto>> call, Response<List<RouteDto>> response) {
                        switch (response.code()) {
                            case 200:
                                Intent intent = new Intent(SearchActivity.this, ResultActivity.class);
                                RouteDto[] routes = response.body().toArray(new RouteDto[response.body().size()]);
                                Bundle bundle = new Bundle();
                                bundle.putParcelableArray(KEY, routes);
                                intent.putExtras(bundle);
                                startActivity(intent);
                                break;
                            case 400:
                                Toast.makeText(SearchActivity.this, R.string.routes_not_found, Toast.LENGTH_LONG).show();
                                break;
                            case 403:
                                startActivity(new Intent(SearchActivity.this, LoginActivity.class));
                                break;
                        }
                    }

                    @Override
                    public void onFailure(Call<List<RouteDto>> call, Throwable t) {
                        t.printStackTrace();
                    }
                });

            }
        });


        Call<List<StopDto>> call = apiInterface.getAllStops();

        call.enqueue(new Callback<List<StopDto>>() {
            @Override
            public void onResponse(Call<List<StopDto>> call, final Response<List<StopDto>> response) {
                if (response.code() == 200) {
                    final AutoCompleteTextView cityFromTextView = (AutoCompleteTextView) findViewById(R.id.cityFromTextView);
                    final AutoCompleteTextView cityToTextView = (AutoCompleteTextView) findViewById(R.id.cityToTextView);

                    final String[] stops = new String[response.body().size()];

                    fillArray(response.body(), stops);
                    ArrayAdapter adapter = new ArrayAdapter(SearchActivity.this, android.R.layout.select_dialog_item, stops);

                    cityFromTextView.setThreshold(1);
                    cityToTextView.setThreshold(1);

                    cityFromTextView.setAdapter(adapter);
                    cityToTextView.setAdapter(adapter);

                }

            }

            @Override
            public void onFailure(Call<List<StopDto>> call, Throwable t) {
                t.printStackTrace();
            }
        });

        showDialogButtonClick();
    }

    public void fillArray(List<StopDto> response, String[] stops) {
        int i = 0;
        for (StopDto stop : response) {
            stops[i] = stop.getCityName();
            i++;
        }
    }

    public void showDialogButtonClick() {
        datePickerButton = (ImageButton) findViewById(R.id.datePickerButton);
        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DIALOG_ID);
            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG_ID) {
            Calendar cal = Calendar.getInstance();
            return new DatePickerDialog(this, datePickerListener, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        } else {
            return null;
        }
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            date = formatter.format(new GregorianCalendar(year, month, dayOfMonth).getTime());

        }
    };


}
