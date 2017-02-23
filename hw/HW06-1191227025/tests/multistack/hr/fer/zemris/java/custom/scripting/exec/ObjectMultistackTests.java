package hr.fer.zemris.java.custom.scripting.exec;

import static org.junit.Assert.*;

import java.util.EmptyStackException;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

import org.junit.Ignore;
import org.junit.Test;

import hr.fer.zemris.java.custom.scripting.exec.ObjectMultistack.MultistackEntry;

@SuppressWarnings("javadoc")
public class ObjectMultistackTests {

	// @Ignore
	@Test
	public void TestMultiStackAcceptsNullValues() {
		ObjectMultistack multistack = new ObjectMultistack();
		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
		multistack.push("year", year);
		ValueWrapper price = new ValueWrapper(200.51);
		multistack.push("price", price);
		multistack.push("value", null);

	}

	// @Ignore
	@Test
	public void TestMultiStackAcceptsNullValuesToExistingKey() {
		ObjectMultistack multistack = new ObjectMultistack();
		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
		multistack.push("year", year);
		ValueWrapper price = new ValueWrapper(200.51);
		multistack.push("price", price);
		multistack.push("price", null);

		assertNull(multistack.pop("price"));
		assertEquals(multistack.pop("price").getValue(), 200.51);

	}

	// @Ignore
	@Test
	public void TestMultiStackPeek() {
		ObjectMultistack multistack = new ObjectMultistack();
		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
		multistack.push("year", year);
		ValueWrapper price = new ValueWrapper(200.51);
		multistack.push("price", price);
		multistack.push("price", null);

		assertNull(multistack.peek("price"));
		assertNull(multistack.peek("price"));
		assertNull(multistack.pop("price"));
		assertEquals(multistack.peek("price").getValue(), 200.51);
		assertEquals(multistack.peek("price").getValue(), 200.51);
		assertEquals(multistack.peek("price").getValue(), 200.51);

	}

	// @Ignore
	@Test(expected = EmptyStackException.class)
	public void TestMultiStackPeekOnEmptyStack() {
		ObjectMultistack multistack = new ObjectMultistack();

		assertNull(multistack.peek("price"));

	}

	// @Ignore
	@Test(expected = EmptyStackException.class)
	public void TestMultiStackPopOnEmptyStack() {
		ObjectMultistack multistack = new ObjectMultistack();

		assertNull(multistack.pop("price"));

	}

	// @Ignore
	@Test
	public void TestMultiStackIncrementDoubleThis() {
		ObjectMultistack multistack = new ObjectMultistack();
		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
		multistack.push("year", year);
		ValueWrapper price = new ValueWrapper(200.51);
		multistack.push("price", price);
		multistack.push("price", new ValueWrapper(2));

		ValueWrapper val1 = multistack.pop("price");

		multistack.peek("price").increment(val1.getValue());
		assertEquals(multistack.peek("price").getValue(), 202.51);
		assertEquals(multistack.peek("price").getValue().getClass(),
				Double.class);

	}

	// @Ignore
	@Test
	public void TestMultiStackdecrementDoubleThis() {
		ObjectMultistack multistack = new ObjectMultistack();
		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
		multistack.push("year", year);
		ValueWrapper price = new ValueWrapper(200.51);
		multistack.push("price", price);
		multistack.push("price", new ValueWrapper(2));

		ValueWrapper val1 = multistack.pop("price");

		multistack.peek("price").decrement(val1.getValue());
		assertEquals(multistack.peek("price").getValue(), 198.51);
		assertEquals(multistack.peek("price").getValue().getClass(),
				Double.class);

	}

	// @Ignore
	@Test
	public void TestMultiStackMultiplyDoubleThis() {
		ObjectMultistack multistack = new ObjectMultistack();
		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
		multistack.push("year", year);
		ValueWrapper price = new ValueWrapper(200.51);
		multistack.push("price", price);
		multistack.push("price", new ValueWrapper(2));

		ValueWrapper val1 = multistack.pop("price");

		multistack.peek("price").multiply(val1.getValue());
		assertEquals(multistack.peek("price").getValue(), 401.02);
		assertEquals(multistack.peek("price").getValue().getClass(),
				Double.class);

	}

	// @Ignore
	@Test
	public void TestMultiStackDivideDoubleThis() {
		ObjectMultistack multistack = new ObjectMultistack();
		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
		multistack.push("year", year);
		ValueWrapper price = new ValueWrapper(200.51);
		multistack.push("price", price);
		multistack.push("price", new ValueWrapper(2));

		ValueWrapper val1 = multistack.pop("price");

		multistack.peek("price").divide(val1.getValue());
		assertEquals(multistack.peek("price").getValue(), 100.255);
		assertEquals(multistack.peek("price").getValue().getClass(),
				Double.class);

	}

