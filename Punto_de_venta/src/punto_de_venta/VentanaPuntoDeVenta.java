package punto_de_venta;

// Importación de bibliotecas necesarias para la interfaz gráfica y funcionalidades
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.Border;
import java.util.HashMap;
import java.util.Map;

// Clase principal de la ventana de punto de venta
public class VentanaPuntoDeVenta {
    // Modelo de lista para los productos
    static DefaultListModel<String> modeloListaProductos = new DefaultListModel<>();
    // Mapa para almacenar la cantidad de cada producto
    static Map<String, Integer> cantidadesProductos = new HashMap<>();

    // Método para crear y mostrar la ventana de punto de venta
    public static void WindowPuntoDeVenta() {
        JFrame ventanaVenta = new JFrame("Ventana de venta"); // Crear la ventana
        ventanaVenta.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE); // Cerrar la ventana sin terminar el programa
        ventanaVenta.setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximizar la ventana
        ventanaVenta.setResizable(false); // Hacer la ventana no redimensionable

        // Crear y establecer el menú
        ClassMenuWindVenta menu = new ClassMenuWindVenta();
        ventanaVenta.setJMenuBar(menu.crearMenu());

        // Crear y agregar el panel de productos
        JScrollPane panelProductos = new PanelProductosCantidad();
        ventanaVenta.add(panelProductos);

        // Crear y agregar el panel de cantidad de productos
        JScrollPane panelTotalProductoIndiv = new PanelCantProductos();
        ventanaVenta.add(panelTotalProductoIndiv);

        // Etiqueta para la barra de búsqueda
        JLabel busquedaLabel = new JLabel("Busqueda");
        busquedaLabel.setBounds(540, 20, 200, 30); // Establecer la posición y el tamaño
        busquedaLabel.setFont(new Font("Arial", Font.BOLD, 20)); // Establecer la fuente
        ventanaVenta.add(busquedaLabel); // Agregar la etiqueta a la ventana

        // Crear y agregar la barra de búsqueda
        BarraBusqueda barraBusqueda = new BarraBusqueda();
        ventanaVenta.add(barraBusqueda.crearBarra());

        // Crear y agregar el botón de pagar
        ButtonPagar botonPagar = new ButtonPagar(ventanaVenta);
        ventanaVenta.add(botonPagar.crearBotonPagar());

        // Crear y agregar el panel con el logo
        JPanel panelLogo = new ImagenPanel("src/imagenes/Logo_Pollo de montaña.jpg");
        panelLogo.setBounds(1400, -10, 100, 130);
        panelLogo.setLayout(null); // Sin disposición para que se coloque en la posición especificada
        ventanaVenta.add(panelLogo);

        // Crear y agregar el spinner para la cantidad de productos
        CantidadSpinner cantidadSpinner = new CantidadSpinner((PanelProductosCantidad) panelProductos);
        ventanaVenta.add(cantidadSpinner.crearSpinnerCantidad());

        // Crear y agregar el botón de cancelar
        ButtonCancelar botonCancelar = new ButtonCancelar(ventanaVenta);
        ventanaVenta.add(botonCancelar.crearBotonCancelar());

        // Crear y agregar el área para mostrar el total
        AreaMostrarTotal totalArea = new AreaMostrarTotal();
        ventanaVenta.add(totalArea.crearAreaTotal());

