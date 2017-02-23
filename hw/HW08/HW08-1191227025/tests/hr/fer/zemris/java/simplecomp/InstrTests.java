package hr.fer.zemris.java.simplecomp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.List;

import hr.fer.zemris.java.simplecomp.impl.ComputerImpl;
import hr.fer.zemris.java.simplecomp.impl.MemoryImpl;
import hr.fer.zemris.java.simplecomp.impl.RegistersImpl;
import hr.fer.zemris.java.simplecomp.impl.instructions.InstrCall;
import hr.fer.zemris.java.simplecomp.impl.instructions.InstrLoad;
import hr.fer.zemris.java.simplecomp.impl.instructions.InstrMove;
import hr.fer.zemris.java.simplecomp.impl.instructions.InstrPop;
import hr.fer.zemris.java.simplecomp.impl.instructions.InstrPush;
import hr.fer.zemris.java.simplecomp.impl.instructions.InstrRet;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;
import hr.fer.zemris.java.simplecomp.models.Memory;
import hr.fer.zemris.java.simplecomp.models.Registers;

@SuppressWarnings("javadoc")
@RunWith(MockitoJUnitRunner.class)
public class InstrTests {
	@Mock
	Computer computer;
	@Mock
	Registers registers;
	@Mock
	Memory memory;
	@Mock
	InstructionArgument args1;
	@Mock
	InstructionArgument args2;
	@Mock
	List<InstructionArgument> list;
	
	@Test(expected=IllegalArgumentException.class)
	public void testLoadWrongArgsNo() {

		when(list.size()).thenReturn(-1);
		InstrLoad load = new InstrLoad(list);

		load.execute(computer);

	}
	
