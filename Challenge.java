package lunchtimeattackonfhe;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Challenge {

	public static void main(String[] args) {
		
		System.out.println("Driver method");
		System.out.println("**************");
		System.out.println("Running Attack..!");
		System.out.println("**************");
		/*
		 * Defining variables:
		 * c ~ cipher text (public)
		 * p ~ plain text (to be found and verified)
		 * w ~ secret key (to be found); w = L = U once while loop in attack method is complete
		 * d ~ range to which c will belong i.e. c belongs to [0, d)
		 * 
		 */
		
		List<Cipher> ciphers = readFile("public.txt");
		System.out.println(ciphers.size()-1); // First entry in ciphers is d
		List<PlainText> pts = new ArrayList<>();
		
		BigInteger d = new BigInteger(ciphers.get(0).getCipherBits().get(0));
		
//		BigInteger w = attack(d);
		BigInteger w = new BigInteger("1382614006537758947794899193386428635694177266040575331699547372738844574155388781044191873351681930112399181353056694358474450465353649132027870203801718686945659545301462212572042089773931505326191815944980524589629740015775413618609494224468992766442129369309838754970494979842951589425687891971930577025121209231305014591933429509494159514177");
		
//		System.out.println("w :" + w);

		StringBuilder plainTextBuilder = new StringBuilder();
		for(int i = 1; i<ciphers.size(); i++) {
			Cipher c = ciphers.get(i);
			PlainText pt = new PlainText();
			pt.setPlainTextNumber(c.getCipherNumber());
			pt.setPlainTextBinary(decryptCharacter(c.getCipherBits(), w, d));
			pts.add(pt);
			System.out.println(pt);
			
			//Testing conversion to characters
			int temp = Integer.parseInt(pt.getPlainTextBinary(), 2);
			System.out.println("PlainText Integer : " + temp);
			System.out.println("PlainText Character : " + new Character((char)temp).toString());
			System.out.println();
			plainTextBuilder.append(new Character((char)temp).toString());
		}
		
		System.out.println("Final Plain Text ::: " + plainTextBuilder.toString());
		System.out.println("**************");

	}
	
	public static BigInteger attack(BigInteger d) {
		
		/*Algorithm as mentioned in challenge, to attack and find the secret key w
		 * L = 0, U = d - 1
		 * while U - L > 1 do
		 * c = floor(d/(U - L))
		 * b = "OD"(c)----------> Calls to oracle with integer c
		 * q = (c + b) mod 2
		 * k = floor(Lc/d + 1/2)
		 * B = (k + 1/2)*d/c
		 * if (k mod 2 = q) then
		 * U = floor(B)
		 * else
		 * L = roof(B)
		 * end if
		 * end while
		 * return L
		 * 
		 * After the while loop gets over we'll get L=U=w
		 */

		BigDecimal two = new BigDecimal("2");
		BigDecimal one = new BigDecimal("1");
		
		BigDecimal range = new BigDecimal(d);
		System.out.println();
		System.out.println("d :" + range);
		BigDecimal U = range.subtract(one);
		BigDecimal L = new BigDecimal("0");
//		BigDecimal U = new BigDecimal("");
//		BigDecimal L = new BigDecimal("");
		BigDecimal c = new BigDecimal("0");
		BigDecimal b = new BigDecimal("0");
		BigDecimal q = new BigDecimal("0");
		BigDecimal k = new BigDecimal("0");
		BigDecimal B = new BigDecimal("0");

		System.out.println("U initial:" + U);
		System.out.println("L initial:" + L);
		
		while(U.compareTo(L) == 1) {
			c = range.divideToIntegralValue(U.subtract(L)); //floor
			System.out.println("c :" + c);
			
			b = BigDecimal.valueOf(contactOracle(c.toBigInteger())); //put a check for response to be always be either 0 or 1
			System.out.println("b :" + b);

			q = (c.add(b)).remainder(two); //mod 2
			System.out.println("q :" + q);

			k = new BigDecimal((((L.multiply(c)).divide(range, 200, RoundingMode.DOWN)).add(one.divide(two))).toBigInteger()); //floor
			System.out.println("k :" + k);

			B = (k.add(one.divide(two)).multiply(range)).divide(c, 200, RoundingMode.DOWN);
			System.out.println("B :" + B);

			if(k.remainder(two).equals(q)) {
				U = new BigDecimal(B.toBigInteger()); //floor
			} else {
				L = new BigDecimal(B.toBigInteger().add(new BigInteger("1"))); //roof
			}
			
			System.out.println("U : " + U);
			System.out.println("L : " + L);
		}
		
		return L.toBigInteger();
	}
	
	public static String decryptCharacter(List<String> cipherBits, BigInteger w, BigInteger d) {
		
		/*
		 * Decryption is computed by expression :: ((c*w) mod d) mod 2
		 * where result of (c*w) mod d 
		 * should be reduced to [-d/2, d/2) instead of [0, d) --------> Confirm by applying 
		 */
		BigInteger c = new BigInteger("0");
		BigInteger cwModD = new BigInteger("0");
		BigInteger p = new BigInteger("0");
		
		List<Integer> ptBits = new ArrayList<>();
		StringBuilder sb = new StringBuilder();

		for(String bit : cipherBits) {
			c = new BigInteger(bit);
			cwModD = fheMappedMod(c.multiply(w), d);
			
//			System.out.println("cwModD : " + cwModD);
			
			p = cwModD.mod(new BigInteger("2"));
			ptBits.add(p.intValue());
			
			sb.append(String.valueOf(p.intValue()));
		}
		String ptBinary = sb.toString();
		
//		System.out.println(ptBits);
//		System.out.println(ptBinary);
		
		return ptBinary;
	}

	private static BigInteger fheMappedMod(BigInteger z, BigInteger d) {

		/*
		 * We'll use the following formula-
		 * z mod d = z - (z/d)*d ; where z/d is rounded of to nearest integer
		 */
		
		BigDecimal tempZ = new BigDecimal(z);
		BigDecimal tempD = new BigDecimal(d);
		
		BigInteger result = new BigInteger("0");
		BigInteger q = new BigInteger("0");
		
//		if((tempZ.divide(tempD, 200, RoundingMode.UP)).compareTo(new BigDecimal("1.5")) >= 0) { // FAULTY CODE
		if((tempZ.remainder(tempD)).compareTo(tempD.divide(new BigDecimal("2"))) >=0) {
			q = (tempZ.divide(tempD, RoundingMode.UP)).toBigInteger();
//			System.out.println("Rounded UP");
		} else {
			q = (tempZ.divide(tempD, RoundingMode.DOWN)).toBigInteger();
//			System.out.println("Rounded Down");
		}
		
		result = z.subtract(q.multiply(d));
		
		return result;
	}

	public static List<Cipher> readFile(String filename){

		List<Cipher> ciphers = new ArrayList<Cipher>();
        List<String> cipherBits = new ArrayList<String>();
        List<String> dValue = new ArrayList<String>();
		List<String> file = new ArrayList<>();
		
		// Read the file and store it in memory
		File f = new File(filename);
        Scanner sc;
		try {
			sc = new Scanner(f);
			
	        while(sc.hasNext()) {
	        	file.add(sc.next());
	        }
	        
//	        System.out.println(file.get(0));

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		// Extract d from public.txt file 
		dValue.add(file.get(0).substring(2, file.get(0).length()-10));
		Cipher d = new Cipher();
		d.setCipherNumber("d");
		d.setCipherBits(dValue);
		ciphers.add(d);
//		System.out.println(d.toString());
		
		
		// Extract and store ciphers from values read
		
		for(int i = 1; i<file.size(); i++) {

			Cipher c = new Cipher();

			cipherBits = Arrays.asList(file.get(i).split(","));

			//Trim first and last value
			cipherBits.set(0, cipherBits.get(0).substring(1));
			cipherBits.set(6, cipherBits.get(6).substring(0, cipherBits.get(6).indexOf("}%")));

			c.setCipherNumber(String.valueOf(i-1));
			c.setCipherBits(cipherBits);
			ciphers.add(c);
			
		}

//		System.out.println(ciphers.get(0).toString());
//		System.out.println(ciphers.get(1).toString());
        
		return ciphers;
	}
	
	public static int contactOracle(BigInteger c) {
		
		URL oracle = null;
		HttpURLConnection conn = null;
		int responseCode = 0;
		String response = null;
		
		try {
		
			oracle = new URL("https://www.mysterytwisterc3.org/cgi-bin/mtc3-ramae-11-homomorphic.cgi?c=" + c.toString());
//			System.out.println("Contacting the great Oracle!");
			conn = (HttpURLConnection) oracle.openConnection();
//			System.out.println("Connection established");
			conn.setRequestMethod("GET");
			System.out.println("Fetching response from the Oracle...");
			responseCode = conn.getResponseCode();
			
			if(responseCode == HttpURLConnection.HTTP_OK) {
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String inputLine;
				StringBuffer sb = new StringBuffer();

				while ((inputLine = br.readLine()) != null) {
					sb.append(inputLine);
				}
				br.close();

				// store and print result
				response = sb.toString();
//				System.out.println(response);
				
				if(!response.contains("permission")) {
					return Integer.parseInt(response);
				} else {
					System.out.println("Illegal number being asked to decrypt.");
				}
				
			} else {
				System.out.println("GET request not worked");
			}
			
			conn.disconnect();
					
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return -1;
	}

}
