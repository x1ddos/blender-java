<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ include file="_top.jsp" %>

  <div role="main" class="container">
    <h1>Add a new event</h1>
    <form action="/event" method="post">
      <%@ include file="_formfields.jsp" %>
      <p>
        <button type="submit" class="primary">Submit</button>
      </p>
    </form>
  </div>

<%@ include file="_bottom.jsp" %>