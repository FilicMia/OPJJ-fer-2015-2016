package hr.fer.zemris.java.webserver;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import hr.fer.zemris.java.custom.scripting.demo.ReadUtil;
import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * Simple smart http server.
 * 
 * It offers several services: running smart scripts stored in scripts
 * folder(http://127.0.0.1:5721/scripts/osnovni.smscr), running workers defined
 * in document-roor_path/workers folder (http://127.0.0.1:5721/ext/worker's
 * name), running workers defined in workers.properties file
 * (http://127.0.0.1:5721/attachet_path), sending contents one of three files
 * (index.html, fruits.png and sample.txt).
 * 
 * @author mia
 *
 */
public class SmartHttpServer {
	/** Time distance between the check of living sessions. */
	static final long BETWEEN_CONTROL = 5000 * 60;

	/** Alphabet. For generating the random SID's. */
	static final String ABC = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	/** Length of the random generated SID. */
	private static final int RANDOM_LEN = 20;

	/** Address of the server. */
	private String address;
	/** Port on which the server listens. */
	private int port;
	/** Number of worker threads. */
	private int workerThreads;

	/** Mime type of the message that will be send to the client. */
	private Map<String, String> mimeTypes = new HashMap<String, String>();
	/** Server thread threads. */
	private ServerThread serverThread;
	/** Pool of serving threads. */
	private ExecutorService threadPool;
	/**
	 * Map of IWebWorkers available. Path to the interface implementation is a
	 * key and reference to concrete object is a value.
	 */
	private Map<String, IWebWorker> workersMap;

	/** Path to the root to the serving files. */
	private Path documentRoot;

	/**
	 * Session duration. After this timeout, the connection to the client is
	 * closed.
	 */
	private int sessionTimeout;
	/** Session cookies hashed to its session id's. */
	private Map<String, SessionMapEntry> sessions = new HashMap<String, SmartHttpServer.SessionMapEntry>();
	/** Random generator. Used in creating SID for each session. */
	private Random sessionRandom = new Random();

	/**
	 * Constructor. Reconstruct the initial values from {@code configFileName}.
	 * 
	 * @param configFileName
	 *            path to the file wit configuration settings.
	 */
	@SuppressWarnings("unused")
	public SmartHttpServer(String configFileName) {
		InputStream inputStream = null;

		try {
			Properties prop = new Properties();
			String propFileName = configFileName;

			inputStream = new FileInputStream(configFileName);

			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName
						+ "' not found in the classpath");
			}

			address = prop.getProperty("server.address");
			port = Integer.parseInt(prop.getProperty("server.port"));
			workerThreads = Integer
					.parseInt(prop.getProperty("server.workerThreads"));
			documentRoot = Paths.get(prop.getProperty("server.documentRoot"));
			sessionTimeout = Integer
					.parseInt(prop.getProperty("session.timeout"));
			String mimeConfig = prop.getProperty("server.mimeConfig");
			setMime(mimeConfig);

			String workersConfig = prop.getProperty("server.workers");
			setWorkers(workersConfig);

		} catch (Exception e) {
			System.out.println("Exception: " + e);
			try {
				inputStream.close();
			} catch (IOException ex) {
				System.out.println(
						"Unable to close file " + inputStream.toString());
				System.exit(-1);
			}
		}
	}

	/**
	 * Function sets the mime properties. It reads all available mime types from
	 * the file and puts it into map.
	 * 
	 * @param configFileName
	 *            path to the configuration file.
	 */
	@SuppressWarnings("unused")
	private synchronized void setMime(String configFileName) {
		InputStream inputStream = null;

		try {
			Properties prop = new Properties();
			String propFileName = configFileName;

			inputStream = new FileInputStream(configFileName);

			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName
						+ "' not found in the classpath");
			}

			prop.entrySet().forEach((e) -> {
				mimeTypes.put(e.getKey().toString(), e.getValue().toString());
				// System.out.println(mimeTypes.get(e.getKey().toString()));
			});

		} catch (Exception e) {
			System.out.println("Exception: " + e);
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				System.out.println(
						"Unable to close file " + inputStream.toString());
				System.exit(-1);
			}
		}

	}

	/**
	 * Reads form worker's property file the path mapped to each worker.
	 * 
	 * Stores instance of specific worker class mapped to its path.
	 * 
	 * @param configFileName
	 *            string path to the worker's property file
	 */
	@SuppressWarnings("unused")
	private void setWorkers(String configFileName) {
		InputStream inputStream = null;
		workersMap = new HashMap<>();

		try {
			Properties prop = new Properties();
			String propFileName = configFileName;

			inputStream = new FileInputStream(configFileName);

			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName
						+ "' not found in the classpath");
			}

			prop.entrySet().forEach((e) -> {
				String path = e.getKey().toString();
				String fqcn = e.getValue().toString();
				Class<?> referenceToClass;
				Object newObject = null;
				try {
					referenceToClass = this.getClass().getClassLoader()
							.loadClass(fqcn);
					newObject = referenceToClass.newInstance();
				} catch (Exception e1) {
					e1.printStackTrace();
					System.exit(-1);
				}
				IWebWorker iww = (IWebWorker) newObject;
				workersMap.put(path, iww);
			});

		} catch (Exception e) {
			System.out.println("Exception: " + e);
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				System.out.println(
						"Unable to close file " + inputStream.toString());
				System.exit(-1);
			}
		}

	}

	/**
	 * Method starting the server. It starts the server, {@link controlThread}
	 * and creates the pool of threads used by server.
	 */
	protected synchronized void start() {

		if (serverThread == null) {
			serverThread = new ServerThread();
		}
		controlThread.setDaemon(true);
		controlThread.start();

		threadPool = Executors.newFixedThreadPool(workerThreads);
		System.out.println("Server is ready...");
		serverThread.run();

	}

	/**
	 * Method closing the server. It closes the server thread and its workers.
	 */
	protected synchronized void stop() {
		serverThread.stopRunning();
		threadPool.shutdown();
	}

	/**
	 * The thread representing the server. When the server starts, it is created
	 * and its run method is called.
	 * 
	 * @author mia
	 *
	 */
	protected class ServerThread extends Thread {
		/**
		 * Indicates if the port is open or not.
		 */
		boolean accessable = true;

		@Override
		public void run() {
			// open serverSocket on specified port
			ServerSocket serverSocket = null;
			try {
				serverSocket = new ServerSocket();
				serverSocket
						.bind(new InetSocketAddress((InetAddress) null, port));

			} catch (IOException e) {
				System.err
						.println("Error while initializing the serverSocket.");
			}

			accessable = true;

			while (accessable) {
				System.out.println("Server running");
				Socket client;
				try {
					client = serverSocket.accept();
					ClientWorker cw = new ClientWorker(client);
					threadPool.submit(cw);
					System.out.println("Client created.");
				} catch (IOException e) {
					System.err.println(e.getMessage());
					try {
						serverSocket.close();
					} catch (IOException ex) {
						System.err.println("Cannot close the server!");
						System.exit(-1);
					}
				}
			}

			try {
				serverSocket.close();
			} catch (IOException e) {
				System.err.println("Cannot close the server!");
				System.exit(-1);
			}

		}

		/**
		 * Method signals the thread that it should stop accepting connections
		 * form clients.
		 */
		public void stopRunning() {
			accessable = false;

		}
	}

	/**
	 * Server job that should be done for the client. It will be send to one of
	 * the server's worker thread.
	 * 
	 * @author mia
	 *
	 */
	private class ClientWorker implements Runnable {
		/** Separates the path and parameter string in requestPath. */
		private static final String SEPARATOR = "?";
		/** Default mime type. */
		private static final String DEFAULT_MIME_TYPE = "application/octet-stream";

		/** Client socket. */
		private Socket csocket;

		/** Stream used form communication from client to server. */
		private PushbackInputStream istream;

		/** Stream used form communication from server to client. */
		private OutputStream ostream;

		/** Version used in communication. */
		private String version;
		/** Method used in communication. */
		private String method;
		/** Parameters of the communication. */
		private Map<String, String> params = new HashMap<String, String>();
		/** Persistent parameters of the communication. */
		private Map<String, String> permPrams = null;
		/** Cookies of the communication. */
		private List<RCCookie> outputCookies = new ArrayList<RequestContext.RCCookie>();
		/** Identity of client. */
		private String SID;

		/**
		 * Constructor.
		 * 
		 * @param csocket
		 *            client socket.
		 */
		public ClientWorker(Socket csocket) {
			super();
			this.csocket = csocket;
		}

		@Override
		public void run() {

			try {
				istream = new PushbackInputStream(csocket.getInputStream());
				ostream = csocket.getOutputStream();
				System.out.println("worker");

				byte[] request = readRequest(istream);
				if (request == null) {
					sendError(400, "Bad raquest");
					return;
				}

				String requestString = new String(request,
						StandardCharsets.ISO_8859_1);

				List<String> headers = extractHeaders(requestString);
				String[] firstLine = headers.isEmpty() ? null
						: headers.get(0).split(" ");

				if (firstLine == null || firstLine.length < 3) {
					sendError(400, "Bad raquest");
					return;
				}

				method = firstLine[0].toUpperCase();
				if (!method.equals(RequestUtil.METHOD_GET)) {
					sendError(400, "Bad raquest");
					return;
				}

				version = firstLine[2].toUpperCase();
				if (!version.equals(RequestUtil.HTTP_1)
						&& version.equals(RequestUtil.HTTP_0)) {
					sendError(400, "Bad raquest");
					return;
				}
				SID = checkSession(headers);

				if (SID == null) {
					SID = generateSID();
				}

				String host = checkHost(headers);
				setSession(host == null ? address : host);

				String requestedPath = firstLine[1].trim();
				int indexSeparator = requestedPath.indexOf(SEPARATOR);
				String path;
				String paramString = null;
				if (indexSeparator != -1) {
					path = requestedPath.substring(0, indexSeparator);
					paramString = requestedPath.substring(indexSeparator + 1);
				} else {
					path = requestedPath;
				}

				parseParameters(paramString);

				Path documentPath = Paths.get(documentRoot + path);
				RequestContext context = new RequestContext(ostream, params,
						permPrams, outputCookies);
				IWebWorker iww = null;
				String ext = documentPath.getNameCount() > 2 ? documentPath
						.getName(documentPath.getNameCount() - 2).toString()
						: null;

				if (ext != null && ext.equals(RequestUtil.EXT)) {
					iww = createWebWorker(documentPath
							.getName(documentPath.getNameCount() - 1)
							.toString());
				} else {
					iww = workersMap.get("/" + documentPath
							.getName(documentPath.getNameCount() - 1)
							.toString());
				}
				if (iww != null) {
					iww.processRequest(context);
				} else {

					String extension = getExtension(documentPath);
					String mimeType = mimeTypes.get(extension);
					mimeType = mimeType == null ? DEFAULT_MIME_TYPE : mimeType;

					context.setMimeType(mimeType);
					context.setStatusCode(RequestUtil.STATUS_CODE_OK);
					context.setStatusText(RequestUtil.STATUS_OK);

					if (extension.equals(SmartScriptEngine.EXTENSION)) {
						composeSmartScriptResponse(documentPath, context);
					} else {
						composeFileResponse(documentPath, context);
					}
				}

			} catch (IOException ignorable) {
			} finally {
				while (true) {
					try {
						ostream.flush();
						csocket.close();
						break;
					} catch (Exception ignorable) {
					}
				}
			}
		}

		/**
		 * Sets the session for the client. It is supposed that {@code SID} is
		 * set before the call of the function.
		 * 
		 * @param host
		 *            host of the request.
		 */
		private synchronized void setSession(String host) {

			SessionMapEntry sessionMap = sessions.get(SID);
			if (sessionMap != null
					&& sessionMap.validUntil < System.currentTimeMillis()) {
				sessions.remove(SID);
				SID = generateSID();
				sessionMap = null;
			}

			if (sessionMap == null) {
				sessionMap = new SessionMapEntry();
				sessionMap.sid = SID;
				sessionMap.validUntil = System.currentTimeMillis()
						+ sessionTimeout;

				permPrams = new ConcurrentHashMap<String, String>();
				sessionMap.map = permPrams;

				sessions.put(SID, sessionMap);
			} else {
				sessionMap.validUntil += sessionTimeout;
				permPrams = sessionMap.map;
			}

			RCCookie cookie = new RequestContext.RCCookie("sid", SID, null,
					host, "/");
			System.out.println(cookie.toString());
			cookie.setType("HttpOnly");
			outputCookies.add(cookie);
		}

		/**
		 * Generates SID of length {@code RANDOM_LEN} form {@code ABC}
		 * characters.
		 * 
		 * @return generated SID
		 */
		private String generateSID() {
			StringBuilder sb = new StringBuilder();
			int abcLen = ABC.length();

			for (int i = 0; i < RANDOM_LEN; ++i) {
				char c = ABC.charAt(sessionRandom.nextInt(abcLen));
				sb.append(c);
			}

			return sb.toString();
		}

		/**
		 * Reads the request.
		 * 
		 * @param is
		 *            input stream from which the request should be read
		 * @return the byte representation of the request.
		 * @throws IOException
		 *             is reading from is went wrong
		 */
		private byte[] readRequest(InputStream is) throws IOException {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			int state = 0;
			l: while (true) {
				int b = is.read();
				if (b == -1) {
					return null;
				}
				if (b != 13) {
					bos.write(b);
				}

				switch (state) {
				case 0:
					if (b == 13) {
						state = 1;
					} else {
						if (b == 10) {
							state = 4;
						}
					}
					break;
				case 1:
					if (b == 10) {
						state = 2;
					} else {
						state = 0;
					}
					break;
				case 2:
					if (b == 13) {
						state = 3;
					} else {
						state = 0;
					}
					break;
				case 3:
					if (b == 10) {
						break l;
					} else {
						state = 0;
					}
					break;
				case 4:
					if (b == 10) {
						break l;
					} else {
						state = 0;
					}
					break;

				}

			}
			return bos.toByteArray();
		}

		/**
		 * Method extracting the header from given string.
		 * 
		 * @param requestString
		 *            string from which it should be extracted.
		 * @return the list of lines in headers.
		 */
		private List<String> extractHeaders(String requestString) {
			List<String> headers = new ArrayList<>();
			String currentLine = null;

			for (String s : requestString.split("\n")) {
				if (s.isEmpty())
					break;
				char c = s.charAt(0);
				if (c == 9 || c == 32) {
					currentLine += s;
				} else {
					if (currentLine != null) {
						headers.add(currentLine);
						System.out.println(currentLine);
					}
					currentLine = s;
				}

			}
			if (currentLine != null) {
				headers.add(currentLine);
			}

			return headers;
		}

		/**
		 * Method checks if there is SID connected with the client that has send
		 * the request.
		 * 
		 * @param headers
		 *            list of lines in request's header.
		 * @return the client's session SID or null, if the client has't got one
		 *         yet.
		 */
		private synchronized String checkSession(List<String> headers) {

			for (String line : headers) {

				if (line.startsWith("Cookie:")) {
					int position = line.indexOf("sid=\"");
					int positionEnd = line.indexOf("\"", position + 5);
					String sidCandidate = position == -1 ? null
							: line.substring(position + 5, positionEnd);
					System.out.println("Session ID: " + sidCandidate);
					return sidCandidate;

				}
			}

			return null;
		}

		/**
		 * Method reads the host from the request. It is used in setting the
		 * address of cookies. Return s local host if the host is local host and
		 * not 127.0.0.1
		 * 
		 * @param headers
		 *            list of lines in request's header.
		 * @return the requested host if its defined..
		 */
		private synchronized String checkHost(List<String> headers) {

			for (String line : headers) {

				if (line.startsWith("Host: ")) {
					int position = line.indexOf("localhost");
					String host = position == -1 ? null : "localhost";

					return host;

				}
			}

			return null;
		}

		/**
		 * Method parses the parameters string onto parameters.
		 * 
		 * @param paramString
		 *            string containing parameters
		 */
		private void parseParameters(String paramString) {
			if (paramString == null) {
				return;
			}

			String[] params = paramString.split("&");
			for (String param : params) {
				String[] entry = param.split("=");
				this.params.put(entry[0], entry[1]);
			}

		}

		/**
		 * Check if requestedPath exists, is file and is readable; if not,
		 * return status 404 else extract file extension and returns it. Also if
		 * document path is not below the documentRoot returnes status 403.
		 * 
		 * @param documentPath
		 *            path to the document which is required by the client
		 * @return the extension of required file
		 * @throws IOException
		 *             if sending error message went wrong.
		 */
		private String getExtension(Path documentPath) throws IOException {
			String extension = null;
			if (!documentPath.startsWith(documentRoot)) {
				sendError(403, "Forbidden");
			} else if (!documentPath.toFile().exists()) {
				sendError(404, "Not Found");
			} else if (!documentPath.toFile().isFile()) {
				sendError(404, "Not File");
			} else if (!documentPath.toFile().canRead()) {
				sendError(404, "Not Found");
			} else {
				int position = documentPath.getFileName().toString()
						.lastIndexOf('.');
				extension = position == -1 ? null
						: documentPath.getFileName().toString()
								.substring(position + 1);
			}
			return extension.trim();
		}

		/**
		 * Method sending the error message to the client.
		 * 
		 * @param statusCode
		 *            status code of the message.
		 * @param statusString
		 *            status message of the message
		 * @throws IOException
		 *             if sending trought os went wrong
		 */
		private void sendError(int statusCode, String statusString)
				throws IOException {
			RequestContext context = new RequestContext(ostream, null, null,
					null);
			context.setStatusCode(statusCode);
			context.setStatusText(statusString);
			try {
				context.write(statusString);
				context.write(Integer.toString(statusCode));
				while (true) {
					try {
						ostream.flush();
						csocket.close();
						break;
					} catch (Exception ignorable) {
					}
				}
			} catch (IOException ignorable) {
			}

		}

		/**
		 * Composes the response for client if the client called for file.
		 * 
		 * @param filePath
		 *            path to the requested file
		 * @param context
		 *            context of the request.
		 * @throws IOException
		 *             if reading and writing requested file went wrong.
		 */
		private void composeFileResponse(Path filePath, RequestContext context)
				throws IOException {

			BufferedInputStream stream = null;
			System.out.println("File response.");
			try {
				stream = new BufferedInputStream(
						new FileInputStream(filePath.toFile()));
				byte[] buffer = new byte[4096];
				int read = 0;
				while ((read = stream.read(buffer)) != -1) {
					byte[] readPart = Arrays.copyOfRange(buffer, 0, read);
					context.write(readPart);
				}
			} catch (IOException e) {
			} finally {
				if (stream != null) {
					try {
						stream.close();
					} catch (IOException ignorable) {
					}
				}
			}
		}

		/**
		 * Composes the response for client if the client called execution of
		 * smart script.
		 * 
		 * @param scriptPath
		 *            path to the requested smart script.
		 * @param context
		 *            context of the request.
		 * @throws IOException
		 *             if reading and writing requested file went wrong.
		 */
		private void composeSmartScriptResponse(Path scriptPath,
				RequestContext context) {
			System.out.println("Starting smart script! "
					+ scriptPath.getFileName().toString());
			String documentBody = ReadUtil.readFromDisk(scriptPath.toString(),
					StandardCharsets.UTF_8);

			new SmartScriptEngine(
					new SmartScriptParser(documentBody).getDocumentNode(),
					new RequestContext(ostream, params, permPrams,
							outputCookies)).execute();
		}

		/**
		 * Method creates class implementing the interface IWebWorker. For
		 * implementation, the {@link RequestUtil.DEFAULT_PATH} folder is
		 * searched.
		 * 
		 * @param name
		 *            name of class implementing the IWebWorker
		 * @return instance of class {@code name}.
		 */
		private IWebWorker createWebWorker(String name) {
			String fqcn = RequestUtil.DEFAULT_FQCN + "." + name;
			Class<?> referenceToClass;
			Object newObject = null;
			try {
				referenceToClass = this.getClass().getClassLoader()
						.loadClass(fqcn);
				newObject = referenceToClass.newInstance();
			} catch (Exception e1) {
				e1.printStackTrace();
				System.err.println(e1.getMessage());
				System.out.println("Continuing working!");
			}
			return (IWebWorker) newObject;
		}

	}

	/** Session map entry. Holds information about session. */
	private static class SessionMapEntry {
		/** Session identification number. */
		@SuppressWarnings("unused")
		String sid;
		/** Date when the entry has expired. In miliseconds. */
		long validUntil;
		/** Clients data. */
		Map<String, String> map = new ConcurrentHashMap<String, String>();
	}

	/**
	 * Instance of {@link Thread} class. Evary BETWEEN_CONTROL ms goes trough
	 * the session map and deletes expired ones.
	 */
	private Thread controlThread = new Thread() {

		@Override
		public void run() {
			while (true) {
				try {
					Thread.sleep(BETWEEN_CONTROL);
				} catch (InterruptedException ignorable) {
				}
				sessions.entrySet()
						.removeIf(e -> e.getValue().validUntil < System
								.currentTimeMillis());
			}
		}
	};

	/**
	 * Method invoked when calling the program.
	 * 
	 * @param args
	 *            command-line arguments
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.println(
					"One command-line argument expected: .config file");
			return;
		}
		try {
			SmartHttpServer server = new SmartHttpServer(args[0]);
			System.out.println("Starting server...");
			System.out.println("Server stops when entering 'stop'");
			server.start();
			BufferedReader br = new BufferedReader(
					new InputStreamReader(System.in));
			String toExit = br.readLine().trim();
			if (toExit.toLowerCase().equals("stop")) {
				server.stop();
				System.exit(0);
			}
		} catch (Exception e) {
			System.err.println("Error occured while running server.");
			System.exit(-1);
		}

	}
}