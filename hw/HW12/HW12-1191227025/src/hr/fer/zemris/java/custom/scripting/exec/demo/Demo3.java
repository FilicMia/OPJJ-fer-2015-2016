package hr.fer.zemris.java.custom.scripting.exec.demo;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.java.custom.scripting.demo.ReadUtil;
import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * Program demonstarating the usage of SmartscriptEngine
 * 
 * @author mia
 *
 */
public class Demo3 {

	/**
	 * Method called when invoking program.
	 * 
	 * @param args
	 *            command-line arguments.
	 */
	public static void main(String[] args) {

		String documentBody = ReadUtil
				.readFromDisk("examples/brojPoziva.smscr",StandardCharsets.UTF_8);
		Map<String, String> parameters = new HashMap<String, String>();
		Map<String, String> persistentParameters = new HashMap<String, String>();
		List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();
		persistentParameters.put("brojPoziva", "3");
		RequestContext rc = new RequestContext(System.out, parameters,
				persistentParameters, cookies);
		new SmartScriptEngine(
				new SmartScriptParser(documentBody).getDocumentNode(), rc)
						.execute();
		System.out.println("Vrijednost u mapi: "
				+ rc.getPersistentParameter("brojPoziva"));
	}
}