

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import hr.fer.zemris.java.simplecomp.impl.instructions.InstrMove;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;
import hr.fer.zemris.java.simplecomp.models.Memory;
import hr.fer.zemris.java.simplecomp.models.Registers;

@SuppressWarnings("javadoc")
@RunWith(MockitoJUnitRunner.class)
public class MoveTest {

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
		new InstrMove(arguments);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidArguments() {
		initialize();
		when(arg1.isRegister()).thenReturn(false);// invalid
		new InstrMove(arguments);
	}

	@Test
	public void testDirectDirect() {
		initialize();
		when(arg1.isRegister()).thenReturn(true);
		when(arg1.getValue()).thenReturn(0x6);
		when(arg2.isRegister()).thenReturn(true);
		when(arg2.getValue()).thenReturn(0x5);
		when(r.getRegisterValue(5)).thenReturn(15);
		Instruction instr = new InstrMove(arguments);
		instr.execute(c);
		verify(r, times(1)).getRegisterValue(5);
		verify(r, times(1)).setRegisterValue(6, 15);
	}

	@Test
	public void testDirectIndirect() {
		initialize();
		when(arg1.isRegister()).thenReturn(true);
		when(arg1.getValue()).thenReturn(3);
		when(arg2.isRegister()).thenReturn(true);
		when(arg2.getValue()).thenReturn(1 << 24 | 3 << 8 | 5);
		when(r.getRegisterValue(5)).thenReturn(7);
		when(m.getLocation(10)).thenReturn(4);
		Instruction instr = new InstrMove(arguments);
		instr.execute(c);
		verify(m, times(1)).getLocation(10);
		verify(r, times(1)).getRegisterValue(5);
		verify(r, times(1)).setRegisterValue(3, 4);
	}

	@Test
	public void testIndirectDirect() {
		initialize();
		when(arg1.isRegister()).thenReturn(true);
		when(arg1.getValue()).thenReturn(1 << 24 | 3 << 8 | 5);
		when(arg2.isRegister()).thenReturn(true);
		when(arg2.getValue()).thenReturn(3);
		when(r.getRegisterValue(5)).thenReturn(7);
		when(r.getRegisterValue(3)).thenReturn(4);
		Instruction instr = new InstrMove(arguments);
		instr.execute(c);
		verify(m, times(1)).setLocation(10, 4);
		verify(r, times(1)).getRegisterValue(5);
		verify(r, times(1)).getRegisterValue(3);
	}

	@Test
	public void testIndirectNumber() {
		initialize();
		when(arg1.isRegister()).thenReturn(true);
		when(arg1.getValue()).thenReturn(1 << 24 | 3 << 8 | 5);
		when(arg2.isNumber()).thenReturn(true);
		when(arg2.getValue()).thenReturn(23);
		when(r.getRegisterValue(5)).thenReturn(7);
		Instruction instr = new InstrMove(arguments);
		instr.execute(c);
		verify(m, times(1)).setLocation(10, 23);
		verify(r, times(1)).getRegisterValue(5);
	}

	@Test
	public void testIndirectIndirect() {
		initialize();
		when(arg1.isRegister()).thenReturn(true);
		when(arg1.getValue()).thenReturn(1 << 24 | 3 << 8 | 5);
		when(arg2.isRegister()).thenReturn(true);
		when(arg2.getValue()).thenReturn(1 << 24 | ((short) -2) << 8 | 4);
		when(r.getRegisterValue(5)).thenReturn(7);
		when(r.getRegisterValue(4)).thenReturn(20);
		when(m.getLocation(18)).thenReturn(111);
		Instruction instr = new InstrMove(arguments);
		instr.execute(c);
		verify(m, times(1)).setLocation(10, 111);
		verify(r, times(1)).getRegisterValue(5);
		verify(r, times(1)).getRegisterValue(4);
	}

	private void initialize() {
		when(c.getMemory()).thenReturn(m);
		when(c.getRegisters()).thenReturn(r);
		when(arguments.size()).thenReturn(2);
		when(arguments.get(0)).thenReturn(arg1);
		when(arguments.get(1)).thenReturn(arg2);
	}
}
