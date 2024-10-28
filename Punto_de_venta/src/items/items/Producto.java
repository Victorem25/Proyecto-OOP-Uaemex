package items;

public class Producto {
    public String codigo,nombre;
    public double precio;
    public int stock;
    public Proveedor proveedor;
    
    Producto(){
        codigo = "";
        nombre = "";
        precio = 0.0;
        stock = 0;
        proveedor = new Proveedor();
    }
    
    Producto(String codigo, String nombre, double precio, int stock, Proveedor proveedor){
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.proveedor = proveedor;
    }
}
