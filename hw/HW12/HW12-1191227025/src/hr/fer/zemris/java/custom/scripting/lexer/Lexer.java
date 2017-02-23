package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * A simple lexical analyzer . It makes the first part of the subsystem to
 * produce lexical analysis .
 * 
 * @author mia
 *
 */
public class Lexer {
	/** Input text. */
	private char[] data;
	/** Current token. */
	private Token token;
	/** Index of first untokenized char in data. */
	private int currentIndex;
	/** Help variable to be able to print the token in which an error occurs. */
	private int start;
	/** State of lexer. */
	private LexerState state = LexerState.BASIC;

	/**
	 * Constructor.
	 * 
	 * 
	 * @param text
	 *            import text for lexer analyzer; string
	 */
	public Lexer(String text) {
		if (text == null) {
			throw new IllegalArgumentException(text);
		}
		data = text.toCharArray();
		currentIndex = 0;
		start = 0;
		token = null;
	}

	/**
	 * Generates and returns the next token. Throws LexerException if an error
	 * occurs . If prompted after generating a token type EOF throws
	 * LexerException .
	 * 
	 * @return generated token
	 * 
	 * @throws LexerException
	 *             when an error occurs.
	 */
	public Token nextToken() {
		if (token != null && token.getType() == TokenType.EOF) {
			throw new LexerException(
					"The end of file. No next token to generate.");
		}

		try {
			if (state == LexerState.BASIC) {
				basicNext();
			} else {
				extendedNext();
			}
		} catch (NumberFormatException e) {
			String message = "";
			while (currentIndex > start) {
				message += data[start++];
			}

			throw new LexerException("Error in token! " + message
					+ " that ends at " + (currentIndex - 1));
		} catch (Exception e) {
			String message = "";
			while (currentIndex > start) {
				message += data[start++];
			}

			throw new LexerException("Error in generating " + "next token! "
					+ message + " that ends at " + (currentIndex - 1) + "\n"
					+ e.getMessage());

		}
		// System.out.println(token.getType());
		return token;

	}

	/**
	 * Supplementary method that generates the next token so as the state of
	 * lexer is BASIC. Throws LexerException if an error occurs .
	 */
	private void basicNext() {
		// start = currentIndex;
		if (currentIndex >= data.length) {
			token = new Token(TokenType.EOF, null);
		} else {
			createBasicToken();
		}
	}

	/**
	 * Supplementary method that generates the next token so as the state of
	 * lexer is EXTENDED. Throws LexerException if an error occurs .
	 */
	private void extendedNext() {
		while (currentIndex < data.length
				&& Character.isWhitespace(data[currentIndex])) {
			currentIndex++;
			continue;
		} // we arw now on first non wmpty space
		if (data.length <= currentIndex) {
			token = new Token(TokenType.EOF, null); // null reference
			return;
		}
		start = currentIndex;
		// System.out.print(data);
		// System.out.println(" "+data[start]);;
		switch (data[currentIndex]) {
		case '@':
			currentIndex++;
			createFunctName();
			return;
		case '"':// string
			createString();// "" are left
			return;
		case '*':
		case '+':
		case '/':
		case '^':
			token = new Token(TokenType.OPERATOR, data[currentIndex++]);
			break;
		case '$':
			String value = "";
			if (currentIndex + 1 < data.length && data[currentIndex] == '$'
					&& data[currentIndex + 1] == '}') {
				value += data[currentIndex++];
				value += data[currentIndex++];
				token = new Token(TokenType.WORD, value);
			}
			break;
		case '=':
			token = new Token(TokenType.NAME,
					String.valueOf(data[currentIndex++]));
			return;
		case '-':
			if (currentIndex + 1 >= data.length
					|| !Character.isDigit(data[currentIndex + 1])) {
				token = new Token(TokenType.OPERATOR, data[currentIndex++]);
				break;
			}
		default:
			// if we get here,after - is definitely a number
			if (data[currentIndex] == '-'
					|| Character.isDigit(data[currentIndex])) {
				// if its decimal number ,it must starts with 0
				createNumber();
				return;
			}

			if (Character.isLetter(data[currentIndex])) {
				value = name();
				token = new Token(TokenType.NAME, value);
				return;
			}
			throw new LexerException(
					"No miningful tag starts with " + data[currentIndex]);
			// createWord();

		}
	}

	/**
	 * Private method that creates an NUMBER token starting at current index The
	 * value is <code>Integer</code> or <code>double</code> representation of
	 * number. That token is now current token in Lexer.
	 * 
	 */
	@SuppressWarnings("unused")
	private void createWord() {
		String value = "";
		while (currentIndex < data.length
				&& Character.isWhitespace(data[currentIndex])) {
			currentIndex++;
		}
		if (currentIndex + 1 < data.length && data[currentIndex] == '$'
				&& data[currentIndex + 1] == '}') {
			value += data[currentIndex++];
			value += data[currentIndex++];
			token = new Token(TokenType.WORD, value);
			return;
		}
		// no escaping!
		while (currentIndex < data.length
				&& !Character.isWhitespace(data[currentIndex])) {
			if (data[currentIndex] == '\\') {
				throw new LexerException("No exceping alowed.");
			}
			value += data[currentIndex++];
		}
		token = new Token(TokenType.WORD, value);

	}

