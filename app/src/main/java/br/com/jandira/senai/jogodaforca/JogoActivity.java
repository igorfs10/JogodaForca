package br.com.jandira.senai.jogodaforca;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

public class JogoActivity extends AppCompatActivity {

    TextView txtPalavraJogo;
    TextView txtErros;
    TextView txtAcertos;
    TextView txtCategoria;
    String palavra = "";

    static final  String categorias[] = {
            "ANIMAL",
            "CARRO",
            "TIME",
            "PAÍS",
            "FRUTA"
    };
    static final String[][] palavras = {{ //ANIMAIS
            "GATO",
            "CACHORRO",
            "COELHO",
            "RAPOSA",
            "ELEFANTE",
            "ESQUILO",
            "ZEBRA",
            "POMBO",
            "HIPOPOTAMO",
            "COBRA",
            "LONTRA",
            "MACACO"
        },{ //MARCAS DE CARROS
            "VOLKSWAGEN",
            "RENAULT",
            "CHEVROLET",
            "HYUNDAI",
            "HONDA",
            "FORD",
            "FIAT",
            "PEUGEOT",
            "MAZDA",
            "FERRARI",
            "PORSCHE",
            "LAMBORGHINI",
            "AUDI"
        },{ //TIMES
            "CORINTHIANS",
            "SANTOS",
            "PALMEIRAS",
            "FLAMENGO",
            "FLUMINENSE",
            "CHAPECOENSE",
            "PORTUGUESA",
            "GREMIO",
            "FORTALEZA",
            "VASCO",
            "BARCELONA",
            "CHELSEA",
            "LIVERPOOL",
            "JUVENTUS"
        },{ //PAIS
            "BRASIL",
            "ITALIA",
            "HOLANDA",
            "TURQUIA",
            "INGLATERRA",
            "CHINA",
            "AUSTRALIA",
            "IRAQUE",
            "IRLANDA",
            "ARGENTINA",
            "SUECIA",
            "COLOMBIA",
            "NORUEGA"
        },{ //FRUTAS
            "BANANA",
            "LARANJA",
            "MARACUJA",
            "UVA",
            "KIWI",
            "CARAMBOLA",
            "CEREJA",
            "MORANGO",
            "PESSEGO",
            "MANGA",
            "JABUTICABA",
            "AMEIXA"
        }
    };

    static final int VITORIA = 0;
    static final int DERROTA = 1;

    int numeroCategoria = escolherCategoria();
    String categoriaEscolhida = categorias[numeroCategoria];
    String palavraEscolhida = escolherPalavra(numeroCategoria);

    int acertosTotal = palavraEscolhida.length();
    int errosTotal = (int) Math.ceil(palavraEscolhida.length() / 1.7);

    int quantidaDeErros = 0;
    int quantidaDeAcertos = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jogo);

        txtPalavraJogo = findViewById(R.id.txtPalavraJogo);
        txtErros = findViewById(R.id.txtErros);
        txtAcertos = findViewById(R.id.txtAcertos);
        txtCategoria = findViewById(R.id.txtCategoria);

        txtCategoria.setText(categoriaEscolhida);

        for(int i=0; i <= palavraEscolhida.length() - 1; i++){
            if(i == palavraEscolhida.length() - 1){
                palavra += "_";
            }else{
                palavra += "_ ";
            }
        }
        txtPalavraJogo.setText(palavra);

        atualizarQuantidaDeAcertos();
        atualizarQuantidaDeErros();
    }

    public void acaobotao(View v) {
        char letraEscolhida = v.getTag().toString().charAt(0);
        v.setEnabled(false);
        v.setBackgroundColor(getResources().getColor(R.color.desativado));

        boolean erro = true;

        for(int i = 0; i <= palavraEscolhida.length() - 1; i++){
            if(letraEscolhida == palavraEscolhida.charAt(i)){
                StringBuilder novaPalavra = new StringBuilder(palavra);
                novaPalavra.setCharAt(i * 2, letraEscolhida);
                palavra = novaPalavra.toString();
                txtPalavraJogo.setText(palavra);
                quantidaDeAcertos += 1;
                erro = false;
            }
        }
        if(erro){
            quantidaDeErros += 1;
            atualizarQuantidaDeErros();
        }else{
            atualizarQuantidaDeAcertos();
        }
        if(quantidaDeErros >= errosTotal){
            gameOver(DERROTA);
        }else if(quantidaDeAcertos >= acertosTotal){
            gameOver(VITORIA);
        }
    }

    public void reiniciar(View v){
        recreate();
    }

    private void gameOver(int condicaoVitoria){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setCancelable(false);

        if(condicaoVitoria == VITORIA){
            alert.setTitle("Você Ganhou \uD83D\uDE00");
            alert.setMessage("Você acertou a palavra. Parabéns.");
        }else{
            alert.setTitle("Você Perdeu \uD83D\uDE22");
            alert.setMessage("Você errou a palavra. A palavra é '" + palavraEscolhida + "'.");
        }
        alert.setNegativeButton("sair", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        alert.setPositiveButton("reiniciar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                recreate();
            }
        });

        alert.create().show();
    }

    private void atualizarQuantidaDeAcertos(){
        txtAcertos.setText("Acertos: " + quantidaDeAcertos);
    }

    private void atualizarQuantidaDeErros(){
        txtErros.setText("Erros: " + quantidaDeErros + "/" + errosTotal);
    }


    private int escolherCategoria(){
        int min = 0;
        int max = categorias.length - 1;

        return new Random().nextInt((max - min) + 1) + min;
    }
    private String escolherPalavra(int numeroCategoria){
        int min = 0;
        int max = palavras[numeroCategoria].length - 1;
        final int numeroPalavra = new Random().nextInt((max - min) + 1) + min;

        return palavras[numeroCategoria][numeroPalavra];
    }
}