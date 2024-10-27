package punto_de_venta;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.Border;
import java.util.HashMap;
import java.util.Map;

public class VentanaPuntoDeVenta {
    static DefaultListModel<String> modeloListaProductos = new DefaultListModel<>();
    static Map<String, Integer> cantidadesProductos = new HashMap<>();

    public static void WindowPuntoDeVenta() {
        JFrame ventanaVenta = new JFrame("Ventana de venta");
        ventanaVenta.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        ventanaVenta.setExtendedState(JFrame.MAXIMIZED_BOTH);
        ventanaVenta.setResizable(false);

        ClassMenuWindVenta menu = new ClassMenuWindVenta();
        ventanaVenta.setJMenuBar(menu.crearMenu());

        JScrollPane panelProductos = new PanelProductosCantidad();
        ventanaVenta.add(panelProductos);

        JScrollPane panelTotalProductoIndiv = new PanelCantProductos();
        ventanaVenta.add(panelTotalProductoIndiv);

        JLabel busquedaLabel = new JLabel("Busqueda");
        busquedaLabel.setBounds(540, 20, 200, 30);
        busquedaLabel.setFont(new Font("Arial", Font.BOLD, 20));
        ventanaVenta.add(busquedaLabel);

        BarraBusqueda barraBusqueda = new BarraBusqueda();
        ventanaVenta.add(barraBusqueda.crearBarra());

        ButtonPagar botonPagar = new ButtonPagar(ventanaVenta);
        ventanaVenta.add(botonPagar.crearBotonPagar());

        JPanel panelLogo = new ImagenPanel("src/imagenes/Logo_Pollo de montaña.jpg");
        panelLogo.setBounds(1400, -10, 100, 130);
        panelLogo.setLayout(null);
        ventanaVenta.add(panelLogo);

        CantidadSpinner cantidadSpinner = new CantidadSpinner((PanelProductosCantidad) panelProductos);
        ventanaVenta.add(cantidadSpinner.crearSpinnerCantidad());

        ButtonCancelar botonCancelar = new ButtonCancelar(ventanaVenta);
        ventanaVenta.add(botonCancelar.crearBotonCancelar());


        AreaMostrarTotal totalArea = new AreaMostrarTotal();
        ventanaVenta.add(totalArea.crearAreaTotal());

        ventanaVenta.setLayout(null);
        ventanaVenta.setVisible(true);
    }

    // Modificación en PanelProductosCantidad
  public static class PanelProductosCantidad extends JScrollPane {
    private final JList<String> listaProductos;
    private int selectedIndex = -1;

    public PanelProductosCantidad() {
        listaProductos = new JList<>(modeloListaProductos);
        listaProductos.setFont(new Font("Arial", Font.BOLD, 20));
        this.setViewportView(listaProductos);
        this.setBounds(270, 100, 900, 600);

        listaProductos.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                selectedIndex = listaProductos.getSelectedIndex();
                if (selectedIndex != -1) {
                    String producto = modeloListaProductos.getElementAt(selectedIndex).split(" ")[0];
                    int cantidadActual = cantidadesProductos.getOrDefault(producto, 1);
                    CantidadSpinner.actualizarValorSpinner(cantidadActual);
                }
            }
        });

        // Agrega un KeyListener para detectar cuando se presiona la tecla "Delete"
        listaProductos.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                // Verifica si se ha presionado la tecla "Delete"
                if (e.getKeyCode() == java.awt.event.KeyEvent.KEY_EVENT_MASK) {
                    int selectedIndex = listaProductos.getSelectedIndex(); // Obtiene el índice del elemento seleccionado
                    // Si hay un elemento seleccionado, continúa
                    if (selectedIndex != -1) {
                        // Muestra un cuadro de diálogo de confirmación para preguntar si se desea eliminar el producto seleccionado
                        int confirm = JOptionPane.showConfirmDialog(
                            null,
                            "¿Deseas eliminar el producto seleccionado?", // Mensaje del cuadro de confirmación
                            "Confirmar eliminación", // Título del cuadro de confirmación
                            JOptionPane.YES_NO_OPTION // Opciones "Sí" y "No"
                        );
                        // Si el usuario confirma la eliminación, se elimina el producto de la lista
                        if (confirm == JOptionPane.YES_OPTION) {
                            String producto = modeloListaProductos.getElementAt(selectedIndex).split(" ")[0];
                            cantidadesProductos.remove(producto); // Elimina el producto del mapa
                            modeloListaProductos.remove(selectedIndex); // Elimina el elemento seleccionado del modelo de lista
                        }
                    }
                }
            }
        });
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }

    // Método para eliminar el producto seleccionado
    public void eliminarProductoSeleccionado() {
        if (selectedIndex != -1) {
            String producto = modeloListaProductos.getElementAt(selectedIndex).split(" ")[0];
            cantidadesProductos.remove(producto); // Eliminar el producto del mapa
            modeloListaProductos.remove(selectedIndex); // Eliminar el producto de la lista
        } else {
            JOptionPane.showMessageDialog(null, "Selecciona un producto para eliminar");
        }
    }
}


    // Modificación en CantidadSpinner
    public static class CantidadSpinner {
        private final PanelProductosCantidad panelProductos;
        private static JSpinner spinnerCantidad;

        public CantidadSpinner(PanelProductosCantidad panelProductos) {
            this.panelProductos = panelProductos;
        }

        public JSpinner crearSpinnerCantidad() {
            SpinnerModel model = new SpinnerNumberModel(1, 1, 100, 1);
            spinnerCantidad = new JSpinner(model);
            spinnerCantidad.setBounds(900, 20, 50, 30);

            spinnerCantidad.addChangeListener(e -> {
                int selectedIndex = panelProductos.getSelectedIndex();
                if (selectedIndex != -1) {
                    String producto = modeloListaProductos.getElementAt(selectedIndex).split(" ")[0];
                    int nuevaCantidad = (Integer) spinnerCantidad.getValue();
                    cantidadesProductos.put(producto, nuevaCantidad);
                    modeloListaProductos.set(selectedIndex, producto + " (Cantidad: " + nuevaCantidad + ")");
                }
            });

            return spinnerCantidad;
        }

        public static void actualizarValorSpinner(int cantidad) {
            if (spinnerCantidad != null) {
                spinnerCantidad.setValue(cantidad);
            }
        }
    }

