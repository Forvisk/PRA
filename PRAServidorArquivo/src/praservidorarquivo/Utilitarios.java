/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package praservidorarquivo;

import java.util.Collections;

/**
 * @author Gustavo
 * Contem uma série de utilidades diversas
 *
 */
public class Utilitarios {

	/**
	 * @param i inteiro diferente de 0
	 * @return quantidade de digitos do numero i
	 */
	public static int getNumberOfDigits(int i) {
		if (i > 0) {
			return (int) (Math.log10(i) + 1);
		} else if (i < 0) {
			return (int) (Math.log10(-i) + 1);
		} else
			return 0;
	}

	/**
	 * @param str String a ser repetida
	 * @param n quantidade de vezes a repetir a String
	 * @return a String repetida
	 */
	public static String repeatString(String str, int n) {
		return String.join("", Collections.nCopies(n, str));
	}

}