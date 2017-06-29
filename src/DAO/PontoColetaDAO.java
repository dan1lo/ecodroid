package DAO;

import java.util.ArrayList;
import java.util.List;

import negocio.PontoColeta;
import negocio.TipoColeta;

import android.content.ContextWrapper;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class PontoColetaDAO {
	
	private SQLiteDatabase db2 = null;
	private BancoDados banco;
	private ContextWrapper wrapper;
	
	public PontoColetaDAO(ContextWrapper wrapper){
		this.wrapper=wrapper;
		this.banco= new BancoDados(wrapper);
	}
	
	@SuppressWarnings("finally")
	public List<PontoColeta> pegarAllPontos(){
		List<PontoColeta> lista= new ArrayList<PontoColeta>();
		try{
			String sql ="SELECT latitude,longitude FROM ponto_coleta";
			
			db2 = this.wrapper.openOrCreateDatabase(this.banco.getBanco(),0,null);
			Cursor query = db2.rawQuery(sql,null);
			
			query.moveToFirst();
			while(!query.isAfterLast()){
				PontoColeta ponto= new PontoColeta(query.getDouble(0), query.getDouble(1));
				lista.add(ponto);
				query.moveToNext();
			}
			query.close();
		}catch(Exception e){
			Log.e("ERRO",e.toString());
			
		}
		finally{
			this.db2.close();
			return lista;
		}
	 }
	
	public List<PontoColeta> consultarPontoPorCidade(String cidade){
		List<PontoColeta> lista= new ArrayList<PontoColeta>();
		
		try{
			String sql ="SELECT latitude,longitude FROM ponto_coleta WHERE cidade='"+cidade+"'";
			db2 = this.wrapper.openOrCreateDatabase(this.banco.getBanco(),0,null);
			Cursor query = db2.rawQuery(sql,null);
			
			query.moveToFirst();
			while(!query.isAfterLast()){
				PontoColeta ponto= new PontoColeta(query.getDouble(0), query.getDouble(1));
				lista.add(ponto);
				query.moveToNext();
			}
			query.close();
			return lista;
		}catch(Exception e){
			Log.e("ERRO",e.toString());
			return null;
		}
		finally{
			this.db2.close();
			return lista;
		}
	 }
	public List<PontoColeta> consultarPontoPorTipoColeta(TipoColeta tipo){
		List<PontoColeta> lista= new ArrayList<PontoColeta>();
		try{
			String sql ="SELECT latitude,longitude FROM ponto_coleta WHERE tipo_coleta="+tipo.getTipo().toString()+"";
			
			db2 = this.wrapper.openOrCreateDatabase(this.banco.getBanco(),0,null);
			Cursor query = db2.rawQuery(sql,null);
			
			query.moveToFirst();
			while(!query.isAfterLast()){
				PontoColeta ponto= new PontoColeta(query.getDouble(0), query.getDouble(1));
				lista.add(ponto);
				query.moveToNext();
			}
			query.close();
			return lista;
		}catch(Exception e){
			Log.e("ERRO",e.toString());
			return null;
		}
		finally{
			this.db2.close();
			return lista;
		}
	 }
	
	public List<PontoColeta> consultarPontoPorTipoColetaECidade(TipoColeta tipo,String cidade){
		List<PontoColeta> lista= new ArrayList<PontoColeta>();
		try{
			String sql ="SELECT latitude,longitude FROM ponto_coleta WHERE tipo_coleta="+tipo.getTipo().toString()+" AND cidade='"+cidade+"'";
			
			db2 = this.wrapper.openOrCreateDatabase(this.banco.getBanco(),0,null);
			Cursor query = db2.rawQuery(sql,null);
			
			query.moveToFirst();
			while(!query.isAfterLast()){
				PontoColeta ponto= new PontoColeta(query.getDouble(0), query.getDouble(1));
				lista.add(ponto);
				query.moveToNext();
			}
			query.close();
			return lista;
		}catch(Exception e){
			Log.e("ERRO",e.toString());
			return null;
		}
		finally{
			this.db2.close();
			return lista;
		}
	 }
	public List<PontoColeta> consultarPontoRaio(PontoColeta pontoColeta,Double raioKM){
		List<PontoColeta> lista= new ArrayList<PontoColeta>();
		try{
			String sql ="SELECT latitude,longitude FROM ponto_coleta";
			
			db2 = this.wrapper.openOrCreateDatabase(this.banco.getBanco(),0,null);
			Cursor query = db2.rawQuery(sql,null);
			
			query.moveToFirst();
			while(!query.isAfterLast()){
				PontoColeta ponto= new PontoColeta(query.getDouble(0), query.getDouble(1));
				if(pontoColeta.calcularDistanciaKM(ponto)<=raioKM){
					lista.add(ponto);
				}
				query.moveToNext();
			}
			query.close();
			return lista;
		}catch(Exception e){
			Log.e("ERRO",e.toString());
			return null;
		}
		finally{
			this.db2.close();
			return lista;
		}
	 }
	public List<PontoColeta> consultarPontoRaioETipo(PontoColeta pontoColeta,Double raioKM){
		List<PontoColeta> lista= new ArrayList<PontoColeta>();
		try{
			String sql ="SELECT latitude,longitude FROM ponto_coleta WHERE tipo_coleta="+pontoColeta.getTipo().getTipo().toString();
			
			db2 = this.wrapper.openOrCreateDatabase(this.banco.getBanco(),0,null);
			Cursor query = db2.rawQuery(sql,null);
			
			query.moveToFirst();
			while(!query.isAfterLast()){
				PontoColeta ponto= new PontoColeta(query.getDouble(0), query.getDouble(1));
				if(pontoColeta.calcularDistanciaKM(ponto)<=raioKM){
					lista.add(ponto);
				}
				query.moveToNext();
			}
			query.close();
			return lista;
		}catch(Exception e){
			Log.e("ERRO",e.toString());
			return null;
		}
		finally{
			this.db2.close();
			return lista;
		}
	 }

}
