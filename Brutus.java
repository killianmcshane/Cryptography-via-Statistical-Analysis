/**
 * A class that implements the methods declared in Cipher, however this time without a cipher key.
 * 
 * 
 * @author Killian McShane
 */
public class Brutus implements Cipher {
    
    public static void main (String args[]){
        int key = 0;
        String text = "";

        // Parses command line parameter
        // Checks if there is exactly 1 parameter
        if (args.length > 1) endEarly("Too many parameters!");
        if (args.length < 1) endEarly("Too few parameters!");
       
        try {
            text = args[0];
        }
        catch (NumberFormatException e) {
            endEarly("Could not parse first parameter (n) as String");
        }
       
        // Instantiates the Brutus class, "Et tu, Brute?"
        // Uses it to call decrypt()
        Brutus et_tu = new Brutus();
        System.out.println(et_tu.decrypt(text));
    }
   
   public static void endEarly(String message){
       System.out.println(message);
       System.out.println("Usage: java CaesarEncode n \"cipher text\"");
       System.exit(-1);
   }
    
    public Brutus() {
        
    }
    
    /** simply returns the unencrypted plainText parameter */
    public String encrypt(String plainText) {
        return (plainText);
    }
    
    /**
    * A method that decrypts a given text String based on statistical analysis.
    * The frequency of letters in each incremental rotation of the input text is measured against the frequency of letters in the english language.
    * The 'closeness' determines the correct number of rotations to translate the encrypted cipher to English.
    *
    * @param cipherText The encrypted text String to be rotated.
    * @return The decrypted text String.
    * @author Killian McShane
    */
    public String decrypt(String cipherText) {
        double[] chiArray = new double[26];
        double[] english = { 0.0855, 0.0160, 0.0316, 0.0387, 0.1210, 0.0218,
                     0.0209, 0.0496, 0.0733, 0.0022, 0.0081, 0.0421,
                     0.0253, 0.0717, 0.0747, 0.0207, 0.0010, 0.0633,
                     0.0673, 0.0894, 0.0268, 0.0106, 0.0183, 0.0019,
                     0.0172, 0.0011 };
        
        // Instantiates CaesarCipher class to use the previously defined rotate() method
        CaesarCipher cipher = new CaesarCipher(1);
        
        // Calls the chiSquared() method with its corresponding parameters to compute all 26 chi square values
        // After each chi squared value is stored in its corresponding array place, the cipher text is rotated wish a shift of 1 and the process repeats.
        for (int i=0;i<26;i++) {
            chiArray[i] = chiSquared(computeFrequencies(cipherText), english);
            cipherText = cipher.rotate(1, cipherText);
        }
        
        // Simply finds the minimum value in the chi squared array and it's corresponding shift index.
        double min = chiArray[0];
        int shift = 0;
        for (int i=1;i<26;i++) {
            if (chiArray[i] < min) {
                min = chiArray[i];
                shift = i;
            }
        }
        // Uses the minimum chi squared value's shift index as the key to decrypt the text.
        return cipher.rotate(shift, cipherText);  
    }
    
    /**
    *  A method that computes the frequency of each letter in a given text String.
    *
    * @param text The encrypted text String.
    * @return An array of type double containing the frequency of each letter in the given input String.
    * @author Killian McShane
    */
    private double[] computeFrequencies(String text) {
        // Initialises the array and sets all characters in text to lowercase
        // This makes the frequency count much easier
        double[] freqArray = new double[26];
        text = text.toLowerCase();
        
        // Initialise a counter to 0 for each iteration of the 26 letters in the array
        for (int j=0;j<26;j++) {
            int counter = 0; 
            
            // Searches through the text, incrementing the frequency of the corresponding letter accordingly
            for (int i=0;i<text.length();i++) {  
                if (text.charAt(i) == (char) (97 + j)) {
                    counter++;  
                }
            }

            // Sets each letter's frequency to it's counter value
            freqArray[j] = counter;
        }
        
        // Simply finds the total sum of all the letters in the array.
        // Updates the frequency array with the letter count divided by the total number of letters.
        double sum = 0;
        for (double value : freqArray) {
            sum += value;
        }
        for (int j=0;j<26;j++) {
            freqArray[j] = freqArray[j] / sum;
        }            
        return freqArray;
    }
    
    /**
    *  A method that computes the statistical analysis probability distribution of chi square from two given frequency arrays.
    *
    * @param freqsA Two arrays of type double. 'freqsA' represents the frequency array of the cipher text. 
    * @param freqsB Two arrays of type double. 'freqsB' represents the frequency array of English.
    * @return A single number of type double, representing the Chi-Square Sum of the given frequencies.
    * @author Killian McShane
    */
    private double chiSquared(double[] freqsA, double[] freqsB) {
        // Initialises each chiSquared value to 0
        double chiSq = 0;
        
        // Computes each individual chiSquared value for every letter.
        for (int i=0;i<26;i++) {
            chiSq += Math.pow(Math.abs(freqsA[i] - freqsB[i]), 2) / freqsB[i];
        }
        return chiSq;
    } 
    
}