public static class BarraBusqueda {
    public JTextField crearBarra() {
        JTextField barraBusqueda = new JTextField();
        barraBusqueda.setBounds(650, 20, 200, 30);

        barraBusqueda.addActionListener(e -> {
            String producto = barraBusqueda.getText();
            if (!producto.isEmpty()) {
                // Solicita la cantidad deseada al usuario
                String cantidadStr = JOptionPane.showInputDialog(null, "Ingresa la cantidad para el producto:");
                try {
                    int cantidad = Integer.parseInt(cantidadStr);
                    
                    if (cantidad > 0) {
                        // Agrega el producto con la cantidad especificada
                        modeloListaProductos.addElement(producto + " (Cantidad: " + cantidad + ")");
                        cantidadesProductos.put(producto, cantidad);
                        barraBusqueda.setText("");
                    } else {
                        JOptionPane.showMessageDialog(null, "La cantidad debe ser mayor a 0", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Cantidad no válida", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        return barraBusqueda;
    }
}

    public static class PanelCantProductos extends JScrollPane {
        private final JPanel panelCantTotalproducto;

        public PanelCantProductos() {
            panelCantTotalproducto = new JPanel();
            panelCantTotalproducto.setLayout(new BorderLayout());
            panelCantTotalproducto.setBackground(Color.LIGHT_GRAY);
            this.setViewportView(panelCantTotalproducto);
            this.setBounds(1230, 100, 300, 600);
        }
    }

    public static class ButtonPagar {
        private final JFrame ventanaVenta;

        public ButtonPagar(JFrame ventanaVenta) {
            this.ventanaVenta = ventanaVenta;
        }

        public JButton crearBotonPagar() {
            JButton pagarButton = new JButton("Pagar");
            pagarButton.setBounds(1120, 720, 100, 30);

            pagarButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (modeloListaProductos.isEmpty()) {
                        JOptionPane.showMessageDialog(ventanaVenta, "Debes añadir al menos un producto a la lista");
                    } else {
                        VentanaPagar.WindowPagar(ventanaVenta);
                    }
                }
            });

            return pagarButton;
        }
    }

    static class ImagenPanel extends JPanel {
        private final Image imagen;

        public ImagenPanel(String rutaImagen) {
            this.imagen = new ImageIcon(rutaImagen).getImage();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
        }
    }

    public static class ButtonCancelar {
        private final JFrame ventanaVenta;

        public ButtonCancelar(JFrame ventanaVenta) {
            this.ventanaVenta = ventanaVenta;
        }

        public JButton crearBotonCancelar() {
            JButton cancelarButton = new JButton("Cancelar");
            cancelarButton.setBounds(1000, 720, 100, 30);

            cancelarButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (modeloListaProductos.isEmpty()) {
                        JOptionPane.showMessageDialog(ventanaVenta, "Debes añadir al menos un producto a la lista");
                    } else {
                        modeloListaProductos.clear();
                    }
                }
            });

            return cancelarButton;
        }
    }


    public static class AreaMostrarTotal {
        public JTextArea crearAreaTotal() {
            JTextArea areaTotal = new JTextArea();
            areaTotal.setBounds(1300, 705, 150, 100);
            areaTotal.setBackground(Color.WHITE);
            Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
            areaTotal.setBorder(border);
            return areaTotal;
        }
    }
}