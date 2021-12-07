package lunchtimeattackonfhe;

import java.util.ArrayList;
import java.util.List;
public class Cipher {
    private String cipherNumber;
    List<String> cipherBits = new ArrayList<String>();
    
    public Cipher() {
    	
    }

    public Cipher(String cipherNumber, List<String> cipherBits){
      this.cipherNumber = cipherNumber;
      this.cipherBits = cipherBits;
    }

    public String getCipherNumber() {
      return cipherNumber;
    }

    public void setCipherNumber(String cipherNumber) {
     this.cipherNumber = cipherNumber;
    }

    public List<String> getCipherBits() {
     return cipherBits;
    }

    public void setCipherBits(List<String> cipherBits) {
     this.cipherBits = cipherBits;
    }
    
    public String toString() {
    	return ("Cipher ::\n"
    			+ "cipherNumber : " + this.cipherNumber + "\n"
    			+ "cipherBits list : " + this.cipherBits.toString() + "\n"
    			);
    }

 }
