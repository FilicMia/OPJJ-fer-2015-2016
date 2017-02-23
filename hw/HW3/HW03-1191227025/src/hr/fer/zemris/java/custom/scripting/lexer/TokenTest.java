package hr.fer.zemris.java.custom.scripting.lexer;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;


/**
 * Class that tests the Lexer class.
 * 
 * @author mia
 *
 */
public class TokenTest {

	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.println(
					"Needed only one command-line argument: file_path\n");
			System.exit(-1);
		}
		
		String docBody = null;
		
		try {
			docBody = new String(Files.readAllBytes(Paths.get(args[0])),
					StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Lexer lexer = new Lexer(docBody);
		System.out.println(Character.isLetter('\\'));//false :D
		
		while(lexer.nextToken().getType() != TokenType.EOF){
			System.out.println(lexer.getToken().getType()+", "+lexer.getToken().getValue());
			if(lexer.getToken().getValue().equals("{$")){
				lexer.setState(LexerState.EXTENDED);
				break;
			}
		}
		while(lexer.nextToken().getType() != TokenType.EOF){

			System.out.println(lexer.getToken().getType()+", "+lexer.getToken().getValue());
			if(lexer.getToken().getValue().equals("$}")){
				lexer.setState(LexerState.BASIC);
				break;
			}
		}
		
		while(lexer.nextToken().getType() != TokenType.EOF){
			//System.out.println(lexer.getToken().getType()+" "+lexer.getToken().getValue());
			System.out.println(lexer.getToken().getType()+", "+lexer.getToken().getValue());
			if(lexer.getToken().getValue().equals("{$")){
				lexer.setState(LexerState.EXTENDED);
				break;
			}
		}
		while(lexer.nextToken().getType() != TokenType.EOF){
			
			System.out.println(lexer.getToken().getType()+", "+lexer.getToken().getValue());
			if(lexer.getToken().getValue().equals("$}")){
				lexer.setState(LexerState.BASIC);
				break;
			}
		}
		
		while(lexer.nextToken().getType() != TokenType.EOF){
			
			System.out.println(lexer.getToken().getType()+", "+lexer.getToken().getValue());
			if(lexer.getToken().getValue().equals("{$")){
				lexer.setState(LexerState.EXTENDED);
				break;
			}
		}
		while(lexer.nextToken().getType() != TokenType.EOF){
			
			System.out.println(lexer.getToken().getType()+", "+lexer.getToken().getValue());
			if(lexer.getToken().getValue().equals("$}")){
				lexer.setState(LexerState.BASIC);
				break;
			}
		}
		
		while(lexer.nextToken().getType() != TokenType.EOF){
			System.out.println(lexer.getToken().getType()+", "+lexer.getToken().getValue());
			if(lexer.getToken().getValue().equals("{$")){
				lexer.setState(LexerState.EXTENDED);
				break;
			}
		}
		while(lexer.nextToken().getType() != TokenType.EOF){

			System.out.println(lexer.getToken().getType()+", "+lexer.getToken().getValue());
			if(lexer.getToken().getValue().equals("$}")){
				lexer.setState(LexerState.BASIC);
				break;
			}
		}
		
		while(lexer.nextToken().getType() != TokenType.EOF){
			//System.out.println(lexer.getToken().getType()+" "+lexer.getToken().getValue());
			System.out.println(lexer.getToken().getType()+", "+lexer.getToken().getValue());
			if(lexer.getToken().getValue().equals("{$")){
				lexer.setState(LexerState.EXTENDED);
				break;
			}
		}
		while(lexer.nextToken().getType() != TokenType.EOF){
			
			System.out.println(lexer.getToken().getType()+", "+lexer.getToken().getValue());
			if(lexer.getToken().getValue().equals("$}")){
				lexer.setState(LexerState.BASIC);
				break;
			}
		}
		
		while(lexer.nextToken().getType() != TokenType.EOF){
			
			System.out.println(lexer.getToken().getType()+", "+lexer.getToken().getValue());
			if(lexer.getToken().getValue().equals("{$")){
				lexer.setState(LexerState.EXTENDED);
				break;
			}
		}
		while(lexer.nextToken().getType() != TokenType.EOF){
			
			System.out.println(lexer.getToken().getType()+", "+lexer.getToken().getValue());
			if(lexer.getToken().getValue().equals("$}")){
				lexer.setState(LexerState.BASIC);
				break;
			}
		}
		
		

	}

}
