package hr.fer.zemris.java.custom.collections.demo;

import java.util.Arrays;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.collections.LinkedListIndexedCollection;
import hr.fer.zemris.java.custom.collections.Processor;

public class Demo123 {

	public static void main(String[] args) {
		class P extends Processor {
			public void process(Object o) {
			System.out.println(o);
			//System.out.println("pozval me");
			}
			};
		
		ArrayIndexedCollection col = new ArrayIndexedCollection(2);
		col.add(new Integer(20));
		col.add("New York");
		col.add("San Francisco");
		col.add("New York");
		col.add("San Francisco");
		System.out.println("Lets print col:");
		col.forEach(new P());
		System.out.println("--------------------------------");
		System.out.println(col.contains("New York")); // writes: true
		col.remove(1); // removes "New York"; shifts "San Francisco" to position 1
		System.out.println("Lets print col:");
		col.forEach(new P());
		System.out.println("--------------------------------");
		System.out.println(col.get(1)); // writes: "San Francisco"
		System.out.println(col.size()); // writes: 2
		col.add("Los Angeles");
		LinkedListIndexedCollection col2 = new LinkedListIndexedCollection(col);
		System.out.println("col1 elements:");
		col.forEach(new P());
		System.out.println("col1 elements again:");
		System.out.println(Arrays.toString(col.toArray()));
		System.out.println("col2 elements:");
		col2.forEach(new P());
		System.out.println("col2 elements again:");
		System.out.println(Arrays.toString(col2.toArray()));
		
		System.out.println(col.contains(col2.get(1))); // true
		System.out.println(col2.contains(col.get(1))); // true
		col.remove(new Integer(20)); // removes 20 from collection (at position 0).
		System.out.println("Lets print col:");
		col.forEach(new P());
		System.out.println("--------------------------------");
		System.out.println("1. element od col "+col.get(1));
		
		System.out.println("col2 elements: "+col2.size());
		col2.forEach(new P());
		col2.remove(new String("Los Angeles"));
		col2.remove(2);//ostaju 20,SF,SF
		System.out.println("Lets print col2:");
		col2.insert(new Integer(22), 2);
		col2.forEach(new P());
		System.out.println("--------------------------------");

	}

}