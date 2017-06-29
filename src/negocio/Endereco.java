package negocio;

public class Endereco {
	
	private String logradouro;
	private String numero;
	private String bairro;
	private String cidade;

	public Endereco(){
		this.bairro="";
		this.cidade="";
		this.logradouro="";
		this.numero="";
	}
	public Endereco( String logradouro,String numero,String bairro,String cidade){
		this.bairro=bairro;
		this.cidade=cidade;
		this.logradouro=logradouro;
		this.numero=numero;
	}
	public String getLogradouro() {
		return logradouro;
	}
	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
}
