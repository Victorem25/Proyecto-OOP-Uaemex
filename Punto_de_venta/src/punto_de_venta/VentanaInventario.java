package punto_de_venta; // Paquete donde se encuentra la clase

import javax.swing.*; // Importa las clases necesarias para la creación de interfaces gráficas
import javax.swing.table.DefaultTableModel; // Importa la clase para el modelo de tabla
import java.awt.*; // Importa clases para componentes de interfaz gráfica
import java.awt.event.ActionEvent; // Importa clases para manejar eventos de acción
import java.awt.event.ActionListener; // Importa la interfaz para manejar eventos de acción
import java.io.BufferedReader; // Importa clases para leer datos de archivos
import java.io.IOException; // Importa clases para manejar excepciones de entrada/salida
import java.io.InputStream; // Importa clases para trabajar con flujos de entrada
import java.io.InputStreamReader; // Importa clases para leer flujos de entrada
import java.util.ArrayList; // Importa la clase para trabajar con listas dinámicas
import java.util.List; // Importa la interfaz de listas

public class VentanaInventario { // Clase principal para la ventana de inventario

    // Definición de variables estáticas
    private static JLabel etiquetaFiltroSeleccionado; // Etiqueta para mostrar el filtro seleccionado
    private static DefaultTableModel modeloTabla; // Modelo de la tabla para actualizar
    private static List<String[]> productosFiltrados; // Lista para almacenar productos filtrados

    public static void WindowInventario() { // Método principal para crear la ventana de inventario
        // Crear la ventana con el título "Ventana de inventario"
        JFrame Ventana_de_Inventario = new JFrame("Ventana de inventario");
        Ventana_de_Inventario.setExtendedState(JFrame.MAXIMIZED_BOTH); // Maxima la ventana al tamaño de la pantalla

        // Crear un panel principal para organizar el buscador y el área de resultados
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout()); // Establece un layout de borde para el panel

        // Panel para el buscador
        JPanel panelBuscador = new JPanel();
        panelBuscador.setLayout(new FlowLayout()); // Establece un layout de flujo para el panel de búsqueda

        // Campo de texto para la búsqueda
        JTextField campoBusqueda = new JTextField(40); // Crea un campo de texto con un tamaño de 40 columnas
        panelBuscador.add(campoBusqueda); // Agrega el campo de búsqueda al panel

        // Etiqueta para mostrar el filtro seleccionado
        etiquetaFiltroSeleccionado = new JLabel("Filtro seleccionado: Ninguno"); // Crea una etiqueta inicial
        panelBuscador.add(etiquetaFiltroSeleccionado); // Agrega la etiqueta al panel

        // Botón para abrir el menú de filtros
        JButton botonFiltros = new JButton("Filtros"); // Crea un botón para abrir filtros
        panelBuscador.add(botonFiltros); // Agrega el botón al panel

        // Botón para buscar
        JButton botonBuscar = new JButton("Buscar"); // Crea un botón para buscar productos
        panelBuscador.add(botonBuscar); // Agrega el botón al panel

        // Crear modelo de tabla con 7 columnas
        modeloTabla = new DefaultTableModel(); // Inicializa el modelo de tabla
        modeloTabla.addColumn("Imagen"); // Agrega columna para imagen
        modeloTabla.addColumn("Nombre"); // Agrega columna para nombre
        modeloTabla.addColumn("Código"); // Agrega columna para código
        modeloTabla.addColumn("Precio"); // Agrega columna para precio
        modeloTabla.addColumn("Proveedor"); // Agrega columna para proveedor
        modeloTabla.addColumn("Caducidad"); // Agrega columna para fecha de caducidad
        modeloTabla.addColumn("Stock"); // Agrega columna para stock