        ventanaVenta.setLayout(null); // Sin disposición para que se puedan establecer posiciones absolutas
        ventanaVenta.setVisible(true); // Hacer visible la ventana
    }

    // Clase para manejar la lista de productos
    public static class PanelProductosCantidad extends JScrollPane {
        private final JList<String> listaProductos; // Lista de productos
        private int selectedIndex = -1; // Índice del producto seleccionado

        public PanelProductosCantidad() {
            // Inicializar la lista de productos
            listaProductos = new JList<>(modeloListaProductos);
            listaProductos.setFont(new Font("Arial", Font.BOLD, 20)); // Establecer la fuente de la lista
            this.setViewportView(listaProductos); // Establecer la vista de la lista en el JScrollPane
            this.setBounds(270, 100, 950, 600); // Establecer posición y tamaño del panel

            // Añadir un listener para detectar cambios de selección en la lista
            listaProductos.addListSelectionListener(e -> {
                if (!e.getValueIsAdjusting()) { // Solo si la selección ha terminado
                    selectedIndex = listaProductos.getSelectedIndex(); // Obtener el índice seleccionado
                    if (selectedIndex != -1) { // Si hay un elemento seleccionado
                        String producto = modeloListaProductos.getElementAt(selectedIndex).split(" ")[0]; // Obtener el nombre del producto
                        int cantidadActual = cantidadesProductos.getOrDefault(producto, 1); // Obtener la cantidad actual del producto
                        CantidadSpinner.actualizarValorSpinner(cantidadActual); // Actualizar el valor del spinner
                    }
                }
            });

            // Agregar un KeyListener para detectar la tecla "Delete"
            listaProductos.addKeyListener(new java.awt.event.KeyAdapter() {
                @Override
                public void keyPressed(java.awt.event.KeyEvent e) {
                    // Verificar si se ha presionado la tecla "Delete"
                    if (e.getKeyCode() == java.awt.event.KeyEvent.KEY_EVENT_MASK) { // Cambiado a KEY_EVENT_MASK para la detección correcta
                        int selectedIndex = listaProductos.getSelectedIndex(); // Obtener el índice seleccionado
                        if (selectedIndex != -1) { // Si hay un elemento seleccionado
                            // Mostrar un cuadro de diálogo de confirmación
                            int confirm = JOptionPane.showConfirmDialog(
                                null,
                                "¿Deseas eliminar el producto seleccionado?", // Mensaje de confirmación
                                "Confirmar eliminación", // Título del cuadro de confirmación
                                JOptionPane.YES_NO_OPTION // Opciones de "Sí" y "No"
                            );
                            // Si el usuario confirma, eliminar el producto de la lista
                            if (confirm == JOptionPane.YES_OPTION) {
                                String producto = modeloListaProductos.getElementAt(selectedIndex).split(" ")[0]; // Obtener el nombre del producto
                                cantidadesProductos.remove(producto); // Eliminar el producto del mapa
                                modeloListaProductos.remove(selectedIndex); // Eliminar el elemento seleccionado de la lista
                            }
                        }
                    }
                }
            });
        }

        // Método para obtener el índice del producto seleccionado
        public int getSelectedIndex() {
            return selectedIndex;
        }

        // Método para eliminar el producto seleccionado
        public void eliminarProductoSeleccionado() {
            if (selectedIndex != -1) { // Si hay un elemento seleccionado
                String producto = modeloListaProductos.getElementAt(selectedIndex).split(" ")[0]; // Obtener el nombre del producto
                cantidadesProductos.remove(producto); // Eliminar el producto del mapa
                modeloListaProductos.remove(selectedIndex); // Eliminar el producto de la lista
            } else {
                JOptionPane.showMessageDialog(null, "Selecciona un producto para eliminar"); // Mensaje si no hay selección
            }
        }
    }

    // Clase para manejar el spinner de cantidades
    public static class CantidadSpinner {
        private final PanelProductosCantidad panelProductos; // Referencia al panel de productos
        private static JSpinner spinnerCantidad; // Spinner para la cantidad

        // Constructor que recibe el panel de productos
        public CantidadSpinner(PanelProductosCantidad panelProductos) {
            this.panelProductos = panelProductos; // Inicializa la referencia
        }

        // Método para crear el spinner de cantidad
        public JSpinner crearSpinnerCantidad() {
            // Configuración del modelo del spinner
            SpinnerModel model = new SpinnerNumberModel(1, 1, 100, 1);
            spinnerCantidad = new JSpinner(model); // Crear el spinner con el modelo
            spinnerCantidad.setBounds(900, 20, 50, 30); // Establecer posición y tamaño del spinner

            // Añadir un listener para detectar cambios en el spinner
            spinnerCantidad.addChangeListener(e -> {
                int selectedIndex = panelProductos.getSelectedIndex(); // Obtener el índice del producto seleccionado
                if (selectedIndex != -1) { // Si hay un elemento seleccionado
                    String producto = modeloListaProductos.getElementAt(selectedIndex).split(" ")[0]; // Obtener el nombre del producto
                    int nuevaCantidad = (Integer) spinnerCantidad.getValue(); // Obtener la nueva cantidad
                    cantidadesProductos.put(producto, nuevaCantidad); // Actualizar la cantidad en el mapa
                    modeloListaProductos.set(selectedIndex, producto + " (Cantidad: " + nuevaCantidad + ")"); // Actualizar la lista con la nueva cantidad
                }
            });

            return spinnerCantidad; // Devolver el spinner creado
        }

        // Método para actualizar el valor del spinner
        public static void actualizarValorSpinner(int cantidad) {
            if (spinnerCantidad != null) { // Si el spinner ha sido creado
                spinnerCantidad.setValue(cantidad); // Establecer el valor del spinner
            }
        }
    }

    // Clase para manejar la barra de búsqueda