	// @Ignore
	@Test
	public void TestMultiStackIncrementDoubleArgument() {
		ObjectMultistack multistack = new ObjectMultistack();
		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
		multistack.push("year", year);
		ValueWrapper price = new ValueWrapper(200);
		multistack.push("price", price);
		multistack.push("price", new ValueWrapper(2.0));

		ValueWrapper val1 = multistack.pop("price");

		multistack.peek("price").increment(val1.getValue());
		assertEquals(multistack.peek("price").getValue(), 202.00);
		assertEquals(multistack.peek("price").getValue().getClass(),
				Double.class);

	}

	// @Ignore
	@Test
	public void TestMultiStackdecrementDoubleArgument() {
		ObjectMultistack multistack = new ObjectMultistack();
		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
		multistack.push("year", year);
		ValueWrapper price = new ValueWrapper(200);
		multistack.push("price", price);
		multistack.push("price", new ValueWrapper(2.0));

		ValueWrapper val1 = multistack.pop("price");

		multistack.peek("price").decrement(val1.getValue());
		assertEquals(multistack.peek("price").getValue(), 198.00);
		assertEquals(multistack.peek("price").getValue().getClass(),
				Double.class);

	}

	// @Ignore
	@Test
	public void TestMultiStackMultiplyDoubleArg() {
		ObjectMultistack multistack = new ObjectMultistack();
		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
		multistack.push("year", year);
		ValueWrapper price = new ValueWrapper(200);
		multistack.push("price", price);
		multistack.push("price", new ValueWrapper(2.0));

		ValueWrapper val1 = multistack.pop("price");

		multistack.peek("price").multiply(val1.getValue());
		assertEquals(multistack.peek("price").getValue(), 400.0);
		assertEquals(multistack.peek("price").getValue().getClass(),
				Double.class);

	}

	// @Ignore
	@Test
	public void TestMultiStackDivideDoubleArg() {
		ObjectMultistack multistack = new ObjectMultistack();
		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
		multistack.push("year", year);
		ValueWrapper price = new ValueWrapper(200);
		multistack.push("price", price);
		multistack.push("price", new ValueWrapper(2.0));

		ValueWrapper val1 = multistack.pop("price");

		multistack.peek("price").divide(val1.getValue());
		assertEquals(multistack.peek("price").getValue(), 100.00);
		assertEquals(multistack.peek("price").getValue().getClass(),
				Double.class);

	}

	// @Ignore
	@Test
	public void TestMultiStackIncrementInt() {
		ObjectMultistack multistack = new ObjectMultistack();
		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
		multistack.push("year", year);
		ValueWrapper price = new ValueWrapper(200);
		multistack.push("price", price);
		multistack.push("price", new ValueWrapper(2));

		ValueWrapper val1 = multistack.pop("price");

		multistack.peek("price").increment(val1.getValue());
		assertEquals(multistack.peek("price").getValue(), 202);
		assertEquals(multistack.peek("price").getValue().getClass(),
				Integer.class);

	}

	// @Ignore
	@Test
	public void TestMultiStackdecrementInt() {
		ObjectMultistack multistack = new ObjectMultistack();
		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
		multistack.push("year", year);
		ValueWrapper price = new ValueWrapper(200);
		multistack.push("price", price);
		multistack.push("price", new ValueWrapper(2));

		ValueWrapper val1 = multistack.pop("price");

		multistack.peek("price").decrement(val1.getValue());
		assertEquals(multistack.peek("price").getValue(), 198);
		assertEquals(multistack.peek("price").getValue().getClass(),
				Integer.class);

	}

	// @Ignore
	@Test
	public void TestMultiStackMultiplyInt() {
		ObjectMultistack multistack = new ObjectMultistack();
		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
		multistack.push("year", year);
		ValueWrapper price = new ValueWrapper(200);
		multistack.push("price", price);
		multistack.push("price", new ValueWrapper(2));

		ValueWrapper val1 = multistack.pop("price");

		multistack.peek("price").multiply(val1.getValue());
		assertEquals(multistack.peek("price").getValue(), 400);
		assertEquals(multistack.peek("price").getValue().getClass(),
				Integer.class);

	}

