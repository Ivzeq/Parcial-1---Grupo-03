package stackModule;

import application.Exercise;
import queueModule.SimpleArrayQueue;
import queueModule.SimpleLinkedQueue;

import java.util.Scanner;

public class StackExercise extends Exercise {

    private SimpleStack<String> stack;
    private boolean firstTime = true;

    public StackExercise( Scanner scanner) {
        super(scanner);
        stack = new SimpleArrayStack<>() {
        };
    }

    @Override
    protected void exerciseLogic() {
        String choice = "";
        while (!(choice.equals("1") || choice.equals("2"))){
            System.out.println("¿Qué implementación desea usar?");
            System.out.println("  1. SimpleArrayStack");
            System.out.println("  2. SimpleLinkedStack");

            choice = scanner.nextLine().trim();

            if (choice.equals("1")) {
                stack = new SimpleArrayStack<>();
            } else if (choice.equals("2")){
                stack = new SimpleLinkedStack<>();
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
                case "1" -> doPush();
                case "2" -> doPop();
                case "3" -> doPeek();
                case "4" -> doClear();
                case "0" -> {
                    System.out.println("Saliendo del ejercicio de Pila. ¡Hasta luego!");
                    active = false;
                }
                default -> System.out.println("  Opción no reconocida. Intente nuevamente.");
            }
        }
    }

    private void printWelcome() {
        separator();
        System.out.println("  Bienvenido al ejercicio de Stack (Pila)");
        System.out.println("  Implementación: " + stack.getClass().getSimpleName());
        separator();
    }

    private void printStatus() {
        separator();
        System.out.println("  Estado de la pila");
        System.out.println("  isEmpty : " + stack.isEmpty());
        System.out.println("  size    : " + stack.size());
        separator();
    }

    private void printMenu() {
        System.out.println("Seleccione una operación:");
        System.out.println("  1. push  (insertar elemento)");
        System.out.println("  2. pop   (remover tope)");
        System.out.println("  3. peek  (ver tope sin remover)");
        System.out.println("  4. clear (vaciar la pila)");
        System.out.println("  0. Salir");
        System.out.print("Opción: ");
    }

    private void separator() {
        System.out.println("----------------------------------------");
    }

    /** Push – repetible. */
    private void doPush() {
        boolean repeat = true;
        while (repeat) {
            System.out.print("Elemento a pushear: ");
            String element = scanner.nextLine().trim();
            if (!element.isEmpty()) {
                stack.push(element);
                System.out.println("  ===> Pusheado: \"" + element + "\"");
                System.out.println("  ===> Pila ahora: " + stack);
            }
            System.out.print("¿Repetir push? (s/n): ");
            repeat = scanner.nextLine().trim().equalsIgnoreCase("s");
        }
    }

    /** Pop – repetible; verifica vacío antes de entrar y en cada iteración. */
    private void doPop() {
        if (stack.isEmpty()) {
            System.out.println("  ✗ La pila está vacía, no se puede hacer pop.");
            return;
        }
        boolean repeat = true;
        while (repeat) {
            if (stack.isEmpty()) {
                System.out.println("  ✗ La pila está vacía, no se puede continuar.");
                break;
            }
            String removed = stack.pop();
            System.out.println("  ===> Pop devolvió: \"" + removed + "\"");
            System.out.println("  ===> Pila ahora: " + stack);
            System.out.print("¿Repetir pop? (s/n): ");
            repeat = scanner.nextLine().trim().equalsIgnoreCase("s");
        }
    }

    /** Peek – NO repetible; vuelve al menú principal automáticamente. */
    private void doPeek() {
        if (stack.isEmpty()) {
            System.out.println("  ✗ La pila está vacía, no se puede hacer peek.");
            return;
        }
        System.out.println("  ===> Tope de la pila: \"" + stack.peek() + "\"");
    }

    /** Clear – solo ejecuta si la pila tiene elementos. */
    private void doClear() {
        if (stack.isEmpty()) {
            System.out.println("  ✗ La pila ya está vacía, no es necesario limpiarla.");
            return;
        }
        stack.clear();
        System.out.println("  ===> Pila vaciada correctamente.");
    }
}