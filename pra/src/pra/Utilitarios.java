package pra;

import java.util.Collections;

public class Utilitarios {

	public static int getNumberOfDigits(int i){
		if (i > 0){
			return (int)(Math.log10(i)+1);
		} else if (i < 0){
			return (int)(Math.log10(-i)+1);
		}
		else return 0;
	}
	
	public static String repeatString(String str, int n){
		return String.join("", Collections.nCopies(n, str));
	}
	
}
