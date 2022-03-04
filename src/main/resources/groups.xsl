<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="html" omit-xml-declaration="yes" indent="yes"/>
    <xsl:template match="/">
        <xsl:param name="projectName"/>
        <html lang='en'>
            <head>
                <title>Group list</title>
            </head>
            <body>
                <h3>Group list of project <xsl:value-of select="$projectName"/></h3>
                <table border="1">
                    <tr>
                        <th>Group ID</th>
                        <th>Status</th>
                    </tr>
                    <xsl:for-each select="//*[name()='Project' and @name=$projectName]/*">
                        <tr>
                            <td><xsl:value-of select="@id"/></td>
                            <td><xsl:value-of select="@status"/></td>
                        </tr>
                    </xsl:for-each>
                </table>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>