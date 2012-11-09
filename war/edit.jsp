<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ include file="_top.jsp" %>

<div role="main" class="container">
  <h1><c:out value='${title}'/></h1>
  <form action="/event" method="post">
    <input type="hidden" name="id" value="<c:out value='${event.id}'/>">
    <%@ include file="_formfields.jsp" %>
    <p>
      <button type="submit" class="primary">Save changes</button>
    </p>
  </form>
</div>

<%@ include file="_bottom.jsp" %>