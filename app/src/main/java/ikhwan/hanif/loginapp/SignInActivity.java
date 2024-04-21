package ikhwan.hanif.loginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;

public class SignInActivity extends AppCompatActivity {

    TextInputEditText email, password, username, phone;
    Button btnEmailPass, btnPhone;
    SignInButton btnGoogle;
    LinearLayout dontHaveAccount, dontHaveAccount2;

    private GoogleSignInClient mGoogleSignInClient;
    private int RC_SIGN_IN = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        btnGoogle = findViewById(R.id.btnGoogle);
        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });


        dontHaveAccount = findViewById(R.id.dontHaveAccountTV);
        dontHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
                finish();
            }
        });
        dontHaveAccount2 = findViewById(R.id.dontHaveAccountTV2);
        dontHaveAccount2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignInActivity.this, SignUpActivity2.class));
                finish();
            }
        });

        email = findViewById(R.id.emailET);
        password = findViewById(R.id.passwordET);
        username = findViewById(R.id.username);
        phone = findViewById(R.id.phoneNumber);

        btnEmailPass = findViewById(R.id.btnLoginEmailPass);
        btnPhone = findViewById(R.id.btnLoginPhone);

        btnEmailPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailInput = email.getText().toString().trim();
                String passwordInput = password.getText().toString().trim();

                // Mengambil email dan password yang tersimpan dari SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                String savedEmail = sharedPreferences.getString("email", "");
                String savedPassword = sharedPreferences.getString("password", "");

                // Lakukan validasi email dan password
                if (emailInput.equals(savedEmail) && passwordInput.equals(savedPassword) && !emailInput.isEmpty() && !passwordInput.isEmpty()) {
                    // Email dan password valid, lanjutkan ke MainActivity
                    Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                    intent.putExtra("loginMethod", "email");
                    intent.putExtra("email", emailInput);
                    startActivity(intent);
                    finish();
                } else if (emailInput.isEmpty() && passwordInput.isEmpty()) {

                    Toast.makeText(SignInActivity.this, "Email and Password cant be empty", Toast.LENGTH_SHORT).show();

                } else {
                    // Email atau password tidak cocok, tampilkan pesan kesalahan
                    Toast.makeText(SignInActivity.this, "Email or password is incorrect", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usernameInput = username.getText().toString().trim();
                String phoneInput = phone.getText().toString().trim();

                // Mengambil email dan password yang tersimpan dari SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                String savedUsername = sharedPreferences.getString("username", "");
                String savedPhone = sharedPreferences.getString("phone", "");

                // Lakukan validasi email dan password
                if (usernameInput.equals(savedUsername) && phoneInput.equals(savedPhone) && !usernameInput.isEmpty() && !phoneInput.isEmpty()) {
                    // Email dan password valid, lanjutkan ke MainActivity
                    Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                    intent.putExtra("loginMethod", "phone");
                    intent.putExtra("username", usernameInput);
                    startActivity(intent);
                    finish();
                } else if (usernameInput.isEmpty() && phoneInput.isEmpty()) {

                    Toast.makeText(SignInActivity.this, "Username and Phone cant be empty", Toast.LENGTH_SHORT).show();

                } else {
                    // Email atau password tidak cocok, tampilkan pesan kesalahan
                    Toast.makeText(SignInActivity.this, "Username or Phone is incorrect", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            // Signed in successfully, show authenticated UI.
            Toast.makeText(this, "Signed in as " + account.getEmail(), Toast.LENGTH_SHORT).show();
            // Redirect to another activity with user's email
            redirectToNewActivity(account.getEmail());
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            Log.w("GoogleSignIn", "signInResult:failed code=" + e.getStatusCode());
        }
    }

    private void redirectToNewActivity(String email) {
        // Menghilangkan bagian "@gmail.com"
        String cleanEmail = email.replace("@gmail.com", "");

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("loginMethod","google");
        intent.putExtra("USER_EMAIL", cleanEmail);
        startActivity(intent);
        // Tutup aktivitas saat ini jika perlu
        finish();
    }
}