package blender.gdgtrento.org.frontend;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Base class which is extended by all other servlets
 */
@SuppressWarnings("serial")
public abstract class BaseServlet extends HttpServlet {
	// Common context parameter names for JSP 
	public static final String CTX_TITLE       = "title";
	public static final String CTX_EVENT       = "event";
	public static final String CTX_EVENTS_LIST = "events_list";
	public static final String CTX_QRIMAGE     = "qr_image_url";
	
	public static final String CHARTS_BASE_URL =
			"https://chart.googleapis.com/chart";
	
	/**
	 * Includes provided JSP via RequestDispatcher. Sends an error response
	 * in case any exception is thrown during the processing.
	 * @throws IOException 
	 */
	protected void renderTemplate(
	String jspTemplate, HttpServletRequest req, HttpServletResponse resp)
	throws IOException {
		resp.setContentType("text/html; charset=utf-8");
		RequestDispatcher rd = req.getRequestDispatcher(jspTemplate);
		try {
			rd.include(req, resp);
		} catch (ServletException e) {
			e.printStackTrace();
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.toString());
		}
	}
	
	/**
	 * Generates a URL suitable for <img src="..."> tag
	 * @param targetURL a URL to which this QR code will be pointing to.
	 */
	protected String generateQrImageUrl(String targetURL) {
		try {
			return CHARTS_BASE_URL + '?' +
					"cht=qr&" +
					"chs=150x150&" +
					"chld=H|0&" +
					"chl=" + java.net.URLEncoder.encode(targetURL, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	protected String getEventUrl(HttpServletRequest req, Long eventId) {
		return "http://" + req.getRemoteHost() + "/event?id=" + eventId;
	}
}
