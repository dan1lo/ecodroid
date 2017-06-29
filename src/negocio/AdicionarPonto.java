package negocio;

import java.util.List;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

public class AdicionarPonto {
	private MapView mapView;
	private List<Overlay> listOfOverlays;
	
	public AdicionarPonto (MapView mapa){
		this.mapView=mapa;
		this.listOfOverlays = this.mapView.getOverlays();
	}
	public void add(IconePonto ponto){						
        listOfOverlays.add(ponto);
	}
	public void clear(){
		this.listOfOverlays.clear();
	}
	public void get(int index){
		this.listOfOverlays.get(index);
	}

}
