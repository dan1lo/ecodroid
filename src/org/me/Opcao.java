package org.me;

import java.io.IOException;
import java.util.List;

import negocio.AdicionarPonto;
import negocio.IconePonto;
import negocio.PontoColeta;
import negocio.TipoColeta;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;

import DAO.BancoDados;
import DAO.PontoColetaDAO;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Opcao extends MapActivity {
	
	private static final int MODO_CIDADE=0;
	private static final int MODO_RAIO=1;
	
	private Button btn_mandar;
	private TextView txtBusca;
	private int modo;
	private Spinner spnOp;
	private double latPoint=0.000000d ;
    private double lngPoint=0.000000d ;
	private static final String[] opcao ={"Todos","Papel","Metal","Vidro","Plástico",
										"Orgânico","Resíduo Perigoso","Madeira","Irreciclável",
										"Radiotivo","Ambulatorial"};
	private ArrayAdapter<String> aOp;
	private EditText edt_cidade;
	
	//variaveis da outra tela
	private MapView mapView;
	private MapController mapController;
	private PontoColetaDAO pontoDAO;
	private List<PontoColeta> pontoLista;
	private IconePonto mapOverlay;
	private AdicionarPonto adiciona;
	private RadioButton rdBtnStel;
	private RadioButton rdBtnStreet;
	private Intent intente;
	private TipoColeta tipoColeta=new TipoColeta(-1);
	
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.op);
	    btn_mandar = (Button) findViewById(R.id.btn_mandar);
	    this.edt_cidade = (EditText) findViewById(R.id.edt_cidade);
	    this.txtBusca=(TextView) this.findViewById(R.id.txtBusca);

		this.mapView = (MapView) this.findViewById(R.id.map_view);
		//this.rdBtnStel=(RadioButton)this.findViewById(R.botoes.rbtnSatel);
		//this.rdBtnStreet=(RadioButton)this.findViewById(R.botoes.rbtnStreet);
		
		//inicia as variaveis
		this.pontoDAO=new PontoColetaDAO(this);
		this.adiciona= new AdicionarPonto(this.mapView);
		this.mapController =this.mapView.getController(); 
		this.intente=this.getIntent();
		this.MyLocation();
		
		//this.rdBtnStel.setChecked(true);
		//configurações
		Bundle param=this.intente!=null?this.intente.getExtras():null;
		if(param!=null){
			String cidade=param.getString("cidade");
			boolean street=false;
			street=param.getBoolean("street");
			//tipo de pesquisa
			if(cidade!=null){
				this.txtBusca.setText(cidade);
				this.modo=this.MODO_CIDADE;
				this.edt_cidade.setInputType(InputType.TYPE_CLASS_TEXT);
			}
			else{
				cidade=param.getString("raio");
				this.txtBusca.setText(cidade);
				this.modo=this.MODO_RAIO;
				this.edt_cidade.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
			}
			//tipo de mapa
			if(street){
				this.mapView.setStreetView(true);
				this.mapView.setSatellite(false);
			}
			else{
				this.mapView.setStreetView(false);
				this.mapView.setSatellite(true);
			}
		}
		this.mapView.setBuiltInZoomControls(true); 
		this.mapView.setClickable(true) ;
		
		
	    aOp = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,opcao);
	    spnOp = (Spinner) findViewById(R.id.Spinner01);
	    spnOp.setAdapter(aOp);
	    
	    btn_mandar.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View arg0) {
					switch(spnOp.getSelectedItemPosition()){
					case 0: 
							Opcao.this.tipoColeta= new TipoColeta(-1);
							break;
					case 1: 
							Opcao.this.tipoColeta= new TipoColeta(TipoColeta.PAPEL);
							break;
					case 2: 
							Opcao.this.tipoColeta= new TipoColeta(TipoColeta.METAL);
							break;
					case 3: 
							Opcao.this.tipoColeta= new TipoColeta(TipoColeta.VIDRO);
							break;
					case 4: 
							Opcao.this.tipoColeta= new TipoColeta(TipoColeta.PLASTICO);
							break;
					case 5: 
							Opcao.this.tipoColeta= new TipoColeta(TipoColeta.ORGANICO);
							break;
					case 6: 
							Opcao.this.tipoColeta= new TipoColeta(TipoColeta.RESIDUO_PERIGOSO);
							break;
					case 7: 
							Opcao.this.tipoColeta= new TipoColeta(TipoColeta.MADEIRA);
							break;
					case 8: 
							Opcao.this.tipoColeta= new TipoColeta(TipoColeta.IRRECICLAVEL);
							break;
					case 9: 
							Opcao.this.tipoColeta= new TipoColeta(TipoColeta.RADIOTIVO);
							break;
					case 10: 
							Opcao.this.tipoColeta= new TipoColeta(TipoColeta.AMBULATORIAL);
							break;
					default:
							Opcao.this.tipoColeta= new TipoColeta(-1);
					
					}
					/*
				   cidade =  edt_cidade.getText().toString();
					inserirTBL(cidade);
					*/
					//Intent it= new Intent(Opcao.this,Mapa.class);
					//it.putExtra("cidade",Opcao.this.edt_cidade.getText().toString());
					//Opcao.this.startActivityForResult(it, 1); 
					if(Opcao.this.modo==Opcao.MODO_CIDADE){
						Opcao.this.pesquisaCidade();
					}
					else{
						Opcao.this.pesquisaRaio();
					}
				}	
	        });
	        
	    }


	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	protected void pesquisaCidade(){
		int i=0;
		String cidade=this.edt_cidade.getText().toString();
		if(cidade!=""){
			//escolheu uma cidade
			if(this.tipoColeta.getTipo()>=0){
				//escolheu um tipo
				this.pontoLista=this.pontoDAO.consultarPontoPorTipoColetaECidade(this.tipoColeta, cidade);
				if(!this.pontoLista.isEmpty()){
					this.adiciona.clear();
					for(i=0;i<this.pontoLista.size();i++){
						//coloca o mapa perto do ponto
						this.mapController.animateTo(this.pontoLista.get(i));
						this.mapOverlay= new IconePonto(this.pontoLista.get(i),this.getResources(),this.getBaseContext(),R.drawable.icon_01);
						this.adiciona.add(this.mapOverlay);
					}
					//centraliza o ponto mapa
					this.mapController.setCenter(this.pontoLista.get(i));
					this.mapController.setZoom(16);
					this.mapView.invalidate();
				}
			}//fim do if do tipo
			else{
				this.pontoLista=this.pontoDAO.consultarPontoPorCidade(cidade);
				if(!this.pontoLista.isEmpty()){
					this.adiciona.clear();
					for(i=0;i<this.pontoLista.size();i++){
						//coloca o mapa perto do ponto
						this.mapController.animateTo(this.pontoLista.get(i));
						this.mapOverlay= new IconePonto(this.pontoLista.get(i),this.getResources(),this.getBaseContext(),R.drawable.icon_01);
						this.adiciona.add(this.mapOverlay);
					}
					//centraliza o ponto mapa
					this.mapController.setCenter(this.pontoLista.get(i));
					this.mapController.setZoom(16);
					this.mapView.invalidate();
				}
			}//fim do else do tipo
		}//fim do if da cidade
		/*else{
			Double raio=Double.valueOf(this.edt_cidade.getText().toString());
			PontoColeta ponto=this.MyLocation();
			if(ponto!=null){
			this.pontoLista=this.pontoDAO.consultarPontoRaio(ponto,raio);
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
		}*/
		
		}//fim do metodo
	protected void pesquisaRaio(){
		int i=0;
		String cidade=this.edt_cidade.getText().toString();
		if(cidade!=""){
			//forneceu um raio
			Double raio=Double.valueOf(cidade);
			PontoColeta ponto=new PontoColeta(this.latPoint,this.lngPoint);
			ponto.setTipo(this.tipoColeta);
			//pesquisa por raio e tipo
			if(this.tipoColeta.getTipo()>=0 && ponto!=null){
				//escolheu um tipo
				this.pontoLista=this.pontoDAO.consultarPontoRaioETipo(ponto, raio);
				if(!this.pontoLista.isEmpty()){
					this.adiciona.clear();
					for(i=0;i<this.pontoLista.size();i++){
						//coloca o mapa perto do ponto
						this.mapController.animateTo(this.pontoLista.get(i));
						this.mapOverlay= new IconePonto(this.pontoLista.get(i),this.getResources(),this.getBaseContext(),R.drawable.icon_01);
						this.adiciona.add(this.mapOverlay);
					}
					//centraliza o ponto mapa
					this.mapController.setCenter(this.pontoLista.get(i-1));
					this.mapController.setZoom(16);
					this.mapView.invalidate();
				}
			}//fim do if do tipo
			//pesquisa somente por raio
			else if(ponto!=null){
				this.pontoLista=this.pontoDAO.consultarPontoRaio(ponto, raio);
				if(!this.pontoLista.isEmpty()){
					this.adiciona.clear();
					for(i=0;i<this.pontoLista.size();i++){
						//coloca o mapa perto do ponto
						this.mapController.animateTo(this.pontoLista.get(i));
						this.mapOverlay= new IconePonto(this.pontoLista.get(i),this.getResources(),this.getBaseContext(),R.drawable.icon_01);
						this.adiciona.add(this.mapOverlay);
					}

					//centraliza o ponto mapa
					this.mapController.setCenter(this.pontoLista.get(i-1));
					this.mapController.setZoom(16);
					this.mapView.invalidate();
				}
			}//fim do else do tipo
		}//fim do if da cidade
		
		}//fim do metodo
	
	protected void MyLocation(){
		Localiza lo=new Localiza();
		Location locat =null;
		try{
			LocationManager locationManager=null; 
			locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE); 
			locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000l,500.0f,lo);
			//locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0,lo);
			locat= locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		}catch(Exception ex){
			Log.e("ERRO",ex.toString());
		}
		/*finally{
			if(locat!=null)
				return new PontoColeta(locat.getLatitude(),locat.getLongitude());
			else{
				return null;
			}
		}*/
		
	}
	class Localiza implements LocationListener{

		@Override
		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub
			getCityByLocation(location);
		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onStatusChanged(String provider, int status,
				Bundle extras) {
			// TODO Auto-generated method stub
			
		}
		
	}
	private void getCityByLocation(Location location) {
	    //obtendo coordenadas
	    this.latPoint = location.getLatitude();
	    this.lngPoint = location.getLongitude();
	    /*Classe que fornece a localização da cidade
	    Geocoder geocoder = new Geocoder(this.getApplicationContext());
	    List<Address> myLocation = null;
	 
	    try {
	        //Obtendo os dados do endereço
	        myLocation = geocoder.getFromLocation(latPoint, lngPoint, 1);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	 
	    if ( myLocation != null  ) {
	    	if(myLocation.size() > 0){
	        Address a = myLocation.get(0);
	        //Pronto! Vocêm tem o nome da cidade!
	        String city = a.getLocality();
	       //Seu código continua aqui…
	    	}
	    	else {
		        Log.d("geolocation", "endereço vazio");
		    }
	    } else {
	        Log.d("geolocation", "endereço não localizado");
	    }*/
	}


	
}
