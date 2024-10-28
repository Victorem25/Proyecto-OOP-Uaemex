package punto_de_venta;

public class Punto_de_venta {
    public static void main(String[] args) {
        // Llamamos al metodo WindowLogin de la clase VentanaLogin
        // VentanaLogin.WindowLogin();
        // VentanaPuntoDeVenta.WindowPuntoDeVenta();

        // Crear una instancia de VentanaInventario
        //new VentanaInventario();
        new VentanaProveedores();// Esto llamará al constructor que a su vez llama a WindowInventario()
    }
}