// Clase para manejar la barra de búsqueda
public static class BarraBusqueda {
    public JTextField crearBarra() {
        JTextField barraBusqueda = new JTextField(); // Crear el campo de texto para la búsqueda
        barraBusqueda.setBounds(650, 20, 200, 30); // Establecer posición y tamaño del campo
        
        // Añadir un listener para detectar la acción de entrada de texto
        barraBusqueda.addActionListener(e -> {
            String producto = barraBusqueda.getText().trim(); // Obtener el texto ingresado y eliminar espacios en blanco
            if (!producto.isEmpty()) { // Si el campo no está vacío
                // Verificar si el producto ya existe en la lista
                boolean existe = false;
                for (int i = 0; i < modeloListaProductos.size(); i++) {
                    if (modeloListaProductos.getElementAt(i).contains(producto)) {
                        existe = true;
                        break;
                    }
                }
                
                if (existe) {
                    JOptionPane.showMessageDialog(null, "El producto ya existe en la lista", "Error", JOptionPane.WARNING_MESSAGE); // Mostrar mensaje de error
                } else {
                    // Solicitar la cantidad deseada al usuario
                    String cantidadStr = JOptionPane.showInputDialog(null, "Ingresa la cantidad para el producto:");
                    try {
                        int cantidad = Integer.parseInt(cantidadStr); // Convertir la entrada a entero
                        
                        if (cantidad > 0) { // Verificar que la cantidad sea mayor a 0
                            // Agregar el producto con la cantidad especificada
                            modeloListaProductos.addElement(producto + " (Cantidad: " + cantidad + ")"); // Agregar producto a la lista
                            cantidadesProductos.put(producto, cantidad); // Guardar la cantidad en el mapa
                            barraBusqueda.setText(""); // Limpiar el campo de búsqueda
                        } else {
                            JOptionPane.showMessageDialog(null, "La cantidad debe ser mayor a 0", "Error", JOptionPane.ERROR_MESSAGE); // Mensaje de error si la cantidad es inválida
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Cantidad no válida", "Error", JOptionPane.ERROR_MESSAGE); // Mensaje de error si la entrada no es un número
                    }
                }
            }
        });
        return barraBusqueda; // Devolver el campo de búsqueda creado
    }
}


    // Clase para manejar el panel de cantidad de productos
    public static class PanelCantProductos extends JScrollPane {
        private final JList<String> listaProducts; // Lista para mostrar los productos

