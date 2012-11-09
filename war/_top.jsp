<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8" >
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <title><c:out value="${title}"/></title>
    <link href="/static/main.css" type="text/css" rel="stylesheet">
    <link href="/static/buttons.css" type="text/css" rel="stylesheet">
  </head>
  <body>
    <header>
      <div class="container" style="padding-top:5px;">
        <a href="/" id="logo">Events Blender</a>
        <a href="/event">Add event</a>
      </div>
    </header>