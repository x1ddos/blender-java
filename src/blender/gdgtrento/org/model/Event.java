package blender.gdgtrento.org.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import blender.gdgtrento.org.util.StringToTime;
import blender.gdgtrento.org.util.StringToTimeException;
import blender.gdgtrento.org.util.TextParser;
import blender.gdgtrento.org.util.TimeToString;

import com.google.appengine.api.datastore.*;

/**
 * POJO that represents a single event.
 */
public class Event {
	/**
	 * Entity kind of a single event stored in the Datastore. 
	 */
	public static final String EVENT_KIND = "Event";
	
	// Datastore entity property names
	public static final String PROP_TITLE = "title";
	public static final String PROP_START = "start";
	public static final String PROP_END = "end";
	public static final String PROP_PLACE = "place";
	public static final String PROP_DESCRIPTION = "desc";
	public static final String PROP_LINKS = "links";
	
	// Instance properties
	private Long id;
	private String title;
	private Date start;
	private Date end;
	private String place;
	private String description;
	private List<String> links;
	
	/**
	 * Empty constructor, used by Gson to decode from JSON.
	 */
	public Event() {
		this.id = null;
	}

	public static Event newEvent(
	String title, Date start, Date end, String place, String desc,
	List<String> links) {
		if (end == null && start != null) {
			end = new Date(start.getTime() + 2*3600*1000); // + 2 hours 
		}
		Event e = new Event();
		e.setTitle(title);
		e.setStart(start);
		e.setEnd(end);
		e.setPlace(place);
		e.setDescription(desc);
		e.setLinks(links);
		return e;
	}
	
	public static Event newEvent(
	String id, String title, String start, String end, String place,
	String desc) {
		Long eventId = null;
		try {
			eventId = new Long(id);
		} catch (NumberFormatException e) {} // ignored
		return newEvent(eventId, title, start, end, place, desc);
	}
	
	public static Event newEvent(
	Long id, String title, String start, String end, String place,
	String desc) {
		Date startDate = null, endDate = null;
		try {
			startDate = new StringToTime(start).asDate();
		} catch (StringToTimeException exc) {} // ignored
		try {
			endDate = new StringToTime(end).asDate();
		} catch (StringToTimeException exc) {} // ignored
		
		List<String> links = TextParser.extractLinksFromText(desc);
		
		Event event = newEvent(title, startDate, endDate, place, desc, links);
		event.setId(id);
		return event;
	}
	
	/**
	 * Creates a new Event object from a Datastore entity
	 */
	@SuppressWarnings("unchecked")
	public static Event newEvent(Entity ent) {
		Event event = newEvent(
			(String) ent.getProperty(PROP_TITLE),
			(Date) ent.getProperty(PROP_START),
			(Date) ent.getProperty(PROP_END),
			(String) ent.getProperty(PROP_PLACE),
			((Text) ent.getProperty(PROP_DESCRIPTION)).getValue(),
			(List<String>) ent.getProperty(PROP_LINKS));
		event.setId(ent.getKey().getId());
		return event;
	}

	public static Event getById(long eventId) {
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		Key eventKey = KeyFactory.createKey(EVENT_KIND, eventId);
		try {
			Entity ent = ds.get(eventKey);
			return newEvent(ent);
		} catch (EntityNotFoundException e) {
			return null;
		}
	}
	
	public static Event getById(String eventId) {
		try {
			long id = Long.parseLong(eventId);
			return getById(id);
		} catch (NumberFormatException e) {
			return null;
		}
	}
	
	public static List<Event> getMany() {
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		Query query = new Query(EVENT_KIND).
				addSort(PROP_START, Query.SortDirection.ASCENDING);
		List<Entity> entities = ds.prepare(query).
				asList(FetchOptions.Builder.withLimit(500));
		
		List<Event> events = new ArrayList<Event>(entities.size());
		for (Entity ent: entities) {
			events.add(newEvent(ent));
		}
		return events;
	}
	
	/**
	 * Updates existing event or creates a new one.
	 * This method doesn't do validation, you should call isValid() to make sure.
	 * @return true if successful
	 */
	public boolean updateOrCreate() {
		Entity e;
		Key eventKey;
		if (id != null) {
			eventKey = KeyFactory.createKey(EVENT_KIND, id);
			e = new Entity(eventKey);
		} else {
			e = new Entity(EVENT_KIND);
		}
		
		e.setProperty(PROP_TITLE, title);
		e.setProperty(PROP_START, start);
		e.setProperty(PROP_END, end);
		e.setProperty(PROP_LINKS, links);
		e.setUnindexedProperty(PROP_PLACE, place);
		e.setUnindexedProperty(PROP_DESCRIPTION, new Text(description));
		
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		try {
			eventKey = ds.put(e);
			this.id = eventKey.getId();
			return true;

		} catch (Exception exc) {
			exc.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Validates this event instance.
	 * @return true if the event has all required properties set 
	 *         to non-null values.
	 */
	public boolean isValid() {
		return title != null && start != null && place != null;
	}
	
	public String getFormattedStart() {
		return TimeToString.format(start);
	}
	
	public String getFormattedEnd() {
		return TimeToString.format(end);
	}
	
	public String getHumanStart() {
		return TimeToString.humanize(start);
	}
	
	public String getHumanEnd() {
		return TimeToString.humanize(end);
	}
	
	// Getters and setters
	
	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }

	public String getTitle() { return title; }
	public void setTitle(String title) { this.title = title; }
	
	public Date getStart() { return start; }
	public void setStart(Date start) { this.start = start; }
	
	public Date getEnd() { return end; }
	public void setEnd(Date end) { this.end = end; }

	public String getPlace() { return place; }
	public void setPlace(String place) { this.place = place; }

	public String getDescription() { return description; }
	public void setDescription(String description) {
		this.description = description;
	}

	public List<String> getLinks() { return links; }
	public void setLinks(List<String> links) { this.links = links; }
}
