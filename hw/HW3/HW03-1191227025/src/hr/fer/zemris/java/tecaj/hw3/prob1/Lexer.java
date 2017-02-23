package hr.fer.zemris.java.tecaj.hw3.prob1;

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
		start = 0; // it hasnt started jet
		token = null;
	}

	/**
	 * Generates and returns the next token. Throws LexerException if an error
	 * occurs . If prompted after generating a token type EOF throws
	 * LexerException .
	 * 
	 * @throws LexerException
	 *             when an error occurs.
	 */
	public Token nextToken() {
		if (token != null && token.getType() == TokenType.EOF) {
			throw new LexerException("The end of file. No next token to generate.");
		}

		try {
			if (state == LexerState.BASIC) {
				stateBasicNext();
			} else {
				stateExtendedNext();
			}
		} catch (NumberFormatException e) {
			String message = "";
			while (currentIndex > start) {
				message += data[start++];
			}

			throw new LexerException("Error in token! " + message + " that ends at " + (currentIndex - 1));
		} catch (Exception e) {
			String message = "";
			while (currentIndex > start) {
				message += data[start++];
			}

			throw new LexerException(
					"Error in generating " + "next token! " + message + " that ends at " + (currentIndex - 1));

		}
		// System.out.println(token.getType());
		return token;

	}

	/**
	 * Supplementary method that generates the next token so as the state of
	 * lexer is BASIC. Throws LexerException if an error occurs .
	 */
	private void stateBasicNext() {
		while (currentIndex < data.length && Character.isWhitespace(data[currentIndex])) {
			currentIndex++;
			continue;
		}
		// start = currentIndex;
		if (currentIndex >= data.length) {
			token = new Token(TokenType.EOF, null);
		} else {
			token = createToken();
		}
	}

	/**
	 * Supplementary method that generates the next token so as the state of
	 * lexer is EXTENDED. Throws LexerException if an error occurs .
	 */
	private void stateExtendedNext() {
		while (currentIndex < data.length && Character.isWhitespace(data[currentIndex])) {
			currentIndex++;
			continue;
		}
		start = currentIndex;
		if (currentIndex >= data.length) {
			token = new Token(TokenType.EOF, null);
		} else {
			if(data[currentIndex] == '#'){
				token = new Token(TokenType.SYMBOL,'#');
				currentIndex++;
				return;
			}
			
			String value = "";
			while (currentIndex < data.length && data[currentIndex] != '#'
					&& !Character.isWhitespace(data[currentIndex])) {
				value = value + data[currentIndex];
				currentIndex++;
			}
			// currentIndex is now end of token+1

			token = new Token(TokenType.WORD, value);

		}
	}

	/**
	 * Private method that creates an token starting at current index index of
	 * data field.
	 * 
	 * @return Token
	 */
	private Token createToken() {
		// if we get to the end of file
		if (data.length <= currentIndex) {
			return new Token(TokenType.EOF, null); // null reference
		}
		start = currentIndex;
		if (isLetter(data, currentIndex)) {
			return createWord();
		}

		if (Character.isDigit(data[currentIndex])) {
			return createNumber();
		}
		return new Token(TokenType.SYMBOL, data[currentIndex++]);
	}

	/**
	 * Supplemenary method that checks if an occurance of \num, \\ occured at
	 * (ch[currIndex],ch[currIndex+1]) occured or if char ch[currIndex] is
	 * letter. Checking if ch[currIndex] is letter is done calling
	 * Character.isLetter().
	 * 
	 * @return boolean
	 */
	private boolean isLetter(char[] ch, int currIndex) {
		if (Character.isLetter(ch[currIndex])) {
			return true;
		}
		if (ch[currIndex] != '\\') {
			return false;
		} else {
			if (currIndex < ch.length && ((Character.isDigit(ch[currIndex + 1]) || ch[currIndex + 1] == '\\'))) {
				return true;
			} else {
				throw new LexerException();
			}

		}
	}

	/**
	 * Supplementary method that creates new instance of token, type WORD.
	 * 
	 * @return
	 */
	private Token createWord() {
		String value = "";
		start = currentIndex;
		while (currentIndex < data.length && isLetter(data, currentIndex)) {
			if (data[currentIndex] == '\\') {
				currentIndex++;
			}
			value = value + data[currentIndex];
			currentIndex++;
		}
		// currentIndex is now end of token+1

		return new Token(TokenType.WORD, value);
	}

	/**
	 * Supplementary method that creates new instance of token, type NUMBER.
	 * 
	 * @return
	 */
	private Token createNumber() {
		String value = "";
		start = currentIndex;
		while (currentIndex < data.length && Character.isDigit(data[currentIndex])) {
			value = value + data[currentIndex];
			currentIndex++;
		}
		// currentIndex is now end of token+1

		return new Token(TokenType.NUMBER, Long.parseLong(value));
	}

	/**
	 * Methods returns the last generated token . It can be invoked / *
	 * repeatedly . Doesn't launch the next generation of tokens
	 */
	public Token getToken() {
		return token;
	}

	/**
	 * Method sets lexer state.
	 * 
	 * @param next
	 *            state of lexer
	 */
	public void setState(LexerState state) {
		if (state == null) {
			throw new IllegalArgumentException("State cannot be " + state);
		}
		this.state = state;
	}
}
