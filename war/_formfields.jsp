<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<p><label>Title:
  <input type="text" name="title" size="60" placeholder="Awesome event title"
         value="<c:out value='${event.title}'/>">
</label></p>

<p><label>When:
  <input type="text" name="start" size="40" placeholder="next week"
         value="<fmt:formatDate type="both" pattern="dd/MM/yyyy HH:mm"
                                            value='${event.start}'/>">
</label></p>

<p><label>Until:
  <input type="text" name="end" size="40"
         placeholder="e.g. 'next month' or just leave it blank"
         value="<fmt:formatDate type="both" pattern="dd/MM/yyyy HH:mm"
                                            value='${event.end}'/>">
</label></p>

<p><label>Where:
  <input type="text" name="place" size="60"
         placeholder="e.g. 'via Sommarive 18, Trento'"
         value="<c:out value='${event.place}'/>">
</label></p>

<p><label>Description:<br>
  <textarea style="height:250px;width:90%"
    placeholder="Say something more about this event"
    name="description"><c:out value='${event.description}'/></textarea>
</label></p>
