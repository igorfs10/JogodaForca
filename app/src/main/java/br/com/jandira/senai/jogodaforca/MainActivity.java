package br.com.jandira.senai.jogodaforca;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import br.com.jandira.senai.jogodaforca.R;


public class MainActivity extends AppCompatActivity {


    public void iniciarJogo(View v){
        Intent intent = new Intent(this, JogoActivity.class);
        startActivity(intent);
        finish();
        }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
