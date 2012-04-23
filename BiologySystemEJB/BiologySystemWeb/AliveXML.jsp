<?xml version="1.0" encoding="UTF-8" ?>
<?xml-stylesheet type="text/xsl" href="aliveStyle.xsl"?>
<%-- Set the content type --%>
<%@ page contentType="text/xml" %>
<jsp:useBean id="model" type="biosys.model.BiosystemDAO" scope="request" />
<%  pageContext.setAttribute("alive", model.getAlive(Integer.parseInt(request.getParameter("id")))); %>
<%-- Generate the XML --%> 
<Alive xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:noNamespaceSchemaLocation="AliveXMLSchema.xsd">
    <id>${alive.id }</id>
    <name>${alive.name }</name>
    <name_latin>${alive.nameLatin }</name_latin>
    <lifespan>${alive.lifespan }</lifespan>
    <avg_weight>${alive.avgWeight }</avg_weight>
    <native_range>${alive.nativeRange }</native_range>
    <population>${alive.population }</population>
    <class>${alive.bioClass }</class>
</Alive>