package pt.isel.ls.utils;

public class Utils<T> {

	/**
	 * Creates a new array identical with an added element at the end
	 * @param array Array.
	 * @param push Element to add.
	 * @return Array.
	 */
	public static Object[] push(Object[] array, Object push) {
		Object[] longer = new Object[array.length + 1];
		System.arraycopy(array, 0, longer, 0, array.length);
		longer[array.length] = push;
		return longer;
	}

	/**
	 * Checks if a string holds an Integer value.
	 * @param value String.
	 * @return Boolean.
	 */
	public static boolean isInteger(String value){
		try {
			Integer.valueOf(value);
		} catch (NumberFormatException ex) {
			return false;
		}
		return true;
	}
}
