<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="_top.jsp" %>

<div role="main" class="container">
  <!-- <p><label>
    <input type="search" size="50" name="location" class="large"
           placeholder="Type something"></label>
  </p> -->
  
  <div id="events">
    loading...
  </div>
</div>

<script src="/static/utils.js"></script>
<script>
  sendRequest("/list", function(resp){
    var el = document.getElementById('events');
    if (el) {
      el.innerHTML = resp.responseText;
    }
  });
</script>
<%@ include file="_bottom.jsp" %>