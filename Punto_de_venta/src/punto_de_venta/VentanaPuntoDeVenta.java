package punto_de_venta;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaPuntoDeVenta {
    private static DefaultListModel<String> modeloListaProductos; // Modelo para almacenar los productos en la lista

    public static void WindowPuntoDeVenta() {
        // Crear una nueva ventana JFrame con el título "Ventana de venta"
        JFrame ventanaVenta = new JFrame("Ventana de venta");
        ventanaVenta.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Cerrar la aplicación al cerrar la ventana
        ventanaVenta.setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximizar la ventana en toda la pantalla
        ventanaVenta.setResizable(false); // Deshabilitar la redimensión de la ventana

        // Crear el menú para la ventana de venta usando ClassMenuWindVenta
        ClassMenuWindVenta menu = new ClassMenuWindVenta();
        ventanaVenta.setJMenuBar(menu.crearMenu()); // Establecer el menú en la ventana

        // Crear y agregar el panel de productos y cantidad
        JScrollPane panelProductos = new PanelProductosCantidad(); // Panel para mostrar los productos agregados
        ventanaVenta.add(panelProductos);

        // Crear y agregar el panel para mostrar el total por cada producto
        JScrollPane panelTotalProductoIndiv = new PanelCantProductos();
        ventanaVenta.add(panelTotalProductoIndiv);

        // Crear y agregar una etiqueta "Busqueda" al lado de la barra de búsqueda
        JLabel busquedaLabel = new JLabel("Busqueda");
        busquedaLabel.setBounds(540, 20, 200, 30);
        busquedaLabel.setFont(new Font("Arial", Font.BOLD, 20));
        ventanaVenta.add(busquedaLabel);

        // Crear la barra de búsqueda para agregar productos
        BarraBusqueda barraBusqueda = new BarraBusqueda();
        ventanaVenta.add(barraBusqueda.crearBarra());

        // Crear y agregar el botón "Pagar" a la ventana
        ButtonPagar botonPagar = new ButtonPagar();
        ventanaVenta.add(botonPagar.crearBotonPagar());

        // Crear y agregar un panel para mostrar el logo
        JPanel panelLogo = new ImagenPanel("src/imagenes/Logo_Pollo de montaña.jpg");
        panelLogo.setBounds(1400, -10, 100, 130);
        panelLogo.setLayout(null);
        ventanaVenta.add(panelLogo);
        
        // Crear y agregar el spinner para seleccionar la cantidad de productos
        CantidadSpinner cantidadSpinner = new CantidadSpinner();
        ventanaVenta.add(cantidadSpinner.crearSpinnerCantidad());
        
        // Crear y agregar el botón "Cancelar" a la ventana
        ButtonCancelar botonCancelar = new ButtonCancelar();
        ventanaVenta.add(botonCancelar.crearBotonCancelar());

        // Establecer el layout de la ventana como null para posicionar componentes manualmente
        ventanaVenta.setLayout(null);

        // Hacer visible la ventana con todos los componentes agregados
        ventanaVenta.setVisible(true);
    }

    // Clase interna que define el panel para la lista de productos y cantidades
    public static class PanelProductosCantidad extends JScrollPane {
        private final JList<String> listaProductos; // JList para mostrar los productos agregados

        public PanelProductosCantidad() {
            // Inicializar el modelo de lista para almacenar productos
            modeloListaProductos = new DefaultListModel<>();
            // Crear un JList para mostrar los productos y asignarle el modelo
            listaProductos = new JList<>(modeloListaProductos);

            // Agregar el JList al JScrollPane para permitir desplazamiento
            this.setViewportView(listaProductos);
            this.setBounds(20, 100, 1200, 600); // Posición y tamaño del JScrollPane
        }
    }

    // Clase interna que define el panel para mostrar el total por cada producto
    public static class PanelCantProductos extends JScrollPane {
        private final JPanel panelCantTotalproducto; // Panel donde se mostrará el total por producto

        public PanelCantProductos() {
            // Crear el JPanel para el contenido
            panelCantTotalproducto = new JPanel();
            panelCantTotalproducto.setLayout(new BorderLayout());
            panelCantTotalproducto.setBackground(Color.LIGHT_GRAY);

            // Establecer el JPanel en el JScrollPane y definir su tamaño y posición
            this.setViewportView(panelCantTotalproducto);
            this.setBounds(1230, 100, 300, 600);
        }
    }

    // Clase interna para la barra de búsqueda donde se ingresarán los productos
    public static class BarraBusqueda {
        public JTextField crearBarra() {
            JTextField barraBusqueda = new JTextField(); // Campo de texto para buscar productos
            barraBusqueda.setColumns(20); // Configurar el tamaño del campo
            barraBusqueda.setBounds(650, 20, 200, 30); // Posición y tamaño del campo

            // Agregar ActionListener para añadir productos a la lista cuando se presiona Enter
            barraBusqueda.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String producto = barraBusqueda.getText(); // Obtener el texto ingresado
                    if (!producto.isEmpty()) { // Verificar que no esté vacío
                        modeloListaProductos.addElement(producto); // Agregar el producto al modelo de lista
                        barraBusqueda.setText(""); // Limpiar el campo de búsqueda después de agregar
                    }
                }
            });

            return barraBusqueda;
        }
    }

    // Clase interna para el botón de pagar
    public static class ButtonPagar {
        public JButton crearBotonPagar() {
            JButton pagarButton = new JButton("Pagar"); // Crear el botón "Pagar"
            pagarButton.setBounds(1120, 720, 100, 30); // Posición y tamaño del botón

            // Agregar ActionListener para abrir la ventana de pago cuando se presione el botón
            pagarButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    VentanaPagar.WindowPagar(); // Llamar a la ventana de pago
                }
            });

            return pagarButton;
        }
    }

    // Clase interna para mostrar una imagen en un JPanel
    static class ImagenPanel extends JPanel {
        private final Image imagen; // Imagen a mostrar

        // Constructor que carga la imagen desde la ruta especificada
        public ImagenPanel(String rutaImagen) {
            this.imagen = new ImageIcon(rutaImagen).getImage();
        }

        // Sobrescribir el método paintComponent para dibujar la imagen en el JPanel
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
        }
    }
    
    // Clase interna para el spinner de selección de cantidad
    public static class CantidadSpinner {
        public JSpinner crearSpinnerCantidad() {
            // Modelo del spinner: valor inicial 1, mínimo 1, máximo 100, incremento de 1
            SpinnerModel model = new SpinnerNumberModel(1, 1, 100, 1);
            JSpinner spinnerCantidad = new JSpinner(model);
            spinnerCantidad.setBounds(900, 20, 50, 30); // Posición y tamaño del spinner

            return spinnerCantidad;
        }
}
    
    // Clase interna para el botón de cancelar
    public static class ButtonCancelar {
        public JButton crearBotonCancelar() {
            JButton cancelarButton = new JButton("Cancelar"); // Crear el botón "Cancelar"
            cancelarButton.setBounds(1000, 720, 100, 30); // Posición y tamaño del botón

            // Agregar ActionListener para limpiar la lista de productos al presionar el botón
            cancelarButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    modeloListaProductos.clear(); // Limpiar todos los elementos de la lista
                }
            });

            return cancelarButton;
        }
    }
}
