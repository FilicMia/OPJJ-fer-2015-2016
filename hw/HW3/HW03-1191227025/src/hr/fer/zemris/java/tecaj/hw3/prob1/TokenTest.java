package hr.fer.zemris.java.tecaj.hw3.prob1;
/**
 * Class that demonstrates usage of Lexer class.
 * 
 * @author mia
 *
 */
public class TokenTest {

	public static void main(String[] args) {
		String input = "  ložica  hak #hos66bvdo osbd  \r#";
		Lexer lexer = new Lexer(input);
		//System.out.println(Character.isLetter('\\'));//false :D
		
		while(lexer.nextToken().getType() != TokenType.EOF){
			System.out.println(lexer.getToken().getType()+", "+lexer.getToken().getValue());
			if(lexer.getToken().getValue().equals('#')){
				lexer.setState(LexerState.EXTENDED);
				break;
			}
		}
		while(lexer.nextToken().getType() != TokenType.EOF){
			System.out.println(lexer.getToken().getType()+", "+lexer.getToken().getValue());
			if(lexer.getToken().getValue().equals('#')){
				lexer.setState(LexerState.BASIC);
				break;
			}
		}

	}

}
