<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<ul class="toplist">
  <c:forEach var="e" items="${events_list}">
    <li>
      <a href="<c:out value='/event?id=${e.id}'/>">
        <c:out value="${e.title}"/>
      </a><br/>
      <c:out value="${e.humanStart}"/>
      at <fmt:formatDate type="time" pattern="HH:mm" value='${e.start}'/>,
      in <c:out value="${e.place}"/>
    </li>
  </c:forEach>
  <c:if test="${empty events_list}">
    <li>No events here, yet.</li>
  </c:if>
</ul>
