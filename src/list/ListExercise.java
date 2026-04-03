package list;

import application.Exercise;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class ListExercise extends Exercise {

    private int currentPhase = 0;
    private List<String> list;

    public ListExercise(Scanner scnr) {
        super(scnr);
        list = new ArrayList<>();
    }


    @Override
    protected void exerciseLogic() {
        switch (currentPhase){
            case 0:
                menuLogic();
                break;
            case 1:
                addElement();
                break;
            case 2:
                removeElementByIndex();
                break;
            case 3:
                removeElementByReference();
                break;
            case 4:
                clear();
                break;
        }
    }


    private void menuLogic(){
        System.out.println("Bienvenido " +
                "\n su lista actual es: " + list.toString() +
                "\n A- Agregar elemento a la lista" +
                "\n B- remover elemento por índice" +
                "\n C- Remover por referencia" +
                "\n D- clear");
        String userInput = scanner.nextLine().toUpperCase();
        switch (userInput) {
            case "A":
                currentPhase = 1;
                exerciseLogic();
                break;
            case "B":
                currentPhase = 2;
                exerciseLogic();
                break;
            case "C":
                currentPhase = 3;
                exerciseLogic();
                break;
            case "D":
                currentPhase = 4;
                exerciseLogic();
                break;
            default:
                System.out.println("ingrese otro input");
                menuLogic();
        }
    }

    private void addElement() {
        System.out.println("Cargue nuevo elemento");
        String newValue = scanner.nextLine().toUpperCase();
        list.add(newValue);
        System.out.println(list);
        System.out.println("Desea agregar otro elemento? y/otro valor");
        String userInput = scanner.nextLine();
        if(Objects.equals(userInput, "y")){
            addElement();
        } else {
            currentPhase = 0;
            menuLogic();
        }
    }

    private void removeElementByIndex() {
        System.out.println("Su lista actual es: " + list);
        System.out.println("Indique el índice de referencia");
        int index = scanner.nextInt();
        list.remove(index);
        System.out.println(list);
        System.out.println("Desea eliminar otro elemento? y/otro valor");
        String userInput = scanner.nextLine();
        if(Objects.equals(userInput, "y")){
            removeElementByIndex();
        } else {
            currentPhase = 0;
            menuLogic();
        }

    }
    private void removeElementByReference() {
        System.out.println("Indique el elemento de referencia");
        String reference = scanner.nextLine().toUpperCase();
        list.remove(reference);
    }
    private void clear() {
        list.clear();
    }



}
