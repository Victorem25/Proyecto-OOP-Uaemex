package punto_de_venta;

import javax.swing.*;

public class VentanaUsuario {
    public static void WindowUsuarios() {
        JFrame ventanaUsuario = new JFrame("Usuario");
        ventanaUsuario.setSize(800, 500);
        ventanaUsuario.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        ventanaUsuario.setAlwaysOnTop(true); // Asegurar que la ventana esté siempre al frente
        ventanaUsuario.toFront(); // Llevar la ventana al frente
        ventanaUsuario.setLocationRelativeTo(null); // Centra la ventana en la pantalla
        ventanaUsuario.setVisible(true);
    }
}
