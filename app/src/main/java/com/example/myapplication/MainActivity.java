package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.services.ApiService;
import com.example.myapplication.services.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    EditText editTextNovaSenha;
    EditText editTextConfimarNovaSenha;

    Button buttonEnviarSenha;
    ApiService service = RetrofitClient.getRetrofitInstance().create(ApiService.class);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editTextNovaSenha = findViewById(R.id.inputNovaSenha);
        editTextConfimarNovaSenha = findViewById(R.id.inputConfirmeNovaSenha);
        buttonEnviarSenha = findViewById(R.id.buttonEnviar);

        buttonEnviarSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String novaSenha = editTextNovaSenha.getText().toString();
                changePassword("e8547635-ff77-4398-90a9-7292244c7bab",novaSenha);;
            }
        });
    }

    private void changePassword(String idUsuario, String novaSenha) {
        ApiService.PasswordChangeRequest request = new ApiService.PasswordChangeRequest(novaSenha);
        Call<Void> call = service.changePassword(idUsuario, request);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Senha alterada com sucesso", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Erro ao alterar senha", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Falha na requisição: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}