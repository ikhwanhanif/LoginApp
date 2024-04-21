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

public class SignUpActivity2 extends AppCompatActivity {

    LinearLayout alreadyHaveAccount;
    TextInputEditText username, phone;
    Button regist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up2);

        username = findViewById(R.id.usernameET);
        phone = findViewById(R.id.phoneET);
        regist = findViewById(R.id.btnRegistPhone);

        regist.setOnClickListener(view -> {
            String usernameInput = username.getText().toString().trim();
            String phoneInput = phone.getText().toString().trim();

            if (!usernameInput.isEmpty() && !phoneInput.isEmpty()){

                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("username", usernameInput);
                editor.putString("phone", phoneInput);
                editor.apply();

                Toast.makeText(SignUpActivity2.this, "successfully Regist", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SignUpActivity2.this, SignInActivity.class));
                finish();

            } else {
                Toast.makeText(SignUpActivity2.this, "Username and Phone cant be empty", Toast.LENGTH_SHORT).show();
            }

        });

        alreadyHaveAccount = findViewById(R.id.alreadyHaveAccountTV);
        alreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity2.this, SignInActivity.class));
                finish();
            }
        });

    }
}