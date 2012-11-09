package blender.gdgtrento.org.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Utility class that converts Date/Time to a string.
 */
public class TimeToString {
	/**
	 * Default Date and Time formatter.
	 */
	public static final DateFormat dateFormatter = new SimpleDateFormat(
			"EEE, d MMM yyyy 'at' HH:mm z", Locale.ITALY);
	
	/**
	 * Converts Date to a pretty string, e.g.
	 * "last year"
   * "in 5 minutes"
   * "about a week ago"
	 */
	public static String humanize(Date date) {
		if (date == null) {
			return null;
		}
		Date now = new Date();
		long current = now.getTime(),
				 timestamp = date.getTime(),
				 diff = Math.abs(current - timestamp)/1000;
		boolean future = date.after(now);
		int	amount = 0;
		String	what = "";

		/**
		 * Second counts
		 * 3600: hour
		 * 86400: day
		 * 604800: week
		 * 2592000: month
		 * 31536000: year
		 */

		if(diff > 31536000) {
			amount = (int)(diff/31536000);
			what = "year";
		}
		else if(diff > 31536000) {
			amount = (int)(diff/31536000);
			what = "month";
		}
		else if(diff > 604800) {
			amount = (int)(diff/604800);
			what = "week";
		}
		else if(diff > 86400) {
			amount = (int)(diff/86400);
			what = "day";
		}
		else if(diff > 3600) {
			amount = (int)(diff/3600);
			what = "hour";
		}
		else if(diff > 60) {
			amount = (int)(diff/60);
			what = "minute";
		}
		else {
			amount = (int)diff;
			what = "second";
			if(amount < 6) {
				return "just now";
			}
		}

		if(amount == 1) {
			if(what.equals("day")) {
				return future ? "tomorrow" : "yesterday";
			} else if (what.equals("week") ||
					       what.equals("month") ||
					       what.equals("year")) {
				return (future ? "next " : "last ") + what;
			}
		} else {
			what += "s";
		}

		return future ?
			"in about " + amount + " " + what :
			"about " + amount + " " + what + " ago";
	}
	
	/**
	 * Formats date/time using DateFormat with a predefined format.
	 */
	public static String format(Date date) {
		if (date == null) {
			return null;
		}
		return dateFormatter.format(date);
	}
}
