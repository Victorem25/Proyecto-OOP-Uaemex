package punto_de_venta;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class VentanaInventario {

    private JLabel etiquetaFiltroSeleccionado;
    private DefaultTableModel modeloTabla;
    private List<Object[]> productosFiltrados;
    private List<Object[]> productos;

    public VentanaInventario() {
        WindowInventario();
        cargarProductos();
    }

    public void WindowInventario() {
        JFrame Ventana_de_Inventario = new JFrame("Ventana de inventario");
        Ventana_de_Inventario.setExtendedState(JFrame.MAXIMIZED_BOTH);

        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout());

        JPanel panelBuscador = new JPanel();
        panelBuscador.setLayout(new FlowLayout());

        JTextField campoBusqueda = new JTextField(40);
        panelBuscador.add(campoBusqueda);

        etiquetaFiltroSeleccionado = new JLabel("Filtro seleccionado: Ninguno");
        panelBuscador.add(etiquetaFiltroSeleccionado);

        JButton botonFiltros = new JButton("Filtros");
        panelBuscador.add(botonFiltros);

        JButton botonBuscar = new JButton("Buscar");
        panelBuscador.add(botonBuscar);

        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("Imagen");
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Código");
        modeloTabla.addColumn("Precio");
        modeloTabla.addColumn("Proveedor");
        modeloTabla.addColumn("Caducidad");
        modeloTabla.addColumn("Stock");

        JTable tablaResultados = new JTable(modeloTabla);
        tablaResultados.getColumnModel().getColumn(0).setCellRenderer(new ImageRenderer());

        // Establecer la altura de las filas
        tablaResultados.setRowHeight(100); // Ajusta el valor a la altura que desees

        // Establecer el ancho preferido de las columnas
        tablaResultados.getColumnModel().getColumn(0).setPreferredWidth(100); // Imagen
        tablaResultados.getColumnModel().getColumn(1).setPreferredWidth(200); // Nombre
        tablaResultados.getColumnModel().getColumn(2).setPreferredWidth(100); // Código
        tablaResultados.getColumnModel().getColumn(3).setPreferredWidth(100); // Precio
        tablaResultados.getColumnModel().getColumn(4).setPreferredWidth(150); // Proveedor
        tablaResultados.getColumnModel().getColumn(5).setPreferredWidth(100); // Caducidad
        tablaResultados.getColumnModel().getColumn(6).setPreferredWidth(100); // Stock

        JScrollPane scrollPane = new JScrollPane(tablaResultados);
        scrollPane.setBounds(50, 50, 700, 400);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(60, 60, 60, 60));

        panelPrincipal.add(panelBuscador, BorderLayout.NORTH);
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);

        Ventana_de_Inventario.add(panelPrincipal, BorderLayout.CENTER);

        JPopupMenu menuFiltros = new JPopupMenu();
        JMenuItem filtroProveedor = new JMenuItem("Filtrar por Proveedor");
        JMenuItem filtroExistencias = new JMenuItem("Filtrar por Existencias");
        JMenuItem filtroPrecio = new JMenuItem("Filtrar por Precio");

        menuFiltros.add(filtroProveedor);
        menuFiltros.add(filtroExistencias);
        menuFiltros.add(filtroPrecio);

        JPopupMenu subMenuProveedor = new JPopupMenu();
        String[] proveedores = {"Proveedor A", "Proveedor B", "Proveedor C"};
        for (String proveedor : proveedores) {
            JMenuItem itemProveedor = new JMenuItem(proveedor);
            itemProveedor.addActionListener(e -> {
                etiquetaFiltroSeleccionado.setText("Filtro: Proveedor - " + proveedor);
                productosFiltrados = filtrarDatos("proveedor", proveedor);
                actualizarTabla();
            });
            subMenuProveedor.add(itemProveedor);
        }
        filtroProveedor.addActionListener(e -> subMenuProveedor.show(botonFiltros, 0, botonFiltros.getHeight()));

        JPopupMenu subMenuExistencias = new JPopupMenu();
        String[] existencias = {"Menos de 10", "10 a 50", "Más de 50"};
        for (String existencia : existencias) {
            JMenuItem itemExistencia = new JMenuItem(existencia);
            itemExistencia.addActionListener(e -> {
                etiquetaFiltroSeleccionado.setText("Filtro: Existencias - " + existencia);
                productosFiltrados = filtrarDatos("existencias", existencia);
                actualizarTabla();
            });
            subMenuExistencias.add(itemExistencia);
        }
        filtroExistencias.addActionListener(e -> subMenuExistencias.show(botonFiltros, 0, botonFiltros.getHeight()));

        filtroPrecio.addActionListener(e -> {
            String valorFiltro = JOptionPane.showInputDialog(null, "Introduce el rango de precios (min y max) separados por coma:");
            if (valorFiltro != null && valorFiltro.matches("\\d+(\\.\\d+)?,\\d+(\\.\\d+)?")) {
                etiquetaFiltroSeleccionado.setText("Filtro: Precio - " + valorFiltro);
                productosFiltrados = filtrarDatos("precio", valorFiltro);
                actualizarTabla();
            } else {
                JOptionPane.showMessageDialog(null, "Formato incorrecto. Debe ingresar dos números separados por coma.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            }
        });

        botonFiltros.addActionListener(e -> menuFiltros.show(botonFiltros, 0, botonFiltros.getHeight()));

        botonBuscar.addActionListener(e -> {
            String busqueda = campoBusqueda.getText();
            productosFiltrados = buscarProductos(busqueda);
            if (!productosFiltrados.isEmpty()) {
                modeloTabla.setRowCount(0);
                for (Object[] producto : productosFiltrados) {
                    modeloTabla.addRow(producto);
                }
            } else {
                JOptionPane.showMessageDialog(null, "No se encontraron productos.", "Resultado de búsqueda", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        Ventana_de_Inventario.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Ventana_de_Inventario.setSize(800, 600);
        Ventana_de_Inventario.setVisible(true);
    }

    private void cargarProductos() {
        productos = new ArrayList<>();
        try (InputStream is = getClass().getResourceAsStream("/productos.txt");
             BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            if (is == null) {
                System.out.println("Archivo 'productos.txt' no encontrado en la ruta especificada.");
                return;
            }
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                ImageIcon imagen;
                URL imageUrl = getClass().getResource("/imagenes/" + datos[0]); // Asegúrate de que haya una barra (/) aquí
                if (imageUrl != null) {
                    imagen = new ImageIcon(imageUrl);
                } else {
                    System.out.println("Imagen no encontrada para el producto: " + datos[0]);
                    imagen = new ImageIcon(getClass().getResource("/imagenes/producto1.png"));
                }

                Object[] producto = new Object[datos.length];
                producto[0] = imagen;
                System.arraycopy(datos, 1, producto, 1, datos.length - 1);
                productos.add(producto);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println("Archivo 'productos.txt' o imágenes faltantes.");
        }
        productosFiltrados = new ArrayList<>(productos);
        actualizarTabla();
    }

    private List<Object[]> buscarProductos(String busqueda) {
        List<Object[]> resultados = new ArrayList<>();
        for (Object[] producto : productos) {
            for (Object dato : producto) {
                if (dato.toString().toLowerCase().contains(busqueda.toLowerCase())) {
                    resultados.add(producto);
                    break;
                }
            }
        }
        return resultados;
    }

    private List<Object[]> filtrarDatos(String tipoFiltro, String valorFiltro) {
        List<Object[]> productosFiltrados = new ArrayList<>();
        for (Object[] producto : productos) {
            switch (tipoFiltro) {
                case "proveedor":
                    if (producto[4].toString().equals(valorFiltro)) {
                        productosFiltrados.add(producto);
                    }
                    break;
                case "existencias":
                    int stock = Integer.parseInt(producto[6].toString());
                    if (valorFiltro.equals("Menos de 10") && stock < 10 ||
                            valorFiltro.equals("10 a 50") && stock >= 10 && stock <= 50 ||
                            valorFiltro.equals("Más de 50") && stock > 50) {
                        productosFiltrados.add(producto);
                    }
                    break;
                case "precio":
                    String[] rangos = valorFiltro.split(",");
                    double minPrecio = Double.parseDouble(rangos[0].trim());
                    double maxPrecio = Double.parseDouble(rangos[1].trim());
                    double precio = Double.parseDouble(producto[3].toString());
                    if (precio >= minPrecio && precio <= maxPrecio) {
                        productosFiltrados.add(producto);
                    }
                    break;
            }
        }
        return productosFiltrados;
    }

    private void actualizarTabla() {
        modeloTabla.setRowCount(0);
        for (Object[] producto : productosFiltrados) {
            modeloTabla.addRow(producto);
        }
    }

    // Clase interna para renderizar imágenes en la tabla
    class ImageRenderer implements TableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JLabel label = new JLabel();

            if (value instanceof ImageIcon) {
                ImageIcon icon = (ImageIcon) value;
                Image img = icon.getImage(); // Obtener la imagen original

                // Escalar la imagen al tamaño deseado (ajusta el ancho y alto según sea necesario)
                Image scaledImg = img.getScaledInstance(80, 80, Image.SCALE_SMOOTH); // Cambia 80, 80 por el tamaño deseado

                label.setIcon(new ImageIcon(scaledImg)); // Establecer la imagen escalada
                label.setHorizontalAlignment(JLabel.CENTER);
            }

            return label;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(VentanaInventario::new);
    }
}
