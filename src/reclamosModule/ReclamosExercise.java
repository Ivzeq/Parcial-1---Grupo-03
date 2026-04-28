package reclamosModule;

import application.Exercise;

import java.util.Scanner;

public class ReclamosExercise extends Exercise {

    private final PriorityQueue<Reclamo> colaPendientes;

    private int totalResueltos = 0;

    private static final String LINEA_DOBLE = "═".repeat(60);

    private static final String LINEA_FINA  = "─".repeat(60);

    public ReclamosExercise(Scanner scanner) {
        super(scanner);
        colaPendientes = new LinkedPriorityQueue<>();
    }
    @Override

    protected void exerciseLogic() {
        mostrarBienvenida();

        boolean active = true;
        while (active) {
            mostrarMenu();
            String opcion = scanner.nextLine().trim();

            switch (opcion) {
                case "1" -> redactarReclamo();
                case "2" -> visualizarReclamos();
                case "0" -> {
                    mostrarResumenSesion();
                    System.out.println("Saliendo del sistema de reclamos. ¡Hasta luego!");
                    active = false;
                }
                default  -> System.out.println("  Opción no reconocida. Ingrese 1, 2 o 0.");
            }
        }
    }

    // OPCIÓN 1 – REDACTAR RECLAMO

    private void redactarReclamo() {
        System.out.println(LINEA_DOBLE);
        System.out.println("  NUEVO RECLAMO");
        System.out.println(LINEA_DOBLE);

        String titulo      = leerTexto("  Título      : ");
        String descripcion = leerTexto("  Descripción : ");
        Reclamo.Urgencia urgencia = leerUrgencia();

        Reclamo nuevo = new Reclamo(titulo, descripcion, urgencia);
        colaPendientes.enqueue(nuevo, urgencia.getValor());

        System.out.println(LINEA_FINA);
        System.out.printf("  ✓ Reclamo #%03d registrado correctamente.%n", nuevo.getId());
        System.out.println(LINEA_FINA);
    }

    // OPCIÓN 2 – VISUALIZAR RECLAMOS

    private void visualizarReclamos() {
        System.out.println(LINEA_DOBLE);
        System.out.println("  COLA DE RECLAMOS  (orden: mayor urgencia primero)");
        System.out.println(LINEA_DOBLE);

        if (colaPendientes.isEmpty()) {
            System.out.println("  No hay reclamos pendientes. ✓");
            System.out.println(LINEA_FINA);
            return;
        }

        int total = colaPendientes.size();
        Reclamo[] buffer = new Reclamo[total];
        for (int i = 0; i < total; i++) {
            buffer[i] = colaPendientes.dequeue();
        }
        System.out.printf("  Total pendientes: %d%n%n", total);
        for (int i = 0; i < total; i++) {
            System.out.printf("  %2d. %s%n", i + 1, buffer[i]);
        }
        System.out.println();
        boolean[] resueltos = new boolean[total];

        boolean continuar = true;
        while (continuar) {
            System.out.println(LINEA_FINA);
            System.out.println("  ¿Desea atender algún reclamo?");
            System.out.println("  s - Sí    |    n - No (volver al menú)");
            System.out.print("  Opción: ");
            String resp = leerSiNo();

            if (resp.equals("n")) {
                continuar = false;
            } else {
                atenderReclamo(buffer, resueltos, total);
                boolean hayPendientes = false;
                for (int i = 0; i < total; i++) {
                    if (!resueltos[i]) { hayPendientes = true; break; }
                }
                if (!hayPendientes) {
                    System.out.println("  ✓ Todos los reclamos fueron resueltos.");
                    continuar = false;
                }
            }
        }
        for (int i = 0; i < total; i++) {
            if (!resueltos[i]) {
                colaPendientes.enqueue(buffer[i], buffer[i].getUrgencia().getValor());
            }
        }

        System.out.println(LINEA_FINA);
    }

