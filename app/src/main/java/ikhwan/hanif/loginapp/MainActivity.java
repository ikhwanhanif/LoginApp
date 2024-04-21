package ikhwan.hanif.loginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView infoLoginWith;
    Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        infoLoginWith = findViewById(R.id.infoTV);
        btnLogout = findViewById(R.id.logoutBtn);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SignInActivity.class));
                finish();
            }
        });

        Intent intent = getIntent();
        String loginMethod = intent.getStringExtra("loginMethod");

        if (loginMethod != null) {
            if (loginMethod.equals("email")) {
                String email = intent.getStringExtra("email");
                // Tampilkan email di TextView
                infoLoginWith.setText(email);
            } else if (loginMethod.equals("phone")) {
                String username = intent.getStringExtra("username");
                // Tampilkan username di TextView
                infoLoginWith.setText(username);
            } else if (loginMethod.equals("google")) {
                String google = intent.getStringExtra("USER_EMAIL");
                infoLoginWith.setText(google);
            }
        } else {
            Toast.makeText(MainActivity.this,"loginMethod is null", Toast.LENGTH_SHORT).show();
        }

    }
}