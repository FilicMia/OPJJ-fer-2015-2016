

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import hr.fer.zemris.java.simplecomp.impl.instructions.InstrRet;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;
import hr.fer.zemris.java.simplecomp.models.Memory;
import hr.fer.zemris.java.simplecomp.models.Registers;

@SuppressWarnings("javadoc")
@RunWith(MockitoJUnitRunner.class)
public class RetTest {

	@Mock
	private Computer c;

	@Mock
	private Memory m;

	@Mock
	private Registers r;

	@Mock
	private List<InstructionArgument> arguments;

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidNumberOfArguments() {
		when(arguments.size()).thenReturn(2);
		new InstrRet(arguments);
	}

	@Test
	public void testValidArguments1() {
		initialize();
		when(r.getRegisterValue(Registers.STACK_REGISTER_INDEX)).thenReturn(99);
		when(m.getLocation(100)).thenReturn(23);
		Instruction instr = new InstrRet(arguments);
		instr.execute(c);
		verify(r, times(1)).getRegisterValue(Registers.STACK_REGISTER_INDEX);
		verify(r, times(1)).setRegisterValue(Registers.STACK_REGISTER_INDEX,
				100);
		verify(r, times(1)).setProgramCounter(23);
	}

	@Test
	public void testValidArguments2() {
		initialize();
		when(r.getRegisterValue(Registers.STACK_REGISTER_INDEX)).thenReturn(99);
		when(m.getLocation(100)).thenReturn(158);
		Instruction instr = new InstrRet(arguments);
		instr.execute(c);
		verify(r, times(1)).getRegisterValue(Registers.STACK_REGISTER_INDEX);
		verify(r, times(1)).setRegisterValue(Registers.STACK_REGISTER_INDEX,
				100);
		verify(r, times(1)).setProgramCounter(158);
	}

	private void initialize() {
		when(c.getMemory()).thenReturn(m);
		when(c.getRegisters()).thenReturn(r);
		when(arguments.size()).thenReturn(0);
	}
}
