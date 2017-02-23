package hr.fer.zemris.java.custom.scripting.demo;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * Program demonstrites the usage of {@link SmartScriptEngine}.
 * 
 */
public class SmartScriptEngineDemo {

	/**
	 * Method called when invoking program.
	 * 
	 * @param args
	 *            command-line arguments.
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.println(
					"Only one command-line argument needed: file_path\n");
			System.exit(-1);
		}

		String documentBody = ReadUtil.readFromDisk(args[0],StandardCharsets.UTF_8);
		Map<String, String> parameters = new HashMap<String, String>();
		Map<String, String> persistentParameters = new HashMap<String, String>();
		List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();
		// put some parameter into parameters map
		parameters.put("broj", "4");
		// create engine and execute it
		new SmartScriptEngine(
				new SmartScriptParser(documentBody).getDocumentNode(),
				new RequestContext(System.out, parameters, persistentParameters,
						cookies)).execute();
	}
}
