<?xml version="1.0" encoding="UTF-8" ?>
<?xml-stylesheet type="text/xsl" href="classStyle.xsl"?>
<%-- Set the content type --%>
<%@ page contentType="text/xml" %>
<jsp:useBean id="model" type="biosys.model.BiosystemDAO" scope="request" />
<%  pageContext.setAttribute("bioClass", model.getBioClass(Integer.parseInt(request.getParameter("id")))); %>
<%-- Generate the XML --%> 
<Class xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:noNamespaceSchemaLocation="ClassXMLSchema.xsd">
    <id>${bioClass.id }</id>
    <name>${bioClass.name }</name>
    <parent>${bioClass.parentId }</parent>
</Class>