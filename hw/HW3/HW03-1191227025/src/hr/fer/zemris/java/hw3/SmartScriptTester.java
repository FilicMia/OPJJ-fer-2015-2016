/**
 * 
 */
package hr.fer.zemris.java.hw3;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.collections.Processor;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

/**
 * Class showing the the usage of SmartScriptParser.
 * 
 * @author mia
 *
 */
public class SmartScriptTester {

	/**
	 * Method invoked when running the program.
	 * 
	 * @param args
	 *            command-line arguments
	 */
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
		// docBody = "";
		SmartScriptParser parser = null;
		try {
			parser = new SmartScriptParser(docBody);
		} catch (SmartScriptParserException e) {
			System.out.println("Unable to parse document!");
			e.printStackTrace();
			System.exit(-1);
		} catch (Exception e) {
			System.out.println(
					"If this line ever executes, you have failed this class!");
			e.printStackTrace();
			System.exit(-1);
		}
		
		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = createOriginalDocumentBody(document);
		System.out.println("Doc1:\n" + originalDocumentBody); // should write
																// something
																// like
		printDocumentTree(document);
		// original
		// content of docBody
		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
		DocumentNode document2 = parser2.getDocumentNode();
		String origDoc2 = createOriginalDocumentBody(document2);
		System.out.println("Doc2:\n" + createOriginalDocumentBody(document2));
		// now document and document2 should be structurally identical trees
		System.out.println(
				"Jesu li doc i doc2 jednaki(prema generiranju izvornog dokumenta iz stabla dokuments)? "
						+ origDoc2.equals(originalDocumentBody));
		
		printDocumentTree(document2);
		System.out.println(
				"Jesu li doc i doc2 jednaki(prema generiranom stablu)? "
						+ documentEqual(document, document2));

	}

	/**
	 * Private method that creates string representation of document represented
	 * by its tree representation.
	 * 
	 * @param document
	 *            root of document-tree representation of the document;
	 *            <code>DocumentNode</code>
	 */
	private static String createOriginalDocumentBody(DocumentNode document) {
		String string = "";
		string += createOriginal(document);
		return string;
	}

	/**
	 * Supplementary method which is called recursively to create string
	 * representation of document represented by its root of the document-tree.
	 * 
	 * @param node
	 *            root of document-tree; <code>Node</code>
	 */
	private static String createOriginal(Node node) {
		int numOfChildren = node.numberOfChildren();
		String text = node.toString();

		for (int i = 0; i < numOfChildren; ++i) {
			Node child = node.getChild(i);
			//System.out.println("Parent: " + node.toString() + " Child:"
			//		+ child.toString());
			text += createOriginal(child);
		}
		if (node instanceof ForLoopNode) {
			text += "{$END$}";
		}

		return text;
	}

	/**
	 * Private method that prints the document tree without the
	 * DocumentNode(root of the document).
	 */
	private static void printDocumentTree(Node node) {
		int numOfChildren = node.numberOfChildren();
		for (int i = 0; i < numOfChildren; ++i) {
			Node child = node.getChild(i);
			System.out.print(" Child "+i+":"+ child.toString());
		}
		System.out.println();
		for (int i = 0; i < numOfChildren; ++i) {
			Node child = node.getChild(i);
			System.out.println(i+"---------------------------------");
			printDocumentTree(child);
		}
	}
	
	/**
	 * Private method that checks if 2 document trees are identical.
	 * 
	 * @param node root of the first document tree.
	 * @param other root of the second document tree.
	 */

	private static boolean documentEqual(Node node,Node other) {
		if(node.getClass() != other.getClass() || !node.toString().equals(other.toString())){
			return false;
		}
		
		int numOfChildren = node.numberOfChildren();
		if(numOfChildren != other.numberOfChildren()){
			return false;
		}
		
		for (int i = 0; i < numOfChildren; ++i) {
			Node child = node.getChild(i);
			if(!documentEqual(child,other.getChild(i))){
				return false;
			}
		}
		
		return true;
	}
}
