<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ include file="_top.jsp" %>

<div role="main" class="container">
  <h1><c:out value="${event.title}"/></h1>
  
  <div class="clearfix">
    <c:if test="${not empty qr_image_url}">
	    <div class="qrcode">
	      <img src="<c:out value='${qr_image_url}'/>">
	    </div>
	  </c:if>
	  <span class="time"><c:out value="${event.humanStart}"/>
    (<c:out value="${event.formattedStart}"/>)</span>
	  
	  <div style="margin: 1em 0;">
	    <ul>
	      <c:forEach var="url" items="${event.links}">
	        <li>
	          <a href="<c:out value="${url}"/>" target="_blank" rel="nofollow">
	            <c:out value="${url}"/>
	          </a>
	        </li>
	      </c:forEach>
	    </ul>
	    <c:out value='${event.description}' escapeXml='false'/>
	  </div>
	  
	  <p>Location: <c:out value="${event.place}"/></p>
  </div>

  <div id="map" class="canvas" style="height: 450px;"></div>
  
  <p>
    <a href="<c:url value='/event?id=${event.id}&edit'/>"
       class="btn primary">Edit event</a>
  </p>
</div>

<script type="text/javascript">
  var map, geocoder,
      place = null,
      mapReady = false;
  
  function addMarker(location, doCenter) {
    if (!mapReady) {
      setTimeout(function(){
        addMarker(location, doCenter);
      }, 200);
      return;
    }
    
    if (doCenter) {
      map.setCenter(location);  
    }
    new google.maps.Marker({
        map: map,
        position: location
    });
  }
  
  function geocode(address) {
    geocoder.geocode({'address': address}, function(results, status) {
      if (status == google.maps.GeocoderStatus.OK) {
        place = results[0].geometry.location;
        addMarker(place, true);
      }
    });
  }
  
  function initMaps() {
    geocoder = new google.maps.Geocoder();
    geocode("<c:out value='${event.place}'/>");
    
    var mapOptions = {
        // Trento
      center: new google.maps.LatLng(46.069695, 11.121093),
      zoom: 14,
      mapTypeId: google.maps.MapTypeId.ROADMAP
    };
    map = new google.maps.Map(document.getElementById("map"),
        mapOptions);
    mapReady = true;
  }
  
  function showDirections(pos) {
    if (!mapReady || !place) {
      setTimeout(function(){ showDirections(pos) }, 200);
      return;
    }
    
    var start = new google.maps.LatLng(pos.coords.latitude, pos.coords.longitude),
        directionsService = new google.maps.DirectionsService(),
        directionsDisplay = new google.maps.DirectionsRenderer();
    directionsDisplay.setMap(map);
    
    var request = {
      origin:start,
      destination:place,
      // other modes: WALKING, BICYCLING or TRANSIT
      travelMode: google.maps.TravelMode.DRIVING
    };
    directionsService.route(request, function(response, status) {
      if (status == google.maps.DirectionsStatus.OK) {
        directionsDisplay.setDirections(response);
      }
    });
  }
  
  if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition(showDirections, null, {
      enableHighAccuracy: true,
      timeout: 60000,
      maximumAge: 0});
  }
</script>
<script type="text/javascript"
  src="https://maps.googleapis.com/maps/api/js?sensor=false&callback=initMaps">
</script>
<%@ include file="_bottom.jsp" %>
