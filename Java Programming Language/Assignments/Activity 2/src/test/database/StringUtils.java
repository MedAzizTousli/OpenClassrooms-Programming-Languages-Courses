package test.database;
/**
 * Classe utilitaire pour centrer les valeurs des tables SQL
 * @author cysboy
 */
class StringUtils {

	public static String center(String s, int size) {
		return center(s, size, " ");
	}

	public static String center(String s, int size, String pad) {
		if (pad == null)
			throw new NullPointerException("pad cannot be null");
		if (pad.length() <= 0)
			throw new IllegalArgumentException("pad cannot be empty");
		if (s == null || size <= s.length())
			return s;

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < (size - s.length()) / 2; i++) {
			sb.append(pad);
		}
		sb.append(s);
		while (sb.length() < size) {
			sb.append(pad);
		}
		return sb.toString();
	}
}