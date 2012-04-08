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
            <li><a href="handleUpdate?action=getAliveAction">Alive Page</a></li>
            <li><a href="handleUpdate?action=getClassesAction">Classes Page</a></li>
            <li><a href="addalive">Add Alive Page</a></li>
            <li class="current_page_item"><a href="#">Add Class Page</a></li>
            <li><a href="handleUpdate?action=getClassesHierarchyAction">Classes hierarchy Page</a></li>
        </ul>
    </div>
</div>
<div id="logo">
    <h2>Fill class attributes form</h2>
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
            <td>Parent id</td>
            <td><input type='text' name='parentId' /></td>
        </tr>
        <tr>
        <td colspan=2>
            <input type='hidden' name='action' value="addClassAction" />
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