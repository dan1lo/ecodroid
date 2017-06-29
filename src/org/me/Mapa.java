package org.me;

import java.util.List;

import DAO.PontoColetaDAO;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;

import negocio.AdicionarPonto;
import negocio.IconePonto;
import negocio.PontoColeta;

public class Mapa extends MapActivity {
	
	private MapView mapView;
	private MapController mapController;
	private PontoColetaDAO pontoDAO;
	private List<PontoColeta> pontoLista;
	private IconePonto mapOverlay;
	private AdicionarPonto adiciona;
	private RadioButton rdBtnStel;
	private RadioButton rdBtnStreet;
	private Intent intente;

	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.mapa);
		this.mapView = (MapView) this.findViewById(R.id.map_view);
		this.rdBtnStel=(RadioButton)this.findViewById(R.botoes.rbtnSatel);
		this.rdBtnStreet=(RadioButton)this.findViewById(R.botoes.rbtnStreet);
		
		//inicia as variaveis
		this.pontoDAO=new PontoColetaDAO(this);
		this.adiciona= new AdicionarPonto(this.mapView);
		this.mapController =this.mapView.getController(); 
		this.intente=this.getIntent();
		
		this.rdBtnStel.setChecked(true);
		this.mapView.setSatellite(true);
		this.mapView.setBuiltInZoomControls(true); 
		this.mapView.setClickable(true) ;
		//eventos
		this.rdBtnStel.setOnCheckedChangeListener( new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					Mapa.this.rdBtnStreet.setChecked(false);
					Mapa.this.mapView.setSatellite(true);
					Mapa.this.mapView.setStreetView(false);
				}
				
			}
		});
		this.rdBtnStreet.setOnCheckedChangeListener( new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					Mapa.this.rdBtnStel.setChecked(false);
					Mapa.this.mapView.setSatellite(false);
					Mapa.this.mapView.setStreetView(true);
				}
				
			}
		});
	}
	

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	
	protected void onStart(){
		super.onStart();
		int i=0;
		Bundle parame=this.intente!=null?this.intente.getExtras():null;
		if(parame!=null){
			String cidade=parame.getString("cidade");
			//digitou alguma cidade
			if(cidade!=""){
				//faz a pesquisa
				this.pontoLista=this.pontoDAO.consultarPontoPorCidade(cidade);
				if(!this.pontoLista.isEmpty()){
					this.adiciona.clear();
					//coloca o mapa perto do ponto
					this.mapController.animateTo(this.pontoLista.get(i));
					//centraliza o ponto mapa
					this.mapController.setCenter(this.pontoLista.get(i));
					for(i=0;i<this.pontoLista.size();i++){
						this.mapOverlay= new IconePonto(this.pontoLista.get(i),this.getResources(),this.getBaseContext(),R.drawable.icon_01);
						this.adiciona.add(this.mapOverlay);
					}
					this.mapController.setZoom(16);
					this.mapView.invalidate();
				}
			}
		}
		//nenhuma cidade
		else{
			this.pontoLista=this.pontoDAO.pegarAllPontos();
			if(!this.pontoLista.isEmpty()){
				this.adiciona.clear();
				//coloca o mapa perto do ponto
				this.mapController.animateTo(this.pontoLista.get(i));
				//centraliza o ponto mapa
				this.mapController.setCenter(this.pontoLista.get(i));
				for(i=0;i<this.pontoLista.size();i++){
					this.mapOverlay= new IconePonto(this.pontoLista.get(i),this.getResources(),this.getBaseContext(),R.drawable.icon_01);
					this.adiciona.add(this.mapOverlay);
				}
				this.mapController.setZoom(16);
				this.mapView.invalidate();
			}
		}
	}
	
	protected void MyLocation(){
		try{
			LocationManager locationManager; 
			locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE); 
			Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			if(location!=null){
				Toast.makeText(this, Double.toString(location.getLatitude()), Toast.LENGTH_LONG).show();
			}
			else{
				Toast.makeText(this, "Loc null", Toast.LENGTH_LONG).show();
			}
		}catch(Exception ex){
			Log.e("ERRO",ex.toString());
		}
	}

}
