package hr.fer.zemris.java.tecaj.hw6.demo5;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Program solving multiple queries using stream API on list of
 * {@link StudentRecords}.
 * 
 * @author Mia Filić
 *
 */
public class StudentDemo {
	/**
	 * Method called when invoking the program.
	 * 
	 * @param args
	 *            command-line arguments
	 */
	public static void main(String[] args) {
		List<String> lines = null;
		try {
			lines = Files.readAllLines(Paths.get("./studenti.txt"),
					StandardCharsets.UTF_8);
		} catch (IOException e) {
			System.err.println(e.getMessage());
			System.exit(-1);
		}
		List<StudentRecord> records = convert(lines);

		//records.forEach(System.out::println);

		// 1. Odrediti broj studenata koji u sumi MI+ZI+LAB imaju više od 25
		// bodova
		long broj = records.stream()
				.filter(s -> s.getScoreMidExam() + s.getScoreEndExam()
						+ s.getScoreLab() == 25)
				.collect(Collectors.counting()).longValue();
		System.out.println(
				"Number of students that have MI+ZI+LAB > 25 is: " + broj);
		System.out.println(
				"******************************************************"
						+ "************************");
		// 2. Odrediti broj studenata koji su dobili ocjenu 5:
		long broj5 = records.stream().filter(s -> (s.getFinalGrade() == 5))
				.collect(Collectors.counting());
		System.out.println("Number of students with grade 5 is: " + broj5);
		System.out.println(
				"******************************************************");
		// 3. Pripremiti listu studenata koji su dobili ocjenu 5 (redosljed u
		// listi nije bitan):
		List<StudentRecord> odlikasi = records.stream()
				.filter(s -> (s.getFinalGrade() == 5))
				.collect(Collectors.toList());

		System.out.println("Odlikasi: ");
		odlikasi.forEach(s -> System.out.println(s));
		System.out.println(
				"******************************************************");
		// 4. Pripremiti listu studenata koji su dobili ocjenu 5 pri čemu
		// redosljed u listi mora biti takav da je na prvom
		// mjestu student koji je ukupno ostvario najviše bodova a na zadnjem
		// mjestu onaj koji je ukupno ostvario
		// najmanje (dakle, sortirano po bodovima):
		List<StudentRecord> odlikasiSortirano = records.stream()
				.filter(s -> (s.getFinalGrade() == 5))
				.sorted((s1,
						s2) -> -Double.compare(
								s1.getScoreMidExam() + s1.getScoreEndExam()
										+ s1.getScoreLab(),
								s2.getScoreMidExam() + s2.getScoreEndExam()
										+ s2.getScoreLab()))
				.collect(Collectors.toList());
		System.out.println("OdlikasiSortirano: ");
		odlikasiSortirano.forEach(s -> System.out.println(s));
		System.out.println(
				"******************************************************");
		// 5. Pripremiti listu JMBAG-ova studenata koji nisu položili kolegij,
		// sortiranu prema JMBAG-u (od manjeg
		// prema većem; uočite, svi su JMBAGovi stringovi s 10 znamenaka).
		List<String> nepolozeniJMBAGovi = records.stream()
				.filter(s -> s.getFinalGrade() < 2)
				.sorted((s1, s2) -> s1.getJmbag().compareTo(s2.getJmbag()))
				.map(s -> s.getJmbag()).collect(Collectors.toList());
		System.out.println("Nepolozeni JMBAGovi: ");
		nepolozeniJMBAGovi.forEach(s -> System.out.println(s));
		System.out.println(
				"******************************************************");
		// 6. Pripremiti mapu čiji su ključevi ocjene a vrijednosti liste
		// studenata s tim ocjenama (hint: pogledati
		// Collectors.groupingBy).
		Map<Integer, List<StudentRecord>> mapaPoOcjenama = records.stream()
				.collect(Collectors.groupingBy(StudentRecord::getFinalGrade));
		mapaPoOcjenama.forEach(new BiConsumer<Integer, List<StudentRecord>>() {

			@Override
			public void accept(Integer t, List<StudentRecord> u) {
				System.out.println("Key : " + t);
				u.forEach(System.out::println);

			}
		});
		System.out.println(
				"******************************************************");
		// 7. Pripremiti mapu čiji su ključevi ocjene a vrijednosti broj
		// studenata s tim ocjenama:
		Map<Integer, Integer> mapaPoOcjenama2 = records.stream()
				.collect(Collectors.toMap(StudentRecord::getFinalGrade, t -> 1,
						(s1, s2) -> s1 + s2));

		mapaPoOcjenama2.forEach((k, v) -> System.out.println(k + " " + v));
		System.out.println(
				"******************************************************");
		
		// 8.8. Pripremiti mapu s ključevima true/false i vrijednostima koje su
		// liste studenata koji su prošli (za ključ
		// true) odnosno koji nisu prošli kolegij (za ključ false). Hint:
		// koristite Collectors.partitioningBy.

		Map<Boolean, List<StudentRecord>> prolazNeprolaz = records.stream()
				.collect(
						Collectors.partitioningBy(t -> t.getFinalGrade() >= 2));
		prolazNeprolaz.forEach(new BiConsumer<Boolean, List<StudentRecord>>() {

			@Override
			public void accept(Boolean t, List<StudentRecord> u) {
				System.out.println(t);
				u.forEach(System.out::println);
				System.out.println();

			}
		});
	}

	/**
	 * Supplementary method converting the list of string lines into list of
	 * {@link StudentRecord}.
	 * 
	 * @param lines
	 *            list of {@code String} lines. Each line corresponds one
	 *            student record.
	 * @return list of {@code StudentRecord} corresponding passed list of
	 *         strings
	 */
	private static List<StudentRecord> convert(List<String> lines) {
		List<StudentRecord> records = new LinkedList<>();
		lines.forEach(new Consumer<String>() {
			@Override
			public void accept(String t) {
				String parts[] = t.trim().split("\\s+");
				if (parts.length != StudentRecord.ARGC) {
					if (parts.length > 1 && !parts[0].matches("\\s+")) {
						throw new IllegalArgumentException(
								"Wrong input string list.");
					}
				} else {
					StudentRecord record = new StudentRecord(parts[0], parts[1],
							parts[2], Double.parseDouble(parts[3]),
							Double.parseDouble(parts[4]),
							Double.parseDouble(parts[5]),
							Integer.parseInt(parts[6]));
					records.add(record);
				}
			}
		});

		return records;
	}

}
