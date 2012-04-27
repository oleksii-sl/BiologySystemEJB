<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" errorPage="Error.jsp"
    import="biosys.model.*" import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Hierarchy classes navigation</title>
<link rel="stylesheet" href="style.css" type="text/css" />
</head>
<body>
<!-- start header -->
<div id="header">
    <div id="menu">
        <ul>
            <li><a href="main">Main Page</a></li>
            <li><a href="handleUpdate?action=getAlivesAction">Alive Page</a></li>
            <li><a href="handleUpdate?action=getClassesAction">Classes Page</a></li>
            <li><a href="addalive">Add Alive Page</a></li>
            <li><a href="addclass">Add Class Page</a></li>
            <li class="current_page_item"><a href="#">Classes hierarchy Page</a></li>
        </ul>
    </div>
</div>
<div id="logo">
    <h2>Hierarchy tree of Biological Classes</h2>
</div>
<!-- end header -->
<hr />
<!-- start page -->
<div id="page">
    <!-- start content -->
        <!-- table -->
	<table id='rounded-corner' width=40%>
	    <thead>
	    <tr>
	        <th scope='col' class='rounded-company'>Id</th>
	        <th scope='col'>Name</th>
	        <th scope='col'>Parent id</th>
	        <th scope='col' class='rounded'>
	            <form action="handleUpdate" method="post" >
	                <input type="hidden" name="action" value="getClassesHierarchyAction" />
	                <button class='green-button'>Full tree!</button>
	            </form>
	        </th>
	    </tr>
	    </thead>
	    <c:forEach items="${classesHierarchy}" var="element">
	        <tr>
	            <td>${element.id}</td>
	            <td>
                    <a href='handleUpdate?id=${element.id }&target=xmlclass&action=getClassAction'>
                        ${element.name }
                    </a>
                </td>
	            <td>${element.parentId}</td>
	            <td>
	            <form action='handleUpdate' method='post'> 
	                <input type="hidden" name="id" value='${element.id }' />
	                <input type="hidden" name="action" value="getClassesHierarchyAction" />
	                <button class='green-button'>Get children!</button>
	            </form>
	            </td>
	        </tr>
	    </c:forEach>
	    </table>
<!-- end table -->
</div>
<!-- end page -->
<hr />
<!-- start footer -->
<div id="footer">
    <p>&copy;2007 All Rights Reserved. &nbsp;&bull;&nbsp; Designed by <a href="http://www.freecsstemplates.org/">Free CSS Templates</a>.</p>
</div>
<!-- end footer -->
</body>
</html>