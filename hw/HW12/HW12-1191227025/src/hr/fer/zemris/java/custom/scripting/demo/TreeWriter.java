package hr.fer.zemris.java.custom.scripting.demo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

/**
 * Program opens smart script file, parse it into a tree and that reproduce its
 * (aproximate) original form onto standard output using Visitor pattern.
 * 
 * @author mia
 *
 */
public class TreeWriter {

	/**
	 * Method called when invoking program.
	 * 
	 * @param args
	 *            command-line arguments.
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

		SmartScriptParser parser = null;
		try {
			parser = new SmartScriptParser(docBody);
		} catch (SmartScriptParserException e) {
			System.out.println("Unable to parse document!");
			System.exit(-1);
		} catch (Exception e) {
			System.out.println(
					"If this line ever executes, you have failed this class!");
			System.exit(-1);
		}

		WriterVisitor visitor = new WriterVisitor();
		parser.getDocumentNode().accept(visitor);
	}

	/**
	 * {@link INodeVisitor} which writes the reconstruction of each node on
	 * standard output.
	 * 
	 * @author mia
	 *
	 */
	public static class WriterVisitor implements INodeVisitor {

		@Override
		public void visitTextNode(TextNode node) {
			int numOfChildren = node.numberOfChildren();
			System.out.print(node.toString());
			for (int i = 0; i < numOfChildren; ++i) {
				node.getChild(i).accept(this);
			}

		}

		@Override
		public void visitForLoopNode(ForLoopNode node) {
			int numOfChildren = node.numberOfChildren();
			System.out.print(node.toString());
			for (int i = 0; i < numOfChildren; ++i) {
				node.getChild(i).accept(this);
			}
			System.out.println("{$END$}");
		}

		@Override
		public void visitEchoNode(EchoNode node) {
			int numOfChildren = node.numberOfChildren();
			System.out.print(node.toString());
			for (int i = 0; i < numOfChildren; ++i) {
				node.getChild(i).accept(this);
			}

		}

		@Override
		public void visitDocumentNode(DocumentNode node) {
			int numOfChildren = node.numberOfChildren();

			for (int i = 0; i < numOfChildren; ++i) {
				node.getChild(i).accept(this);
			}

		}

	}

}