    private void atenderReclamo(Reclamo[] buffer, boolean[] resueltos, int total) {
        System.out.println();
        System.out.println("  Reclamos pendientes:");
        boolean hayPendientes = false;
        for (int i = 0; i < total; i++) {
            if (!resueltos[i]) {
                System.out.printf("  %2d. %s%n", i + 1, buffer[i]);
                hayPendientes = true;
            }
        }
        if (!hayPendientes) return;

        System.out.println();
        System.out.print("  Ingrese el número del reclamo a atender: ");
        int eleccion = leerEnteroEnRango(1, total);
        int idx      = eleccion - 1;

        if (resueltos[idx]) {
            System.out.println("  ✗ Ese reclamo ya fue marcado como resuelto.");
            return;
        }

        System.out.println();
        System.out.println(buffer[idx].toDetalle());
        System.out.println();
        System.out.println("  ¿Marcar este reclamo como resuelto?");
        System.out.println("  s - Sí (resolver)    |    n - No (dejar en cola)");
        System.out.print("  Opción: ");
        String confirmacion = leerSiNo();

        if (confirmacion.equals("s")) {
            buffer[idx].marcarResuelto();
            resueltos[idx] = true;
            totalResueltos++;
            System.out.printf("  ✓ Reclamo #%03d marcado como resuelto.%n", buffer[idx].getId());
        } else {
            System.out.printf("  El reclamo #%03d se mantiene en la cola.%n", buffer[idx].getId());
        }
    }

    // PRESENTACIÓN

    private void mostrarBienvenida() {
        System.out.println(LINEA_DOBLE);
        System.out.println("  SISTEMA DE GESTIÓN DE RECLAMOS");
        System.out.println("  Atención al Consumidor");
        System.out.println(LINEA_DOBLE);
    }

    private void mostrarMenu() {
        System.out.println();
        System.out.println(LINEA_FINA);
        System.out.printf("  Pendientes en cola: %d%n", colaPendientes.size());
        if (!colaPendientes.isEmpty()) {
            String urg = colaPendientes.peek().getUrgencia().getEtiqueta().trim();
            System.out.printf("  Próximo a atender : [#%03d] %s  (Urgencia: %s)%n",
                colaPendientes.peek().getId(),
                colaPendientes.peek().getTitulo(),
                urg);
        }
        System.out.println(LINEA_FINA);
        System.out.println("  1. Redactar nuevo reclamo");
        System.out.println("  2. Visualizar / atender reclamos");
        System.out.println("  0. Salir");
        System.out.print("  Opción: ");
    }

    private void mostrarResumenSesion() {
        System.out.println();
        System.out.println(LINEA_DOBLE);
        System.out.println("  RESUMEN DE SESIÓN");
        System.out.println(LINEA_DOBLE);
        System.out.printf("  Reclamos resueltos en esta sesión : %d%n", totalResueltos);
        System.out.printf("  Reclamos pendientes en cola       : %d%n", colaPendientes.size());
        System.out.println(LINEA_DOBLE);
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

    private Reclamo.Urgencia leerUrgencia() {
        Reclamo.Urgencia urgencia = null;
        while (urgencia == null) {
            System.out.println("  Nivel de urgencia:");
            System.out.println("    1 - Bajo");
            System.out.println("    2 - Medio");
            System.out.println("    3 - Alto");
            System.out.println("    4 - Crítico");
            System.out.print("  Opción: ");
            String input = scanner.nextLine().trim();
            try {
                urgencia = Reclamo.Urgencia.desde(input);
            } catch (IllegalArgumentException e) {
                System.out.println("  ✗ " + e.getMessage() +
                    " Use 1, 2, 3, 4 o el nombre de la urgencia.");
            }
        }
        return urgencia;
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

    private int leerEnteroEnRango(int min, int max) {
        while (true) {
            String linea = scanner.nextLine().trim();
            try {
                int valor = Integer.parseInt(linea);
                if (valor >= min && valor <= max) {
                    return valor;
                }
                System.out.printf("  ✗ Ingrese un número entre %d y %d: ", min, max);
            } catch (NumberFormatException e) {
                System.out.printf("  ✗ Valor no numérico. Ingrese un número entre %d y %d: ", min, max);
            }
        }
    }
}