        // Constructor para inicializar el panel
        public PanelCantProductos() {
            // Inicializar la lista de productos utilizando el modelo compartido
            listaProducts = new JList<>(VentanaPuntoDeVenta.modeloListaProductos);
            listaProducts.setFont(new Font("Arial", Font.BOLD, 16)); // Establecer la fuente de la lista
            this.setViewportView(listaProducts); // Agregar la lista al JScrollPane
            this.setBounds(1230, 100, 300, 600); // Establecer posición y tamaño del panel
            this.setBackground(Color.WHITE); // Establecer color de fondo
        }
    
        // Método opcional para actualizar la lista si es necesario
        public void actualizarLista() {
            listaProducts.setListData((String[]) VentanaPuntoDeVenta.modeloListaProductos.toArray());
        }
    }

    // Clase para manejar el botón de pagar
    public static class ButtonPagar {
        private final JFrame ventanaVenta; // Referencia a la ventana de venta

        public ButtonPagar(JFrame ventanaVenta) {
            this.ventanaVenta = ventanaVenta; // Inicializar la referencia
        }

        // Método para crear el botón de pagar
        public JButton crearBotonPagar() {
            JButton pagarButton = new JButton("Pagar"); // Crear el botón
            pagarButton.setBounds(1120, 720, 100, 30); // Establecer posición y tamaño del botón

            // Añadir un listener para detectar la acción de pago
            pagarButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Verificar si la lista de productos está vacía
                    if (modeloListaProductos.isEmpty()) {
                        JOptionPane.showMessageDialog(ventanaVenta, "Debes añadir al menos un producto a la lista"); // Mensaje de advertencia
                    } else {
                        VentanaPagar.WindowPagar(ventanaVenta); // Abrir la ventana de pago
                    }
                }
            });

            return pagarButton; // Devolver el botón creado
        }
    }

    // Clase para manejar el panel que contiene una imagen
    static class ImagenPanel extends JPanel {
        private final Image imagen; // Variable para almacenar la imagen

        public ImagenPanel(String rutaImagen) {
            this.imagen = new ImageIcon(rutaImagen).getImage(); // Cargar la imagen desde la ruta
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g); // Llamar al método de la clase padre
            g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this); // Dibujar la imagen en el panel
        }
    }

    // Clase para manejar el botón de cancelar
    public static class ButtonCancelar {
        private final JFrame ventanaVenta; // Referencia a la ventana de venta
        
        
        public ButtonCancelar(JFrame ventanaVenta) {
            this.ventanaVenta = ventanaVenta; // Inicializar la referencia
        }

        // Método para crear el botón de cancelar
        public JButton crearBotonCancelar() {
            JButton cancelarButton = new JButton("Cancelar"); // Crear el botón
            cancelarButton.setBounds(1000, 720, 100, 30); // Establecer posición y tamaño del botón

            // Añadir un listener para detectar la acción de cancelar
            cancelarButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Verificar si la lista de productos está vacía
                    if (modeloListaProductos.isEmpty()) {
                        JOptionPane.showMessageDialog(ventanaVenta, "Debes añadir al menos un producto a la lista"); // Mensaje de advertencia
                    } else {
                        modeloListaProductos.clear(); // Limpiar la lista de productos
                    }
                }
            });

            return cancelarButton; // Devolver el botón creado
        }
    }

    // Clase para mostrar el área total
    public static class AreaMostrarTotal {
        // Método para crear el área total
        public JTextArea crearAreaTotal() {
            JTextArea areaTotal = new JTextArea(); // Crear el área de texto
            areaTotal.setBounds(1300, 705, 150, 100); // Establecer posición y tamaño del área
            areaTotal.setBackground(Color.WHITE); // Establecer color de fondo
            Border border = BorderFactory.createLineBorder(Color.BLACK, 1); // Crear un borde
            areaTotal.setBorder(border); // Establecer el borde al área
            return areaTotal; // Devolver el área total creada
        }
    }
}
