package hr.fer.zemris.java.hw18.init;

import java.io.IOException;
import java.nio.file.Paths;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import hr.fer.zemris.java.hw18.model.Galery;

/**
 * Initializing the gallery. Map of pictures is created and ready to use.
 * 
 * @author mia
 *
 */
@WebListener
public class Initialization implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		String fileName = sce.getServletContext()
				.getRealPath("WEB-INF");

		try {
			Galery.loadDescriptions(Paths.get(fileName,Galery.DESCRIPTOR));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}
}