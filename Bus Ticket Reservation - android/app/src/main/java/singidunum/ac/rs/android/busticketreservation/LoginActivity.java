package singidunum.ac.rs.android.busticketreservation;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import singidunum.ac.rs.android.busticketreservation.api.ApiClient;
import singidunum.ac.rs.android.busticketreservation.api.ApiInterface;
import singidunum.ac.rs.android.busticketreservation.data.SignInDto;
import singidunum.ac.rs.android.busticketreservation.data.TokenDto;


public class LoginActivity extends AppCompatActivity {


    private static String token = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/Pickle Pushing.otf");

        Button loginButton = (Button) findViewById(R.id.signInButton);
        loginButton.setTypeface(tf);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animAlpha = AnimationUtils.loadAnimation(v.getContext(), R.anim.anim_alpha);
                v.startAnimation(animAlpha);

                ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

                EditText editTextPassword = (EditText) findViewById(R.id.password);

                AutoCompleteTextView emailTextView = (AutoCompleteTextView) findViewById(R.id.email);
                String email = emailTextView.getText().toString();
                String password = editTextPassword.getText().toString();
                Call<TokenDto> call = apiInterface.login(new SignInDto(email, password));

                call.enqueue(new Callback<TokenDto>() {
                    @Override
                    public void onResponse(Call<TokenDto> call, Response<TokenDto> response) {
                        switch (response.code()) {
                            case 200:
                                token = response.body().getToken();
                                MainActivity.loginButton.setText(R.string.logout_button);
                                startActivity(new Intent(LoginActivity.this, SearchActivity.class));
                                break;
                            case 400:
                                Toast.makeText(getApplicationContext(), R.string.invalid_credentials, Toast.LENGTH_SHORT).show();

                                break;
                        }
                    }

                    @Override
                    public void onFailure(Call<TokenDto> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        });
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        LoginActivity.token = token;
    }

}

