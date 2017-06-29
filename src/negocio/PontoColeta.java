package negocio;

import com.google.android.maps.GeoPoint;

public class PontoColeta extends GeoPoint{
	private double latitudeGraus;
	private double longitudeGraus;
	private TipoColeta tipo;
	private Endereco endereco;
	
	public TipoColeta getTipo() {
		return tipo;
	}
	public void setTipo(TipoColeta tipo) {
		this.tipo = tipo;
	}
	public Endereco getEndereco() {
		return endereco;
	}
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	//getts e setts
	public double getLatitudeGraus() {
		return latitudeGraus;
	}
	public double getLongitudeGraus() {
		return longitudeGraus;
	}
	public PontoColeta(int latitudeE6, int longitudeE6) {
		super(latitudeE6, longitudeE6);
	}
	public PontoColeta(double latitude, double longitude){
		//faz a conversão
		super((int) (1e6*(latitude)),(int)(1e6*( longitude)));
		this.latitudeGraus=latitude;
		this.longitudeGraus=longitude;
	}
	
	//metodos para distancia
	public double calcularDistanciaKM(PontoColeta ponto){
		double latitude,longitude,latitudePto,longitudePto;
		double dlon, dlat, a, distancia;
		
		latitude=Math.toRadians(this.latitudeGraus);
		latitudePto=Math.toRadians(ponto.getLatitudeGraus());
		longitude=Math.toRadians(this.longitudeGraus);
		longitudePto=Math.toRadians(ponto.getLongitudeGraus());
		
		dlon = longitudePto - longitude;  
		dlat = latitudePto - latitude;  
		
		a = Math.pow(Math.sin(dlat/2),2) + Math.cos(latitude) * Math.cos(latitudePto) * Math.pow(Math.sin(dlon/2),2);  
		distancia = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)); 
		
		distancia*=6378140;
		return   distancia/1000; /* 6378140 is the radius of the Earth in meters*/
	}
	//distancia em metros
	public double calcularDistanciaM(PontoColeta ponto){
		double latitude,longitude,latitudePto,longitudePto;
		double dlon, dlat, a, distancia;
		
		latitude=Math.toRadians(this.latitudeGraus);
		latitudePto=Math.toRadians(ponto.getLatitudeGraus());
		longitude=Math.toRadians(this.longitudeGraus);
		longitudePto=Math.toRadians(ponto.getLongitudeGraus());
		
		dlon = longitudePto - longitude;  
		dlat = latitudePto - latitude;  
		
		a = Math.pow(Math.sin(dlat/2),2) + Math.cos(latitude) * Math.cos(latitudePto) * Math.pow(Math.sin(dlon/2),2);  
		distancia = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)); 
		
		distancia*=6378140;
		return   distancia; /* 6378140 is the radius of the Earth in meters*/
	}

}
