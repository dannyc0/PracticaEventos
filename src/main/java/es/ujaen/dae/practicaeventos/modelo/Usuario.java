package es.ujaen.dae.practicaeventos.modelo;

//Bean o POJO
public class Usuario {
	String dni;
	String nombre;
	String correo;
	String telefono;
	String nickname;
	String password;
	
	public Usuario() {}

	public Usuario(String dni, String nombre, String correo, String telefono, String nickname, String password) {
		super();
		this.dni = dni;
		this.nombre = nombre;
		this.correo = correo;
		this.telefono = telefono;
		this.nickname = nickname;
		this.password = password;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Usuario [dni=" + dni + ", nombre=" + nombre + ", correo=" + correo + ", telefono=" + telefono
				+ ", nickname=" + nickname + "]";
	}
	
	
	
}
