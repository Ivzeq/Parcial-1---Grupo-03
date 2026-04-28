package inventarioModule;

import application.Exercise;
import java.util.Scanner;

public class InventarioExercise extends Exercise {

    private final SimpleDictionary<String, Producto> inventario;

    private static final String LINEA_DOBLE = "═".repeat(60);

    private static final String LINEA_FINA  = "─".repeat(60);

    public InventarioExercise(Scanner scanner) {
        super(scanner);
        inventario = new LinkedDictionary<>();
        cargarDatosDemo();
    }

    @Override

    protected void exerciseLogic() {
        mostrarBienvenida();

        boolean active = true;
        while (active) {
            mostrarMenu();
            String opcion = scanner.nextLine().trim();

            switch (opcion) {
                case "1" -> buscarProducto();
                case "2" -> agregarProducto();
                case "3" -> editarProducto();
                case "4" -> eliminarProducto();
                case "5" -> listarInventario();
                case "0" -> {
                    System.out.println(LINEA_FINA);
                    System.out.println("  Saliendo del sistema de inventario. ¡Hasta luego!");
                    active = false;
                }
                default -> System.out.println("  ✗ Opción no reconocida. Ingrese un número del 0 al 5.");
            }
        }
    }

    // OPCIÓN 1 – BUSCAR PRODUCTO POR CÓDIGO

    private void buscarProducto() {
        System.out.println(LINEA_DOBLE);
        System.out.println("  BUSCAR PRODUCTO");
        System.out.println(LINEA_DOBLE);

        System.out.print("  Ingrese el código del producto: ");
        String codigo = normalizarCodigo(scanner.nextLine());

        if (codigo.isBlank()) {
            System.out.println("  ✗ El código no puede estar vacío.");
            return;
        }

        Producto p = inventario.get(codigo);
        if (p == null) {
            System.out.println("  ✗ No existe ningún producto con código \"" + codigo + "\".");
        } else {
            System.out.println();
            System.out.println(p.toDetalle());
        }
    }

    // OPCIÓN 2 – AGREGAR PRODUCTO

    private void agregarProducto() {
        System.out.println(LINEA_DOBLE);
        System.out.println("  AGREGAR PRODUCTO");
        System.out.println(LINEA_DOBLE);

        System.out.print("  Código único del producto: ");
        String codigo = normalizarCodigo(scanner.nextLine());

        if (codigo.isBlank()) {
            System.out.println("  ✗ El código no puede estar vacío. Operación cancelada.");
            return;
        }

        if (inventario.containsKey(codigo)) {
            System.out.println("  ✗ Ya existe un producto con ese código:");
            System.out.println(inventario.get(codigo).toDetalle());
            System.out.println("  Operación cancelada. Use 'Editar' para modificarlo.");
            return;
        }

        String nombre = leerTexto("  Nombre del producto   : ");
        double precio = leerPrecio("  Precio unitario ($)   : ");
        int    stock  = leerStock ("  Stock inicial (unid.) : ");

        Producto nuevo = new Producto(codigo, nombre, precio, stock);
        inventario.put(codigo, nuevo);

        System.out.println(LINEA_FINA);
        System.out.println("  ✓ Producto agregado correctamente:");
        System.out.println(nuevo.toDetalle());
    }

    // OPCIÓN 3 – EDITAR PRODUCTO

