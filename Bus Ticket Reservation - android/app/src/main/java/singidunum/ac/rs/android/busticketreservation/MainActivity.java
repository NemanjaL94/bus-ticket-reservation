package singidunum.ac.rs.android.busticketreservation;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import singidunum.ac.rs.android.busticketreservation.api.ApiClient;
import singidunum.ac.rs.android.busticketreservation.api.ApiInterface;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    private Button registerButton;
    public static Button loginButton;
    private ImageView busImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/Pickle Pushing.otf");
        loginButton = (Button) findViewById(R.id.loginButton);
        registerButton = (Button) findViewById(R.id.registerButton);
        busImage = (ImageView) findViewById(R.id.busImage);


        loginButton.setTypeface(tf);
        registerButton.setTypeface(tf);

        busImage.setOnClickListener(this);
        loginButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        Animation animAlpha = AnimationUtils.loadAnimation(this, R.anim.anim_alpha);
        v.startAnimation(animAlpha);

        if(loginButton.getId() == v.getId() && LoginActivity.getToken().equals("")) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));

        } else if(loginButton.getId() == v.getId() && !LoginActivity.getToken().equals("")){
            Call<Void> call = ApiClient.getClient().create(ApiInterface.class).logout(LoginActivity.getToken());
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    switch(response.code()){
                        case 200:
                            Toast.makeText(getApplicationContext(), R.string.successful_logout, Toast.LENGTH_SHORT).show();
                            loginButton.setText(R.string.login);
                            LoginActivity.setToken("");
                            break;
                        case 400:
                            Toast.makeText(getApplicationContext(), R.string.unsuccessful_logout, Toast.LENGTH_SHORT).show();
                            break;
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        } else if(registerButton.getId() == v.getId()) {
            startActivity(new Intent(MainActivity.this, RegisterActivity.class));

        } else if(busImage.getId() == v.getId()){
            startActivity(new Intent(MainActivity.this, SearchActivity.class));
        }
    }

}
