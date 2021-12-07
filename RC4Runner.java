package rc4encryption.runner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RC4Runner {

	public static void test(String[] args) {

		List<Integer> keySpace = new ArrayList<>(Arrays.asList(0x1a, 0x2b, 0x3c, 0x4d, 0x5e, 0x6f, 0x77));
		int keyLength = keySpace.size();
		System.out.println("keySpace :: ");
		printK(keySpace);

		// Create S space
		List<Integer> sSpace = new ArrayList<>();
		for(int i = 0; i < 256; i++) {
			sSpace.add(i);
		}
		System.out.println("Default S space :: ");
		printS(sSpace);

		//Start initialization of S space
		System.out.println("Begin initialization of S!");
		
		int i, j = 0;
		
		for(i = 0; i<256; i++) {
			j = (j + sSpace.get(i) + keySpace.get(i%keyLength))%256;
			//swapping S[i] and S[j]
			sSpace.set(i, sSpace.get(j)+sSpace.get(i));
			sSpace.set(j, sSpace.get(i)-sSpace.get(j));
			sSpace.set(i, sSpace.get(i)-sSpace.get(j));
		}
		
		System.out.println("Permutation of S space after initialization :: ");
		printS(sSpace);
		System.out.println("Indices i : j after initialization :: " + i + " : " + j);
		
		//Generate n bits of KeyStream
		List<Integer> keyStream = new ArrayList<>();
		
		i = j = 0;
		
		for(int bits = 0; bits < 1000 ; bits++) {
			i = (i+1)%256;
			j = (j + sSpace.get(i))%256;
			
			//swapping S[i] and S[j]
			sSpace.set(i, sSpace.get(j)+sSpace.get(i));
			sSpace.set(j, sSpace.get(i)-sSpace.get(j));
			sSpace.set(i, sSpace.get(i)-sSpace.get(j));
			
			keyStream.add(sSpace.get((sSpace.get(i) + sSpace.get(j))%256));
			
			if(bits == 99 || bits == 999) {
				System.out.println("Permutation of S space after generating " + (bits+1) + " bits of keyStream :: ");
				printS(sSpace);
				System.out.println("Indices i : j after " + (bits+1) + " bits of key stream generation :: " + i + " : " + j);
				System.out.println("KeyStream of " + (bits+1) + " bits ::");
				printK(keyStream);

			}
		}
		
	}

	public static void printK(List<Integer> s) {
		for(int i = 0; i<s.size(); i++) {
			if(i>1 && (i+1)%20 ==1) {
				System.out.println();
			}
			System.out.print(Integer.toHexString(s.get(i)) + "\t");
		}
		System.out.println();
	}

	public static void printS(List<Integer> s) {
		for(int i = 0; i<s.size(); i++) {
			if(i>1 && (i+1)%16 ==1) {
				System.out.println();
			}
			System.out.print(Integer.toHexString(s.get(i)) + "\t");
		}
		System.out.println();
	}
}
