<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.*" errorPage="Error.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Classes Page</title>
<link rel="stylesheet" href="style.css" type="text/css" />
</head>
<body>
<!-- start header -->
<div id="header">
    <div id="menu">
        <ul>
            <li><a href="main">Main Page</a></li>
            <li><a href="handleUpdate?action=getAlivesAction">Alive Page</a></li>
            <li class="current_page_item"><a href="#">Classes Page</a></li>
            <li><a href="addalive">Add Alive Page</a></li>
            <li><a href="addclass">Add Class Page</a></li>
            <li><a href="handleUpdate?action=getClassesHierarchyAction">Classes hierarchy Page</a></li>
        </ul>
    </div>
</div>
<div id="logo">
    <h2>Biological classification</h2>
</div>
<!-- end header -->
<hr />
<!-- start page -->
<div id="page">
    <!-- start content -->
        <!-- table -->
        <table id='rounded-corner' width=50%>
            <thead>
            <tr>
                <th scope='col' class='rounded-company'>Id</th>
                <th scope='col'>Name</th>
                <th scope='col'>Parent id</th>
                <th scope='col' class='rounded'></th>
            </tr>
            </thead>
            <form action="handleUpdate" method="post" >
            <input type="hidden" name="action" value="getClassesAction" />
            <tr>
                <td></td>
                <td>
                    <input type="radio" name="ordercol" onClick='this.form.submit()' value="name" />Ascending<br>
                    <input type="radio" name="ordercol" onClick='this.form.submit()' value="name desc" />Descending
                </td>
                <td>
                    <input type="radio" name="ordercol" onClick='this.form.submit()' value="parent" />Ascending<br>
                    <input type="radio" name="ordercol" onClick='this.form.submit()' value="parent desc" />Descending
                </td>
                <td><input type="hidden" name="ordercol" value='${param.col }' /></td>
            </tr>
            <tr>
                <td>Id</td>
                <td>Substring:<br>
                    <input type="text" name="nameSubstr" value='${param.nameSubstr }' size="12" /><br>
                </td>
                <td>
                    Min: <input type="text" name="parentMin" value='${param.parentMin }' size="5" /><br>
                    Max: <input type="text" name="parentMax" value='${param.parentMax }' size="5" />
                </td>
                <td><button class='green-button'>Refresh!</button></td>
            </tr>
            </form>
            <c:forEach items='${classList}' var='element'>
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
                        <input type="hidden" name="action" value="getClassAction" />
                        <input type="hidden" name="target" value="editclass" />
                        <input type='hidden' name='id' value='${element.id }' />
                        <button class='green-button' >Edit!</button>
                    </form>
                    <form action='handleUpdate' method='post'>  
                        <input type='hidden' name='id' value='${element.id }' />
                        <input type='hidden' name='action' value='deleteClassAction' />
                        <button class='green-button' >Delete!</button>
                    </form>
                    </td>
                </tr>
            </c:forEach>
            <tfoot>
                <tr>
                    <td colspan="3" class="rounded-foot-left"><em>The above data were fictional and made up, please do not sue me</em></td>
                    <td class="rounded-foot-right">&nbsp;</td>
                </tr>
            </tfoot>
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