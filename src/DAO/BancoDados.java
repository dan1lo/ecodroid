package DAO;

import android.content.Context;
import android.content.ContextWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class BancoDados {
	
	String[] sql ={ "INSERT INTO ponto_coleta(latitude,longitude,logradouro,bairro,cidade,numero,tipo_coleta)"+
"VALUES(-8.250905,-35.971985,'Caruaru','Centro','caruaru','132', 0); ","INSERT INTO ponto_coleta(latitude,longitude,logradouro,bairro,cidade,numero,tipo_coleta)"+
"VALUES(-8.280209,-36.015587,'Pólo Comercial de Caruaru','Nova Caruaru','caruaru','12', 1); ","INSERT INTO ponto_coleta(latitude,longitude,logradouro,bairro,cidade,numero,tipo_coleta)"+
"VALUES(-8.280209,-36.015587,'Pólo Comercial de Caruaru','Nova Caruaru','caruaru','12', 2);  ","INSERT INTO ponto_coleta(latitude,longitude,logradouro,bairro,cidade,numero,tipo_coleta)"+
"VALUES(-8.280209,-36.015587,'Pólo Comercial de Caruaru','Nova Caruaru','caruaru','12', 0);  ","INSERT INTO ponto_coleta(latitude,longitude,logradouro,bairro,cidade,numero,tipo_coleta)"+
"VALUES(-8.280209,-36.015587,'Pólo Comercial de Caruaru','Nova Caruaru','caruaru','12', 3);  ","INSERT INTO ponto_coleta(latitude,longitude,logradouro,bairro,cidade,numero,tipo_coleta)"+
"VALUES(-8.269932,-35.976191,'Casa de Saúde Santa Efigência','Mauricio de Nassau','caruaru','34', 8);  ","INSERT INTO ponto_coleta(latitude,longitude,logradouro,bairro,cidade,numero,tipo_coleta)"+
"VALUES(-8.269932,-35.976191,'Casa de Saúde Santa Efigência','Mauricio de Nassau','caruaru','34', 9);","INSERT INTO ponto_coleta(latitude,longitude,logradouro,bairro,cidade,numero,tipo_coleta)"+
	"VALUES (-8.276876, -35.972371, 'Shopping Difusora', 'Centro', 'caruaru', '54', 5);  ","INSERT INTO ponto_coleta(latitude,longitude,logradouro,bairro,cidade,numero,tipo_coleta)"+
	"VALUES (-8.276876, -35.972371, 'Shopping Difusora', 'Centro', 'caruaru', '54', 4);  " };
	
	/*+


+

+

+

+

  "+

"+

+


*/
	private SQLiteDatabase db2 = null;
	private ContextWrapper wrapper;
	private String banco = "Ecodroid";
	
	private String tabelaCidade ="CREATE TABLE IF NOT EXISTS cidade (id INTEGER NOT NULL PRIMARY KEY autoincrement,nome varchar2(30));";
	
	private String tabelaPontoColeta="CREATE TABLE IF NOT EXISTS ponto_coleta(longitude integer,latitude integer,logradouro varchar(30)," +
			"bairro varchar(30),cidade varchar(30),numero varchar(5),tipo_coleta integer,primary key (longitude,latitude,tipo_coleta));" ;
	
	public BancoDados(ContextWrapper wrapper){
		this.wrapper=wrapper;
	}


	public String getBanco() {
		return banco;
	}


	public void setBanco(String banco) {
		this.banco = banco;
	}


	public boolean criaBanco(){
		try{
			String sql2="drop table cidade";
			this.db2 = this.wrapper.openOrCreateDatabase(this.banco,Context.MODE_PRIVATE,null);
			this.db2.execSQL(sql2);
			sql2="drop table ponto_coleta";
			this.db2.execSQL(sql2); 
			this.db2.execSQL(this.tabelaCidade);
			this.db2.execSQL(this.tabelaPontoColeta);
			int i;
			for (i=0;i<9;i++) {
				
				this.db2.execSQL(sql[i]);
			}
			Log.i("INFO","Banco criado com sucesso");
			return true;
			}catch(Exception e){
				Log.e("ERRO",e.toString());
				return false;
			}
	}
	
	public boolean excluiBanco(){
		//implementar aqui
		return false;
	}
	
	public boolean bancoExiste(){
		//implementar aqui..
		return false;
	}
	
}
