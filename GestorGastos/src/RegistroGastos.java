import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class RegistroGastos {
    private static final String ARCHIVO_GASTOS = "gastos.txt";
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcion;
        do {
            System.out.println("\n--- Registro de Gastos Personales ---");
            System.out.println("1. Añadir gasto");
            System.out.println("2. Ver todos los gastos");
            System.out.println("3. Calcular total de gastos");
            System.out.println("4. Ver gastos por categoría");
            System.out.println("5. Editar gasto");
            System.out.println("6. Eliminar gasto");
            System.out.println("7. Buscar gastos por rango de fechas");
            System.out.println("8. Exportar a CSV");
            System.out.println("9. Mostrar estadísticas");
            System.out.println("0. Salir");
            System.out.print("Elige una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea
            switch (opcion) {
                case 1:
                    anadirGasto(scanner);
                    break;
                case 2:
                    verGastos();
                    break;
                case 3:
                    calcularTotalGastos();
                    break;
                case 4:
                    verGastosPorCategoria(scanner);
                    break;
                case 5:
                    editarGasto(scanner);
                    break;
                case 6:
                    eliminarGasto(scanner);
                    break;
                case 7:
                    buscarGastosPorRangoFechas(scanner);
                    break;
                case 8:
                    exportarAGastosCSV(scanner);
                    break;
                case 9:
                    mostrarEstadisticas();
                    break;
                case 0:
                    System.out.println("¡Hasta luego!");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
        scanner.close();
    }

    private static void anadirGasto(Scanner scanner) {
        System.out.print("Introduce la fecha (DD/MM/YYYY): ");
        String fecha = scanner.nextLine();
        System.out.print("Introduce la categoría: ");
        String categoria = scanner.nextLine();
        System.out.print("Introduce la descripción: ");
        String descripcion = scanner.nextLine();
        System.out.print("Introduce la cantidad: ");
        double cantidad = scanner.nextDouble();
        try (PrintWriter writer = new PrintWriter(new FileWriter(ARCHIVO_GASTOS, true))) {
            writer.println(fecha + "," + categoria + "," + descripcion + "," + cantidad);
            System.out.println("Gasto registrado correctamente.");
        } catch (IOException e) {
            System.out.println("Error al registrar el gasto: " + e.getMessage());
        }
    }

    private static void verGastos() {
        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO_GASTOS))) {
            String linea;
            System.out.println("\n--- Todos los Gastos ---");
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",");
                System.out.println("Fecha: " + partes[0] + ", Categoría: " + partes[1] +
                        ", Descripción: " + partes[2] + ", Cantidad: $" + partes[3]);
            }
        } catch (IOException e) {
            System.out.println("Error al leer los gastos: " + e.getMessage());
        }
    }

    private static void calcularTotalGastos() {
        double total = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO_GASTOS))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",");
                total += Double.parseDouble(partes[3]);
            }
            System.out.println("Total de gastos: $" + total);
        } catch (IOException e) {
            System.out.println("Error al calcular el total de gastos: " + e.getMessage());
        }
    }

    private static void verGastosPorCategoria(Scanner scanner) {
        System.out.print("Introduce la categoría a buscar: ");
        String categoriaBuscada = scanner.nextLine().toLowerCase();
        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO_GASTOS))) {
            String linea;
            boolean encontrado = false;
            System.out.println("\n--- Gastos de la categoría '" + categoriaBuscada + "' ---");
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes[1].toLowerCase().equals(categoriaBuscada)) {
                    System.out.println("Fecha: " + partes[0] + ", Descripción: " + partes[2] + ", cantidad: $" + partes[3]);
                    encontrado = true;
                }
            }
            if (!encontrado) {
                System.out.println("No se encontraron gastos en esta categoría.");
            }
        } catch (IOException e) {
            System.out.println("Error al buscar gastos por categoría: " + e.getMessage());
        }
    }

    private static void editarGasto(Scanner scanner) {
        verGastos();
        System.out.print("Introduce el número de línea del gasto a editar: ");
        int lineaAEditar = scanner.nextInt() - 1; // Convertir a índice 0
        scanner.nextLine(); // Consumir el salto de línea
        try {
            File archivo = new File(ARCHIVO_GASTOS);
            File archivoTemporal = new File("gastos_temp.txt");
            try (BufferedReader reader = new BufferedReader(new FileReader(archivo));
                 PrintWriter writer = new PrintWriter(new FileWriter(archivoTemporal))) {
                String linea;
                int indice = 0;
                while ((linea = reader.readLine()) != null) {
                    if (indice == lineaAEditar) {
                        System.out.print("Introduce la nueva fecha (DD/MM/YYYY): ");
                        String nuevaFecha = scanner.nextLine();
                        System.out.print("Introduce la nueva categoría: ");
                        String nuevaCategoria = scanner.nextLine();
                        System.out.print("Introduce la nueva descripción: ");
                        String nuevaDescripcion = scanner.nextLine();
                        System.out.print("Introduce la nueva cantidad: ");
                        double nuevaCantidad = scanner.nextDouble();
                        writer.println(nuevaFecha + "," + nuevaCategoria + "," + nuevaDescripcion + "," + nuevaCantidad);
                        System.out.println("Gasto editado correctamente.");
                    } else {
                        writer.println(linea);
                    }
                    indice++;
                }
            }
            archivo.delete();
            archivoTemporal.renameTo(archivo);
        } catch (IOException e) {
            System.out.println("Error al editar el gasto: " + e.getMessage());
        }
    }

    private static void eliminarGasto(Scanner scanner) {
        verGastos();
        System.out.print("Introduce el número de línea del gasto a eliminar: ");
        int lineaAEliminar = scanner.nextInt() - 1; // Convertir a índice 0
        scanner.nextLine(); // Consumir el salto de línea
        try {
            File archivo = new File(ARCHIVO_GASTOS);
            File archivoTemporal = new File("gastos_temp.txt");
            try (BufferedReader reader = new BufferedReader(new FileReader(archivo));
                 PrintWriter writer = new PrintWriter(new FileWriter(archivoTemporal))) {
                String linea;
                int indice = 0;
                while ((linea = reader.readLine()) != null) {
                    if (indice != lineaAEliminar) {
                        writer.println(linea);
                    }
                    indice++;
                }
            }
            archivo.delete();
            archivoTemporal.renameTo(archivo);
            System.out.println("Gasto eliminado correctamente.");
        } catch (IOException e) {
            System.out.println("Error al eliminar el gasto: " + e.getMessage());
        }
    }

    private static void buscarGastosPorRangoFechas(Scanner scanner) {
        System.out.print("Introduce la fecha de inicio (DD/MM/YYYY): ");
        String fechaInicioStr = scanner.nextLine();
        System.out.print("Introduce la fecha de fin (DD/MM/YYYY): ");
        String fechaFinStr = scanner.nextLine();

        LocalDate fechaInicio = LocalDate.parse(fechaInicioStr, FORMATO_FECHA);
        LocalDate fechaFin = LocalDate.parse(fechaFinStr, FORMATO_FECHA);

        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO_GASTOS))) {
            String linea;
            boolean encontrado = false;
            System.out.println("\n--- Gastos entre " + fechaInicio + " y " + fechaFin + " ---");
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",");
                LocalDate fecha = LocalDate.parse(partes[0], FORMATO_FECHA);
                if (!fecha.isBefore(fechaInicio) && !fecha.isAfter(fechaFin)) {
                    System.out.println("Fecha: " + partes[0] + ", Categoría: " + partes[1] +
                            ", Descripción: " + partes[2] + ", Cantidad: $" + partes[3]);
                    encontrado = true;
                }
            }
            if (!encontrado) {
                System.out.println("No se encontraron gastos en este rango de fechas.");
            }
        } catch (IOException e) {
            System.out.println("Error al buscar gastos por rango de fechas: " + e.getMessage());
        }
    }

    private static void exportarAGastosCSV(Scanner scanner) {
        System.out.print("Introduce el nombre del archivo para exportar (sin extensión): ");
        String nombreArchivo = scanner.nextLine();
        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO_GASTOS));
             PrintWriter writer = new PrintWriter(new FileWriter(nombreArchivo + ".csv"))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                writer.println(linea);
            }
            System.out.println("Gastos exportados correctamente a " + nombreArchivo + ".csv");
        } catch (IOException e) {
            System.out.println("Error al exportar los gastos: " + e.getMessage());
        }
    }

    private static void mostrarEstadisticas() {
        double total = 0;
        int totalGastos = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO_GASTOS))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",");
                total += Double.parseDouble(partes[3]);
                totalGastos++;
            }
            System.out.println("Total de gastos: $" + total);
            System.out.println("Número total de gastos: " + totalGastos);
            System.out.println("Promedio de gastos: $" + (totalGastos > 0 ? total / totalGastos : 0));
        } catch (IOException e) {
            System.out.println("Error al mostrar estadísticas: " + e.getMessage());
        }
    }
}