    private void editarProducto() {
        System.out.println(LINEA_DOBLE);
        System.out.println("  EDITAR PRODUCTO");
        System.out.println(LINEA_DOBLE);

        System.out.print("  Código del producto a editar: ");
        String codigo = normalizarCodigo(scanner.nextLine());

        if (codigo.isBlank()) {
            System.out.println("  ✗ El código no puede estar vacío. Operación cancelada.");
            return;
        }

        Producto p = inventario.get(codigo);
        if (p == null) {
            System.out.println("  ✗ No existe ningún producto con código \"" + codigo + "\".");
            return;
        }

        System.out.println("  Producto actual:");
        System.out.println(p.toDetalle());

        boolean editando = true;
        while (editando) {
            System.out.println("  ¿Qué desea editar?");
            System.out.println("  1. Precio");
            System.out.println("  2. Stock");
            System.out.println("  3. Ambos");
            System.out.println("  0. Cancelar");
            System.out.print("  Opción: ");
            String subOp = scanner.nextLine().trim();

            switch (subOp) {
                case "1" -> {
                    double nuevoPrecio = leerPrecio("  Nuevo precio ($): ");
                    p.setPrecio(nuevoPrecio);
                    System.out.printf("  ✓ Precio actualizado a $%.2f%n", nuevoPrecio);
                    editando = false;
                }
                case "2" -> {
                    int nuevoStock = leerStock("  Nuevo stock (unid.): ");
                    p.setStock(nuevoStock);
                    System.out.printf("  ✓ Stock actualizado a %d unidades%n", nuevoStock);
                    editando = false;
                }
                case "3" -> {
                    double nuevoPrecio = leerPrecio("  Nuevo precio ($): ");
                    int    nuevoStock  = leerStock ("  Nuevo stock (unid.): ");
                    p.setPrecio(nuevoPrecio);
                    p.setStock(nuevoStock);
                    System.out.printf("  ✓ Precio: $%.2f  |  Stock: %d unidades%n",
                        nuevoPrecio, nuevoStock);
                    editando = false;
                }
                case "0" -> {
                    System.out.println("  Edición cancelada.");
                    editando = false;
                }
                default -> System.out.println("  ✗ Opción inválida. Ingrese 1, 2, 3 o 0.");
            }
        }

        System.out.println("  Estado final del producto:");
        System.out.println(p.toDetalle());
    }

    // OPCIÓN 4 – ELIMINAR PRODUCTO

    private void eliminarProducto() {
        System.out.println(LINEA_DOBLE);
        System.out.println("  ELIMINAR PRODUCTO");
        System.out.println(LINEA_DOBLE);

        System.out.print("  Código del producto a eliminar: ");
        String codigo = normalizarCodigo(scanner.nextLine());

        if (codigo.isBlank()) {
            System.out.println("  ✗ El código no puede estar vacío. Operación cancelada.");
            return;
        }

        Producto p = inventario.get(codigo);
        if (p == null) {
            System.out.println("  ✗ No existe ningún producto con código \"" + codigo + "\".");
            return;
        }

        System.out.println("  Producto a eliminar:");
        System.out.println(p.toDetalle());
        System.out.println("  ⚠ Esta acción es irreversible.");
        System.out.println("  ¿Confirma la eliminación? (s/n):");
        System.out.print("  Opción: ");

        if (leerSiNo().equals("s")) {
            inventario.remove(codigo);
            System.out.printf("  ✓ Producto \"%s\" eliminado del inventario.%n", codigo);
        } else {
            System.out.println("  Eliminación cancelada.");
        }
    }

    // OPCIÓN 5 – LISTAR EL INVENTARIO

    private void listarInventario() {
        System.out.println(LINEA_DOBLE);
        System.out.println("  LISTADO COMPLETO DEL INVENTARIO");
        System.out.println(LINEA_DOBLE);

        if (inventario.isEmpty()) {
            System.out.println("  El inventario está vacío.");
            System.out.println(LINEA_FINA);
            return;
        }

        Object[] productos = inventario.values();
        double   valorTotal = 0;
        int      totalUnidades = 0;

        System.out.printf("  %-10s | %-30s | %12s | %s%n",
            "CÓDIGO", "NOMBRE", "PRECIO", "STOCK");
        System.out.println(LINEA_FINA);

        for (Object obj : productos) {
            Producto p = (Producto) obj;
            System.out.printf("  %-10s | %-30s | $%10.2f | %d unid.%n",
                p.getCodigo(),
                p.getNombre().length() > 30
                    ? p.getNombre().substring(0, 27) + "..."
                    : p.getNombre(),
                p.getPrecio(),
                p.getStock());
            valorTotal    += p.getPrecio() * p.getStock();
            totalUnidades += p.getStock();
        }

        System.out.println(LINEA_FINA);
        System.out.printf("  Productos distintos : %d%n", inventario.size());
        System.out.printf("  Total unidades      : %d%n", totalUnidades);
        System.out.printf("  Valor del inventario: $%.2f%n", valorTotal);
        System.out.println(LINEA_DOBLE);
    }

