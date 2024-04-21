package ikhwan.hanif.loginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class SignUpActivity extends AppCompatActivity {

    TextInputEditText email, password;
    Button regist;
    LinearLayout alreadyHaveAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        email = findViewById(R.id.emailET);
        password = findViewById(R.id.passwordET);
        regist = findViewById(R.id.btnRegistEmailPass);
        alreadyHaveAccount = findViewById(R.id.alreadyHaveAccountTV);

        alreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
                finish();
            }
        });

        regist.setOnClickListener(view -> {
            String emailInput = email.getText().toString().trim();
            String passwordInput = password.getText().toString().trim();

            if (!emailInput.isEmpty() && !passwordInput.isEmpty()){

                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("email", emailInput);
                editor.putString("password", passwordInput);
                editor.apply();

                startActivity(new Intent(SignUpActivity.this, SignInActivity.class));

                Toast.makeText(SignUpActivity.this, "successfully Regist", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
                finish();

            } else {
                Toast.makeText(SignUpActivity.this, "Email and Password cant be empty", Toast.LENGTH_SHORT).show();
            }
        });


    }
}