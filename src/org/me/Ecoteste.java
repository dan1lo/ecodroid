package org.me;

import DAO.BancoDados;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class Ecoteste extends Activity {
    /** Called when the activity is first created. */
	private Button btn_entrar;
	private Button btn_entrar_raio;
	private Button btnSobre;
	private RadioButton rdBtnStel;
	private RadioButton rdBtnStreet;
	private BancoDados banco;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        this.banco=new BancoDados(this);
		this.banco.criaBanco();
        
        this.btn_entrar = (Button) findViewById(R.id.btn_entrar);
        this.btn_entrar_raio = (Button) findViewById(R.id.btn_entrar_raio);
        this.btnSobre = (Button) findViewById(R.botoes.btnSobre);
        this.rdBtnStel=(RadioButton)this.findViewById(R.botoes.rbtnSatel);
		this.rdBtnStreet=(RadioButton)this.findViewById(R.botoes.rbtnStreet);
		
        this.rdBtnStel.setOnCheckedChangeListener( new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					Ecoteste.this.rdBtnStreet.setChecked(false);
				}
				
			}
		});
		this.rdBtnStreet.setOnCheckedChangeListener( new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					Ecoteste.this.rdBtnStel.setChecked(false);
				}
				
			}
		});
		
        this.btn_entrar.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View arg0) {
				Intent it = new Intent(Ecoteste.this,Opcao.class); 
				it.putExtra("cidade", "Forneça a cidade");
				if(Ecoteste.this.rdBtnStel.isChecked()){
					it.putExtra("street", false);
				}
				else{
					it.putExtra("street", true);
				}
				Ecoteste.this.startActivity(it);
			}
        	
        	
        });
        
        this.btn_entrar_raio.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent it = new Intent(Ecoteste.this,Opcao.class); 
				it.putExtra("raio", "Forneça o raio (KM)");
				if(Ecoteste.this.rdBtnStel.isChecked()){
					it.putExtra("street", false);
				}
				else{
					it.putExtra("street", true);
				}
				Ecoteste.this.startActivity(it);
			}
		});
        
        this.btnSobre.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String menssagem = "Equipe: \n\n Aline Alves\nCarlos Mágno\nCésar Albuquerque\nDanilo Monteiro\nJosé Bartholomeu\nJosé Paulo\nWilker Lima\nMorgana Giorgia\n\n Versão 1.0 - Open Source \n O projeto ECODROID visa melhorar o meio ambiente, com pontos da coleta Seletiva de lixo.";

				AlertDialog.Builder dialog = new AlertDialog.Builder(Ecoteste.this);
				                dialog.setTitle("Ecodroid");
				                dialog.setMessage(menssagem);
				                dialog.setNeutralButton("Ok", null);
				                dialog.show();

				
			}
		});
    }
}