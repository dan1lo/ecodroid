package negocio;



import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;

public class IconePonto extends com.google.android.maps.Overlay {
	private GeoPoint ponto;
	private Resources resources;
	private Context context;
	private int idIcone;
	
	public IconePonto(GeoPoint ponto,Resources resources,Context context,int idIcone){
		super();
		this.resources=resources;
		this.ponto=ponto;
		this.idIcone=idIcone;
		this.context=context;
	}

        @Override
        public boolean draw(Canvas canvas, MapView mapView, 
        boolean shadow, long when) 
        {
            super.draw(canvas, mapView, shadow);                   
            try{
            //---translate the GeoPoint to screen pixels---
            Point screenPts = new Point();
            mapView.getProjection().toPixels(ponto, screenPts);
 
            //---add the marker---
            Bitmap bmp = BitmapFactory.decodeResource(
               this.resources, this.idIcone);            
            canvas.drawBitmap(bmp, screenPts.x, screenPts.y-50, null);
            }catch(Exception ex){
            	Log.e("Erro:",ex.toString());
            }
            return true;
        }
        
        @Override
        public boolean onTouchEvent(MotionEvent event, MapView mapView) 
        {   
            //---when user lifts his finger---
            if (event.getAction() == 1) {                
                GeoPoint p = mapView.getProjection().fromPixels(
                    (int) event.getX(),
                    (int) event.getY());
                //Toast.makeText(this.context, Integer.toString(p.getLatitudeE6()), Toast.LENGTH_LONG).show();
               /* Geocoder geoCoder = new Geocoder(
                        this.context, Locale.getDefault());
                    try {
                        List<Address> addresses = geoCoder.getFromLocation(
                            p.getLatitudeE6()  / 1E6, 
                            p.getLongitudeE6() / 1E6, 1);
     
                        String add = "";
                        if (addresses.size() > 0) 
                        {
                            for (int i=0; i<addresses.get(0).getMaxAddressLineIndex(); 
                                 i++)
                               add += addresses.get(0).getAddressLine(i) + "\n";
                        }
     
                        Toast.makeText(this.context, add, Toast.LENGTH_SHORT).show();
                    }
                    catch (IOException e) {                
                    	Log.e("ERRO",e.toString());
                    	 Toast.makeText(this.context, e.toString(), Toast.LENGTH_SHORT).show();
                    }   
                    return true;*/
                }
            else{
            	return false;
            }
			return false;
        }
    }