	// @Ignore
	@Test
	public void TestMultiStackDivideInt() {
		ObjectMultistack multistack = new ObjectMultistack();
		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
		multistack.push("year", year);
		ValueWrapper price = new ValueWrapper(200);
		multistack.push("price", price);
		multistack.push("price", new ValueWrapper(3));

		ValueWrapper val1 = multistack.pop("price");

		multistack.peek("price").divide(val1.getValue());
		assertEquals(multistack.peek("price").getValue(), 66);
		assertEquals(multistack.peek("price").getValue().getClass(),
				Integer.class);

	}

	// @Ignore
	@Test
	public void TestMultiStackIncrementString() {
		ObjectMultistack multistack = new ObjectMultistack();
		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
		multistack.push("year", year);
		ValueWrapper price = new ValueWrapper(200);
		multistack.push("price", price);
		multistack.push("price", new ValueWrapper("20"));

		ValueWrapper val1 = multistack.pop("price");

		multistack.peek("price").increment(val1.getValue());
		assertEquals(multistack.peek("price").getValue(), 220);

	}

	// @Ignore
	@Test
	public void TestMultiStackdecrementString() {
		ObjectMultistack multistack = new ObjectMultistack();
		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
		multistack.push("year", year);
		ValueWrapper price = new ValueWrapper(200);
		multistack.push("price", price);
		multistack.push("price", new ValueWrapper("150.00"));

		ValueWrapper val1 = multistack.pop("price");

		multistack.peek("price").decrement(val1.getValue());
		assertEquals(multistack.peek("price").getValue(), 50.00);

	}

	// @Ignore
	@Test
	public void TestMultiStackMultiplyString() {
		ObjectMultistack multistack = new ObjectMultistack();
		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
		multistack.push("year", year);
		ValueWrapper price = new ValueWrapper(200);
		multistack.push("price", price);
		multistack.push("price", new ValueWrapper("2E10"));

		ValueWrapper val1 = multistack.pop("price");

		multistack.peek("price").multiply(val1.getValue());
		assertEquals(multistack.peek("price").getValue(), 4.0E12);
	}

	// @Ignore
	@Test
	public void TestMultiStackDivideString() {
		ObjectMultistack multistack = new ObjectMultistack();
		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
		multistack.push("year", year);
		ValueWrapper price = new ValueWrapper("200");
		multistack.push("price", price);
		multistack.push("price", new ValueWrapper(3));

		ValueWrapper val1 = multistack.pop("price");

		multistack.peek("price").divide(val1.getValue());
		assertEquals(multistack.peek("price").getValue(), 66);
		assertEquals(multistack.peek("price").getValue().getClass(),
				Integer.class);

	}

	// @Ignore
	@Test
	public void TestMultiStackCompareString() {
		ObjectMultistack multistack = new ObjectMultistack();
		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
		multistack.push("year", year);
		ValueWrapper price = new ValueWrapper("200.0");
		multistack.push("price", price);
		multistack.push("price", new ValueWrapper(3));

		ValueWrapper val1 = multistack.pop("price");

		assertTrue(multistack.peek("price").numCompare(val1.getValue()) > 0);

	}

	// @Ignore
	@Test
	public void TestMultiStackCompareDoubleThis() {
		ObjectMultistack multistack = new ObjectMultistack();
		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
		multistack.push("year", year);
		ValueWrapper price = new ValueWrapper(200.0);
		multistack.push("price", price);
		multistack.push("price", new ValueWrapper(3));

		ValueWrapper val1 = multistack.pop("price");

		assertTrue(multistack.peek("price").numCompare(val1.getValue()) > 0);

	}

	// @Ignore
	@Test
	public void TestMultiStackCompareDoubleArgs() {
		ObjectMultistack multistack = new ObjectMultistack();
		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
		multistack.push("year", year);
		ValueWrapper price = new ValueWrapper(200);
		multistack.push("price", price);
		multistack.push("price", new ValueWrapper(200.001));

		ValueWrapper val1 = multistack.pop("price");

		assertTrue(multistack.peek("price").numCompare(val1.getValue()) < 0);

	}

	// @Ignore
	@Test
	public void TestMultiStackCompareInt() {
		ObjectMultistack multistack = new ObjectMultistack();
		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
		multistack.push("year", year);
		ValueWrapper price = new ValueWrapper(3);
		multistack.push("price", price);
		multistack.push("price", new ValueWrapper(3));

		ValueWrapper val1 = multistack.pop("price");

		assertTrue(multistack.peek("price").numCompare(val1.getValue()) == 0);

	}

