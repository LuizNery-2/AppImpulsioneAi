package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.services.ApiService;
import com.example.myapplication.services.RetrofitClient;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EsqueciSenhaActivity extends AppCompatActivity {

    private ApiService apiService;
    private EditText editTextEmail;
    private Button buttonEnviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_esqueci_senha);

        // Inicialize os elementos de UI
        editTextEmail = findViewById(R.id.inputEmail);
        buttonEnviar = findViewById(R.id.buttonEnviar);

        // Obtenha uma instância de ApiService

        apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        // Defina um ouvinte de clique para o botão
        buttonEnviar.setOnClickListener(view -> {
            // Obtenha o email digitado
            String email = editTextEmail.getText().toString();

            // Faça a chamada para verificar usuários
            Call<ResponseBody> call = apiService.verificarUsuarios(email);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        // Exiba a resposta
                        try {
                            String resposta = response.body().string();
                            Toast.makeText(EsqueciSenhaActivity.this, resposta, Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        // Exiba uma mensagem de erro caso a resposta não seja bem-sucedida
                        Toast.makeText(EsqueciSenhaActivity.this, "Erro ao verificar usuário", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    // Exiba uma mensagem de erro em caso de falha na requisição
                    Toast.makeText(EsqueciSenhaActivity.this, "Erro de conexão", Toast.LENGTH_SHORT).show();
                    t.printStackTrace();
                }
            });
        });
    }
}