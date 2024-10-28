package items;

public class Proveedor {
    public String nombre,contacto;
    
    Proveedor(){
        nombre = "";
        contacto = "";
    }
    
    Proveedor(String nombre,String contacto){
        this.nombre = nombre;
        this.contacto = contacto;
    }
}