    // PRESENTACIÓN

    private void mostrarBienvenida() {
        System.out.println(LINEA_DOBLE);
        System.out.println("  SISTEMA DE INVENTARIO DE COMERCIO");
        System.out.println(LINEA_DOBLE);
        System.out.printf("  Productos cargados: %d%n", inventario.size());
        System.out.println(LINEA_FINA);
    }

    private void mostrarMenu() {
        System.out.println();
        System.out.println(LINEA_FINA);
        System.out.printf("  Inventario: %d producto(s)%n", inventario.size());
        System.out.println(LINEA_FINA);
        System.out.println("  1. Buscar producto por código");
        System.out.println("  2. Agregar producto");
        System.out.println("  3. Editar precio / stock");
        System.out.println("  4. Eliminar producto");
        System.out.println("  5. Listar todo el inventario  [BONUS]");
        System.out.println("  0. Salir");
        System.out.print("  Opción: ");
    }

    // HELPERS DE LECTURA

    private String leerTexto(String prompt) {
        String valor = "";
        while (valor.isBlank()) {
            System.out.print(prompt);
            valor = scanner.nextLine().trim();
            if (valor.isBlank()) {
                System.out.println("  ✗ El campo no puede estar vacío. Intente nuevamente.");
            }
        }
        return valor;
    }

    private double leerPrecio(String prompt) {
        while (true) {
            System.out.print(prompt);
            String linea = scanner.nextLine().trim().replace(',', '.');
            try {
                double valor = Double.parseDouble(linea);
                if (valor > 0) return valor;
                System.out.println("  ✗ El precio debe ser mayor a cero.");
            } catch (NumberFormatException e) {
                System.out.println("  ✗ Ingrese un número válido (ej.: 1500.00).");
            }
        }
    }

    private int leerStock(String prompt) {
        while (true) {
            System.out.print(prompt);
            String linea = scanner.nextLine().trim();
            try {
                int valor = Integer.parseInt(linea);
                if (valor >= 0) return valor;
                System.out.println("  ✗ El stock no puede ser negativo.");
            } catch (NumberFormatException e) {
                System.out.println("  ✗ Ingrese un número entero (ej.: 50).");
            }
        }
    }

    private String leerSiNo() {
        String valor = "";
        while (!valor.equals("s") && !valor.equals("n")) {
            valor = scanner.nextLine().trim().toLowerCase();
            if (!valor.equals("s") && !valor.equals("n")) {
                System.out.print("  ✗ Ingrese 's' o 'n': ");
            }
        }
        return valor;
    }

    private String normalizarCodigo(String raw) {
        if (raw == null) return "";
        return raw.trim().toUpperCase();
    }

    // DATOS DE DEMO

    private void cargarDatosDemo() {
        inventario.put("CAFE01",  new Producto("CAFE01",  "Café molido 250g",      850.00, 120));
        inventario.put("LECHE01", new Producto("LECHE01", "Leche entera 1L",       450.00, 200));
        inventario.put("AZUC01",  new Producto("AZUC01",  "Azúcar blanca 1kg",     600.00,  80));
        inventario.put("GALL01",  new Producto("GALL01",  "Galletitas de vainilla",320.00, 150));
        inventario.put("AGUA01",  new Producto("AGUA01",  "Agua mineral 1.5L",     350.00, 300));
    }
}
