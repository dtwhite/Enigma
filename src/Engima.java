import java.util.HashMap;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Engima{
    private String reflector;
    private HashMap<String, String> plugboard;
    private Rotor[] rotors;
    final int ASCIIOFFSET = 97;


    public Engima(String rotors){
        initRotorsAndReflector(rotors);
        reflector = "yruhqsldpxingokmiebfzcwjat";
    }


    /* -------------------------------------------------------------   Public Methods   ------------------------------------------------------------ */
    public void encrypt(File input){

    }

    public void encrypt(String input){
        for(int i = 0; i < input.length(); i++){
            char character = input.charAt(i);
            character = encryptViaRotors(character, true);
            character = encryptViaReflector(character);
            //System.out.println("The value after the reflector is " + character);
            character = encryptViaRotors(character, false);
            System.out.print(character);
        }
    }

    public void decrypt(String output){
        //System.out.println(output);
    }

    /*   ------------------------------------------------------------   Private Methods   ---------------------------------------------------------- */

    /**
     * Initializes the Engima's rotors and reflector. 
     */
    private void initRotorsAndReflector(String rotorsType){
        try{
            switch(rotorsType){
                case "Navy":
                    break;
                case "Army":
                    establishRotors("../config/army-rotors.txt");
                    break;
                default:
                    break;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    private void establishRotors(String rotorPath){
        try{
            File configuration = new File(rotorPath);
            Scanner scan = new Scanner(configuration);
            int numberOfRotors = 0;
            while(scan.hasNextLine()){
                scan.nextLine();
                numberOfRotors++;
            }
            this.rotors = new Rotor[numberOfRotors];
            scan = new Scanner(configuration);
            int index = 0;
            while(scan.hasNextLine()){
                String rotor = scan.nextLine();
                rotors[index] = new Rotor(rotor);
                index++;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    private char encryptViaRotors(char character, boolean forwardDirection){
        char encryptedCharacter = character;
        //System.out.print("The given character " + character + " was encrypted as: ");
        if(forwardDirection){
            for(int i = 0; i < rotors.length; i++){
                if(i < rotors.length - 1 && rotors[i].getRotorPosition() == 25){
                    rotors[i].advanceRotor();
                    rotors[i + 1].advanceRotor();
                }
                else if(i == 0){
                    rotors[i].advanceRotor();
                }
                encryptedCharacter = rotors[i].encryptCharacter(encryptedCharacter);
                //System.out.print(encryptedCharacter + " ");
            }
            //System.out.println("");
            return encryptedCharacter;
        }
        else{
            for(int i = rotors.length - 1; i >= 0; i--){
                encryptedCharacter = rotors[i].secondPassCharacter(encryptedCharacter);
                //System.out.print(encryptedCharacter + " ");
            }
            //System.out.println("");
            return encryptedCharacter;
        }

    }

    private char encryptViaReflector(char character){
        int index = (int)character - ASCIIOFFSET;
        return reflector.substring(index, index + 1).charAt(0);
    }

    public static void main(String args[]){
        if(args.length == 0){
            System.out.println("Please enter arguments");
            return;
        }
        Engima engima = new Engima("Army");
        switch(args[0]){
            case "-e":
                engima.encrypt(args[1]);
                break;
            case "-d":
                engima.decrypt(args[1]);
                break;
            default:
                System.out.println("Please provide an identifiable flag");
        }
    }


}