        // Crear la tabla para mostrar resultados
        JTable tablaResultados = new JTable(modeloTabla); // Crea una tabla con el modelo definido
        JScrollPane scrollPane = new JScrollPane(tablaResultados); // Agrega un panel de desplazamiento a la tabla
        scrollPane.setBounds(50, 50, 700, 400); // Establece el tamaño del panel de desplazamiento
        scrollPane.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30)); // Establece un borde vacío alrededor del panel

        // Agregar el panel del buscador y el área de resultados al panel principal
        panelPrincipal.add(panelBuscador, BorderLayout.NORTH); // Agrega el panel de búsqueda al norte del panel principal
        panelPrincipal.add(scrollPane, BorderLayout.CENTER); // Agrega el panel de resultados al centro del panel principal

        // Agregar el panel principal a la ventana
        Ventana_de_Inventario.add(panelPrincipal, BorderLayout.CENTER); // Agrega el panel principal al centro de la ventana

        // Crear el menú emergente para filtros
        JPopupMenu menuFiltros = new JPopupMenu(); // Inicializa el menú emergente

        // Agregar opciones al menú de filtros
        JMenuItem filtroProveedor = new JMenuItem("Filtrar por Proveedor"); // Crea opción de menú para filtrar por proveedor
        JMenuItem filtroExistencias = new JMenuItem("Filtrar por Existencias"); // Crea opción de menú para filtrar por existencias
        JMenuItem filtroPrecio = new JMenuItem("Filtrar por Precio"); // Crea opción de menú para filtrar por precio

        menuFiltros.add(filtroProveedor); // Agrega opción de proveedor al menú
        menuFiltros.add(filtroExistencias); // Agrega opción de existencias al menú
        menuFiltros.add(filtroPrecio); // Agrega opción de precio al menú

        // Submenú para filtrar por Proveedor
        JPopupMenu subMenuProveedor = new JPopupMenu(); // Crea un submenú para proveedores
        String[] proveedores = {"Proveedor A", "Proveedor B", "Proveedor C"}; // Lista de proveedores
        for (String proveedor : proveedores) { // Recorre la lista de proveedores
            JMenuItem itemProveedor = new JMenuItem(proveedor); // Crea un ítem de menú para cada proveedor
            itemProveedor.addActionListener(e -> { // Agrega un ActionListener para manejar la selección
                // Actualiza el Nombre con el filtro seleccionado
                etiquetaFiltroSeleccionado.setText("Filtro: Proveedor - " + proveedor);
                // Filtra los datos
                productosFiltrados = filtrarDatos("proveedor", proveedor); // Llama a método para filtrar productos
            });
            subMenuProveedor.add(itemProveedor); // Agrega el ítem al submenú
        }
        filtroProveedor.addActionListener(e -> subMenuProveedor.show(botonFiltros, 0, botonFiltros.getHeight())); // Muestra el submenú al hacer clic en el filtro de proveedor

        // Submenú para filtrar por Existencias
        JPopupMenu subMenuExistencias = new JPopupMenu(); // Crea un submenú para existencias
        String[] existencias = {"Menos de 10", "10 a 50", "Más de 50"}; // Lista de opciones de existencias
        for (String existencia : existencias) { // Recorre la lista de existencias
            JMenuItem itemExistencia = new JMenuItem(existencia); // Crea un ítem de menú para cada opción de existencia
            itemExistencia.addActionListener(e -> { // Agrega un ActionListener para manejar la selección
                // Actualiza la etiqueta con el filtro seleccionado
                etiquetaFiltroSeleccionado.setText("Filtro: Existencias - " + existencia);
                // Filtra los datos
                productosFiltrados = filtrarDatos("existencias", existencia); // Llama a método para filtrar productos
            });
            subMenuExistencias.add(itemExistencia); // Agrega el ítem al submenú
        }
        filtroExistencias.addActionListener(e -> subMenuExistencias.show(botonFiltros, 0, botonFiltros.getHeight())); // Muestra el submenú al hacer clic en el filtro de existencias

        // Acción para filtrar por Precio con verificación del formato
        filtroPrecio.addActionListener(e -> {
            String valorFiltro = JOptionPane.showInputDialog(null, "Introduce el rango de precios (min y max) separados por coma:"); // Solicita un rango de precios al usuario
            if (valorFiltro != null && valorFiltro.matches("\\d+(\\.\\d+)?,\\d+(\\.\\d+)?")) { // Verifica que el formato sea correcto
                etiquetaFiltroSeleccionado.setText("Filtro: Precio - " + valorFiltro); // Actualiza la etiqueta
                productosFiltrados = filtrarDatos("precio", valorFiltro); // Llama a método para filtrar productos
            } else {
                // Muestra un mensaje de error si el formato es incorrecto
                JOptionPane.showMessageDialog(null, "Formato incorrecto. Debe ingresar dos números separados por coma.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Acción del botón de filtros
        botonFiltros.addActionListener(e -> menuFiltros.show(botonFiltros, 0, botonFiltros.getHeight())); // Muestra el menú de filtros al hacer clic en el botón

        // Acción del botón de búsqueda
        botonBuscar.addActionListener(e -> {
            String busqueda = campoBusqueda.getText().trim(); // Obtiene el texto de búsqueda y elimina espacios
            List<String[]> resultados = buscarProductosFiltrados(busqueda); // Llama al metodo para buscar productos filtrados
            actualizarTabla(resultados); // Actualiza la tabla con los resultados encontrados
        });

        // Muestra la ventana en pantalla
        Ventana_de_Inventario.setVisible(true); // Hace visible la ventana de inventario
    }

    // Método para filtrar los datos según el filtro seleccionado
    private static List<String[]> filtrarDatos(String tipoFiltro, String valorFiltro) {
        List<String[]> resultados = new ArrayList<>(); // Crea una lista para almacenar los resultados filtrados

        // Intenta leer el archivo de productos
        try (InputStream inputStream = VentanaInventario.class.getResourceAsStream("/productos.txt"); // Obtiene el flujo de entrada del archivo
             BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) { // Crea un BufferedReader para leer líneas del archivo

            String linea; // Variable para almacenar cada línea del archivo
            while ((linea = br.readLine()) != null) { // Lee el archivo línea por línea
                String[] datosProducto = linea.split(","); // Divide la línea en datos separados por comas
                boolean agregar = false; // Variable para determinar si se agrega el producto a los resultados

                // Lógica de filtrado
                switch (tipoFiltro) {
                    case "proveedor": // Si el filtro es por proveedor
                        if (datosProducto[4].equalsIgnoreCase(valorFiltro)) { // Compara el proveedor
                            agregar = true; // Marca como agregar si hay coincidencia
                        }
                        break;
                    case "existencias": // Si el filtro es por existencias
                        int stock = Integer.parseInt(datosProducto[6]); // Obtiene el stock del producto
                        switch (valorFiltro) { // Verifica el rango de existencias
                            case "Menos de 10":
                                if (stock < 10) agregar = true; // Agrega si el stock es menor a 10
                                break;
                            case "10 a 50":
                                if (stock >= 10 && stock <= 50) agregar = true; // Agrega si el stock está entre 10 y 50
                                break;
                            case "Más de 50":
                                if (stock > 50) agregar = true; // Agrega si el stock es mayor a 50
                                break;
                        }
                        break;
                    case "precio": // Si el filtro es por precio
                        String[] precios = valorFiltro.split(","); // Divide el rango de precios
                        if (precios.length == 2) { // Verifica que haya dos precios
                            double minPrecio = Double.parseDouble(precios[0].trim()); // Obtiene el precio mínimo
                            double maxPrecio = Double.parseDouble(precios[1].trim()); // Obtiene el precio máximo
                            double precio = Double.parseDouble(datosProducto[3]); // Obtiene el precio del producto
                            if (precio >= minPrecio && precio <= maxPrecio) { // Verifica si el precio está en el rango
                                agregar = true; // Marca como agregar si hay coincidencia
                            }
                        }
                        break;
                }

                if (agregar) {
                    resultados.add(datosProducto); // Agrega el producto a los resultados filtrados
                }
            }
        } catch (IOException e) { // Maneja excepciones de entrada/salida
            // Muestra un mensaje de error si ocurre una excepción
            JOptionPane.showMessageDialog(null, "Error al cargar el archivo de productos. Asegúrate de que productos.txt esté en la ubicación correcta.", "Error de Archivo", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace(); // Imprime el stack trace del error
        }

        // Actualiza la tabla con los resultados filtrados
        actualizarTabla(resultados); // Llama al método para actualizar la tabla
        return resultados; // Devuelve los resultados filtrados
    }

    // Método para buscar productos filtrados
    private static List<String[]> buscarProductosFiltrados(String busqueda) {
        List<String[]> resultados = new ArrayList<>(); // Crea una lista para almacenar los resultados de la búsqueda
        String busquedaNormalizada = busqueda.trim().toLowerCase(); // Normaliza la búsqueda eliminando espacios y convirtiendo a minúsculas

        // Verifica que haya productos filtrados
        if (productosFiltrados == null || productosFiltrados.isEmpty()) {
            return resultados; // Devuelve resultados vacíos si no hay productos filtrados
        }

        // Buscando coincidencias solo en el nombre y código
        for (String[] producto : productosFiltrados) { // Recorre la lista de productos filtrados
            String nombreProducto = producto[1].toLowerCase(); // Obtiene el nombre del producto
            String codigoProducto = producto[2].toLowerCase(); // Obtiene el código del producto

            // Verifica si el nombre o código contienen la búsqueda
            if (nombreProducto.contains(busquedaNormalizada) || codigoProducto.contains(busquedaNormalizada)) {
                resultados.add(producto); // Agrega el producto a los resultados si hay coincidencia
            }
        }

        return resultados; // Devuelve los resultados de la búsqueda
    }

    // Método para actualizar la tabla con los resultados
    private static void actualizarTabla(List<String[]> resultados) {
        modeloTabla.setRowCount(0); // Limpia resultados anteriores en el modelo de la tabla
        if (resultados.isEmpty()) {
            modeloTabla.addRow(new Object[]{"No se encontraron resultados.", "", "", "", "", "", ""}); // Agrega un mensaje si no hay resultados
        } else {
            for (String[] producto : resultados) { // Recorre la lista de resultados
                modeloTabla.addRow(producto); // Agrega cada producto al modelo de la tabla
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(VentanaInventario::WindowInventario); // Inicia la ventana de inventario en el hilo de eventos
    }
}
