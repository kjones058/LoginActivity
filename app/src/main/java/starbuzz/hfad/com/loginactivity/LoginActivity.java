package starbuzz.hfad.com.loginactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

public class LoginActivity extends AppCompatActivity {

    private TextView textViewCreateAccount;
    private Button buttonLogin;
    private EditText editTextUsername;
    private EditText editTextPassword;

    public static final String EXTRA_USERNAME = "username";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        wireWidgets();

        // initialize Backendless connection
        Backendless.initApp(this,
                Credentials.APP_ID, Credentials.API_KEY);


        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginToBackendless();
            }
        });

        textViewCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent createAccountIntent = new Intent(LoginActivity.this,
                        CreateAccountActivity.class);

                String username = editTextUsername.getText().toString();
                if(username.length() > 0) {
                    createAccountIntent.putExtra(EXTRA_USERNAME, username);
                }
                startActivity(createAccountIntent);
            }
        });
    }

    private void loginToBackendless() {
        String login = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();
        Backendless.UserService.login(login, password,
                new AsyncCallback<BackendlessUser>() {
                    @Override
                    public void handleResponse(BackendlessUser response) {
                        // Start the new activity here because
                        // this method is called when the login is complete
                        // and successful
                        Intent intent = new Intent(LoginActivity.this,
                                RestaurantListActivity.class);
                        startActivity(intent);

                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        Toast.makeText(LoginActivity.this,
                                "Login Failed",
                                Toast.LENGTH_SHORT).show();
                    }
                });


    }

    private void wireWidgets() {
        textViewCreateAccount = findViewById(R.id.textview_login_createaccount);
        buttonLogin = findViewById(R.id.button_login_login);
        editTextPassword = findViewById(R.id.edittext_login_password);
        editTextUsername = findViewById(R.id.edittext_main_username);
    }
}
