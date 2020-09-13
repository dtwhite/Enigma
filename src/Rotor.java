public class Rotor{
    String rotorMapping;
    int rotorPosition;
    final int ASCIIOFFSET = 97;
    public Rotor(String rotorMap){
        int position = 0;
        rotorMapping = rotorMap;
    }

    public Rotor(String rotorMap, int offset){
        rotorMapping = rotorMap;
        rotorPosition = offset;
    }   

    public char encryptCharacter(char character){
        int index = (int)character - ASCIIOFFSET;
        return rotorMapping.substring(index, index + 1).charAt(0);
    } 

    public char secondPassCharacter(char character){
        for(int i = 0; i < rotorMapping.length(); i++){
            if(rotorMapping.charAt(i) == character)
               return (char)(i + ASCIIOFFSET);
        }
        return 'a';
    }

    public void displayRotor(){
        System.out.println(rotorMapping);
    }

    public void advanceRotor(){
        rotorPosition = (rotorPosition + 1) % 26;
        rotorMapping = rotorMapping.substring(1, rotorMapping.length()) + rotorMapping.substring(0, 1);
    }

    public int getRotorPosition(){
        return rotorPosition;
    }
}