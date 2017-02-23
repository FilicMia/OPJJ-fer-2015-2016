/**
 * 
 */
package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Implementing the instruction {@code move} which takes 2 arguments. First is
 * index of the register or indirect addressed address, and second is index of
 * the register, indirect addressed address or number. It has form:
 * <table>
 * <tr>
 * </tr>
 * <tr>
 * <td>{@code move rX, rY}</td>
 * </tr>
 * <tr>
 * <td>{@code move rX, number}</td>
 * </tr>
 * </table>
 * 
 * @author mia
 *
 */
public class InstrMove implements Instruction {
	/**Number of arguments of instruction.*/
	private final static int ARGC= 2;
	/** Number zero. */
	public static final int ZERO = 0;
	/** Number one. */
	public static final int ONE = 1;
	/** Number two. */
	public static final int TWO = 2;

	/**
	 * Index of the register or indirect addressed address. It indicates to
	 * where to move object indicated by second argument({@code argument2}).
	 */
	private int argument1;
	/**
	 * Indicates if indirect addressing is used in first argument of the
	 * function.
	 */
	private boolean indirectArgs1 = true;

	/**
	 * Index of the register or indirect addressed address or integer number.
	 * That is from where to move or object(number) that be loaded into
	 * {@code argument1} position.
	 */
	private int argument2;
	/**
	 * Indicates if indirect addressing is used in second argument of the
	 * function.
	 */
	private boolean indirectArgs2 = false;
	/**
	 * Indicates if second argument of the instruction is register.
	 */
	private boolean registar = true;

	/**
	 * Constructor of instruction move.
	 * 
	 * @param arguments
	 *            arguments of the instruction
	 */
	public InstrMove(List<InstructionArgument> arguments) {
		if (arguments.size() != ARGC) {
			throw new IllegalArgumentException("Expected 2 arguments!");
		}

		if (!arguments.get(ZERO).isRegister()) {
			throw new IllegalArgumentException(
					"Type mismatch for argument " + ZERO + "!");
		}
		argument1 = (Integer) arguments.get(ZERO).getValue();
		if (!RegisterUtil
				.isIndirect((Integer) arguments.get(ZERO).getValue())) {
			argument1 = RegisterUtil.getRegisterIndex(argument1);
			indirectArgs1 = false;
		}

		argument2 = (Integer) arguments.get(ONE).getValue();
		if (arguments.get(ONE).isRegister()) {
			if (RegisterUtil
					.isIndirect((Integer) arguments.get(ONE).getValue())) {
				indirectArgs2 = true;
			} else {
				argument2 = RegisterUtil.getRegisterIndex(argument2);
			}
		} else {
			if (!arguments.get(ONE).isNumber()) {
				throw new IllegalArgumentException(
						"Type mismatch for argument " + ONE + "!");
			}
			registar = false;
		}

	}

	@Override
	public boolean execute(Computer computer) {
		Object value;
		if (!registar) {
			value = argument2;
		} else {
			if (indirectArgs2) {
				value = computer.getMemory().getLocation(
						RegisterUtil.getMemoryLocation(argument2, computer));
			} else {
				value =  computer.getRegisters()
						.getRegisterValue(argument2);
			}
		}

		if (!indirectArgs1) {

			computer.getRegisters().setRegisterValue(argument1, value);
		} else {
			int memoryLocation = RegisterUtil.getMemoryLocation(argument1,
					computer);
			computer.getMemory().setLocation(memoryLocation, value);
		}

		return false;
	}

}
