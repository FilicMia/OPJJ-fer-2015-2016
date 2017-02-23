

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import hr.fer.zemris.java.simplecomp.impl.instructions.InstrCall;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;
import hr.fer.zemris.java.simplecomp.models.Memory;
import hr.fer.zemris.java.simplecomp.models.Registers;

@SuppressWarnings("javadoc")
@RunWith(MockitoJUnitRunner.class)
public class CallTest {

	@Mock
	private Computer c;

	@Mock
	private Memory m;

	@Mock
	private Registers r;

	@Mock
	private List<InstructionArgument> arguments;

	@Mock
	private InstructionArgument arg1;

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidNumberOfArguments() {
		when(arguments.size()).thenReturn(2);
		new InstrCall(arguments);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidArguments() {
		initialize();
		when(arg1.isNumber()).thenReturn(false);// invalid
		new InstrCall(arguments);
	}

	@Test
	public void testValidArguments1() {
		initialize();
		when(arg1.isNumber()).thenReturn(true);
		when(arg1.getValue()).thenReturn(27);
		when(r.getProgramCounter()).thenReturn(5);
		when(r.getRegisterValue(Registers.STACK_REGISTER_INDEX))
				.thenReturn(101);
		Instruction instr = new InstrCall(arguments);
		instr.execute(c);
		verify(r, times(1)).getRegisterValue(Registers.STACK_REGISTER_INDEX);
		verify(r, times(1)).setRegisterValue(Registers.STACK_REGISTER_INDEX,
				100);
		verify(r, times(1)).setProgramCounter(27);
		verify(r, times(1)).getProgramCounter();
		verify(m, times(1)).setLocation(101, 5);
	}

	@Test
	public void testValidArguments2() {
		initialize();
		when(arg1.isNumber()).thenReturn(true);
		when(arg1.getValue()).thenReturn(2);
		when(r.getProgramCounter()).thenReturn(10);
		when(r.getRegisterValue(Registers.STACK_REGISTER_INDEX))
				.thenReturn(101);
		Instruction instr = new InstrCall(arguments);
		instr.execute(c);
		verify(r, times(1)).getRegisterValue(Registers.STACK_REGISTER_INDEX);
		verify(r, times(1)).setRegisterValue(Registers.STACK_REGISTER_INDEX,
				100);
		verify(r, times(1)).setProgramCounter(2);
		verify(r, times(1)).getProgramCounter();
		verify(m, times(1)).setLocation(101, 10);
	}

	private void initialize() {
		when(c.getMemory()).thenReturn(m);
		when(c.getRegisters()).thenReturn(r);
		when(arguments.size()).thenReturn(1);
		when(arguments.get(0)).thenReturn(arg1);
	}
}