	// @Ignore
	@Test(expected = NumberFormatException.class)
	public void TestWrongValueWrapperValueString() {
		ObjectMultistack multistack = new ObjectMultistack();
		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
		multistack.push("year", year);
		ValueWrapper price = new ValueWrapper("200.zzz");
		multistack.push("price", price);
		multistack.push("price", new ValueWrapper(3));

		ValueWrapper val1 = multistack.pop("price");

		multistack.peek("price").numCompare(val1.getValue());

	}

	// @Ignore
	@Test(expected = NumberFormatException.class)
	public void TestWrongValueWrapperValue() {
		ObjectMultistack multistack = new ObjectMultistack();
		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
		multistack.push("year", year);
		ValueWrapper price = new ValueWrapper(new HashSet());
		multistack.push("price", price);
		multistack.push("price", new ValueWrapper(3));

		ValueWrapper val1 = multistack.pop("price");

		multistack.peek("price").numCompare(val1.getValue());

	}

	// @Ignore
	@Test(expected = ArithmeticException.class)
	public void TestValueWrapperNullValue() {
		ObjectMultistack multistack = new ObjectMultistack();
		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
		multistack.push("year", year);
		ValueWrapper price = new ValueWrapper("200");
		multistack.push("price", price);
		multistack.push("price", new ValueWrapper(null));

		ValueWrapper val1 = multistack.pop("price");

		multistack.peek("price").divide(val1.getValue());

	}

	// @Ignore
	@Test
	public void TestValueWrapperNullValueArithmeticOperation() {
		ObjectMultistack multistack = new ObjectMultistack();
		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
		multistack.push("year", year);
		ValueWrapper price = new ValueWrapper(null);
		multistack.push("price", price);
		multistack.push("price", new ValueWrapper("0"));

		ValueWrapper val1 = multistack.pop("price");

		multistack.peek("price").increment(val1.getValue());
		assertEquals(multistack.peek("price").getValue(), 0);

	}

	// @Ignore
	@Test
	public void TestMultiStackValueKeyNames() {
		ObjectMultistack multistack = new ObjectMultistack();
		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
		multistack.push("year", year);
		ValueWrapper price = new ValueWrapper(200.51);
		multistack.push("price", price);
		multistack.push("value", null);
		Set<String> set1 = multistack.keySet();
		Set<String> set2 = new HashSet<>();
		set2.add("year");
		set2.add("price");
		set2.add("value");

		assertEquals(set1, set2);

	}

	// @Ignore
	@Test
	public void TestMultistackIsEmpty() {
		ObjectMultistack multistack = new ObjectMultistack();
		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
		multistack.push("year", year);
		ValueWrapper price = new ValueWrapper(3);
		multistack.push("price", price);
		multistack.push("price", new ValueWrapper(3));

		multistack.pop("price");
		multistack.pop("price");

		assertTrue(multistack.isEmpty("price"));
		assertFalse(multistack.isEmpty("year"));

	}

	// @Ignore
	@Test
	public void testMultiOperations() {
		ObjectMultistack multistack = new ObjectMultistack();
		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
		multistack.push("year", year);
		ValueWrapper price = new ValueWrapper(200.51);
		multistack.push("price", price);
		assertEquals(multistack.peek("year").getValue(), 2000);
		assertEquals(multistack.peek("price").getValue(), 200.51);
		multistack.push("year", new ValueWrapper(Integer.valueOf(1900)));
		assertEquals(multistack.peek("year").getValue(), 1900);
		multistack.peek("year").setValue(
				((Integer) multistack.peek("year").getValue()).intValue() + 50);
		assertEquals(multistack.peek("year").getValue(), 1950);
		multistack.pop("year");
		assertEquals(multistack.peek("year").getValue(), 2000);
		multistack.peek("year").increment("5");
		assertEquals(multistack.peek("year").getValue(), 2005);
		multistack.peek("year").increment(5);
		assertEquals(multistack.peek("year").getValue(), 2010);
		multistack.peek("year").increment(5.0);
		assertEquals(multistack.peek("year").getValue(), 2015.0);
	}

	// @Ignore

	private void equalMultistack(ObjectMultistack assumed,
			ObjectMultistack calculated) {

		Set<String> set1 = assumed.keySet();
		Set<String> set2 = assumed.keySet();
		assertEquals(set1, set2);
		set1.forEach(new Consumer<String>() {

			@Override
			public void accept(String t) {
				while (assumed.isEmpty(t)) {
					ValueWrapper entry1 = assumed.pop(t);
					ValueWrapper entry2 = calculated.pop(t);

					assertEquals(entry1.getValue().toString().trim(),
							entry2.getValue().toString().trim());
				}
			}
		});
	}

}
