package hr.fer.zemris.java.tecaj.hw5.collections;

import static org.junit.Assert.*;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

import org.junit.Ignore;
import org.junit.Test;

import hr.fer.zemris.java.tecaj.hw5.collections.SimpleHashtable.TableEntry;

public class HashTable {

	//@Ignore
	@Test(expected = IllegalArgumentException.class)
	public void testCapacityLessthanOne() {
		SimpleHashtable<String, Integer> table = new SimpleHashtable<>(-1);
	}

	//@Ignore
	@Test(expected = IllegalArgumentException.class)
	public void testTableWithNullKeyCreation() {
		String key = null;
		Integer value = 2;

		TableEntry<String, Integer> table = new TableEntry(key, value, null);
	}

	//@Ignore
	@Test
	public void testGet() {
		// create collection:
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		// fill data:
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5); // overwrites old grade for Ivana
		// query collection:
		Integer kristinaGrade = examMarks.get("Kristina");
		assertEquals(Integer.valueOf(5), kristinaGrade);
	}

	//@Ignore
	@Test
	public void testAllowedNullValue() {

		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);

		examMarks.put("Ivana", null);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);

	}

	//@Ignore
	@Test
	public void testChangeValueOfKeyWithNullValue() {

		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);

		examMarks.put("Ivana", null);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		// change
		examMarks.put("Ivana", 5);

	}

	//@Ignore
	@Test
	public void putExistedKeyPair() {
		// create collection:
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		// fill data:
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5); // overwrites old grade for Ivana
		// query collection:
		Integer ivanaGrade = examMarks.get("Ivana");
		assertEquals(Integer.valueOf(5), ivanaGrade);
	}

	//@Ignore
	@Test
	public void removeFromSlotBegining() {

		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);

		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5);

		Integer ivanaGrade = examMarks.get("Ivana");
		examMarks.remove("Ivana");

		SimpleHashtable<String, Integer> examMarks2 = new SimpleHashtable<>(2);

		examMarks2.put("Ante", 2);
		examMarks2.put("Jasna", 2);
		examMarks2.put("Kristina", 5);
		equalCollection(examMarks, examMarks2);

	}

	//@Ignore
	@Test
	public void removeFromSlotEnd() {

		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);

		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5);

		Integer ivanaGrade = examMarks.get("Ivana");
		examMarks.remove("Kristina");

		SimpleHashtable<String, Integer> examMarks2 = new SimpleHashtable<>(2);
		examMarks2.put("Ivana", 5);
		examMarks2.put("Ante", 2);
		examMarks2.put("Jasna", 2);

		equalCollection(examMarks2, examMarks);

	}

	//@Ignore
	@Test
	public void removeFromSlotMiddle() {

		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);

		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5);

		Integer ivanaGrade = examMarks.get("Ivana");
		examMarks.remove("Jasna");

		SimpleHashtable<String, Integer> examMarks2 = new SimpleHashtable<>(2);
		examMarks2.put("Ivana", 5);
		examMarks2.put("Ante", 2);
		examMarks2.put("Kristina", 5);

		equalCollection(examMarks2, examMarks);

	}

	
	@Test
	public void removeOnlyElementInSlot() {

		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);

		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5);

		Integer ivanaGrade = examMarks.get("Ivana");
		examMarks.remove("Ante");

		SimpleHashtable<String, Integer> examMarks2 = new SimpleHashtable<>(2);
		examMarks2.put("Ivana", 5);
		examMarks2.put("Jasna", 2);
		examMarks2.put("Kristina", 5);

		equalCollection(examMarks2, examMarks);

	}

	@Ignore
	@Test
	public void testClear() {

		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);

		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5);

		Integer ivanaGrade = examMarks.get("Ivana");
		examMarks.clear();

		SimpleHashtable<String, Integer> examMarks2 = new SimpleHashtable<>(2);

		equalCollection(examMarks2, examMarks);

	}


	@Test
	public void testReallocation() {

		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(4);

		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.remove("Kristina");
		// System.out.println(examMarks.getCapacity()+" "+examMarks.toString());
		//assertEquals(8, examMarks.getCapacity());

		SimpleHashtable<String, Integer> examMarks2 = new SimpleHashtable<>(2);
		examMarks2.put("Ivana", 2);
		examMarks2.put("Ante", 2);
		examMarks2.put("Jasna", 2);
		// System.out.println(examMarks2.getCapacity());
		//assertEquals(4, examMarks2.getCapacity());

		equalCollection(examMarks2, examMarks);

	}

	@Ignore
	@Test
	public void Promlem3OfficialTest() {
		// create collection:
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		// fill data:
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5); // overwrites old grade for Ivana

		StringBuilder sb = new StringBuilder();
		for (SimpleHashtable.TableEntry<String, Integer> pair : examMarks) {
			sb.append(String.format("(%s => %d)%n", pair.getKey(),
					pair.getValue()));
		}

		String check = "(Ante => 2)\n" + "(Ivana => 5)\n" + "(Jasna => 2)\n"
				+ "(Kristina => 5)\n";
		assertEquals(check, sb.toString());
	}

	//@Ignore
	@Test
	public void Problem3OfficialTestVOl2() {
		// create collection:
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		// fill data:
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5);
		StringBuilder sb = new StringBuilder();
		for (SimpleHashtable.TableEntry<String, Integer> pair1 : examMarks) {
			String s = "";
			for (SimpleHashtable.TableEntry<String, Integer> pair2 : examMarks) {
				s += String.format("(%s => %d) - (%s => %d)%n", pair1.getKey(),
						pair1.getValue(), pair2.getKey(), pair2.getValue());

			}
			sb.append(s);
		}

		String check = "(Ante => 2) - (Ante => 2)\n"
				+ "(Ante => 2) - (Ivana => 5)\n"
				+ "(Ante => 2) - (Jasna => 2)\n"
				+ "(Ante => 2) - (Kristina => 5)\n"
				+ "(Ivana => 5) - (Ante => 2)\n"
				+ "(Ivana => 5) - (Ivana => 5)\n"
				+ "(Ivana => 5) - (Jasna => 2)\n"
				+ "(Ivana => 5) - (Kristina => 5)\n"
				+ "(Jasna => 2) - (Ante => 2)\n"
				+ "(Jasna => 2) - (Ivana => 5)\n"
				+ "(Jasna => 2) - (Jasna => 2)\n"
				+ "(Jasna => 2) - (Kristina => 5)\n"
				+ "(Kristina => 5) - (Ante => 2)\n"
				+ "(Kristina => 5) - (Ivana => 5)\n"
				+ "(Kristina => 5) - (Jasna => 2)\n"
				+ "(Kristina => 5) - (Kristina => 5)\n";

		assertEquals(check, sb.toString());
	}

	//@Ignore
	@Test
	public void Promlem3OfficialTestRemove1() {
		// create collection:
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		// fill data:
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5); // overwrites old grade for Ivana

		// test
		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks
				.iterator();
		while (iter.hasNext()) {
			SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
			if (pair.getKey().equals("Ivana")) {
				iter.remove(); // sam iterator kontrolirano uklanja trenutni
								// element
			}
		}

		StringBuilder sb = new StringBuilder();
		for (SimpleHashtable.TableEntry<String, Integer> pair : examMarks) {
			sb.append(String.format("(%s => %d)%n", pair.getKey(),
					pair.getValue()));
		}

		String check = "(Ante => 2)\n" + "(Jasna => 2)\n" + "(Kristina => 5)\n";
		assertEquals(check, sb.toString());

	}

	//@Ignore
	@Test(expected = IllegalStateException.class)
	public void Promlem3OfficialTestTwiceCalledRemoveAfterOneNext() {
		// create collection:
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		// fill data:
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5); // overwrites old grade for Ivana

		// test
		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks
				.iterator();
		while (iter.hasNext()) {
			SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
			if (pair.getKey().equals("Ivana")) {
				iter.remove();
				iter.remove();
			}
		}

		StringBuilder sb = new StringBuilder();
		for (SimpleHashtable.TableEntry<String, Integer> pair : examMarks) {
			sb.append(String.format("(%s => %d)%n", pair.getKey(),
					pair.getValue()));
		}

		String check = "(Ante => 2)\n" + "(Jasna => 2)\n" + "(Kristina => 5)\n";
		assertEquals(check, sb.toString());

	}
	
	@Test(expected = ConcurrentModificationException.class)
	public void TestCalledRemoveAfterRemovedOutsideIterator() {
		// create collection:
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		// fill data:
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5); // overwrites old grade for Ivana

		// test
		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks
				.iterator();
		while (iter.hasNext()) {
			SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
			if (pair.getKey().equals("Ivana")) {
				examMarks.remove("Ivana");
				iter.remove();
			}
		}

	}
	
	@Test(expected = ConcurrentModificationException.class)
	public void TestCalledhasNextAfterRemovedOutsideIterator() {
		// create collection:
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		// fill data:
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5); // overwrites old grade for Ivana

		// test
		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks
				.iterator();
		examMarks.remove("Ivana");
		iter.hasNext();

	}
	
	@Test(expected = ConcurrentModificationException.class)
	public void TestCalledNextAfterRemovedOutsideIterator() {
		// create collection:
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		// fill data:
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5); // overwrites old grade for Ivana

		// test
		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks
				.iterator();
		examMarks.remove("Ivana");
		iter.next();

	}

	private void equalCollection(SimpleHashtable<String, Integer> first,
			SimpleHashtable<String, Integer> second) {
		assertEquals(first.toString(), second.toString());
		assertEquals(first.size(), second.size());
	}

}
