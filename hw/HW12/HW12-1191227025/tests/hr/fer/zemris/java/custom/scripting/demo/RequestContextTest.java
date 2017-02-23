package hr.fer.zemris.java.custom.scripting.demo;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.charset.spi.CharsetProvider;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

public class RequestContextTest {

	@Test(expected = RuntimeException.class)
	public void testEncodingAfterWrite() throws IOException {
		String filePath = "Test1.txt";
		OutputStream os = Files.newOutputStream(Paths.get(filePath));
		RequestContext rc = new RequestContext(os,
				new HashMap<String, String>(), new HashMap<String, String>(),
				new ArrayList<RequestContext.RCCookie>());
		String encoding = "UTF-8";
		rc.setEncoding(encoding);
		rc.setMimeType("text/plain");
		rc.setStatusCode(205);
		rc.setStatusText("Idemo dalje");
		// Only at this point will header be created and written...
		rc.write("Čevapčići i Šiščevapčići.");
		rc.setEncoding(encoding);
		os.close();
	}

	@Test
	public void testEncodingSetting1() throws IOException {
		String filePath = "Test1.txt";
		OutputStream os = Files.newOutputStream(Paths.get(filePath));
		RequestContext rc = new RequestContext(os,
				new HashMap<String, String>(), new HashMap<String, String>(),
				new ArrayList<RequestContext.RCCookie>());
		String encoding = "ISO-8859-2";
		rc.setEncoding(encoding);
		rc.setMimeType("text/plain");
		rc.setStatusCode(205);
		rc.setStatusText("Idemo dalje");
		rc.write("Čevapčići i Šiščevapčići.");
		os.close();

		String fileContent = ReadUtil.readFromDisk(filePath,
				Charset.forName(encoding));
		String expected = ReadUtil.readFromDisk("test1.txt",
				Charset.forName(encoding));

		assertEquals(expected, fileContent);
	}

	@Test
	public void testEncodingSetting2() throws IOException {
		String filePath = "Test2.txt";
		OutputStream os = Files.newOutputStream(Paths.get(filePath));
		RequestContext rc = new RequestContext(os,
				new HashMap<String, String>(), new HashMap<String, String>(),
				new ArrayList<RequestContext.RCCookie>());
		String encoding = "UTF-8";
		rc.setEncoding(encoding);
		rc.setMimeType("text/plain");
		rc.setStatusCode(205);
		rc.setStatusText("Idemo dalje");
		rc.write("Čevapčići i Šiščevapčići.");
		os.close();

		String fileContent = ReadUtil.readFromDisk(filePath,
				Charset.forName(encoding));
		String expected = ReadUtil.readFromDisk("test2.txt",
				Charset.forName(encoding));

		assertEquals(expected, fileContent);
	}

	@Test
	public void testEncodingSetting3AndCookies() throws IOException {
		String filePath = "Test3.txt";
		OutputStream os = Files.newOutputStream(Paths.get(filePath));
		RequestContext rc = new RequestContext(os,
				new HashMap<String, String>(), new HashMap<String, String>(),
				new ArrayList<RequestContext.RCCookie>());
		String encoding = "UTF-8";
		rc.setMimeType("text/plain");
		rc.setStatusCode(205);
		rc.setStatusText("Idemo dalje");
		rc.addRCCookie(
				new RCCookie("korisnik", "perica", 3600, "127.0.0.1", "/"));
		rc.addRCCookie(new RCCookie("zgrada", "B4", null, null, "/"));
		rc.write("Čevapčići i Šiščevapčići.");
		os.close();

		String fileContent = ReadUtil.readFromDisk(filePath,
				Charset.forName(encoding));
		String expected = ReadUtil.readFromDisk("test3.txt",
				Charset.forName(encoding));

		assertEquals(expected, fileContent);
	}
	
	@Test
	public void testByteStream() throws IOException {
		String filePath = "Test_.txt";
		OutputStream os = Files.newOutputStream(Paths.get(filePath));
		RequestContext rc = new RequestContext(os,
				new HashMap<String, String>(), new HashMap<String, String>(),
				new ArrayList<RequestContext.RCCookie>());
		String encoding = "UTF-8";
		rc.setMimeType("text/plain");
		rc.setStatusCode(205);
		rc.setStatusText("Idemo dalje");
		rc.addRCCookie(
				new RCCookie("korisnik", "perica", 3600, "127.0.0.1", "/"));
		rc.addRCCookie(new RCCookie("zgrada", "B4", null, null, "/"));
		String str = "Čevapčići i Šiščevapčići.";
		
		rc.write(str.getBytes());
		os.close();

		String fileContent = ReadUtil.readFromDisk(filePath,
				Charset.forName(encoding));
		String expected = ReadUtil.readFromDisk("test3.txt",
				Charset.forName(encoding));

		assertEquals(expected, fileContent);
	}

	@Test(expected = NullPointerException.class)
	public void testStreamNotNull() throws IOException {

		RequestContext rc = new RequestContext(null,
				new HashMap<String, String>(), new HashMap<String, String>(),
				new ArrayList<RequestContext.RCCookie>());
	}

