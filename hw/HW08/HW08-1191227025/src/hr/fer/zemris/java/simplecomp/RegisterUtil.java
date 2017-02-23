package hr.fer.zemris.java.simplecomp;

import hr.fer.zemris.java.simplecomp.models.Computer;

/**
 * Implements method for processing the register description. The register
 * descriptor is 24-bit number with following structure:
 * 
 * <table>
 * <tr>
 * <th>Position</th>
 * <th>description</th>
 * </tr>
 * <tr>
 * <td>24</td>
 * <td>If it is one, the indirect addressing is used.</td>
 * </tr>
 * <tr>
 * <td>23 do 8</td>
 * <td>In the case of indirect addressing , this is a 16 -bit signed integer in
 * double complement that keeps shift that should be added to the current
 * content of the register in order to obtain the final address from which is
 * need to retrieve an object from memory . If not addressing indirectly , this
 * field is ignored .</td>
 * </tr>
 * <tr>
 * <td>7 do 0</td>
 * <td>8-bit integer natural number; the index of the register.</td>
 * </tr>
 * </table>
 * 
 * 
 * @author mia
 *
 */
public class RegisterUtil {

	/**
	 * Represents integer of value one.
	 */
	public static final int ONE = 1;
	/**
	 * Represents integer of value how many bits should the address be shifted
	 * to obtain its integer value.
	 */
	public static final int ADDRESS_SHIFT = 8;

	/** Byte mask for getting the register index from descriptor. */
	public static final int BYTE_MASK_REGINDEX = 0x000000FF;
	/**
	 * Byte mask for getting the indirect addressing indicator form the
	 * descriptor..
	 */
	public static final int BYTE_MASK_GET_INDIRECT_ADDRESSING = 0x1000000;
	/**
	 * Byte mask for getting the sign bit of address increment in the
	 * descriptor..
	 */
	public static final int BYTE_MASK_ADDRESS_INCREMENT_SIGN = 0x800000;

	/**
	 * Byte mask for getting address increment stored in descriptor..
	 */
	public static final int BYTE_MASK_GET_ADDRESINCREMENT = 0xFFFF00;
	/**
	 * Byte mask for setting the bits from 16th bit to 31st on 1 .
	 */
	public static final int BYTE_PADDING_ONE = 0xFFFF8000;

	/**
	 * Represents integer of indirect addressing.
	 */
	public static final int GET_INDIRECT_ADDRESSING_SHIFT = 24;

	/**
	 * Extracts the register index from the register descriptor.
	 * 
	 * @param registerDescriptor
	 *            register descriptor.
	 * @return extracted index.
	 */
	public static int getRegisterIndex(int registerDescriptor) {
		return registerDescriptor & BYTE_MASK_REGINDEX;

	}

	/**
	 * Checks if the register description indicates the indirect addressing.
	 * 
	 * @param registerDescriptor
	 *            register descriptor.
	 * @return {@code true} if register descriptor indicates that indirect
	 *         addressing is used, {@code false} otherwise.
	 */
	public static boolean isIndirect(int registerDescriptor) {

		return ((registerDescriptor
				& BYTE_MASK_GET_INDIRECT_ADDRESSING) >>> (GET_INDIRECT_ADDRESSING_SHIFT)) == ONE;

	}

	/**
	 * Gets the register offset saved in {@code registerDescriptor}.
	 * 
	 * @param registerDescriptor
	 *            the register offset saved in {@code registerDescriptor}.
	 * @return registration offset saved in given {@code registrationDescriptor}
	 */
	public static int getRegisterOffset(int registerDescriptor) {
		int indirectAddressingBit = (registerDescriptor
				& BYTE_MASK_ADDRESS_INCREMENT_SIGN);
		if ((indirectAddressingBit) == BYTE_MASK_ADDRESS_INCREMENT_SIGN) {
			return -(~((BYTE_PADDING_ONE
					| ((registerDescriptor) >>> ADDRESS_SHIFT)) - ONE));
		}
		return (registerDescriptor
				& BYTE_MASK_GET_ADDRESINCREMENT) >>> ADDRESS_SHIFT;
	}

	/**
	 * Method extracts the memory location form register descriptor if storing
	 * indirect addressing. Otherwise it returns -1.
	 * 
	 * @param registerDescriptor
	 *            register descriptor
	 * @param computer
	 *            computer on which registers memory location should be
	 *            calculated
	 * @return the memory location form register descriptor if storing indirect
	 *         addressing, otherwise it returns -1.
	 */
	public static int getMemoryLocation(int registerDescriptor,
			Computer computer) {
		if (!isIndirect(registerDescriptor)) {
			//System.out.println("fn");
			return -1;
		} else {
			return (Integer) computer.getRegisters().getRegisterValue(
					RegisterUtil.getRegisterIndex(registerDescriptor))
					+ RegisterUtil.getRegisterOffset(registerDescriptor);
		}
	}
}
