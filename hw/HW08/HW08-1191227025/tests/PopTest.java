

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import hr.fer.zemris.java.simplecomp.impl.instructions.InstrPop;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;
import hr.fer.zemris.java.simplecomp.models.Memory;
import hr.fer.zemris.java.simplecomp.models.Registers;

@SuppressWarnings("javadoc")
@RunWith(MockitoJUnitRunner.class)
public class PopTest {

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
		new InstrPop(arguments);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidArguments() {
		initialize();
		when(arg1.isRegister()).thenReturn(false);// invalid
		new InstrPop(arguments);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidArguments2() {
		initialize();
		when(arg1.isRegister()).thenReturn(true);
		when(arg1.getValue()).thenReturn(0x18000FE);// invalid
		new InstrPop(arguments);
	}

	@Test
	public void testValidArguments1() {
		initialize();
		when(arg1.isRegister()).thenReturn(true);
		when(arg1.getValue()).thenReturn(0x1);
		when(r.getRegisterValue(Registers.STACK_REGISTER_INDEX)).thenReturn(25);
		when(m.getLocation(26)).thenReturn(12);
		Instruction instr = new InstrPop(arguments);
		instr.execute(c);
		verify(r, times(1)).setRegisterValue(1, 12);
		verify(r, times(1)).getRegisterValue(Registers.STACK_REGISTER_INDEX);
		verify(r, times(1)).setRegisterValue(Registers.STACK_REGISTER_INDEX,
				26);
		verify(m, times(1)).getLocation(26);
	}

	@Test
	public void testValidArguments2() {
		initialize();
		when(arg1.isRegister()).thenReturn(true);
		when(arg1.getValue()).thenReturn(0x6);
		when(r.getRegisterValue(Registers.STACK_REGISTER_INDEX)).thenReturn(99);
		when(m.getLocation(100)).thenReturn("Hrothar");
		Instruction instr = new InstrPop(arguments);
		instr.execute(c);
		verify(r, times(1)).setRegisterValue(6, "Hrothar");
		verify(r, times(1)).getRegisterValue(Registers.STACK_REGISTER_INDEX);
		verify(r, times(1)).setRegisterValue(Registers.STACK_REGISTER_INDEX,
				100);
		verify(m, times(1)).getLocation(100);
	}

	private void initialize() {
		when(c.getMemory()).thenReturn(m);
		when(c.getRegisters()).thenReturn(r);
		when(arguments.size()).thenReturn(1);
		when(arguments.get(0)).thenReturn(arg1);
	}
}
