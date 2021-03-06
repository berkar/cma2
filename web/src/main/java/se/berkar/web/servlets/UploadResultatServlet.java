package se.berkar.web.servlets;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.IllegalFormatException;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import se.berkar.model.Resultat;
import se.berkar.qualifiers.CmaLogger;
import se.berkar.services.ResultatServiceBean;

import org.jboss.logging.Logger;

@WebServlet(name = "UploadResultatServlet", urlPatterns = {"/upload/resultat"})
@MultipartConfig
public class UploadResultatServlet extends HttpServlet {

	@Inject
	@CmaLogger
	private transient Logger itsLog;

	@Inject
	private ResultatServiceBean itsResultatService;

	@Inject
	private ResultatReader itsReader;

	@Override
	protected void doPost(final HttpServletRequest req, final HttpServletResponse resp)
			throws ServletException, IOException {
		processRequest(req, resp);
		// super.doPost(req, resp);
	}

	/**
	 * Ta emot fil.
	 * @param request .
	 * @param response .
	 * @throws IOException
	 * @throws ServletException .
	 * @throws IOException .
	 */
	protected void processRequest(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		final Part filePart = request.getPart("resultat");
		if (filePart != null) {
			final String fileName = getFileName(filePart);
			InputStream filecontent = null;
			try {
				filecontent = filePart.getInputStream();
				itsLog.info("Fil " + fileName + " laddas upp");
				List<Resultat> importSubject = itsReader.fromStream(filecontent);
				itsResultatService.upload(importSubject);
			} catch (final IllegalFormatException | IOException exception) {
				itsLog.error("Problems during file upload.", exception);
			} finally {
				close(filecontent);
			}
		}
	}

	private static void close(final Closeable closeable) {
		try {
			if (closeable != null) {
				closeable.close();
			}
		} catch (final IOException e) {
			// sssh
		}
	}

	private String getFileName(final Part part) {
		final String partHeader = part.getHeader("content-disposition");
		itsLog.info("Part Header = " + partHeader);
		for (String content : part.getHeader("content-disposition").split(";")) {
			if (content.trim().startsWith("filename")) {
				return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
			}
		}
		return null;
	}
}
