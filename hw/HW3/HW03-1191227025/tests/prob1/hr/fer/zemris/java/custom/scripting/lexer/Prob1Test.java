package hr.fer.zemris.java.custom.scripting.lexer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Ignore;
import org.junit.Test;

import hr.fer.zemris.java.custom.scripting.lexer.*;
/**
 * JUnit tests of expected  hr.fer.zemris.java.custom.scripting.lexer classes behavior.
 *
 */
public class Prob1Test {

	@Test
	public void testNotNull() {
		Lexer lexer = new Lexer("");

		assertNotNull("Token was expected but null was returned.", lexer.nextToken());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullInput() {
		// must throw!
		new Lexer(null);
	}

	@Test
	public void testEmpty() {
		Lexer lexer = new Lexer("");

		assertEquals("Empty input must generate only EOF token.", TokenType.EOF, lexer.nextToken().getType());
	}

	@Test
	public void testGetReturnsLastNext() {
		// Calling getToken once or several times after calling nextToken must
		// return each time what nextToken returned...
		Lexer lexer = new Lexer("");

		Token token = lexer.nextToken();
		assertEquals("getToken returned different token than nextToken.", token, lexer.getToken());
		assertEquals("getToken returned different token than nextToken.", token, lexer.getToken());
	}

	@Test(expected = LexerException.class)
	public void testRadAfterEOF() {
		Lexer lexer = new Lexer("");

		// will obtain EOF
		lexer.nextToken();
		// will throw!
		lexer.nextToken();
	}

	@Test
	public void testTwoWords() {
		// Lets check for several words...
		Lexer lexer = new Lexer("  Štefanija\r\n\t Automobil   ");

		// We expect the following stream of tokens
		Token correctData[] = { new Token(TokenType.TEXT, "  Štefanija\r\n\t Automobil   "),
				new Token(TokenType.EOF, null) };

		checkTokenStream(lexer, correctData);
	}

	@Test
	public void testNoActualContent() {
		// When input is only of spaces, tabs, newlines, etc...
		Lexer lexer = new Lexer("   \r\n\t    $}");
		lexer.setState(LexerState.EXTENDED);
		Token correctData[] = { new Token(TokenType.WORD, "$}"), new Token(TokenType.EOF, null) };

		checkTokenStream(lexer, correctData);

	}

	@Test
	public void testStringToken() {
		Lexer lexer = new Lexer("  \"\\\\1st\"  \r\n\t   ");

		// We expect the following stream of tokens
		lexer.setState(LexerState.EXTENDED);
		Token correctData[] = { new Token(TokenType.STRING, "\"\\1st\""), new Token(TokenType.EOF, null) };

		checkTokenStream(lexer, correctData);
	}

	@Test(expected = LexerException.class)
	public void testInvalidEscapeEnding() {
		Lexer lexer = new Lexer("   \\"); // this is three spaces and a single
											// backslash -- 4 letters string
		lexer.setState(LexerState.EXTENDED);
		// will throw!
		lexer.nextToken();
	}

	@Test(expected = LexerException.class)
	public void testInvalidEscape() {
		Lexer lexer = new Lexer("   \\a    ");

		// will throw!
		lexer.nextToken();
	}

	@Test(expected = LexerException.class)
	public void testInvalidEscapeE() {
		Lexer lexer = new Lexer("   \\a    ");
		lexer.setState(LexerState.EXTENDED);
		// will throw!
		lexer.nextToken();
	}
	

	@Test(expected = LexerException.class)
	public void testInvalidEscapeEInStringTag() {
		Lexer lexer = new Lexer("   \"\\a\"    ");
		lexer.setState(LexerState.EXTENDED);
		// will throw!
		lexer.nextToken();
	}
	
	@Test
	public void testEscapeEInStringTag() {
		Lexer lexer = new Lexer("   \"\\n\\r\\t\"    ");
		lexer.setState(LexerState.EXTENDED);
		
		Token correctData[] = { new Token(TokenType.STRING, "\"\n\r\t\""),new Token(TokenType.EOF, null) };
		checkTokenStream(lexer, correctData);
	}

	
	@Test
	public void testSingleDigitInTag() {
		Lexer lexer = new Lexer("=1  ");
		lexer.setState(LexerState.EXTENDED);
		// We expect the following stream of tokens
		Token correctData[] = { new Token(TokenType.Name, "="),new Token(TokenType.NUMBER, 1), new Token(TokenType.EOF, null) };

		checkTokenStream(lexer, correctData);
	}

	@Test
	public void testWordWithBackslashesEscapes() {
		// Lets check for several words...
		Lexer lexer = new Lexer("  ab1_2cd3 ab__21cd4\"\\\\\" \r\n\t   ");
		lexer.setState(LexerState.EXTENDED);
		// We expect the following stream of tokens
		Token correctData[] = { new Token(TokenType.Name, "ab1_2cd3"), new Token(TokenType.Name, "ab__21cd4"), 
				new Token(TokenType.STRING,"\"\\\""),
				new Token(TokenType.EOF, null) };

		checkTokenStream(lexer, correctData);
	}
	
	@Test
	public void testTwoNumbers() {
		// Lets check for several numbers...
		Lexer lexer = new Lexer("  1234\r\n\t 5678   ");
		lexer.setState(LexerState.EXTENDED);
		Token correctData[] = { new Token(TokenType.NUMBER, Integer.valueOf(1234)),
				new Token(TokenType.NUMBER, Integer.valueOf(5678)), new Token(TokenType.EOF, null) };

		checkTokenStream(lexer, correctData);
	}
	
	@Test
	public void testMinusAndNegativeNumber() {
		// Lets check for several numbers...
		Lexer lexer = new Lexer("-lak  1234\r\n\t -5678   ");
		lexer.setState(LexerState.EXTENDED);
		Token correctData[] = {new Token(TokenType.Operator,'-'),new Token(TokenType.Name,"lak"), new Token(TokenType.NUMBER, Integer.valueOf(1234)),
				new Token(TokenType.NUMBER, Integer.valueOf(-5678)), new Token(TokenType.EOF, null) };

		checkTokenStream(lexer, correctData);
	}
	
	@Test
	public void testFunctionName() {
		// Lets check for several numbers...
		Lexer lexer = new Lexer("-@lak  1234\r\n\t -5678   ");
		lexer.setState(LexerState.EXTENDED);
		Token correctData[] = {new Token(TokenType.Operator,'-'),new Token(TokenType.FunctName,"lak"), new Token(TokenType.NUMBER, Integer.valueOf(1234)),
				new Token(TokenType.NUMBER, Integer.valueOf(-5678)), new Token(TokenType.EOF, null) };

		checkTokenStream(lexer, correctData);
	}

	@Test(expected = LexerException.class)
	public void testTooBigNumber() {
		Lexer lexer = new Lexer("  12345678912123123432123   ");
		lexer.setState(LexerState.EXTENDED);
		// will throw!
		lexer.nextToken();
	}

	
	@Test
	public void testWordWithManyEscapesAndNumbers() {
		// Lets check for several words...
		Lexer lexer = new Lexer("  ab1 23 cd  \r\n\t   ");
		lexer.setState(LexerState.EXTENDED);

		// We expect following stream of tokens
		Token correctData[] = { new Token(TokenType.Name, "ab1"), new Token(TokenType.NUMBER, Integer.valueOf(23)),
				new Token(TokenType.Name, "cd"), // this
																							// is
																							// 8-letter
																							// long,
																							// not
																							// nine!
																							// Only
																							// single
																							// backslash!
				new Token(TokenType.EOF, null) };

		checkTokenStream(lexer, correctData);
	}


	// Helper method for checking if lexer generates the same stream of tokens
	// as the given stream.
	private void checkTokenStream(Lexer lexer, Token[] correctData) {
		int counter = 0;
		for (Token expected : correctData) {
			Token actual = lexer.nextToken();
			String msg = "Checking token " + counter + ":";
			System.out.println(actual.value);
			System.out.println(expected.value);
			assertEquals(msg, expected.getType(), actual.getType());
			assertEquals(msg, expected.getValue(), actual.getValue());
			counter++;
		}
		// System.out.println();
	}

	// ----------------------------------------------------------------------------------------------------------
	// --------------------- Second part of tests; uncomment when everything
	// above works ------------------------
	// ----------------------------------------------------------------------------------------------------------

	
	@Test(expected = IllegalArgumentException.class)
	public void testNullState() {
		new Lexer("").setState(null);
	}
	
	@Test(expected = LexerException.class)
	public void testWrongDecimalNumber() {
		// Lets check for several numbers...
		Lexer lexer = new Lexer("4.5.21\r\n\t -5678   ");
		lexer.setState(LexerState.EXTENDED);
		System.out.println(lexer.nextToken());
		lexer.nextToken();//no miningfultag
	}
	
	@Test
	public void testDecimalNumber() {
		// Lets check for several numbers...
		Lexer lexer = new Lexer("-@lak  1234.45\r\n\t -5678   ");
		lexer.setState(LexerState.EXTENDED);
		Token correctData[] = {new Token(TokenType.Operator,'-'),new Token(TokenType.FunctName,"lak"), new Token(TokenType.NUMBER, Double.valueOf(1234.45)),
				new Token(TokenType.NUMBER, Integer.valueOf(-5678)), new Token(TokenType.EOF, null) };

		checkTokenStream(lexer, correctData);
	}

	
	@Test
	public void testNotNullInExtended() {
		Lexer lexer = new Lexer("");
		lexer.setState(LexerState.EXTENDED);

		assertNotNull("Token was expected but null was returned.", lexer.nextToken());
	}

	
	@Test
	public void testEmptyInExtended() {
		Lexer lexer = new Lexer("");
		lexer.setState(LexerState.EXTENDED);

		assertEquals("Empty input must generate only EOF token.", TokenType.EOF, lexer.nextToken().getType());
	}


	@Test
	public void testGetReturnsLastNextInExtended() {
		// Calling getToken once or several times after calling nextToken must
		// return each time what nextToken returned...
		Lexer lexer = new Lexer("");
		lexer.setState(LexerState.EXTENDED);

		Token token = lexer.nextToken();
		assertEquals("getToken returned different token than nextToken.", token, lexer.getToken());
		assertEquals("getToken returned different token than nextToken.", token, lexer.getToken());
	}


	@Test(expected = LexerException.class)
	public void testRadAfterEOFInExtended() {
		Lexer lexer = new Lexer("");
		lexer.setState(LexerState.EXTENDED);

		// will obtain EOF
		lexer.nextToken();
		// will throw!
		lexer.nextToken();
	}

	@Test
	public void testNoActualContentInExtended() {
		// When input is only of spaces, tabs, newlines, etc...
		Lexer lexer = new Lexer("   \r\n\t    ");
		lexer.setState(LexerState.EXTENDED);

		assertEquals("Input had no content. Lexer should generated only EOF token.", TokenType.EOF,
				lexer.nextToken().getType());
	}
	
	@Test
	public void testMultipartInput() {
		// Test input which has parts which are tokenized by different rules...
		Lexer lexer = new Lexer("This is sample text.{$ FOR i 1 10 1 $} 463abc zzz");

		checkToken(lexer.nextToken(), new Token(TokenType.TEXT, "This is sample text."));
		checkToken(lexer.nextToken(), new Token(TokenType.WORD, "{$"));

		lexer.setState(LexerState.EXTENDED);
		checkToken(lexer.nextToken(), new Token(TokenType.Name, "FOR"));
		checkToken(lexer.nextToken(), new Token(TokenType.Name, "i"));
		checkToken(lexer.nextToken(), new Token(TokenType.NUMBER, 1));
		checkToken(lexer.nextToken(), new Token(TokenType.NUMBER, 10));
		checkToken(lexer.nextToken(), new Token(TokenType.NUMBER, 1));
		checkToken(lexer.nextToken(),new Token(TokenType.WORD,"$}"));
		
		lexer.setState(LexerState.BASIC);

		checkToken(lexer.nextToken(), new Token(TokenType.TEXT, " 463abc zzz"));
		checkToken(lexer.nextToken(), new Token(TokenType.EOF, null));

	}

	private void checkToken(Token actual, Token expected) {
		String msg = "Token are not equal.";
		System.out.println(actual.getValue() + " " + expected.getValue());
		assertEquals(msg, expected.getType(), actual.getType());
		assertEquals(msg, expected.getValue(), actual.getValue());
	}

}
