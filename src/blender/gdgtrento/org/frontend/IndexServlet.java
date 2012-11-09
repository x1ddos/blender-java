package blender.gdgtrento.org.frontend;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.*;

import blender.gdgtrento.org.model.Event;

/**
 * Homepage servlet 
 */
@SuppressWarnings("serial")
public class IndexServlet extends BaseServlet {
	/**
	 * Responds to root URL (homepage), /list and /search
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws IOException {
		String path = req.getRequestURI();
		
		if (path == null || path.equals("") || path.equals("/")) {
			req.setAttribute(CTX_TITLE, "Events");
			renderTemplate("index.jsp", req, resp);
			return;
		}
		
		if (path.equals("/list")) {
			list(req, resp);
			return;
		} else if (path.equals("/search")) {
			search(req, resp);
			return;
		}
		
		resp.sendError(HttpServletResponse.SC_NOT_FOUND);
	}

	/**
	 * GET /list handler
	 */
	private void list(HttpServletRequest req, HttpServletResponse resp)
	throws IOException {
		List<Event> events = Event.getMany();
		req.setAttribute(CTX_EVENTS_LIST, events);
		renderTemplate("_list.jsp", req, resp);
	}
	
	/**
	 * GET /search handler
	 */
	private void search(HttpServletRequest req, HttpServletResponse resp)
	throws IOException {
		// TODO Auto-generated method stub
	}
}
