package punto_de_venta;

import javax.swing.*; // Importar componentes de Swing
import java.awt.*; // Importar clases para manejar diseño
import java.awt.event.ActionEvent; // Importar para manejar eventos
import java.awt.event.ActionListener; // Importar para escuchar eventos de acción
import java.io.BufferedReader; // Importar para leer archivos
import java.io.FileReader; // Importar para leer archivos de texto
import java.io.IOException; // Importar para manejar excepciones de entrada/salida
import java.util.ArrayList; // Importar para usar listas dinámicas
import java.util.List; // Importar para listas

public class VentanaInventario {

    // Método principal para crear y mostrar la ventana de inventario
    public static void WindowInventario() {
        // Crear la ventana con el título "Ventana de inventario"
        JFrame Ventana_de_Inventario = new JFrame("Ventana de inventario");
        // Configurar la ventana para ocupar toda la pantalla
        Ventana_de_Inventario.setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Configurar el menú para la ventana de venta
        ClassMenuWindVenta menu = new ClassMenuWindVenta();
        Ventana_de_Inventario.setJMenuBar(menu.crearMenu()); // Asignar el menú a la ventana

        // Panel para el buscador
        JPanel panelBuscador = new JPanel();
        panelBuscador.setLayout(new FlowLayout());

        // Campo de texto para la búsqueda
        JTextField campoBusqueda = new JTextField(20); // Crear un campo de texto con 20 columnas
        panelBuscador.add(campoBusqueda); // Agregar el campo de texto al panel del buscador

        // Botón para buscar
        JButton botonBuscar = new JButton("Buscar");
        panelBuscador.add(botonBuscar); // Agregar el botón al panel del buscador

        // Área de texto para mostrar resultados
        JTextArea areaResultados = new JTextArea(10, 30); // Crear un área de texto con 10 filas y 30 columnas
        areaResultados.setEditable(false); // Hacer que el área de texto no sea editable
        JScrollPane scrollPane = new JScrollPane(areaResultados); // Agregar un JScrollPane para permitir el desplazamiento
        panelBuscador.add(scrollPane); // Agregar el JScrollPane al panel del buscador

        // Agregar el panel del buscador a la ventana
        Ventana_de_Inventario.add(panelBuscador, BorderLayout.SOUTH); // Colocar el panel en la parte inferior de la ventana

        // Acción del botón de búsqueda
        botonBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String busqueda = campoBusqueda.getText().trim(); // Obtener texto de búsqueda
                // Llamar al método de búsqueda y obtener resultados
                List<String> resultados = buscarProductos(busqueda, "C:\\Users\\DAVID\\Documents\\punto\\Proyecto-OOP-Uaemex\\Punto_de_venta\\src/archivo_productos.txt");
                areaResultados.setText(""); // Limpiar resultados anteriores en el área de texto
                if (resultados.isEmpty()) {
                    areaResultados.append("No se encontraron resultados.\n"); // Mensaje si no se encuentran resultados
                } else {
                    for (String producto : resultados) {
                        areaResultados.append(producto + "\n"); // Mostrar cada producto encontrado
                    }
                }
            }
        });

        // Muestra la ventana en pantalla
        Ventana_de_Inventario.setVisible(true); // Hacer visible la ventana
    }

    // Método para buscar productos en un archivo
    private static List<String> buscarProductos(String busqueda, String archivo) {
        List<String> resultados = new ArrayList<>(); // Crear una lista para almacenar los resultados
        String busquedaNormalizada = busqueda.trim().toLowerCase(); // Normalizar la búsqueda
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) { // Usar BufferedReader para leer el archivo
            String linea;
            while ((linea = br.readLine()) != null) { // Leer líneas del archivo hasta que no queden más
                String lineaNormalizada = linea.trim().toLowerCase(); // Normalizar la línea del archivo
                // Comprobar si la línea normalizada contiene la búsqueda normalizada
                if (lineaNormalizada.contains(busquedaNormalizada)) {
                    resultados.add(linea); // Agregar producto a los resultados
                }
            }
        } catch (IOException e) { // Manejar excepciones de entrada/salida
            e.printStackTrace(); // Imprimir la traza de la excepción
        }
        return resultados; // Retornar la lista de resultados
    }
}