	@Test
	public void testPParamSetting() throws IOException {
		String filePath = "Test_.txt";
		OutputStream os = Files.newOutputStream(Paths.get(filePath));
		RequestContext rc = new RequestContext(os,
				new HashMap<String, String>(), new HashMap<String, String>(),
				new ArrayList<RequestContext.RCCookie>());
		String encoding = "UTF-8";
		rc.setMimeType("text/plain");
		rc.setStatusCode(205);
		rc.setStatusText("Test");
		rc.setPersistentParameter("name", "Mia");

		String name = rc.getPersistentParameter("name");
		assertEquals("Mia", name);
	}

	@Test
	public void testTParamSetting() throws IOException {
		String filePath = "Test_.txt";
		OutputStream os = Files.newOutputStream(Paths.get(filePath));
		RequestContext rc = new RequestContext(os,
				new HashMap<String, String>(), new HashMap<String, String>(),
				new ArrayList<RequestContext.RCCookie>());
		String encoding = "UTF-8";
		rc.setMimeType("text/plain");
		rc.setStatusCode(205);
		rc.setStatusText("Test");
		rc.setTemporaryParameter("name", "Mia");

		String name = rc.getTemporaryParameter("name");
		assertEquals("Mia", name);
	}

	@Test
	public void testParamNameGetting() throws IOException {
		String filePath = "Test_.txt";
		OutputStream os = Files.newOutputStream(Paths.get(filePath));
		Map<String, String> params = new HashMap<String, String>();
		params.putIfAbsent("summer", "Sea");
		params.putIfAbsent("go", "Out");
		params.putIfAbsent("went", "Home");
		params.putIfAbsent("be", "Fun");
		Set<String> testSet = new HashSet<>();
		testSet.add("summer");
		testSet.add("go");
		testSet.add("went");
		testSet.add("be");

		RequestContext rc = new RequestContext(os, params,
				new HashMap<String, String>(),
				new ArrayList<RequestContext.RCCookie>());
		rc.setMimeType("text/plain");
		rc.setStatusCode(205);
		rc.setStatusText("Test");

		assertEquals(testSet, rc.getParameterNames());
	}

	@Test
	public void testPParamNameGetting() throws IOException {
		String filePath = "Test_.txt";
		OutputStream os = Files.newOutputStream(Paths.get(filePath));
		Map<String, String> params = new HashMap<String, String>();
		params.putIfAbsent("summer", "Sea");
		params.putIfAbsent("go", "Out");
		params.putIfAbsent("went", "Home");
		params.putIfAbsent("be", "Fun");
		Set<String> testSet = new HashSet<>();
		testSet.add("summer");
		testSet.add("go");
		testSet.add("went");
		testSet.add("be");

		RequestContext rc = new RequestContext(os,
				new HashMap<String, String>(), params,
				new ArrayList<RequestContext.RCCookie>());
		rc.setMimeType("text/plain");
		rc.setStatusCode(205);
		rc.setStatusText("Test");

		assertEquals(testSet, rc.getPersistentParameterNames());
	}

	@Test
	public void testPParamRemove() throws IOException {
		String filePath = "Test_.txt";
		OutputStream os = Files.newOutputStream(Paths.get(filePath));
		Map<String, String> params = new HashMap<String, String>();
		params.putIfAbsent("summer", "Sea");
		params.putIfAbsent("go", "Out");
		params.putIfAbsent("went", "Home");
		params.putIfAbsent("be", "Fun");
		Set<String> testSet = new HashSet<>();

		testSet.add("go");
		testSet.add("went");
		testSet.add("be");

		RequestContext rc = new RequestContext(os,
				new HashMap<String, String>(), params,
				new ArrayList<RequestContext.RCCookie>());
		rc.setMimeType("text/plain");
		rc.setStatusCode(205);
		rc.setStatusText("Test");
		rc.removePersistentParameter("summer");

		assertEquals(testSet, rc.getPersistentParameterNames());
	}

	@Test
	public void testTParamRemove() throws IOException {
		String filePath = "Test_.txt";
		OutputStream os = Files.newOutputStream(Paths.get(filePath));
		Map<String, String> params = new HashMap<String, String>();
		Set<String> testSet = new HashSet<>();

		testSet.add("go");
		testSet.add("went");
		testSet.add("be");

		RequestContext rc = new RequestContext(os,
				new HashMap<String, String>(), params,
				new ArrayList<RequestContext.RCCookie>());

		rc.setMimeType("text/plain");
		rc.setStatusCode(205);
		rc.setStatusText("Test");
		rc.setTemporaryParameter("summer", "Sea");
		rc.setTemporaryParameter("go", "Out");
		rc.setTemporaryParameter("went", "Home");
		rc.setTemporaryParameter("be", "Fun");

		rc.removeTemporaryParameter("summer");

		assertEquals(testSet, rc.getTemporaryParameterNames());
	}

	@Test(expected = NullPointerException.class)
	public void notNUllNameInCookie() {
		RequestContext.RCCookie cookie = new RequestContext.RCCookie(null, null,
				null, null, null);
	}
}