	@Test
	public void testLoad() {
		when(args1.isRegister()).thenReturn(true);
		when(args1.getValue()).thenReturn(Integer.valueOf(0x02));
		when(args2.isNumber()).thenReturn(true);
		when(args2.getValue()).thenReturn(Integer.valueOf(0x02));

		when(list.size()).thenReturn(2);
		when(list.get(0)).thenReturn(args1);
		when(list.get(1)).thenReturn(args2);

		InstrLoad load = new InstrLoad(list);
		when(computer.getMemory()).thenReturn(memory);
		when(computer.getRegisters()).thenReturn(registers);
		when(memory.getLocation(2)).thenReturn(12);

		load.execute(computer);

		verify(computer).getMemory();
		verify(computer).getRegisters();
		verify(memory).getLocation(anyInt());
		verify(registers).setRegisterValue(2, 12);

	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testMoveFirstNotRegister() {
		when(args1.isRegister()).thenReturn(false);
		when(args1.getValue()).thenReturn(Integer.valueOf(0x02));
		when(args2.isRegister()).thenReturn(true);
		when(args2.getValue()).thenReturn(Integer.valueOf(0x05));

		when(list.size()).thenReturn(2);
		when(list.get(0)).thenReturn(args1);
		when(list.get(1)).thenReturn(args2);

		InstrMove move = new InstrMove(list);

		move.execute(computer);

	}

	// from registar to registar
	@Test
	public void testMove1() {
		when(args1.isRegister()).thenReturn(true);
		when(args1.getValue()).thenReturn(Integer.valueOf(0x02));
		when(args2.isRegister()).thenReturn(true);
		when(args2.getValue()).thenReturn(Integer.valueOf(0x05));

		when(list.size()).thenReturn(2);
		when(list.get(0)).thenReturn(args1);
		when(list.get(1)).thenReturn(args2);

		InstrMove move = new InstrMove(list);
		when(computer.getMemory()).thenReturn(memory);
		when(computer.getRegisters()).thenReturn(registers);
		when(registers.getRegisterValue(5)).thenReturn(12);

		move.execute(computer);

		verify(computer, never()).getMemory();
		verify(computer, times(2)).getRegisters();
		verify(registers).getRegisterValue(5);
		verify(memory, never()).getLocation(anyInt());
		verify(memory, never()).setLocation(anyInt(), anyObject());
		verify(registers).setRegisterValue(2, 12);

	}

	@Test
	public void testMove2() {
		when(args1.isRegister()).thenReturn(true);
		when(args1.getValue()).thenReturn(Integer.valueOf(0x02));
		when(args2.isRegister()).thenReturn(false);
		when(args2.isNumber()).thenReturn(true);
		when(args2.getValue()).thenReturn(Integer.valueOf(0x05));

		when(list.size()).thenReturn(2);
		when(list.get(0)).thenReturn(args1);
		when(list.get(1)).thenReturn(args2);

		InstrMove move = new InstrMove(list);
		when(computer.getMemory()).thenReturn(memory);
		when(computer.getRegisters()).thenReturn(registers);

		move.execute(computer);

		verify(computer, never()).getMemory();
		verify(computer, times(1)).getRegisters();
		verify(memory, never()).getLocation(anyInt());
		verify(memory, never()).setLocation(anyInt(), anyObject());
		verify(registers).setRegisterValue(2, 5);

	}

	@Test(expected = IllegalArgumentException.class)
	public void testMove3() {
		when(args1.isRegister()).thenReturn(true);
		when(args1.getValue()).thenReturn(Integer.valueOf(0x02));
		when(args2.isRegister()).thenReturn(false);
		when(args2.isNumber()).thenReturn(false);// <-------should throw
													// exception
		when(args2.getValue()).thenReturn(Integer.valueOf(0x05));

		when(list.size()).thenReturn(2);
		when(list.get(0)).thenReturn(args1);
		when(list.get(1)).thenReturn(args2);

		InstrMove move = new InstrMove(list);
		when(computer.getMemory()).thenReturn(memory);
		when(computer.getRegisters()).thenReturn(registers);

		move.execute(computer);

		verify(computer, never()).getMemory();
		verify(computer, times(1)).getRegisters();
		verify(memory, never()).getLocation(anyInt());
		verify(memory, never()).setLocation(anyInt(), anyObject());
		verify(registers).setRegisterValue(2, 5);

	}

	@Test
	public void testPush() {
		when(args1.isRegister()).thenReturn(true);
		when(args1.getValue()).thenReturn(Integer.valueOf(0x02));

		when(list.size()).thenReturn(1);
		when(list.get(0)).thenReturn(args1);

		InstrPush push = new InstrPush(list);
		when(computer.getMemory()).thenReturn(memory);
		when(computer.getRegisters()).thenReturn(registers);
		when(registers.getRegisterValue(15)).thenReturn(20);
		when(registers.getRegisterValue(2)).thenReturn(5);
		
		push.execute(computer);

		verify(computer).getMemory();
		verify(computer, atLeastOnce()).getRegisters();
		
		verify(memory,never()).getLocation(anyInt());
		verify(memory).setLocation(anyInt(), anyObject());
		verify(memory).setLocation(20, 5);
		
		verify(registers,times(2)).getRegisterValue(anyInt());
		verify(registers).getRegisterValue(2);
		verify(registers).getRegisterValue(15);
		verify(registers).setRegisterValue(anyInt(), anyObject());
		verify(registers).setRegisterValue(15, 19);

	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testPushWronsArgumentsNo() {
		when(list.size()).thenReturn(5);
		InstrPush push = new InstrPush(list);
		
		push.execute(computer);

	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testPushNotRegisterPassed() {
		when(args1.isRegister()).thenReturn(false);
		when(args1.getValue()).thenReturn(Integer.valueOf(0x02));

		when(list.size()).thenReturn(1);
		when(list.get(0)).thenReturn(args1);

		InstrPush push = new InstrPush(list);
		
		push.execute(computer);

	}
	
	@Test
	public void testPop() {
		when(args1.isRegister()).thenReturn(true);
		when(args1.getValue()).thenReturn(Integer.valueOf(0x02));

		when(list.size()).thenReturn(1);
		when(list.get(0)).thenReturn(args1);

		InstrPop pop = new InstrPop(list);
		when(computer.getMemory()).thenReturn(memory);
		when(computer.getRegisters()).thenReturn(registers);
		when(registers.getRegisterValue(15)).thenReturn(20);
		when(memory.getLocation(21)).thenReturn(5);//5 is on memory[21]
		
		pop.execute(computer);

		verify(computer).getMemory();
		verify(computer, atLeastOnce()).getRegisters();
		
		verify(memory).getLocation(anyInt());
		verify(memory).getLocation(21);
		verify(memory,never()).setLocation(anyInt(), anyObject());
		
		verify(registers,times(1)).getRegisterValue(anyInt());
		verify(registers).getRegisterValue(15);
		verify(registers,times(2)).setRegisterValue(anyInt(), anyObject());
		verify(registers).setRegisterValue(2,5);
		verify(registers).setRegisterValue(15, 21);

	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testPopNotRegisterPassed() {
		when(args1.isRegister()).thenReturn(false);
		when(args1.getValue()).thenReturn(Integer.valueOf(0x02));

		when(list.size()).thenReturn(1);
		when(list.get(0)).thenReturn(args1);

		InstrPop pop = new InstrPop(list);
		
		pop.execute(computer);

	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCallWrongArgsNo() {

		when(list.size()).thenReturn(-1);
		InstrCall call = new InstrCall(list);

		call.execute(computer);

	}
	
	@Test
	public void testCall() {
		when(args1.isNumber()).thenReturn(true);
		when(args1.getValue()).thenReturn(Integer.valueOf(0x02));

		when(list.size()).thenReturn(1);
		when(list.get(0)).thenReturn(args1);

		InstrCall call = new InstrCall(list);
		when(computer.getMemory()).thenReturn(memory);
		when(computer.getRegisters()).thenReturn(registers);
		when(registers.getProgramCounter()).thenReturn(0x05);
		//na 20 ce biti 5
		when(registers.getRegisterValue(Registers.STACK_REGISTER_INDEX)).thenReturn(20);

		call.execute(computer);

		verify(computer).getMemory();
		verify(computer, atLeastOnce()).getRegisters();
		
		verify(memory).setLocation(anyInt(), anyObject());
		verify(memory).setLocation(20,5);//put current counter number on the stack
		
		verify(registers,times(1)).getRegisterValue(anyInt());
		verify(registers).getRegisterValue(15);//get top of the stack where to save current counter number
		verify(registers,times(1)).setRegisterValue(anyInt(), anyObject());
		verify(registers).setRegisterValue(15, 19);//set new stack top
		
		verify(registers).setProgramCounter(anyInt());
		verify(registers).setProgramCounter(2);//put address of the invoking method

	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCallArgsNotANumber() {
		when(args1.isNumber()).thenReturn(false);//<-----should throw
		when(args1.getValue()).thenReturn(Integer.valueOf(0x02));

		when(list.size()).thenReturn(1);
		when(list.get(0)).thenReturn(args1);

		InstrCall call = new InstrCall(list);
		when(computer.getMemory()).thenReturn(memory);
		when(computer.getRegisters()).thenReturn(registers);
		when(registers.getProgramCounter()).thenReturn(0x05);
		//na 20 ce biti 5
		when(registers.getRegisterValue(Registers.STACK_REGISTER_INDEX)).thenReturn(20);

		call.execute(computer);

		verify(computer).getMemory();
		verify(computer, atLeastOnce()).getRegisters();
		
		verify(memory).setLocation(anyInt(), anyObject());
		verify(memory).setLocation(20,5);//put current counter number on the stack
		
		verify(registers,times(1)).getRegisterValue(anyInt());
		verify(registers).getRegisterValue(15);//get top of the stack where to save current counter number
		verify(registers,times(1)).setRegisterValue(anyInt(), anyObject());
		verify(registers).setRegisterValue(15, 19);//set new stack top
		
		verify(registers).setProgramCounter(anyInt());
		verify(registers).setProgramCounter(2);//put address of the invoked method

	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testRetWrongArgsNo() {

		when(list.size()).thenReturn(-1);
		InstrCall ret = new InstrCall(list);

		ret.execute(computer);

	}
	
	@Test
	public void testRet() {
		when(list.size()).thenReturn(0);

		InstrRet ret = new InstrRet(list);
		when(computer.getMemory()).thenReturn(memory);
		when(computer.getRegisters()).thenReturn(registers);
		//na 20 ce biti 5
		when(registers.getRegisterValue(Registers.STACK_REGISTER_INDEX)).thenReturn(20);
		when(memory.getLocation(21)).thenReturn(2);
		
		ret.execute(computer);

		verify(computer).getMemory();
		verify(computer, atLeastOnce()).getRegisters();
		
		verify(memory,never()).setLocation(anyInt(), anyObject());
		verify(memory,times(1)).getLocation(21);//last put in stack
		
		verify(registers,times(1)).getRegisterValue(anyInt());
		verify(registers).getRegisterValue(Registers.STACK_REGISTER_INDEX);//get top of the stack from where to red next counter number
		verify(registers,times(1)).setRegisterValue(anyInt(), anyObject());
		verify(registers).setRegisterValue(Registers.STACK_REGISTER_INDEX, 21);//set new stack top
		
		verify(registers).setProgramCounter(anyInt());
		verify(registers).setProgramCounter(2);//put address of the invoking method

	}

}
