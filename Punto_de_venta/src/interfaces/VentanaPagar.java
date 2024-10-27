package interfaces;
import javax.swing.*;

public class VentanaPagar {
    public static void WindowPagar(){
        JFrame ventanaPagar = new JFrame("Pago");
        ventanaPagar.setSize(800,500);
        ventanaPagar.setLayout(null);
        ventanaPagar.setLocationRelativeTo(null);
        ventanaPagar.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        // Hacer visible la ventana
        ventanaPagar.setVisible(true);
    }
    
    
    
}