	/**
	 * Private method that creates an NUMBER token starting at current index The
	 * value is <code>Integer</code> or <code>double</code> representation of
	 * number. That token is now current token in Lexer.
	 * 
	 */
	private void createNumber() {
		String value = "";
		value += data[currentIndex++];
		// minus is no longer allowed
		while (currentIndex < data.length
				&& Character.isDigit(data[currentIndex])) {
			value += data[currentIndex++];
		}

		if (currentIndex + 1 < data.length && (data[currentIndex] == '.'
				&& Character.isDigit(data[currentIndex + 1]))) {
			value += data[currentIndex++]; // add .
		} else {
			token = new Token(TokenType.NUMBER, Integer.parseInt(value));
			return;
		}
		// get decimals of number
		while (currentIndex < data.length
				&& Character.isDigit(data[currentIndex])) {
			value += data[currentIndex++];
		}
		token = new Token(TokenType.NUMBER, Double.parseDouble(value));
	}

	/**
	 * Private method that creates an STRING token starting at current index
	 * index of data field. It starts and ends with ". If not, appropriate
	 * exception is thrown. That token is now current token in Lexer.
	 * 
	 */
	private void createString() {

		String value = String.valueOf(data[currentIndex++]);
		while (currentIndex < data.length && data[currentIndex] != '"') {
			if (data[currentIndex] == '\\') {
				currentIndex++;
				if (currentIndex >= data.length) {
					throw new LexerException(
							"Invalid escaping in STRING token.");
				}

				switch (data[currentIndex]) {
				case '"':
				case '\\':
					value += data[currentIndex++];
					break;
				case 'n':
					value += '\n';
					currentIndex++;
					break;
				case 'r':
					value += '\r';
					currentIndex++;
					break;
				case 't':
					value += '\t';
					currentIndex++;
					break;
				default:
					throw new LexerException(
							"Invalid escaping in STRING token.");
				}
			} else {
				value += data[currentIndex++];
			}
		}

		if (currentIndex < data.length) {
			value += data[currentIndex++];
		} else {
			throw new LexerException("Missing end: \".");
		}

		token = new Token(TokenType.STRING, value);
	}

	/**
	 * Private method that creates an FunctName token starting at current index
	 * index of data field. That token is now current token in Lexer.
	 * 
	 */
	private void createFunctName() {
		try {
			String value = name();
			token = new Token(TokenType.FUNCT_NAME, value);
		} catch (LexerException e) {
			currentIndex--;// that @ is also included
			throw new LexerException("No valid function name!");
		}

	}

	/**
	 * Supplementary method that creates string representation of variable or
	 * function name.
	 *
	 * @param valid
	 *            function or variable name; <code>String</code>
	 * @return string representatio of variable or function name
	 */
	private String name() {
		if (currentIndex >= data.length
				|| !Character.isLetter(data[currentIndex])) {
			throw new LexerException();
		}

		String value = String.valueOf(data[currentIndex++]);
		while (currentIndex < data.length
				&& (Character.isLetter(data[currentIndex])
						|| data[currentIndex] == '_'
						|| Character.isDigit(data[currentIndex]))) {
			value += data[currentIndex++];
		}

		return value;
	}

	/**
	 * Private method that creates instance of basic token (of type
	 * <code>TEXT</code> or <code>WORD</code> with content "{$") starting at
	 * current index index of data field.
	 * 
	 */
	private void createBasicToken() {
		// is it the end of text?
		if (data[currentIndex] == '{') {// need to see the next one also
			if ((currentIndex + 1) < data.length) {
				if (data[currentIndex + 1] == '$') {
					token = new Token(TokenType.WORD, "{$");
					currentIndex += 2;
					return;
				}
			}
		}

		String value = "";
		start = currentIndex;
		while (currentIndex < data.length) {
			if (data[currentIndex] == '{') {// need to see the next one also
				if ((currentIndex + 1) < data.length) {
					if (data[currentIndex + 1] == '$') {
						break;
					}
				}
			}

			if (data[currentIndex] == '\\') {// need to see the next also
				currentIndex++;
				if (currentIndex < data.length && (data[currentIndex] == '\\'
						|| data[currentIndex] == '{')) {
					value += data[currentIndex++];
					continue;
				} else {
					throw new LexerException("Illegal escaping in TEXT token.");
				}

			}

			value += data[currentIndex++];
		}

		token = new Token(TokenType.TEXT, value);

	}

	/**
	 * Methods returns the last generated token . It can be invoked / *
	 * repeatedly . Doesn't launch the next generation of tokens
	 * 
	 * @return the last generated token
	 */
	public Token getToken() {
		return token;
	}

	/**
	 * Method sets lexer state.
	 * 
	 * @param state
	 *            state of lexer
	 */
	public void setState(LexerState state) {
		if (state == null) {
			throw new IllegalArgumentException("State cannot be " + state);
		}
		this.state = state;
	}

	/**
	 * Public method that returns an state of LEXER.
	 * 
	 * @return state; <code>LexerState</code>
	 */
	public LexerState getState() {
		return state;
	}
}
