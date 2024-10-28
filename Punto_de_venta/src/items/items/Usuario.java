package items;

public class Usuario {
    public String usuario,nombre,contrasena,tipoUsuario;
    public double salario;
    Usuario(){
        usuario = "";
        nombre = "";
        contrasena = "";
        tipoUsuario = "";
        salario = 0.0;
    }
    Usuario(String usuario,String nombre,String contrasena,String tipoUsuario,double salario){
        this.usuario = usuario;
        this.nombre = nombre;
        this.contrasena = contrasena;
        this.tipoUsuario = tipoUsuario;
        this.salario = salario;
    }
}
