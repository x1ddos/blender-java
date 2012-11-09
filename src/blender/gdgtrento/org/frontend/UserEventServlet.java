package blender.gdgtrento.org.frontend;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import blender.gdgtrento.org.model.Event;

/**
 * End-users frontend servlet
 */
@SuppressWarnings("serial")
public class UserEventServlet extends BaseServlet {
	// Request parameter names
	public static final String PARAM_ID = "id";
	public static final String PARAM_EDIT = "edit";
	
	/**
	 * Handles GET requests to /event path.
	 * If parameter "id" is provided, it is an "edit event" action,
	 * otherwise responds with the form to add a new event.
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse resp) 
	throws IOException {
		String eventId = req.getParameter(PARAM_ID);
		if (eventId == null) {
			// Render "Add a new event" form
			req.setAttribute(CTX_EVENT, new Event());
			req.setAttribute(CTX_TITLE, "Add a new event");
			renderTemplate("add.jsp", req, resp);
			return;
		}
		
		// Request for an existing event
		Event event = Event.getById(eventId);
		if (event == null) {
			resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
			req.setAttribute(CTX_TITLE, "Event not found");
			renderTemplate("notfound.jsp", req, resp);
			return;
		}
		
		req.setAttribute(CTX_EVENT, event);
		
		String template;
		if (req.getParameter(PARAM_EDIT) != null) {
			// Edit existing event
			req.setAttribute(CTX_TITLE, "Edit event");
			template = "edit.jsp";
		} else {
			// Display the event
			req.setAttribute(CTX_TITLE, event.getTitle());
			String eventURL = getEventUrl(req, event.getId()); 
			req.setAttribute(CTX_QRIMAGE, generateQrImageUrl(eventURL));
			template = "event.jsp";
		}
		
		renderTemplate(template, req, resp);
	}
	
	/**
	 * Handles POST requests to /event
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse resp) 
	throws IOException {
		Event event = Event.newEvent(
				req.getParameter(PARAM_ID),
				req.getParameter("title"),
				req.getParameter("start"),
				req.getParameter("end"),
				req.getParameter("place"),
				req.getParameter("description"));
		
		// Update existing event or create a new one.
		// Either way, redirect to the event page if successful.
		if (event.isValid() && event.updateOrCreate()) {
			String targetURL = resp.encodeRedirectURL(
					"/event?" + PARAM_ID + "=" + event.getId()); 
		  resp.sendRedirect(targetURL);
		}

		// Event update failed, render the form so that they can try again.
		req.setAttribute(CTX_TITLE, "Edit event");
		req.setAttribute(CTX_EVENT, event);
		renderTemplate(event.getId() == null ? "add.jsp" : "edit.jsp", req, resp);
	}
}
