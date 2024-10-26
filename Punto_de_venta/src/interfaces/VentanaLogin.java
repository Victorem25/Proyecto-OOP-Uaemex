package funcionalidad;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class VentanaLogin {
    // Clase interna para el botón con bordes redondeados
    static class RoundedButton extends JButton {
        private int borderRadius; // Radio de los bordes redondeados
        private Color defaultColor; // Color original del botón

        // Constructor
        public RoundedButton(String text) {
            super(text);
            this.borderRadius = 10; // Puedes ajustar el radio de los bordes
            this.defaultColor = Color.BLUE; // Color por defecto
            setContentAreaFilled(false); // Para que el fondo no se llene por defecto
            setFocusPainted(false); // Para evitar el contorno al hacer foco
            setBorderPainted(false); // Para evitar el borde por defecto

            // Establecer color por defecto
            setBackground(defaultColor);
            setForeground(Color.WHITE); // Texto blanco

            // Agregar un MouseListener para manejar el cambio de color
            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    setBackground(Color.CYAN); // Color al presionar
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    setBackground(defaultColor); // Regresar al color original
                }
            });
        }

        // Método para dibujar el componente
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g; // Convierte a Graphics2D para un mejor control
            g2.setColor(getBackground()); // Establece el color de fondo del botón
            // Dibuja un rectángulo redondeado
            g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), borderRadius, borderRadius));

            // Dibuja el texto del botón
            g2.setColor(getForeground()); // Cambia al color del texto
            FontMetrics fm = g2.getFontMetrics(); // Obtiene las métricas de la fuente
            int textWidth = fm.stringWidth(getText()); // Calcula el ancho del texto
            int textHeight = fm.getHeight(); // Obtiene la altura del texto
            // Dibuja el texto centrado
            g2.drawString(getText(), (getWidth() - textWidth) / 2, (getHeight() + textHeight) /1-1);

            super.paintComponent(g); // Llama al método de la clase base para dibujar otros elementos
        }

        // Método para dibujar el borde del botón
        @Override
        protected void paintBorder(Graphics g) {
            g.setColor(Color.BLACK); // Establece el color del borde a negro
            // Dibuja el borde redondeado
            g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, borderRadius, borderRadius);
        }
    }

    // Clase interna para cargar y dibujar una imagen en un panel
    static class ImagenPanel extends JPanel {
        private Image imagen;

        // Constructor para cargar la imagen
        public ImagenPanel(String rutaImagen) {
            this.imagen = new ImageIcon(rutaImagen).getImage();
        }

        // Dibujar la imagen
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
        }
    }

    public static void WindowLogin() {
        JFrame ventanaLogin = new JFrame("Login Pollo de Montaña");
        ventanaLogin.setSize(800, 500);
        ventanaLogin.setLayout(null);
        ventanaLogin.setLocationRelativeTo(null);
        ventanaLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // JLabels para "Iniciar Sesión"
        JLabel iniciarSecion = new JLabel("Iniciar Sesión");
        iniciarSecion.setBounds(450, 50, 270, 30);
        iniciarSecion.setFont(new Font("Arial", Font.ITALIC, 20));
        ventanaLogin.add(iniciarSecion);

        // Label usuario
        JLabel usuarioLabel = new JLabel("Usuario");
        usuarioLabel.setBounds(400, 100, 270, 30);
        usuarioLabel.setFont(new Font("Arial", Font.ITALIC, 18));
        ventanaLogin.add(usuarioLabel);

        // Label contraseña
        JLabel contrasenaLabel = new JLabel("Contraseña");
        contrasenaLabel.setBounds(400, 220, 270, 30);
        contrasenaLabel.setFont(new Font("Arial", Font.ITALIC, 18));
        ventanaLogin.add(contrasenaLabel);

        // JTextField para "Usuario"
        JTextField usuarioField = new JTextField();
        usuarioField.setBounds(400, 130, 300, 30);
        usuarioField.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
        usuarioField.setBackground(Color.WHITE);
        ventanaLogin.add(usuarioField);

        // JPasswordField para "Contraseña"
        JPasswordField contrasenaField = new JPasswordField();
        contrasenaField.setBounds(400, 250, 300, 30);
        contrasenaField.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
        contrasenaField.setBackground(Color.WHITE);
        ventanaLogin.add(contrasenaField);

        // JButton "Iniciar" (botón redondeado)
        RoundedButton ingresarButton = new RoundedButton("Iniciar");
        ingresarButton.setBounds(500, 320, 100, 30);
        ventanaLogin.add(ingresarButton);

        // JPanel para la imagen
        JPanel panel1Login = new ImagenPanel("src/imagenes/Logo_Pollo de montaña.jpg");
        panel1Login.setBounds(0, 0, 400, 500);
        panel1Login.setLayout(null);
        ventanaLogin.add(panel1Login);

        // Hacer visible la ventana
        ventanaLogin.setVisible(true);
    }
}
