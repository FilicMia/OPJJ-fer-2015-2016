

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import hr.fer.zemris.java.simplecomp.impl.instructions.InstrLoad;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;
import hr.fer.zemris.java.simplecomp.models.Memory;
import hr.fer.zemris.java.simplecomp.models.Registers;

@SuppressWarnings("javadoc")
@RunWith(MockitoJUnitRunner.class)
public class LoadTest {

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

	@Mock
	private InstructionArgument arg2;

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidNumberOfArguments() {
		when(arguments.size()).thenReturn(1);
		new InstrLoad(arguments);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidArguments() {
		initialize();
		when(arg1.isRegister()).thenReturn(false);// invalid
		new InstrLoad(arguments);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidArguments2() {
		initialize();
		when(arg1.isRegister()).thenReturn(true);
		when(arg1.getValue()).thenReturn(0x18000FE);// invalid
		new InstrLoad(arguments);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidArguments3() {
		initialize();
		when(arg1.isRegister()).thenReturn(true);
		when(arg1.getValue()).thenReturn(0x1);
		when(arg2.isNumber()).thenReturn(false);// invalid
		new InstrLoad(arguments);
	}

	@Test
	public void testValidArguments1() {
		initialize();
		when(arg1.isRegister()).thenReturn(true);
		when(arg1.getValue()).thenReturn(0x1);
		when(arg2.isNumber()).thenReturn(true);
		when(arg2.getValue()).thenReturn(5);
		when(m.getLocation(5)).thenReturn(2);
		Instruction instr = new InstrLoad(arguments);
		instr.execute(c);
		verify(m, times(1)).getLocation(5);
		verify(r, times(1)).setRegisterValue(1, 2);
	}

	@Test
	public void testValidArguments2() {
		initialize();
		when(arg1.isRegister()).thenReturn(true);
		when(arg1.getValue()).thenReturn(0x6);
		when(arg2.isNumber()).thenReturn(true);
		when(arg2.getValue()).thenReturn(23);
		when(m.getLocation(23)).thenReturn(15);
		Instruction instr = new InstrLoad(arguments);
		instr.execute(c);
		verify(m, times(1)).getLocation(23);
		verify(r, times(1)).setRegisterValue(6, 15);
	}

	private void initialize() {
		when(c.getMemory()).thenReturn(m);
		when(c.getRegisters()).thenReturn(r);
		when(arguments.size()).thenReturn(2);
		when(arguments.get(0)).thenReturn(arg1);
		when(arguments.get(1)).thenReturn(arg2);
	}
}
