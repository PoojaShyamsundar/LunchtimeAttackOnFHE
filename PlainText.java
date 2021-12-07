package lunchtimeattackonfhe;

import java.util.ArrayList;
import java.util.List;
public class PlainText {
    private String ptNumber;
    private String ptBinary ;
    
    public PlainText() {
    	
    }

    public PlainText(String ptNumber, String ptBinary){
      this.ptNumber = ptNumber;
      this.ptBinary = ptBinary;
    }

    public String getPlainTextNumber() {
      return ptNumber;
    }

    public void setPlainTextNumber(String ptNumber) {
     this.ptNumber = ptNumber;
    }

    public String getPlainTextBinary() {
     return ptBinary;
    }

    public void setPlainTextBinary(String ptBinary) {
     this.ptBinary = ptBinary;
    }
    
    public String toString() {
    	return ("PlainText ::\n"
    			+ "PlainText Number : " + this.ptNumber + "\n"
    			+ "PlainText Binary : " + this.ptBinary.toString()
    			);
    }

 }
