<?xml version="1.0" encoding="UTF-8" ?>
 
<xsl:stylesheet version="1.0" 
        xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
        xmlns="http://www.w3.org/1999/xhtml">
    <xsl:output method="xml" indent="yes"
        doctype-public="-//W3C//DTD XHTML 1.0 Strict//EN" 
        doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"/>
 
    <!--XHTML document outline--> 
    <xsl:template match="/">
        <html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
            <head>
                <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
                <title>Class</title>
                <style type="text/css">
                    @import url("style.css");
                </style>
            </head>
            <body>
                <xsl:apply-templates/>
            </body>
        </html>
    </xsl:template>
 
    <!--Table headers and outline-->
    <xsl:template match="Alive">
        <table id='rounded-corner' width='70%'>
            <thead>
                <tr>
                    <th scope='col' class='rounded-company'>Id<form></form></th>
                    <th scope='col'>Name</th>
                    <th scope='col'>Name latin</th>
                    <th scope='col'>Lifespan</th>
                    <th scope='col'>Average weight (kg)</th>
                    <th scope='col'>Native range</th>
                    <th scope='col'>Population</th>
                    <th scope='col' class='rounded'>Biological class id</th>
                </tr>
            </thead>
            <xsl:apply-templates/>
        </table>
    </xsl:template>
 
    <xsl:template match="id">
        <td><xsl:value-of select="." /></td>
    </xsl:template>
    <xsl:template match="name">
        <td><xsl:value-of select="." /></td>
    </xsl:template>
    <xsl:template match="name_latin">
        <td><xsl:value-of select="." /></td>
    </xsl:template>
    <xsl:template match="lifespan">
        <td><xsl:value-of select="." /></td>
    </xsl:template>
    <xsl:template match="avg_weight">
        <td><xsl:value-of select="." /></td>
    </xsl:template>
    <xsl:template match="native_range">
        <td><xsl:value-of select="." /></td>
    </xsl:template>
    <xsl:template match="population">
        <td><xsl:value-of select="." /></td>
    </xsl:template>
    <xsl:template match="class">
        <td><xsl:value-of select="." /></td>
    </xsl:template>
 
</xsl:stylesheet>