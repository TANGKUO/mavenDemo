package com.tangkuo.cn.utils.string;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.BreakIterator;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;

import org.apache.oro.text.perl.Perl5Util;

public class StringEncrypt {

	/** 字符集编码 */
	public static final String GBK = "GBK";

	public static final String ISO8859_1 = "ISO-8859-1";
	
	public static final String UTF_8 = "UTF-8";

	/**
	 * 改变字符串的编码 oldCharset(字符集) - newCharset(字符集)
	 * 
	 * @param str
	 * @param oldCharset
	 * @param newCharset
	 * @return String
	 * @throws UnsupportedEncodingException
	 */
	public static String swichCharset(String str, String oldCharset,
			String newCharset) throws UnsupportedEncodingException {
		if (str == null) {
			return null;
		}
		return new String(str.getBytes(oldCharset), newCharset);
	}

	/**
	 * 将字符串编码ISO_8859_1
	 * 
	 * @param arg
	 * @return
	 */
	public static String gbk2Iso(String arg) {
		try {
			return swichCharset(arg, GBK, ISO8859_1);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将ISO_8859_1编码的字符串转换成GBK
	 * 
	 * @param isoStr
	 * @return
	 */
	public static String iso2Gbk(String isoStr) {
		try {
			return swichCharset(isoStr, ISO8859_1, GBK);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static List arrayToList(String[] array) {
		List list = new ArrayList();
		for (int i = 0; i < array.length; i++) {
			list.add(array[i]);
		}
		return list;
	}

	// Constants used by escapeHTMLTags
	private static final char[] QUOTE_ENCODE = "&quot;".toCharArray();

	private static final char[] AMP_ENCODE = "&amp;".toCharArray();

	private static final char[] LT_ENCODE = "&lt;".toCharArray();

	private static final char[] GT_ENCODE = "&gt;".toCharArray();

	/**
	 * Replaces all instances of oldString with newString in line.
	 * 
	 * @param line
	 *            the String to search to perform replacements on
	 * @param oldString
	 *            the String that should be replaced by newString
	 * @param newString
	 *            the String that will replace all instances of oldString
	 * 
	 * @return a String will all instances of oldString replaced by newString
	 */
	public static final String replace(String line, String oldString,
			String newString) {
		if (line == null) {
			return null;
		}
		int i = 0;
		if ((i = line.indexOf(oldString, i)) >= 0) {
			char[] line2 = line.toCharArray();
			char[] newString2 = newString.toCharArray();
			int oLength = oldString.length();
			StringBuffer buf = new StringBuffer(line2.length);
			buf.append(line2, 0, i).append(newString2);
			i += oLength;
			int j = i;
			while ((i = line.indexOf(oldString, i)) > 0) {
				buf.append(line2, j, i - j).append(newString2);
				i += oLength;
				j = i;
			}
			buf.append(line2, j, line2.length - j);
			return buf.toString();
		}
		return line;
	}

	/**
	 * Replaces all instances of oldString with newString in line with the added
	 * feature that matches of newString in oldString ignore case.
	 * 
	 * @param line
	 *            the String to search to perform replacements on
	 * @param oldString
	 *            the String that should be replaced by newString
	 * @param newString
	 *            the String that will replace all instances of oldString
	 * 
	 * @return a String will all instances of oldString replaced by newString
	 */
	public static final String replaceIgnoreCase(String line, String oldString,
			String newString) {
		if (line == null) {
			return null;
		}
		String lcLine = line.toLowerCase();
		String lcOldString = oldString.toLowerCase();
		int i = 0;
		if ((i = lcLine.indexOf(lcOldString, i)) >= 0) {
			char[] line2 = line.toCharArray();
			char[] newString2 = newString.toCharArray();
			int oLength = oldString.length();
			StringBuffer buf = new StringBuffer(line2.length);
			buf.append(line2, 0, i).append(newString2);
			i += oLength;
			int j = i;
			while ((i = lcLine.indexOf(lcOldString, i)) > 0) {
				buf.append(line2, j, i - j).append(newString2);
				i += oLength;
				j = i;
			}
			buf.append(line2, j, line2.length - j);
			return buf.toString();
		}
		return line;
	}

	/**
	 * Replaces all instances of oldString with newString in line with the added
	 * feature that matches of newString in oldString ignore case. The count
	 * paramater is set to the number of replaces performed.
	 * 
	 * @param line
	 *            the String to search to perform replacements on
	 * @param oldString
	 *            the String that should be replaced by newString
	 * @param newString
	 *            the String that will replace all instances of oldString
	 * @param count
	 *            a value that will be updated with the number of replaces
	 *            performed.
	 * 
	 * @return a String will all instances of oldString replaced by newString
	 */
	public static final String replaceIgnoreCase(String line, String oldString,
			String newString, int[] count) {
		if (line == null) {
			return null;
		}
		String lcLine = line.toLowerCase();
		String lcOldString = oldString.toLowerCase();
		int i = 0;
		if ((i = lcLine.indexOf(lcOldString, i)) >= 0) {
			int counter = 0;
			char[] line2 = line.toCharArray();
			char[] newString2 = newString.toCharArray();
			int oLength = oldString.length();
			StringBuffer buf = new StringBuffer(line2.length);
			buf.append(line2, 0, i).append(newString2);
			i += oLength;
			int j = i;
			while ((i = lcLine.indexOf(lcOldString, i)) > 0) {
				counter++;
				buf.append(line2, j, i - j).append(newString2);
				i += oLength;
				j = i;
			}
			buf.append(line2, j, line2.length - j);
			count[0] = counter;
			return buf.toString();
		}
		return line;
	}

	/**
	 * Replaces all instances of oldString with newString in line. The count
	 * Integer is updated with number of replaces.
	 * 
	 * @param line
	 *            the String to search to perform replacements on
	 * @param oldString
	 *            the String that should be replaced by newString
	 * @param newString
	 *            the String that will replace all instances of oldString
	 * 
	 * @return a String will all instances of oldString replaced by newString
	 */
	public static final String replace(String line, String oldString,
			String newString, int[] count) {
		if (line == null) {
			return null;
		}
		int i = 0;
		if ((i = line.indexOf(oldString, i)) >= 0) {
			int counter = 0;
			counter++;
			char[] line2 = line.toCharArray();
			char[] newString2 = newString.toCharArray();
			int oLength = oldString.length();
			StringBuffer buf = new StringBuffer(line2.length);
			buf.append(line2, 0, i).append(newString2);
			i += oLength;
			int j = i;
			while ((i = line.indexOf(oldString, i)) > 0) {
				counter++;
				buf.append(line2, j, i - j).append(newString2);
				i += oLength;
				j = i;
			}
			buf.append(line2, j, line2.length - j);
			count[0] = counter;
			return buf.toString();
		}
		return line;
	}

	/**
	 * This method takes a string which may contain HTML tags (ie, &lt;b&gt;,
	 * &lt;table&gt;, etc) and converts the '&lt'' and '&gt;' characters to
	 * their HTML escape sequences.
	 * 
	 * @param in
	 *            the text to be converted.
	 * @return the input string with the characters '&lt;' and '&gt;' replaced
	 *         with their HTML escape sequences.
	 */
	public static final String escapeHTMLTags(String in) {
		if (in == null) {
			return null;
		}
		char ch;
		int i = 0;
		int last = 0;
		char[] input = in.toCharArray();
		int len = input.length;
		StringBuffer out = new StringBuffer((int) (len * 1.3));
		for (; i < len; i++) {
			ch = input[i];
			if (ch > '>') {
				continue;
			} else if (ch == '<') {
				if (i > last) {
					out.append(input, last, i - last);
				}
				last = i + 1;
				out.append(LT_ENCODE);
			} else if (ch == '>') {
				if (i > last) {
					out.append(input, last, i - last);
				}
				last = i + 1;
				out.append(GT_ENCODE);
			}
		}
		if (last == 0) {
			return in;
		}
		if (i > last) {
			out.append(input, last, i - last);
		}
		return out.toString();
	}

	
	/**
	 * Hashes a String using the Md5 algorithm and returns the result as a
	 * String of hexadecimal numbers. This method is synchronized to avoid
	 * excessive MessageDigest object creation. If calling this method becomes a
	 * bottleneck in your code, you may wish to maintain a pool of MessageDigest
	 * objects instead of using this method.
	 * <p>
	 * A hash is a one-way function -- that is, given an input, an output is
	 * easily computed. However, given the output, the input is almost
	 * impossible to compute. This is useful for passwords since we can store
	 * the hash and a hacker will then have a very hard time determining the
	 * original password.
	 * <p>
	 * In Jive, every time a user logs in, we simply take their plain text
	 * password, compute the hash, and compare the generated hash to the stored
	 * hash. Since it is almost impossible that two passwords will generate the
	 * same hash, we know if the user gave us the correct password or not. The
	 * only negative to this system is that password recovery is basically
	 * impossible. Therefore, a reset password method is used instead.
	 * 
	 * @param data
	 *            the String to compute the hash of.
	 * @return a hashed version of the passed-in String
	 */
	public synchronized static final String hash(String data) {
		MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException nsae) {
			System.err.println("Failed to load the MD5 MessageDigest.");
			nsae.printStackTrace();
		}
		// Now, compute hash.
		digest.update(data.getBytes());
		return encodeHex(digest.digest());
	}
	public synchronized static final String hash(String data,String charsetName) {
		MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException nsae) {
			System.err.println("Failed to load the MD5 MessageDigest.");
			nsae.printStackTrace();
		}
		// Now, compute hash.
		try {
			digest.update(data.getBytes(charsetName));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return encodeHex(digest.digest());
	}
	/**
	 * Turns an array of bytes into a String representing each byte as an
	 * unsigned hex number.
	 * <p>
	 * Method by Santeri Paavolainen, Helsinki Finland 1996 <br>
	 * (c) Santeri Paavolainen, Helsinki Finland 1996 <br>
	 * Distributed under LGPL.
	 * 
	 * @param bytes
	 *            an array of bytes to convert to a hex-string
	 * @return generated hex string
	 */
	public static final String encodeHex(byte[] bytes) {
		StringBuffer buf = new StringBuffer(bytes.length * 2);
		int i;

		for (i = 0; i < bytes.length; i++) {
			if (((int) bytes[i] & 0xff) < 0x10) {
				buf.append("0");
			}
			buf.append(Long.toString((int) bytes[i] & 0xff, 16));
		}
		return buf.toString();
	}

	/**
	 * Turns a hex encoded string into a byte array. It is specifically meant to
	 * "reverse" the toHex(byte[]) method.
	 * 
	 * @param hex
	 *            a hex encoded String to transform into a byte array.
	 * @return a byte array representing the hex String[
	 */
	public static final byte[] decodeHex(String hex) {
		char[] chars = hex.toCharArray();
		byte[] bytes = new byte[chars.length / 2];
		int byteCount = 0;
		for (int i = 0; i < chars.length; i += 2) {
			byte newByte = 0x00;
			newByte |= hexCharToByte(chars[i]);
			newByte <<= 4;
			newByte |= hexCharToByte(chars[i + 1]);
			bytes[byteCount] = newByte;
			byteCount++;
		}
		return bytes;
	}

	/**
	 * Returns the the byte value of a hexadecmical char (0-f). It's assumed
	 * that the hexidecimal chars are lower case as appropriate.
	 * 
	 * @param ch
	 *            a hexedicmal character (0-f)
	 * @return the byte value of the character (0x00-0x0F)
	 */
	private static final byte hexCharToByte(char ch) {
		switch (ch) {
		case '0':
			return 0x00;
		case '1':
			return 0x01;
		case '2':
			return 0x02;
		case '3':
			return 0x03;
		case '4':
			return 0x04;
		case '5':
			return 0x05;
		case '6':
			return 0x06;
		case '7':
			return 0x07;
		case '8':
			return 0x08;
		case '9':
			return 0x09;
		case 'a':
			return 0x0A;
		case 'b':
			return 0x0B;
		case 'c':
			return 0x0C;
		case 'd':
			return 0x0D;
		case 'e':
			return 0x0E;
		case 'f':
			return 0x0F;
		}
		return 0x00;
	}

	// *********************************************************************
	// * Base64 - a simple base64 encoder and decoder.
	// *
	// * Copyright (c) 1999, Bob Withers - bwit@pobox.com
	// *
	// * This code may be freely used for any purpose, either personal
	// * or commercial, provided the authors copyright notice remains
	// * intact.
	// *********************************************************************

	/**
	 * Encodes a String as a base64 String.
	 * 
	 * @param data
	 *            a String to encode.
	 * @return a base64 encoded String.
	 */
	public static String encodeBase64(String data) {
		return encodeBase64(data.getBytes());
	}

	/**
	 * Encodes a byte array into a base64 String.
	 * 
	 * @param data
	 *            a byte array to encode.
	 * @return a base64 encode String.
	 */
	public static String encodeBase64(byte[] data) {
		int c;
		int len = data.length;
		StringBuffer ret = new StringBuffer(((len / 3) + 1) * 4);
		for (int i = 0; i < len; ++i) {
			c = (data[i] >> 2) & 0x3f;
			ret.append(cvt.charAt(c));
			c = (data[i] << 4) & 0x3f;
			if (++i < len) {
				c |= (data[i] >> 4) & 0x0f;
			}

			ret.append(cvt.charAt(c));
			if (i < len) {
				c = (data[i] << 2) & 0x3f;
				if (++i < len) {
					c |= (data[i] >> 6) & 0x03;
				}

				ret.append(cvt.charAt(c));
			} else {
				++i;
				ret.append((char) fillchar);
			}

			if (i < len) {
				c = data[i] & 0x3f;
				ret.append(cvt.charAt(c));
			} else {
				ret.append((char) fillchar);
			}
		}
		return ret.toString();
	}

	/**
	 * Decodes a base64 String.
	 * 
	 * @param data
	 *            a base64 encoded String to decode.
	 * @return the decoded String.
	 */
	public static String decodeBase64(String data) {
		return decodeBase64(data.getBytes());
	}

	/**
	 * Decodes a base64 aray of bytes.
	 * 
	 * @param data
	 *            a base64 encode byte array to decode.
	 * @return the decoded String.
	 */
	public static String decodeBase64(byte[] data) {
		int c, c1;
		int len = data.length;
		StringBuffer ret = new StringBuffer((len * 3) / 4);
		for (int i = 0; i < len; ++i) {
			c = cvt.indexOf(data[i]);
			++i;
			c1 = cvt.indexOf(data[i]);
			c = ((c << 2) | ((c1 >> 4) & 0x3));
			ret.append((char) c);
			if (++i < len) {
				c = data[i];
				if (fillchar == c) {
					break;
				}

				c = cvt.indexOf((char) c);
				c1 = ((c1 << 4) & 0xf0) | ((c >> 2) & 0xf);
				ret.append((char) c1);
			}

			if (++i < len) {
				c1 = data[i];
				if (fillchar == c1) {
					break;
				}

				c1 = cvt.indexOf((char) c1);
				c = ((c << 6) & 0xc0) | c1;
				ret.append((char) c);
			}
		}
		return ret.toString();
	}

	private static final int fillchar = '=';

	private static final String cvt = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
			+ "abcdefghijklmnopqrstuvwxyz" + "0123456789+/";

	/**
	 * Converts a line of text into an array of lower case words using a
	 * BreakIterator.wordInstance().
	 * <p>
	 * 
	 * This method is under the Jive Open Source Software License and was
	 * written by Mark Imbriaco.
	 * 
	 * @param text
	 *            a String of text to convert into an array of words
	 * @return text broken up into an array of words.
	 */
	public static final String[] toLowerCaseWordArray(String text) {
		if (text == null || text.length() == 0) {
			return new String[0];
		}

		ArrayList wordList = new ArrayList();
		BreakIterator boundary = BreakIterator.getWordInstance();
		boundary.setText(text);
		int start = 0;

		for (int end = boundary.next(); end != BreakIterator.DONE; start = end, end = boundary
				.next()) {
			String tmp = text.substring(start, end).trim();
			// Remove characters that are not needed.
			tmp = replace(tmp, "+", "");
			tmp = replace(tmp, "/", "");
			tmp = replace(tmp, "\\", "");
			tmp = replace(tmp, "#", "");
			tmp = replace(tmp, "*", "");
			tmp = replace(tmp, ")", "");
			tmp = replace(tmp, "(", "");
			tmp = replace(tmp, "&", "");
			if (tmp.length() > 0) {
				wordList.add(tmp);
			}
		}
		return (String[]) wordList.toArray(new String[wordList.size()]);
	}

	/**
	 * Pseudo-random number generator object for use with randomString(). The
	 * Random class is not considered to be cryptographically secure, so only
	 * use these random Strings for low to medium security applications.
	 */
	private static Random randGen = new Random();

	/**
	 * Array of numbers and letters of mixed case. Numbers appear in the list
	 * twice so that there is a more equal chance that a number will be picked.
	 * We can use the array to get a random number or letter by picking a random
	 * array index.
	 */
	private static char[] numbersAndLetters = ("0123456789abcdefghijklmnopqrstuvwxyz"
			+ "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();

	/**
	 * Returns a random String of numbers and letters (lower and upper case) of
	 * the specified length. The method uses the Random class that is built-in
	 * to Java which is suitable for low to medium grade security uses. This
	 * means that the output is only pseudo random, i.e., each number is
	 * mathematically generated so is not truly random.
	 * <p>
	 * 
	 * The specified length must be at least one. If not, the method will return
	 * null.
	 * 
	 * @param length
	 *            the desired length of the random String to return.
	 * @return a random String of numbers and letters of the specified length.
	 */
	public static final String randomString(int length) {
		if (length < 1) {
			return null;
		}
		// Create a char buffer to put random letters and numbers in.
		char[] randBuffer = new char[length];
		for (int i = 0; i < randBuffer.length; i++) {
			randBuffer[i] = numbersAndLetters[randGen.nextInt(71)];
		}
		return new String(randBuffer);
	}

	/**
	 * Intelligently chops a String at a word boundary (whitespace) that occurs
	 * at the specified index in the argument or before. However, if there is a
	 * newline character before <code>length</code>, the String will be
	 * chopped there. If no newline or whitespace is found in
	 * <code>string</code> up to the index <code>length</code>, the String
	 * will chopped at <code>length</code>.
	 * <p>
	 * For example, chopAtWord("This is a nice String", 10) will return "This is
	 * a" which is the first word boundary less than or equal to 10 characters
	 * into the original String.
	 * 
	 * @param string
	 *            the String to chop.
	 * @param length
	 *            the index in <code>string</code> to start looking for a
	 *            whitespace boundary at.
	 * @return a substring of <code>string</code> whose length is less than or
	 *         equal to <code>length</code>, and that is chopped at
	 *         whitespace.
	 */
	public static final String chopAtWord(String string, int length) {
		if (string == null) {
			return string;
		}

		char[] charArray = string.toCharArray();
		int sLength = string.length();
		if (length < sLength) {
			sLength = length;
		}

		// First check if there is a newline character before length; if so,
		// chop word there.
		for (int i = 0; i < sLength - 1; i++) {
			// Windows
			if (charArray[i] == '\r' && charArray[i + 1] == '\n') {
				return string.substring(0, i + 1);
			}
			// Unix
			else if (charArray[i] == '\n') {
				return string.substring(0, i);
			}
		}
		// Also check boundary case of Unix newline
		if (charArray[sLength - 1] == '\n') {
			return string.substring(0, sLength - 1);
		}

		// Done checking for newline, now see if the total string is less than
		// the specified chop point.
		if (string.length() < length) {
			return string;
		}

		// No newline, so chop at the first whitespace.
		for (int i = length - 1; i > 0; i--) {
			if (charArray[i] == ' ') {
				return string.substring(0, i).trim();
			}
		}

		// Did not find word boundary so return original String chopped at
		// specified length.
		return string.substring(0, length);
	}

	// Create a regular expression engine that is used by the highlightWords
	// method below.
	private static Perl5Util perl5Util = new Perl5Util();

	/**
	 * Highlights words in a string. Words matching ignores case. The actual
	 * higlighting method is specified with the start and end higlight tags.
	 * Those might be beginning and ending HTML bold tags, or anything else.
	 * <p>
	 * 
	 * This method is under the Jive Open Source Software License and was
	 * written by Mark Imbriaco.
	 * 
	 * @param string
	 *            the String to highlight words in.
	 * @param words
	 *            an array of words that should be highlighted in the string.
	 * @param startHighlight
	 *            the tag that should be inserted to start highlighting.
	 * @param endHighlight
	 *            the tag that should be inserted to end highlighting.
	 * @return a new String with the specified words highlighted.
	 */
	public static final String highlightWords(String string, String[] words,
			String startHighlight, String endHighlight) {
		if (string == null || words == null || startHighlight == null
				|| endHighlight == null) {
			return null;
		}

		StringBuffer regexp = new StringBuffer();

		// Iterate through each word and generate a word list for the regexp.
		for (int x = 0; x < words.length; x++) {
			// Excape "|" and "/" to keep us out of trouble in our regexp.
			words[x] = perl5Util.substitute("s#([\\|\\/\\.])#\\\\$1#g",
					words[x]);
			if (regexp.length() > 0) {
				regexp.append("|");
			}
			regexp.append(words[x]);
		}

		// Escape the regular expression delimiter ("/").
		startHighlight = perl5Util.substitute("s#\\/#\\\\/#g", startHighlight);
		endHighlight = perl5Util.substitute("s#\\/#\\\\/#g", endHighlight);

		// Build the regular expression. insert() the first part.
		regexp.insert(0, "s/\\b(");
		// The word list is here already, so just append the rest.
		regexp.append(")\\b/");
		regexp.append(startHighlight);
		regexp.append("$1");
		regexp.append(endHighlight);
		regexp.append("/igm");

		// Do the actual substitution via a simple regular expression.
		return perl5Util.substitute(regexp.toString(), string);
	}

	/**
	 * Escapes all necessary characters in the String so that it can be used in
	 * an XML doc.
	 * 
	 * @param string
	 *            the string to escape.
	 * @return the string with appropriate characters escaped.
	 */
	public static final String escapeForXML(String string) {
		if (string == null) {
			return null;
		}
		char ch;
		int i = 0;
		int last = 0;
		char[] input = string.toCharArray();
		int len = input.length;
		StringBuffer out = new StringBuffer((int) (len * 1.3));
		for (; i < len; i++) {
			ch = input[i];
			if (ch > '>') {
				continue;
			} else if (ch == '<') {
				if (i > last) {
					out.append(input, last, i - last);
				}
				last = i + 1;
				out.append(LT_ENCODE);
			} else if (ch == '&') {
				if (i > last) {
					out.append(input, last, i - last);
				}
				last = i + 1;
				out.append(AMP_ENCODE);
			} else if (ch == '"') {
				if (i > last) {
					out.append(input, last, i - last);
				}
				last = i + 1;
				out.append(QUOTE_ENCODE);
			}
		}
		if (last == 0) {
			return string;
		}
		if (i > last) {
			out.append(input, last, i - last);
		}
		return out.toString();
	}

	/**
	 * Unescapes the String by converting XML escape sequences back into normal
	 * characters.
	 * 
	 * @param string
	 *            the string to unescape.
	 * @return the string with appropriate characters unescaped.
	 */
	public static final String unescapeFromXML(String string) {
		string = replace(string, "&lt;", "<");
		string = replace(string, "&gt;", ">");
		string = replace(string, "&quot;", "\"");
		return replace(string, "&amp;", "&");
	}

	private static final char[] zeroArray = "0000000000000000".toCharArray();

	/**
	 * Pads the supplied String with 0's to the specified length and returns the
	 * result as a new String. For example, if the initial String is "9999" and
	 * the desired length is 8, the result would be "00009999". This type of
	 * padding is useful for creating numerical values that need to be stored
	 * and sorted as character data. Note: the current implementation of this
	 * method allows for a maximum <tt>length</tt> of 16.
	 * 
	 * @param string
	 *            the original String to pad.
	 * @param length
	 *            the desired length of the new padded String.
	 * @return a new String padded with the required number of 0's.
	 */
	public static final String zeroPadString(String string, int length) {
		if (string == null || string.length() > length) {
			return string;
		}
		StringBuffer buf = new StringBuffer(length);
		buf.append(zeroArray, 0, length - string.length()).append(string);
		return buf.toString();
	}

	/**
	 * Formats a Date as a fifteen character long String made up of the Date's
	 * padded millisecond value.
	 * 
	 * @return a Date encoded as a String.
	 */
	public static final String dateToMillis(Date date) {
		return zeroPadString(Long.toString(date.getTime()), 15);
	}

	public static final String formatHtmlText(String content) {
		if (content == null || content.length() == 0) {
			return "";
		}
		String text = content;
		text = text.replaceAll("<", "&lt;");
		text = text.replaceAll(">", "&gt;");
		text = text.replaceAll("\n", "<br>");
		text = text.replaceAll(" ", "&nbsp;");
		return text;
	}

	public static final String unFormatHtmlText(String content) {
		if (content == null || content.length() == 0) {
			return "";
		}
		String text = content;
		text = text.replaceAll("&lt;", "<");
		text = text.replaceAll("&gt;", ">");
		text = text.replaceAll("<br>", "\n");
		text = text.replaceAll("&nbsp;", " ");
		return text;
	}

	public static String numberToChinese(double d) {
		DecimalFormat decimalformat = new DecimalFormat("############0.00");
		String s = decimalformat.format(d);
		int i = s.indexOf(".");
		if (s.substring(i).compareTo(".00") == 0) {
			s = s.substring(0, i);
		}
		String s1 = "";
		String as[] = new String[4];
		String as1[] = new String[4];
		String as2[] = new String[2];
		String s6 = "";
		int j = 0;
		int k = 0;
		as[0] = "";
		as[1] = "\u62FE";
		as[2] = "\u4F70";
		as[3] = "\u4EDF";
		as1[0] = "";
		as1[1] = "\u4E07";
		as1[2] = "\u4EBF";
		as1[3] = "\u4E07";
		as2[0] = "\u5206";
		as2[1] = "\u89D2";
		if (s.compareTo("0") == 0 || s.compareTo("0.0") == 0
				|| s.compareTo("0.00") == 0) {
			s6 = "\u96F6\u5143\u6574";
			return s6;
		}
		if (s.indexOf(".") > 0) {
			s1 = s.substring(0, s.indexOf("."));
		} else {
			s1 = s;
		}
		j = s1.length() % 4 == 0 ? s1.length() / 4 : s1.length() / 4 + 1;
		for (int i1 = j; i1 >= 1; i1--) {
			int l;
			if (i1 == j && s1.length() % 4 != 0) {
				l = s1.length() % 4;
			} else {
				l = 4;
			}
			String s3 = s1.substring(k, k + l);
			for (int j1 = 0; j1 < s3.length(); j1++) {
				if (Integer.parseInt(s3.substring(j1, j1 + 1)) != 0) {
					switch (Integer.parseInt(s3.substring(j1, j1 + 1))) {
					case 1: // '\001'
						s6 = s6 + "\u58F9" + as[s3.length() - j1 - 1];
						break;

					case 2: // '\002'
						s6 = s6 + "\u8D30" + as[s3.length() - j1 - 1];
						break;

					case 3: // '\003'
						s6 = s6 + "\u53C1" + as[s3.length() - j1 - 1];
						break;

					case 4: // '\004'
						s6 = s6 + "\u8086" + as[s3.length() - j1 - 1];
						break;

					case 5: // '\005'
						s6 = s6 + "\u4F0D" + as[s3.length() - j1 - 1];
						break;

					case 6: // '\006'
						s6 = s6 + "\u9646" + as[s3.length() - j1 - 1];
						break;

					case 7: // '\007'
						s6 = s6 + "\u67D2" + as[s3.length() - j1 - 1];
						break;

					case 8: // '\b'
						s6 = s6 + "\u634C" + as[s3.length() - j1 - 1];
						break;

					case 9: // '\t'
						s6 = s6 + "\u7396" + as[s3.length() - j1 - 1];
						break;
					}
				} else if (j1 + 1 < s3.length() && s3.charAt(j1 + 1) != '0') {
					s6 = s6 + "\u96F6";
				}
			}

			k += l;
			if (i1 < j) {
				if (Integer
						.parseInt(s3.substring(s3.length() - 1, s3.length())) != 0
						|| Integer.parseInt(s3.substring(s3.length() - 2, s3
								.length() - 1)) != 0
						|| Integer.parseInt(s3.substring(s3.length() - 3, s3
								.length() - 2)) != 0
						|| Integer.parseInt(s3.substring(s3.length() - 4, s3
								.length() - 3)) != 0) {
					s6 = s6 + as1[i1 - 1];
				}
			} else {
				s6 = s6 + as1[i1 - 1];
			}
		}

		if (s6.length() > 0) {
			s6 = s6 + "\u5143";
		}
		if (s.indexOf(".") > 0) {
			String s5 = s.substring(s.indexOf(".") + 1);
			for (int k1 = 0; k1 < 2; k1++) {
				if (Integer.parseInt(s5.substring(k1, k1 + 1)) != 0) {
					switch (Integer.parseInt(s5.substring(k1, k1 + 1))) {
					case 1: // '\001'
						s6 = s6 + "\u58F9" + as2[1 - k1];
						break;

					case 2: // '\002'
						s6 = s6 + "\u8D30" + as2[1 - k1];
						break;

					case 3: // '\003'
						s6 = s6 + "\u53C1" + as2[1 - k1];
						break;

					case 4: // '\004'
						s6 = s6 + "\u8086" + as2[1 - k1];
						break;

					case 5: // '\005'
						s6 = s6 + "\u4F0D" + as2[1 - k1];
						break;

					case 6: // '\006'
						s6 = s6 + "\u9646" + as2[1 - k1];
						break;

					case 7: // '\007'
						s6 = s6 + "\u67D2" + as2[1 - k1];
						break;

					case 8: // '\b'
						s6 = s6 + "\u634C" + as2[1 - k1];
						break;

					case 9: // '\t'
						s6 = s6 + "\u7396" + as2[1 - k1];
						break;
					}
				} else if (s6.length() > 0) {
					s6 = s6 + "\u96F6";
				}
			}

		} else {
			s6 = s6 + "\u6574";
		}
		if (s6.substring(s6.length() - 1).compareTo("\u96F6") == 0) {
			s6 = s6.substring(0, s6.length() - 1);
		}
		return s6;
	}

	public static String codeStrgroupToStr(String[] strgroup,
			String strSeparator) {
		StringBuffer strBuf = new StringBuffer();
		int len = strgroup.length;
		for (int i = 0; i < len; i++) {
			if (i != len - 1) {
				strBuf.append(strgroup[i]);
				strBuf.append(strSeparator);
			} else {
				strBuf.append(strgroup[i]);
			}
		}
		return strBuf.toString();
	}

	public static List changeStringGroup(String strParam, String strSeparator) {
		String strData = "";
		List vcReturn = new ArrayList();
		if (strParam != null && !strParam.equals("")) {
			StringTokenizer st = new StringTokenizer(strParam, strSeparator);
			while (st.hasMoreTokens()) {
				strData = st.nextToken();
				vcReturn.add(strData);
			}
		} else {
			vcReturn.add(strData);
		}
		return vcReturn;
	}

	/**
	 * 把字符串转换成Unicode码
	 * 
	 * @param strText
	 *            待转换的字符串
	 * @param code
	 *            转换前字符串的编码，如"GBK"
	 * @return
	 */
	public static String toUnicode(String strText, String code) {
		char c;
		String strRet = "";
		int intAsc;
		String strHex;
		try {
			strText = new String(strText.getBytes(ISO8859_1), code);
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (int i = 0; i < strText.length(); i++) {
			c = strText.charAt(i);
			intAsc = (int) c;
			if (intAsc > 128) {
				strHex = Integer.toHexString(intAsc);
				strRet = strRet + "&#x" + strHex + ";";
			} else {
				strRet = strRet + c;
			}
		}
		return strRet;
	}

	public static String toURLencode(String text, String code) {
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			if (c >= 0 && c <= 255) {
				result.append(c);
			} else {
				byte[] b = new byte[0];
				try {
					b = Character.toString(c).getBytes(code);
				} catch (Exception ex) {
				}
				for (int j = 0; j < b.length; j++) {
					int k = b[j];
					if (k < 0)
						k += 256;
					result.append("%" + Integer.toHexString(k).toUpperCase());
				}
			}
		}
		return result.toString();

	}
	
	/**
	 * Utf8URL解码
	 * 
	 * @param text
	 * @return
	 */
	public static String Utf8URLdecode(String text) {
		String result = "";
		int p = 0;
		if (text != null && text.length() > 0) {
			text = text.toLowerCase();
			p = text.indexOf("%e");
			if (p == -1)
				return text;
			while (p != -1) {
				result += text.substring(0, p);
				text = text.substring(p, text.length());
				if (text.equals("") || text.length() < 9)
					return result;
				result += CodeToWord(text.substring(0, 9));
				text = text.substring(9, text.length());
				p = text.indexOf("%e");
			}
		}
		return result + text;
	}

	/**
	 * 编码是否有效
	 * 
	 * @param text
	 * @return
	 */
	private static boolean Utf8codeCheck(String text) {
		String sign = "";
		if (text.startsWith("%e"))
			for (int i = 0, p = 0; p != -1; i++) {
				p = text.indexOf("%", p);
				if (p != -1)
					p++;
				sign += p;
			}
		return sign.equals("147-1");
	}

	/**
	 * 是否Utf8Url编码
	 * 
	 * @param text
	 * @return
	 */
	public static boolean isUtf8Url(String text) {
		text = text.toLowerCase();
		int p = text.indexOf("%");
		if (p != -1 && text.length() - p > 9) {
			text = text.substring(p, p + 9);
		}
		return Utf8codeCheck(text);
	}

	/**
	 * utf8URL编码转字符
	 * 
	 * @param text
	 * @return
	 */
	private static String CodeToWord(String text) {
		String result;
		if (Utf8codeCheck(text)) {
			byte[] code = new byte[3];
			code[0] = (byte) (Integer.parseInt(text.substring(1, 3), 16) - 256);
			code[1] = (byte) (Integer.parseInt(text.substring(4, 6), 16) - 256);
			code[2] = (byte) (Integer.parseInt(text.substring(7, 9), 16) - 256);
			try {
				result = new String(code, "UTF-8");
			} catch (UnsupportedEncodingException ex) {
				result = null;
			}
		} else {
			result = text;
		}
		return result;
	}
	

}
