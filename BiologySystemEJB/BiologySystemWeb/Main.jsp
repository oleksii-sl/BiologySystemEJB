<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Main Page</title>
<link rel="stylesheet" href="style.css" type="text/css" />
</head>
<body>
<!-- start header -->
<div id="header">
    <div id="menu">
        <ul>
            <li class="current_page_item"><a href="#">Main Page</a></li>
            <li><a href="handleUpdate?action=getAliveAction">Alive Page</a></li>
            <li><a href="handleUpdate?action=getClassesAction">Classes Page</a></li>
            <li><a href="addalive">Add Alive Page</a></li>
            <li><a href="addclass">Add Class Page</a></li>
            <li><a href="handleUpdate?action=getClassesHierarchyAction">Classes hierarchy Page</a></li>
        </ul>
    </div>
</div>
<div id="logo">
    <h2>Welcome</h2>
</div>
<!-- end header -->
<hr />
<!-- start page -->
<div id="page" style="height: 600px" >
    <img alt="" src="images/Biological_classification_L_Pengo.png" hspace="10px" style="float: right;">
    <p>  
        &nbsp;&nbsp;
        Biological classification, or scientific classification in biology, 
        is a method to group and categorize organisms 
        into groups such as genus or species. These groups are known as taxa (singular: taxon). 
        Biological classification is part of scientific taxonomy.
        Modern biological classification has its root in the work of Carolus Linnaeus, who 
        grouped species according to shared physical characteristics. 
        These groupings have since been revised to improve consistency with the Darwinian principle 
        of common descent. With the introduction of the cladistic method in the late 20th century, 
        phylogenetic taxonomy in which organisms are grouped based purely on inferred evolutionary relatedness, 
        ignoring morphological similarity, has become common in some areas of biology. Molecular phylogenetics, 
        which uses DNA sequences as data, has also driven many recent revisions and is likely to continue doing so. 
        Biological classification belongs to the science of biological systematics.
    </p>
</div>
<hr />
<!-- start footer -->
<div id="footer">
    <p>&copy;2007 All Rights Reserved. &nbsp;&bull;&nbsp; Designed by <a href="http://www.freecsstemplates.org/">Free CSS Templates</a>.</p>
</div>
<!-- end footer -->
</body>
</html>