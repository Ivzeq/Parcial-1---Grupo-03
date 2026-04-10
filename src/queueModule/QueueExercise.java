package queueModule;

import application.Exercise;
import java.util.Scanner;

public class QueueExercise extends Exercise {

    private SimpleQueue<String> queue;
    private boolean firstTime = true;

    public QueueExercise(Scanner scanner) {
        super(scanner);
        queue = new SimpleArrayQueue<> ();
    }

    @Override
    protected void exerciseLogic() {
        String choice = "";
        while (!(choice.equals("1") || choice.equals("2"))){
            System.out.println("¿Qué implementación desea usar?");
            System.out.println("  1. SimpleArrayQueue");
            System.out.println("  2. SimpleLinkedQueue");

            choice = scanner.nextLine().trim();

            if (choice.equals("1")) {
                queue = new SimpleArrayQueue<>();
            } else if (choice.equals("2")){
                queue = new SimpleLinkedQueue<>();
            } else {
                System.out.println("Opcion invalida, ingrese una correcta");
            }

        }

        boolean active = true;
        while (active) {
            if (firstTime) {
                printWelcome();
                firstTime = false;
            } else {
                printStatus();
            }

            printMenu();
            String option = scanner.nextLine().trim();

            switch (option) {
                case "1" -> doEnqueue();
                case "2" -> doDequeue();
                case "3" -> doPeek();
                case "4" -> doClear();
                case "0" -> {
                    System.out.println("Saliendo del ejercicio de Cola. ¡Hasta luego!");
                    active = false;
                }
                default -> System.out.println("  Opción no reconocida. Intente nuevamente.");
            }
        }
    }

    private void printWelcome() {
        separator();
        System.out.println("  Bienvenido al ejercicio de Queue (Cola)");
        System.out.println("  Implementación: " + queue.getClass().getSimpleName());
        separator();
    }

    private void printStatus() {
        separator();
        System.out.println("  Estado de la cola");
        System.out.println("  isEmpty : " + queue.isEmpty());
        System.out.println("  size    : " + queue.size());
        separator();
    }

    private void printMenu() {
        System.out.println("Seleccione una operación:");
        System.out.println("  1. enqueue (insertar al final)");
        System.out.println("  2. dequeue (remover del frente)");
        System.out.println("  3. peek    (ver frente sin remover)");
        System.out.println("  4. clear   (vaciar la cola)");
        System.out.println("  0. Salir");
        System.out.print("Opción: ");
    }

    private void separator() {
        System.out.println("----------------------------------------");
    }

    /** Enqueue – repetible. */
    private void doEnqueue() {
        boolean repeat = true;
        while (repeat) {
            System.out.print("Elemento a encolar: ");
            String element = scanner.nextLine().trim();
            if (!element.isEmpty()) {
                queue.enqueue(element);
                System.out.println("  ===> Encolado: \"" + element + "\"");
                System.out.println("  ===> Cola ahora: " + queue);
            }
            System.out.print("¿Repetir enqueue? (s/n): ");
            repeat = scanner.nextLine().trim().equalsIgnoreCase("s");
        }
    }

    /** Dequeue – repetible; verifica vacío antes de entrar y en cada iteración. */
    private void doDequeue() {
        if (queue.isEmpty()) {
            System.out.println("  ✗ La cola está vacía, no se puede hacer dequeue.");
            return;
        }
        boolean repeat = true;
        while (repeat) {
            if (queue.isEmpty()) {
                System.out.println("  ✗ La cola está vacía, no se puede continuar.");
                break;
            }
            String removed = queue.dequeue();
            System.out.println("  ===> Dequeue devolvió: \"" + removed + "\"");
            System.out.println("  ===> Cola ahora: " + queue);
            System.out.print("¿Repetir dequeue? (s/n): ");
            repeat = scanner.nextLine().trim().equalsIgnoreCase("s");
        }
    }

    /** Peek – NO repetible; vuelve al menú principal automáticamente. */
    private void doPeek() {
        if (queue.isEmpty()) {
            System.out.println("  ✗ La cola está vacía, no se puede hacer peek.");
            return;
        }
        System.out.println("  ===> Frente de la cola: \"" + queue.peek() + "\"");
    }

    /** Clear – solo ejecuta si la cola tiene elementos. */
    private void doClear() {
        if (queue.isEmpty()) {
            System.out.println("  ✗ La cola ya está vacía, no es necesario limpiarla.");
            return;
        }
        queue.clear();
        System.out.println("  ===> Cola vaciada correctamente.");
    }
}