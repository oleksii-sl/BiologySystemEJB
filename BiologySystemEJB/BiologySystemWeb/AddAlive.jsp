<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" errorPage="Error.jsp"
    import="biosys.model.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Edit living</title>
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
            <li class="current_page_item"><a href="#">Add Alive Page</a></li>
            <li><a href="addclass">Add Class Page</a></li>
            <li><a href="handleUpdate?action=getClassesHierarchyAction">Classes hierarchy Page</a></li>
        </ul>
    </div>
</div>
<div id="logo">
    <h2>Fill living attributes form</h2>
</div>
<!-- end header -->
<hr />
<!-- start page -->
<div id="page">
    <!-- start content -->
        <!-- table -->
    <form action='handleUpdate' method='post'>
    <table id='rounded-corner' width=40%>
        <thead>
        <tr>
            <th scope='col' class='rounded-company' colspan = 2></th>
        </tr>
        </thead>
        <tr>
            <td>Name</td>
            <td><input type='text' name='name' /></td>
        </tr>
        <tr>
            <td>Name latin</td>
            <td><input type='text' name='nameLatin' /></td>
        </tr>
        <tr>
            <td>Lifespan</td>
            <td><input type='text' name='lifespan'  /></td>
        </tr>
        <tr>
            <td>Average weight (kg)</td>
            <td><input type='text' name='avgWeight' /></td>
        </tr>
        <tr>
            <td>Native range</td>
            <td><input type='text' name='nativeRange' /></td>
        </tr>
        <tr>
            <td>Population</td>
            <td><input type='text' name='population' /></td>
        </tr>
        <tr>
            <td>Biological class id</td>
            <td><input type='text' name='bioClass' /></td>
        </tr>
        <tr>
          <td colspan=2>
              <input type='hidden' name='action' value="addAliveAction" />
              <button class="green-button">Add</button>
          </td>
        </tr>
    </table>
    </form>
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