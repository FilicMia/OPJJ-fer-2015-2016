

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import hr.fer.zemris.java.simplecomp.impl.instructions.InstrPush;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;
import hr.fer.zemris.java.simplecomp.models.Memory;
import hr.fer.zemris.java.simplecomp.models.Registers;

@SuppressWarnings("javadoc")
@RunWith(MockitoJUnitRunner.class)
public class PushTest {

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
		new InstrPush(arguments);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidArguments() {
		initialize();
		when(arg1.isRegister()).thenReturn(false);// invalid
		new InstrPush(arguments);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidArguments2() {
		initialize();
		when(arg1.isRegister()).thenReturn(true);
		when(arg1.getValue()).thenReturn(0x18000FE);// invalid
		new InstrPush(arguments);
	}

	@Test
	public void testValidArguments1() {
		initialize();
		when(arg1.isRegister()).thenReturn(true);
		when(arg1.getValue()).thenReturn(0x1);
		when(r.getRegisterValue(1)).thenReturn(12);
		when(r.getRegisterValue(Registers.STACK_REGISTER_INDEX)).thenReturn(25);
		Instruction instr = new InstrPush(arguments);
		instr.execute(c);
		verify(r, times(1)).getRegisterValue(1);
		verify(r, times(1)).getRegisterValue(Registers.STACK_REGISTER_INDEX);
		verify(r, times(1)).setRegisterValue(Registers.STACK_REGISTER_INDEX,
				24);
		verify(m, times(1)).setLocation(25, 12);
	}

	@Test
	public void testValidArguments2() {
		initialize();
		when(arg1.isRegister()).thenReturn(true);
		when(arg1.getValue()).thenReturn(0x7);
		when(r.getRegisterValue(7)).thenReturn("Hrothgar");
		when(r.getRegisterValue(Registers.STACK_REGISTER_INDEX))
				.thenReturn(101);
		Instruction instr = new InstrPush(arguments);
		instr.execute(c);
		verify(r, times(1)).getRegisterValue(7);
		verify(r, times(1)).getRegisterValue(Registers.STACK_REGISTER_INDEX);
		verify(r, times(1)).setRegisterValue(Registers.STACK_REGISTER_INDEX,
				100);
		verify(m, times(1)).setLocation(101, "Hrothgar");
	}

	private void initialize() {
		when(c.getMemory()).thenReturn(m);
		when(c.getRegisters()).thenReturn(r);
		when(arguments.size()).thenReturn(1);
		when(arguments.get(0)).thenReturn(arg1);
	}
}
