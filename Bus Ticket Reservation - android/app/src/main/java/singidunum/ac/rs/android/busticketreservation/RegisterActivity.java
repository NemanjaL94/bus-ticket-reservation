package singidunum.ac.rs.android.busticketreservation;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.regex.Pattern;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import singidunum.ac.rs.android.busticketreservation.api.ApiClient;
import singidunum.ac.rs.android.busticketreservation.api.ApiInterface;
import singidunum.ac.rs.android.busticketreservation.data.UserDto;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/Pickle Pushing.otf");

        Button registerFormButton = (Button) findViewById(R.id.registerFormButton);

        registerFormButton.setTypeface(tf);
        registerFormButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animAlpha = AnimationUtils.loadAnimation(v.getContext(), R.anim.anim_alpha);
                v.startAnimation(animAlpha);

                UserDto userDto = signUp();

                if(userDto!= null) {

                    ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);


                    Call<UserDto> call = apiInterface.register(userDto);
                    call.enqueue(new Callback<UserDto>() {
                        @Override
                        public void onResponse(Call<UserDto> call, Response<UserDto> response) {
                            if (response.code() == 201) {
                                Toast.makeText(getApplicationContext(), R.string.successful_registration, Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                            } else if(response.code() == 400){
                                Toast.makeText(getApplicationContext(), R.string.unsuccessful_registration, Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<UserDto> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
                }
            }
        });

    }

    public UserDto signUp() {


        String username = ((EditText) findViewById(R.id.usernameEditText)).getText().toString();
        String email = ((EditText) findViewById(R.id.emailEditText)).getText().toString();
        String password = ((EditText) findViewById(R.id.passwordEditText)).getText().toString();
        String confirmPassword = ((EditText) findViewById(R.id.confirmPasswordEditText)).getText().toString();

        Pattern usernamePattern = Pattern.compile("[A-Za-z0-9]{2,20}");
        Pattern emailPattern = Pattern.compile("[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}$");
        Pattern passwordPattern = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,16}$");


        if(!Pattern.matches(usernamePattern.pattern(), username)) {
            Toast.makeText(getApplicationContext(), R.string.username_pattern, Toast.LENGTH_SHORT).show();
        } else if (!Pattern.matches(emailPattern.pattern(), email)) {
            Toast.makeText(getApplicationContext(), R.string.email_pattern, Toast.LENGTH_SHORT).show();
        } else if (!Pattern.matches(passwordPattern.pattern(), password)) {
            Toast.makeText(getApplicationContext(), R.string.password_pattern, Toast.LENGTH_SHORT).show();
        } else if (!password.equals(confirmPassword)) {
            Toast.makeText(getApplicationContext(), R.string.passwords_not_matched, Toast.LENGTH_SHORT).show();
        } else {
            return new UserDto(username, password, email);
        }
    return null;
    }

}
