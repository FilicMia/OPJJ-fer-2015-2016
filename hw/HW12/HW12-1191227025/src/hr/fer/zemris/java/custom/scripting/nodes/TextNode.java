package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * (Class) Node representing a piece of textual data. It inherits form
 * <code>Node</code>
 * 
 * @author mia
 *
 */
public class TextNode extends Node {
	/** Read-only property representing text of this TextNode */
	private String text;

	/**
	 * Constructor. Setting read-only property text.
	 * 
	 * @param text
	 *            value on which property <code>text</code> should be set;
	 *            <code>String</code>
	 */
	public TextNode(String text) {
		this.text = text;
	}

	/**
	 * Gets the property of TextNode.
	 *
	 * @return text property; <code>String</code>
	 */
	public String getText() {
		return this.text;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		char[] charSq = text.toCharArray();
		int size = charSq.length;
		for (int i = 0; i < size; ++i) {
			if (charSq[i] == '\\' || charSq[i] == '{') {
				sb.append('\\');
			}
			sb.append(charSq[i]);
		}
		return sb.toString();
	}

	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitTextNode(this);

	}
}
