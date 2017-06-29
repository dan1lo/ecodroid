package DAO;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class CampoCidadeDAO {
	
	private SQLiteDatabase db2 = null;
	private BancoDados banco;
	private static final String TABELA ="CREATE TABLE IF NOT EXISTS cidade (id INTEGER NOT NULL PRIMARY KEY autoincrement," + "nome varchar2(30));";
	private String cidade;
	private ContextWrapper wrapper;
	
	public CampoCidadeDAO(ContextWrapper wrapper){
		this.wrapper=wrapper;
		this.banco= new BancoDados(wrapper);
	}


public void criaBanco(){
	 try{
		 db2 = this.wrapper.openOrCreateDatabase(this.banco.getBanco(),Context.MODE_PRIVATE,null);
		 db2.execSQL(TABELA);
		 Log.i("INFO","Banco criado com sucesso");
		 
	 }catch(Exception e){
		 Log.e("ERRO",e.toString());
	 }
	 
}
public boolean inserirCidade(String cidade){
	 
	try{
		 db2 = this.wrapper.openOrCreateDatabase(this.banco.getBanco(),0,null);
		 //String sql="insert into cidade (nome) values('"+nome+"')";
		 //pode fazer assim 
		  ContentValues valores = new ContentValues();
	        valores.put("nome",cidade);
	       // valores.put("ID","66666");
	        db2.insert("cidade", null, valores); //(TABELA, NULL,VALORES)
		 //db2.execSQL(sql);
		// Toast.makeText(this.wrapper.get, "Localizando "+nome+"...", Toast.LENGTH_LONG).show();
		 Log.i("INFO","Cidade adicionada com sucesso");
		 return true;
	 }catch(Exception e){
		 Log.e("ERRO",e.toString());
		 return false;
	 }
}

public String consultarCidade(){
	try{
		db2 = this.wrapper.openOrCreateDatabase(this.banco.getBanco(),0,null);
		String sql ="SELECT nome FROM cidade WHERE id=(SELECT MAX(id) FROM cidade)";
		Cursor query = db2.rawQuery(sql,null);
		query.moveToFirst();
		cidade = query.getString(0);
		return cidade;
	}catch(Exception e){
		Log.e("ERRO",e.toString());
	}
	 
	 
	 return cidade;
	 
 }
 public boolean deletarTodasCidades(){
	 try{
		this.db2=this.wrapper.openOrCreateDatabase(this.banco.getBanco(), 0, null);
		String sql="Delete from cidade";
		this.db2.execSQL(sql);
		return true;
	 }catch(Exception ex){
		 Log.e("ERRO",ex.toString());
		 return false;
	 }
 }
}