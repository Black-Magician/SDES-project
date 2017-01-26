import java.util.*;

public class SDES

/**
 * This source file only contains methods that are required for part 2 on the
 * split work page for this project
 * 
 * @author Joe Cardona, Samantha Brech, Eric Carpizo
 */

{
	// 10-bit key
	private boolean[] key;
	// Subkey K1
	private boolean[] k2;
	// Subkey K2
	private boolean[] k1;

	public static void main(String args[]) {
		SDES s = new SDES();
		boolean[] b = { true, true, true, true, false, false, false, false };
		boolean[] r = { true, false, false, true };
		boolean[] l = { false, false, true, true };
		boolean[] k = { true, true, true, false, false, false, true, true,true,true};
		
		byte[] cipher = {-115,-17,-47,-113,-43,-47,15,84,-43,-113,-17,84,-43,79,58,15,64,-113,-43,65,-47,127,84,
						64, -43, -61, 79, -43, 93, -61, -14, 15, -43, -113, 84, -47, 127,
					-43, 127, 84, 127, 10, 84, 15, 64, 43};
		//byte[] cipher = s.encrypt("What are the names of your group members?");
		//System.out.println(s.bitArrayToByte(s.byteToBitArray((byte)60,8)));
		System.out.println(s.byteArrayToString(s.decrypt(cipher)));
		//s.show(s.expPerm(r, s.expansionPerm()));
		//s.show(s.concat(k, b));
	}

	/**
	 * Default constructor for SDES
	 */
	public SDES() {

	}

	/**
	 * Generate subkey K1 and K2
	 */
	public void generateSubkeys() {
		k1 = new boolean[8];
		k2 = new boolean[8];

		// Subkey 1
		k1[0] = key[0];
		k1[1] = key[6];
		k1[2] = key[8];
		k1[3] = key[3];
		k1[4] = key[7];
		k1[5] = key[2];
		k1[6] = key[9];
		k1[7] = key[5];

		// Subkey 2
		k2[0] = key[7];
		k2[1] = key[2];
		k2[2] = key[5];
		k2[3] = key[4];
		k2[4] = key[9];
		k2[5] = key[1];
		k2[6] = key[8];
		k2[7] = key[0];
	}

	private boolean[] initialPerm(boolean[] bits) {
		boolean[] ip = new boolean[8];
		ip[0] = bits[1];
		ip[1] = bits[5];
		ip[2] = bits[2];
		ip[3] = bits[0];
		ip[4] = bits[3];
		ip[5] = bits[7];
		ip[6] = bits[4];
		ip[7] = bits[6];
		return ip;
	}

	private boolean[] inverseIP(boolean[] bits) {
		boolean[] inverseIP = new boolean[8];
		inverseIP[0] = bits[3];
		inverseIP[1] = bits[0];
		inverseIP[2] = bits[2];
		inverseIP[3] = bits[4];
		inverseIP[4] = bits[6];
		inverseIP[5] = bits[1];
		inverseIP[6] = bits[7];
		inverseIP[7] = bits[5];
		return inverseIP;
	}

	/**
	 * Initializes an array with the place values the Expansion Permutation
	 * follows.
	 * 
	 * @author sam
	 * @return an array that represents the Expansion Permutation pattern
	 */
	private int[] expansionPerm() {
		int[] expansionPerm = new int[8];
		expansionPerm[0] = 3;
		expansionPerm[1] = 0;
		expansionPerm[2] = 1;
		expansionPerm[3] = 2;
		expansionPerm[4] = 1;
		expansionPerm[5] = 2;
		expansionPerm[6] = 3;
		expansionPerm[7] = 0;
		return expansionPerm;
	}

	/**
	 * This method takes an array of booleans as an input and converts it to a
	 * byte it adds up to an 8 bit input and uses it to add to the byte
	 * 
	 * @author Joe Cardona
	 * @param inp
	 * @return byte r in two's compliment format
	 */
	public byte bitArrayToByte(boolean[] inp) {
		byte r = 0;
		if (inp.length == 8) {
			for (int i = 0; i < inp.length; i++) {
				switch (i) {
				case 7:
					if (inp[i])
						r += 1;
					break;
				case 6:
					if (inp[i])
						r += 2;
					break;
				case 5:
					if (inp[i])
						r += 4;
					break;
				case 4:
					if (inp[i])
						r += 8;
					break;
				case 3:
					if (inp[i])
						r += 16;
					break;
				case 2:
					if (inp[i])
						r += 32;
					break;
				case 1:
					if (inp[i])
						r += 64;
					break;
				case 0:
					if (inp[i])
						r += 128;
					break;
				}
			}
		} else {
			for (int i = 0; i < inp.length; i++)

			{
				switch (i) {
				case 3:
					if (inp[i])
						r += 1;
					break;
				case 2:
					if (inp[i])
						r += 2;
					break;
				case 1:
					if (inp[i])
						r += 4;
					break;
				case 0:
					if (inp[i])
						r += 8;
					break;
				}
			}
		}

		return r;
	}

	/**
	 * This method takes the byte array that is in binary format and changes it
	 * to a printable string
	 * 
	 * @author Joe Cardona
	 * @param byte
	 *            array that is in binary form ex. {1,1,1,1,0,0,0,0}
	 * @return String representation of the inputed byteArray
	 */
	public String byteArrayToString(byte[] inp) {
		String r ;
		r = new String(inp);
		return r;
	}

	/**
	 * Takes the input of a byte and converts it into a two's compliment binary
	 * representation using modulo and division
	 * 
	 * @author Joe Cardona
	 * @param byte
	 *            input
	 * @param size
	 *            of the desired returned boolean array
	 * @return boolean array of desired size that is a binary representation of
	 *         byte b
	 */
	 public boolean[] byteToBitArray(byte b, int size)
	 {
	        String letter = Integer.toBinaryString(b);
	        if(letter.length() < size)
	        {
	            StringBuilder builder = new StringBuilder("0");
	            for(int i = 0; i < size - letter.length() - 1; i++){
	                builder.append("0");
	            }
	            letter = builder.append(letter).toString();
	        }
	        boolean[] bit = new boolean[size];
	        for(int i = 0; i < size; i++)
	        {
	            bit[size - 1 - i] = letter.charAt(letter.length() - 1 - i) == '1';
	        }
	        return bit;
	    }	
	
	
	
	

	/**
	 * Decrypt the given byte array.
	 * 
	 * @param cipher
	 *            An array of bytes representing the cipher text.
	 * @return An array of bytes representing the original plain text.
	 */
	public byte[] decrypt(byte[] cipher) 
	{
		if(this.key == null)
		{
			Scanner sc = new Scanner(System.in);
		
			// Get the key from the user
			getKey10(sc);
		
			// Generate subkeys
			generateSubkeys();
		}
		
		byte[] plain = new byte[cipher.length];
		for (int i = 0; i < cipher.length; i++)
		{
			plain[i] = decryptByte(cipher[i]);
		}
		return plain;
	}

	/**
	 * Decrypt a single byte using SDES
	 * 
	 * @param b
	 * @return decrypted byte
	 */
	public byte decryptByte(byte b) {
		// Separate each character of the string.
		boolean[] bits = byteToBitArray(b, 8);
		bits = initialPerm(bits);
		// Round 1 of SDES
		boolean[] r1Result = f(bits, k2);
		r1Result = swap(r1Result);
		
		// Round 2 of SDES
		boolean[] r2Result = f(r1Result, k1);
		
		// Create and return the inverse permutation
		return bitArrayToByte(inverseIP(r2Result));
	}
	private boolean[] swap(boolean[] b)
	{
		boolean[] swap;
		boolean[] lh = lh(b);
		boolean[] rh = rh(b);
		swap = concat(rh,lh);
		return swap;
	}

	/**
	 * Encrypt the given string using SDES Each character produces a byte of
	 * cipher.
	 * 
	 * @return An array of bytes representing the cipher text.
	 */
	public byte[] encrypt(String plain) {
		if(key == null)
		{
			Scanner sc = new Scanner(System.in);
		
			// Get the key from the user
			getKey10(sc);
		
			// Generate subkeys
			generateSubkeys();
		}
			
		// Create an array of Strings, separating each number by the spaces
		byte[] plainText = plain.getBytes();
		
		byte[] cipher = new byte[plainText.length];
		// Encrypt each byte
		for (int i = 0; i < plainText.length; i++) {
			cipher[i] = encryptByte(plainText[i]);
		}
		
		return cipher;

	}

	/**
	 * Encrypt a single byte using SDES
	 * 
	 * @param b
	 * @return encrypted byte
	 */
	public byte encryptByte(byte b) {
		// Separate each character of the string.
		boolean[] bits = byteToBitArray(b, 8);
		// Round 1 of SDES
		boolean[] r1Result = f(initialPerm(bits), k1);
		r1Result = swap(r1Result);
		// Round 2 of SDES
		boolean[] r2Result = f(r1Result, k2);
		// Create and return the inverse permutation
		return bitArrayToByte(inverseIP(r2Result));
	}
	 
	/**
	 * Send the bitArray to stdout as 1's and 0's
	 * 
	 * @param inp
	 */
	public void show(boolean[] inp) {
		// Iterate through the array
		for (int i = 0; i < inp.length; i++) {
			// if the value at the current index is true
			// send a 1 to stdout
			if (inp[i])
				System.out.print("1");
			// if the value at the current index is false
			// send a 1 to stdout
			else
				System.out.print("0");
		}
		System.out.println();
	}

	/**
	 * Send the byteArray to stdout
	 * 
	 * @param byteArray
	 */
	public void show(byte[] byteArray) {
		for (int i = 0; i < byteArray.length; i++)
		{
			System.out.println(byteArray[i]);
		}
	}

	/**
	 * Takes the inputs and concatenates them together where x is the left half
	 * and y is the right half appended to the left
	 * 
	 * @author Joe Cardona
	 * @param x
	 *            left half of input
	 * @param y
	 *            right half of input
	 * @return boolean array with a length of both inputs added
	 */
	// this method is assuming that x is the left and y is the right half of the
	// two being concat
	// x || y
	public boolean[] concat(boolean[] x, boolean[] y) {
		boolean[] r = new boolean[x.length + y.length];
		for (int i = 0; i < x.length; i++) {
			r[i] = x[i];
		}
		for (int i = 0; i < y.length; i++) {
			r[i + x.length] = y[i];
		}
		return r;
	}

	/**
	 * method that based on the epv and lenght of the input will make an
	 * permuted/selected/expanded version of the input array
	 * 
	 * @author Joe cardona
	 * @param inp
	 *            boolean array of either 4,8,10 length
	 * @param epv
	 *            integer array deciding where the inputs would be permuted to
	 *            as well as deciding the expansion of the method
	 * @return boolean array of a permuted/selected/expanded of the inputs
	 */
	public boolean[] expPerm(boolean[] inp, int[] epv) {
		boolean[] r = new boolean[epv.length];
		if ((epv.length == 8))// assuming that with a length of 8 that there is
								// supposed to be
		// an expansion/permutation/selection going on rather than just a
		// permutation which
		// comes from a 4 length epv
		{
			if (inp.length == 4)// checks if we are doing an
								// expansion/permutation
			{
				for (int i = 7; i >= 0; i--) {
					r[i] = inp[epv[i]];
				}
			} else if (inp.length == 8)// so we can just permute a larger input
			{
				for (int i = inp.length - 1; i >= 0; i--) {
					r[i] = inp[epv[i]];
				}
			} else if (inp.length == 10)// if we want to select from a key given
			{
				for (int i = 0; i < 8; i++) {
					r[i] = inp[epv[i]];
				}
			}
		} else if (epv.length == 4) // permutes a 4 bit number since
		{
			if (inp.length == 8) {
				return null;
			} else {
				for (int i = inp.length - 1; i >= 0; i--) {
					r[i] = inp[epv[i]];
				}
			}
		} else
			return null;

		return r;
	}

	/**
	 * @author sam
	 * @param x
	 *            the plain text
	 * @param k
	 *            an 8-bit key
	 * @return the resulting cipher text
	 */
	public boolean[] f(boolean[] x, boolean[] k) {
		// split x into its respective halves
		boolean[] left = lh(x);
		boolean[] right = rh(x);

		// call F(k, right)
		boolean[] feistelF = feistel(k, right);

		// xor the left half of x with the F(k, right) result
		boolean[] xorResult = xor(left, feistelF);

		// return the concatenated value of the xor result and the right half
		return concat(xorResult, right);

	}

	/**
	 * This method performs the necessary calculations to perform the Feistel
	 * function.
	 * 
	 * @author sam
	 * @param k
	 *            an 8-bit key
	 * @param x
	 * @return the result of the Feistel function
	 */
	public boolean[] feistel(boolean[] k, boolean[] x) {
		// initialize place-holders for expansion permutation and expand
		int[] expansion = expansionPerm();
		boolean[] expanded = expPerm(x, expansion);

		// xor the key and expansion permutation
		boolean[] xorResult = xor(k, expanded);
		// split the xor result into respective halves
		boolean[] leftXorResult = lh(xorResult);
		boolean[] rightXorResult = rh(xorResult);
		// booleanToString(leftXorResult);
		// get the s-box values for the respective halves
		boolean[] s0 = getS0(leftXorResult);
		boolean[] s1 = getS1(rightXorResult);
		// concatenate the halves and re-arrange into the p4 format
		boolean[] temp = concat(s0, s1);
		boolean[] p4 = new boolean[4];
		p4[0] = temp[1];
		p4[1] = temp[3];
		p4[2] = temp[2];
		p4[3] = temp[0];

		return p4;
	}
	
	
	 

	/**
	 * This method returns the appropriate s0 value.
	 * 
	 * @author sam
	 * @param x
	 *            the input for the S-box
	 * @return the s0 value
	 */
	private boolean[] getS0(boolean[] x) {
		boolean result[] = new boolean[2];

		if ((x[0] == false) && (x[3] == false)) {
			if ((x[1] == false) && (x[2] == false)) {
				result[0] = false;
				result[1] = true;
			} else if ((x[1] == false) && (x[2] == true)) {
				result[0] = false;
				result[1] = false;
			} else if ((x[1] == true) && (x[2] == false)) {
				result[0] = true;
				result[1] = true;
			} else {
				result[0] = true;
				result[1] = false;
			}
		} else if ((x[0] == false) && (x[3] == true)) {
			if ((x[1] == false) && (x[2] == false)) {
				result[0] = true;
				result[1] = true;
			} else if ((x[1] == false) && (x[2] == true)) {
				result[0] = true;
				result[1] = false;
			} else if ((x[1] == true) && (x[2] == false)) {
				result[0] = false;
				result[1] = true;
			} else {
				result[0] = false;
				result[1] = false;
			}
		} else if ((x[0] == true) && (x[3] == false)) {
			if ((x[1] == false) && (x[2] == false)) {
				result[0] = false;
				result[1] = false;
			} else if ((x[1] == false) && (x[2] == true)) {
				result[0] = true;
				result[1] = false;
			} else if ((x[1] == true) && (x[2] == false)) {
				result[0] = false;
				result[1] = true;
			} else {
				result[0] = true;
				result[1] = true;
			}
		} else {
			if ((x[1] == false) && (x[2] == false)) {
				result[0] = true;
				result[1] = true;
			} else if ((x[1] == false) && (x[2] == true)) {
				result[0] = false;
				result[1] = true;
			} else if ((x[1] == true) && (x[2] == false)) {
				result[0] = true;
				result[1] = true;
			} else {
				result[0] = true;
				result[1] = false;
			}
		}

		return result;
	}

	/**
	 * This method returns the appropriate s1 value.
	 * 
	 * @author sam
	 * @param x
	 *            the input for the S-box
	 * @return the s1 value
	 */
	private boolean[] getS1(boolean[] x) {
		boolean result[] = new boolean[2];

		if ((x[0] == false) && (x[3] == false)) {
			if ((x[1] == false) && (x[2] == false)) {
				result[0] = false;
				result[1] = false;
			} else if ((x[1] == false) && (x[2] == true)) {
				result[0] = false;
				result[1] = true;
			} else if ((x[1] == true) && (x[2] == false)) {
				result[0] = true;
				result[1] = false;
			} else {
				result[0] = true;
				result[1] = true;
			}
		} else if ((x[0] == false) && (x[3] == true)) {
			if ((x[1] == false) && (x[2] == false)) {
				result[0] = true;
				result[1] = false;
			} else if ((x[1] == false) && (x[2] == true)) {
				result[0] = false;
				result[1] = false;
			} else if ((x[1] == true) && (x[2] == false)) {
				result[0] = false;
				result[1] = true;
			} else {
				result[0] = true;
				result[1] = true;
			}
		} else if ((x[0] == true) && (x[3] == false)) {
			if ((x[1] == false) && (x[2] == false)) {
				result[0] = true;
				result[1] = true;
			} else if ((x[1] == false) && (x[2] == true)) {
				result[0] = false;
				result[1] = false;
			} else if ((x[1] == true) && (x[2] == false)) {
				result[0] = false;
				result[1] = true;
			} else {
				result[0] = false;
				result[1] = false;
			}
		} else {
			if ((x[1] == false) && (x[2] == false)) {
				result[0] = true;
				result[1] = false;
			} else if ((x[1] == false) && (x[2] == true)) {
				result[0] = false;
				result[1] = true;
			} else if ((x[1] == true) && (x[2] == false)) {
				result[0] = false;
				result[1] = false;
			} else {
				result[0] = true;
				result[1] = true;
			}
		}

		return result;
	}

	/**
	 * makes use of the keyboard to take in a 10 bit key for usage in decryption
	 * and encryption
	 * 
	 * @author Joe Cardona
	 * @param sc
	 *            scanner object input
	 */
	@SuppressWarnings("resource")
	public void getKey10(Scanner sc) {
		String nKey;
		boolean[] r;
		char[] byteArray;
		System.out.println("Enter a 10 digit key, using 1's and 0's");
		do {
			nKey = sc.nextLine();
			r = new boolean[10];
			byteArray = nKey.toCharArray();
			if (nKey.length() != 10) {
				System.out.println("key is incorrect length please input a correct length key");
			}
		} while (nKey.length() != 10);

		for (int i = 0; i < 10; i++) {
			if (byteArray[i] == '1') {
				r[i] = true;
			} else
				r[i] = false;
		}
		key = r;

	}

	/**
	 * returns the left half of the input
	 * 
	 * @author Joe Cardona
	 * @param inp
	 * @return boolean array of the left half of input
	 */
	public boolean[] lh(boolean[] inp) {
		boolean[] left = new boolean[inp.length / 2];
		for (int i = 0; i < inp.length / 2; i++) {
			left[i] = inp[i];
		}
		return left;
	}

	/**
	 * returns the right half of the input
	 * 
	 * @author Joe Cardona
	 * @param inp
	 * @return boolean array of the right half of input
	 */

	public boolean[] rh(boolean[] inp) {
		boolean[] right = new boolean[inp.length / 2];
		for (int i = 0, j = (inp.length / 2); i < inp.length / 2; i++, j++) {
			right[i] = inp[j];
		}
		return right;
	}


	public boolean[] xor(boolean[] x, boolean[] y) {
		boolean[] result = new boolean[x.length];
		for (int i = 0; i < x.length && i < y.length; i++) {
			if ((x[i] == true) && (y[i] == true)) {
				result[i] = false;
			} else if ((x[i] == false) && (y[i] == false)) {
				result[i] = false;
			} else {
				result[i] = true;
			}
		}
		return result;
	}

}
