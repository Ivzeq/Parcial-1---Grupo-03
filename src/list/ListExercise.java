package list;

import application.Exercise;
import listModule.SimpleArrayList;
import listModule.SimpleLinkedList;
import listModule.SimpleList;
import java.util.Scanner;

public class ListExercise extends Exercise {

    private SimpleList<String> list;
    private boolean firstTime = true;

    public ListExercise(Scanner scnr) {
        super(scnr);
        list = new SimpleArrayList<>();
    }

    @Override
    protected void exerciseLogic() {
        String choice = "";
        while (!(choice.equals("1") || choice.equals("2"))) {
            System.out.println("¿Qué implementación desea usar?");
            System.out.println("  1. SimpleArrayList");
            System.out.println("  2. SimpleLinkedList");
            choice = scanner.nextLine().trim();
            if (choice.equals("1")) {
                list = new SimpleArrayList<>();
            } else if (choice.equals("2")) {
                list = new SimpleLinkedList<>();
            } else {
                System.out.println("  Opción inválida, ingrese una correcta.");
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
                case "1" -> doAdd();
                case "2" -> doRemoveByIndex();
                case "3" -> doRemoveByReference();
                case "4" -> doClear();
                case "0" -> {
                    System.out.println("  Saliendo del ejercicio de Lista. ¡Hasta luego!");
                    active = false;
                }
                default -> System.out.println("  Opción no reconocida. Intente nuevamente.");
            }
        }
    }

    private void printWelcome() {
        separator();
        System.out.println("  Bienvenido al ejercicio de Lista");
        System.out.println("  Implementación: " + list.getClass().getSimpleName());
        separator();
    }

    private void printStatus() {
        separator();
        System.out.println("  Estado de la lista");
        System.out.println("  isEmpty : " + list.isEmpty());
        System.out.println("  size    : " + list.size());
        System.out.println("  lista   : " + list);
        separator();
    }

    private void printMenu() {
        System.out.println("Seleccione una operación:");
        System.out.println("  1. add              (agregar elemento)");
        System.out.println("  2. remove por índice");
        System.out.println("  3. remove por referencia");
        System.out.println("  4. clear            (vaciar la lista)");
        System.out.println("  0. Salir");
        System.out.print("Opción: ");
    }

    private void separator() {
        System.out.println("----------------------------------------");
    }

    private void doAdd() {
        boolean repeat = true;
        while (repeat) {
            System.out.print("  Elemento a agregar: ");
            String element = scanner.nextLine().trim().toUpperCase();
            if (!element.isEmpty()) {
                list.add(element);
                System.out.println("  ===> Agregado: \"" + element + "\"");
                System.out.println("  ===> Lista ahora: " + list);
            }
            System.out.print("  ¿Repetir add? (s/n): ");
            repeat = scanner.nextLine().trim().equalsIgnoreCase("s");
        }
    }

    private void doRemoveByIndex() {
        if (list.isEmpty()) {
            System.out.println("  ✗ La lista está vacía, no se puede remover por índice.");
            return;
        }
        boolean repeat = true;
        while (repeat) {
            if (list.isEmpty()) {
                System.out.println("  ✗ La lista está vacía, no se puede continuar.");
                break;
            }
            System.out.println("  Lista actual: " + list);
            System.out.print("  Índice a remover (0-" + (list.size() - 1) + "): ");
            String linea = scanner.nextLine().trim();
            try {
                int index = Integer.parseInt(linea);
                list.remove(index);
                System.out.println("  ===> Lista ahora: " + list);
            } catch (NumberFormatException e) {
                System.out.println("  ✗ Ingrese un número entero válido.");
            } catch (Exception e) {
                System.out.println("  ✗ Índice fuera de rango.");
            }
            System.out.print("  ¿Repetir remove por índice? (s/n): ");
            repeat = scanner.nextLine().trim().equalsIgnoreCase("s");
        }
    }

    private void doRemoveByReference() {
        if (list.isEmpty()) {
            System.out.println("  ✗ La lista está vacía, no se puede remover por referencia.");
            return;
        }
        boolean repeat = true;
        while (repeat) {
            if (list.isEmpty()) {
                System.out.println("  ✗ La lista está vacía, no se puede continuar.");
                break;
            }
            System.out.println("  Lista actual: " + list);
            System.out.print("  Elemento a remover: ");
            String element = scanner.nextLine().trim().toUpperCase();
            list.remove(element);
            System.out.println("  ===> Lista ahora: " + list);
            System.out.print("  ¿Repetir remove por referencia? (s/n): ");
            repeat = scanner.nextLine().trim().equalsIgnoreCase("s");
        }
    }

    private void doClear() {
        if (list.isEmpty()) {
            System.out.println("  ✗ La lista ya está vacía, no es necesario limpiarla.");
            return;
        }
        list.clear();
        System.out.println("  ===> Lista vaciada correctamente.");
    }
}
