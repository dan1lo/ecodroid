package negocio;

public class TipoColeta {

	
	private Integer tipo;
	public static final int PAPEL=0;
	public static final int METAL=1;
	public static final int VIDRO=2;
	public static final int PLASTICO=3;
	public static final int ORGANICO=4;
	public static final int RESIDUO_PERIGOSO=5;
	public static final int MADEIRA=6;
	public static final int IRRECICLAVEL=7;
	public static final int RADIOTIVO=8;
	public static final int AMBULATORIAL=9;
	
	public TipoColeta(Integer tipo){
		this.tipo=tipo;
	}
	
	
	public Integer getTipo() {
		return tipo;
	}
	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}
	

}
