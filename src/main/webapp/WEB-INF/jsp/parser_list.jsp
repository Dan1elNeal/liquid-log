<%@page import="ru.naumen.sd40.log.parser.IRenderMode"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*" %>
<%@ page import="org.influxdb.dto.QueryResult.Series" %>

<html>

<head>
    <title>SD40 Performance indicator</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.5/css/bootstrap.min.css"
          integrity="sha384-AysaV+vQoT3kOAXZkl02PThvDr8HYKPZhNT5h/CXfBThSRXQ6jW5DO2ekP5ViFdi" crossorigin="anonymous"/>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.5/js/bootstrap.min.js"
            integrity="sha384-BLiI7JTZm+JWlgKa0M0kGRpJbF2J8q+qreVrKBC47e3K6BW78kGLrCkeRX6I9RoK"
            crossorigin="anonymous"></script>
   <link rel="stylesheet" href="/css/style.css"/>
</head>
<body>
<%
    Map<String, IRenderMode> renderModes = (Map<String, IRenderMode>)request.getAttribute("renderModes");
    Iterator<String> iterator = renderModes.keySet().iterator();

  //Prepare links
  	String path="";
  	String custom = "";
  	if(request.getAttribute("custom") == null){
    	Object year = request.getAttribute("year");
    	Object month = request.getAttribute("month");
    	Object day = request.getAttribute("day");

	    String countParam = (String)request.getParameter("count");

    	String params = "";
    	String datePath = "";

    	StringBuilder sb = new StringBuilder();


    	if(countParam != null){
        	params = sb.append("?count=").append(countParam).toString();
    	}else{
        	sb.append('/').append(year).append('/').append(month);
        	if(!day.toString().equals("0")){
            	sb.append('/').append(day);
        	}
        	datePath = sb.toString();
    	}
    	path = datePath + params;
  	}
  	else{
  	    custom = "/custom";
  	    Object from = request.getAttribute("from");
  	  	Object to = request.getAttribute("to");
  	  	Object maxResults = request.getAttribute("maxResults");

  	  	StringBuilder sb = new StringBuilder();
  	  	path = sb.append("?from=").append(from).append("&to=").append(to).append("&maxResults=").append(maxResults).toString();
  	}
%>
    <section class="container">
        <header>
            <br>
            <h1>Parser List</h1>
            <a class="btn btn-success btn-lg" href="/">Client list</a>
            <br><br>
        </header>

        <ul class="nav nav-pills">
            <% while (iterator.hasNext()) {
                String key = iterator.next();
                IRenderMode mode = renderModes.get(key);
            %>
                <li class="nav-item">
                    <a class="btn btn-outline-primary" href="/history/${client}<%=custom%>/<%=key%><%=path%>">
                        <%=mode.getBeautifulParserName()%>
                    </a>
                </li>
            <% } %>
        </ul>
    </section>
</body>