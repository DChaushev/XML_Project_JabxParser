<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : SE_Schedule.xsl
    Created on : February 12, 2015, 2:27 PM
    Author     : Dimitar
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html"/>

    <!-- TODO customize transformation rules 
         syntax recommendation http://www.w3.org/TR/xslt 
    -->
    <xsl:template match="/">
        <html>
            <head>
                <title>SE_Schedule.xsl</title>
            </head>
            <body bgcolor="#D4E4F9">
                <h2>Software Engineering Schedule</h2>
                <table border="1">
                    <xsl:for-each select="schedule/semester">
                        <tr>
                            <td bgcolor="#00FF00">Semester <xsl:value-of select="@number" /></td>
                        </tr>
                        <xsl:for-each select="./course">
                            <tr>
                                <td>
                                    <xsl:value-of select="@name" />
                                </td>
                            </tr>
                        </xsl:for-each>
                    </xsl:for-each>
                </table>
            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>
