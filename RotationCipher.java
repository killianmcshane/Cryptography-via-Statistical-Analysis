/**
 * A class that implements the methods declared in Cipher, related to encoding/decoding using a cipher key.
 * Defines the abstract method rotate().
 * 
 * @author Killian McShane
 */
public abstract class RotationCipher implements Cipher {
    
    protected int shift;
    
    public RotationCipher(int key) {
        shift = key;
    } 
    
    public String encrypt(String plainText) {
        return rotate(shift, plainText);
        
    }
    
    public String decrypt(String cipherText) {
        return rotate(26-shift, cipherText);
        
    }
    
    protected abstract String rotate(int shift, String text);
    
}