package singidunum.ac.rs.android.busticketreservation;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import singidunum.ac.rs.android.busticketreservation.api.ApiClient;
import singidunum.ac.rs.android.busticketreservation.api.ApiInterface;
import singidunum.ac.rs.android.busticketreservation.data.ReservationDto;
import singidunum.ac.rs.android.busticketreservation.data.RouteDto;

public class ReservationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);


        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/Pickle Pushing.otf");

        final RouteDto routeDto = getIntent().getExtras().getParcelable(ResultActivity.RESERVATION_KEY);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Company:  " + routeDto.getBusCompanyName() + "\n"
                + "Schedule:  " + routeDto.getSchedule() + "\n" + "Route:         " + routeDto.getRoute());
        ((TextView) findViewById(R.id.reservationInfoTextView)).setText(stringBuilder.toString());

        ((TextView) findViewById(R.id.availableTicketsLabel)).setText("Available tickets:            " + routeDto.getAvailableTickets());

        final EditText numberOfTicketsButton = (EditText) findViewById(R.id.numberOfTicketsButton);

        numberOfTicketsButton.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!numberOfTicketsButton.getText().toString().equals("")) {
                    int numberOfTickets = Integer.parseInt(numberOfTicketsButton.getText().toString());
                    ((TextView) findViewById(R.id.totalPrice)).setText("Total price:                      " + numberOfTickets * routeDto.getFare());
                }
            }
        });
        Button reservationButton = (Button) findViewById(R.id.reservationButton);
        reservationButton.setTypeface(tf);
        reservationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Animation animAlpha = AnimationUtils.loadAnimation(v.getContext(), R.anim.anim_alpha);
                v.startAnimation(animAlpha);

                ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

                int numberOfTickets = Integer.parseInt(numberOfTicketsButton.getText().toString());
                if (numberOfTickets == 0) {
                    Toast.makeText(ReservationActivity.this, R.string.invalid_number_for_tickets, Toast.LENGTH_SHORT).show();
                } else {
                    Call<ReservationDto> call = apiInterface.makeReservation(LoginActivity.getToken(),
                            new ReservationDto(routeDto.getRouteId(), numberOfTickets, routeDto.getFare() * numberOfTickets));
                    call.enqueue(new Callback<ReservationDto>() {
                        @Override
                        public void onResponse(Call<ReservationDto> call, Response<ReservationDto> response) {
                            switch (response.code()) {
                                case 201:
                                    Toast.makeText(ReservationActivity.this, R.string.successful_reservation, Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(ReservationActivity.this, SearchActivity.class));
                                    break;
                                case 400:
                                    Toast.makeText(ReservationActivity.this, R.string.unsuccessful_reservation, Toast.LENGTH_SHORT).show();
                                    break;
                                case 403:
                                    startActivity(new Intent(ReservationActivity.this, LoginActivity.class));
                                    break;
                            }
                        }

                        @Override
                        public void onFailure(Call<ReservationDto> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
                }

            }
        });
    }
}
