package xyz.felipemeloni.android.gingachat;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Intent intent = getIntent();
        String user = intent.getStringExtra(MainActivity.EXTRA_USER);
        //envia os dados coletados do intent para a tela
        adjustParams(user);
    }

    public void adjustParams(String user){
        TextView lblUser = (TextView)findViewById(R.id.text_user);
        lblUser.setText(user);
    }

    public void viewMsgs(View view){
        Intent intent = new Intent(this, ListActivity.class);
        //EditText editText = (EditText) findViewById(R.id.edit_user);
        //String user = editText.getText().toString();
        //intent.putExtra(EXTRA_USER, user);
        startActivity(intent);


    }

    //entra ao clicar o botao de ENVIAR MENSAGEM
    public void sendMsg(View view) throws IOException{
        //Encontra os valores de usuario e mensagem
        TextView user = (TextView) findViewById(R.id.text_user);
        TextView message = (TextView)findViewById(R.id.edit_message);
        String usr = user.getText().toString();
        String msg = message.getText().toString();
        //envia mensagem para lista
        DataStorage.addMsg(msg);
        //apaga o que tinha sido digitado antes de apertar o botao
        TextView lblMessage = (TextView)findViewById(R.id.edit_message);
        lblMessage.setText("");
        //gera a URL para enviar ao servidor PHP
        //String url_content = "http://www.felipemeloni.xyz/gingamsgserver.php/?usr=" + usr + "&msg=" + msg ;
        String url_content = "http://www.tv.unesp.br/webftp/geiza/chat/gingamsgserver.php/?usr=" + usr + "&msg=" + msg ;
        //em PHP espaço é "+"
        String url_temp = url_content.replaceAll(" ", "+");
        Log.d("URL_FORM", url_temp);
        new Solicitacao().execute(url_temp);
    }

    /*  AsyncTask -> recebe 3 tipos de parametro em sua declaração
     *  p1 - Passado parao método doInBackground que realiza a execução da tarefa
     *  p2 - Utizado para monitorar o progresso da atividade (on ProgressUpdate)
     *  p3 - Utilizado para concluir a tarefa (retorno do doInBackground) e parametro para onPostExecute
     */
    private class Solicitacao extends AsyncTask<String, Void, String> {

        protected void onPreExecute(){

        }

        protected void onPostExecute(String param){
            Toast.makeText(ChatActivity.this, "Mensagem enviada com sucesso", Toast.LENGTH_LONG).show();
            Log.d("HTTPCLIENT", param);
        }

        protected String doInBackground(String... param1){

            String conteudo = "";
            String linha;

            try{
                //cria a URL a partir do parametro passado pelo método
                URL url = new URL(param1[0]);
                //cria conexao http a partir da url
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                //realiza a conexão
                connection.connect();
                int response = connection.getResponseCode();
                if (response == HttpURLConnection.HTTP_OK){
                    //cria um leitor a partir da conexão estabelecida
                    InputStreamReader isr = new InputStreamReader(connection.getInputStream());
                    //cria um objeto para ler linha a linha o resultado
                    BufferedReader br = new BufferedReader(isr);
                    while ((linha = br.readLine()) != null){
                        conteudo += linha;
                    }
                    br.close();
                    isr.close();
                }
            }
            catch(Exception ex){
                Log.d("HTTPCLIENT","Erro ao conectar - "+ex.getMessage());
            }
            return conteudo;
        }
    }
}


