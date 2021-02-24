/**
 * A class that implements the method rotate() declared in RotationCipher, related to encoding/decoding using a shift key.
 * 
 * 
 * @author Killian McShane
 */
public class CaesarCipher extends RotationCipher {
    
    public CaesarCipher(int key) {
        super(key);
    }
    
    /**
    *  A method that rotates the characters in a given string of text by a given number of shifts through the alphabet.
    *
    * @param text The cipher key int and the text String to be rotated.
    * @return The rotated text String.
    * @author Killian McShane
    */
    protected String rotate(int shift, String text) {
        int length = text.length();
        String rotatedText = "";
        
        // Iterates through each character in 'text'.
        // Checks whether it is upper/lower case or punctuation.
        // Rotates letters, while leaving spaces/punctuation in their original place.
        // Appends each character to the rotatedText variable and finally returns it.
        
        for (int i=0;i<length;i++) {
            char ch = text.charAt(i);
            
            if ((Character.isLetter(ch)) && (Character.isLowerCase(ch))) {
                // Lower case letter
                ch = (char) ((ch - 'a' + shift) % 26  + 'a');
                rotatedText += ch;
                
            }
            else {
                if ((Character.isLetter(ch)) && (Character.isUpperCase(ch))) {
                    // Upper case letter
                    ch = (char) ((ch - 'A' + shift) % 26  + 'A');
                    rotatedText += ch;
                    
                }
                else {
                    // Punctuation or space
                    rotatedText += ch;
                }
            }
        }
        
        return rotatedText;
    }
    
}