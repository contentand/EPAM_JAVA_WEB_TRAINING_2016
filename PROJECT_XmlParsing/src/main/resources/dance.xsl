<?xml version="1.0" encoding="UTF-16" ?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
                xmlns:e="http://www.daniilyurov.com/training/project/xml/dance"
                xmlns:d="http://www.daniilyurov.com/training/project/xml/dancer"
>

    <xsl:output method="html"/>

    <xsl:template match="/">
        <html>
            <head>
                <meta charset="UTF-16"/>
                <style>
                    table {
                        background:#DDD;
                    }
                    td:first-child {
                        text-align:right;
                        font-weight:bold;
                    }
                    td:nth-child(2) {
                        font-style:italic;
                    }
                    .dancer {
                        padding-left:35px;
                    }
                </style>
            </head>
            <body style="margin:5px; padding:10px;">
                <h1>Выступления</h1>
                <xsl:for-each select="e:dance/e:performance">
                    <h2>Выступление номер <xsl:value-of select="@number"/></h2>
                    <article style="background:#CCC;margin-top:5px; padding:10px;">

                        <table>
                            <tr>
                                <td>Направление танца:</td>
                                <td><xsl:value-of select="e:type"/></td>
                            </tr>
                            <tr>
                                <td>Место выступления:</td>
                                <td><xsl:value-of select="e:scene"/></td>
                            </tr>
                            <tr>
                                <td>Коллектив:</td>
                                <td><xsl:value-of select="e:number_of_dancers"/></td>
                            </tr>
                            <tr>
                                <td>Музыкальное сопровождение:</td>
                                <td><xsl:value-of select="e:music"/></td>
                            </tr>
                        </table>

                        <div>
                            <h4>Информация о танцорах коллектива <xsl:value-of select="d:dancers/@groupName"/>:</h4>
                            <xsl:for-each select="d:dancers/d:dancer">
                                <div class="dancer">
                                    <p>- <xsl:value-of select="d:name"/>, <xsl:value-of select="d:age"/> y.o.,
                                         over <xsl:value-of select="d:years"/> years of experience.
                                        <xsl:value-of select="d:description"/>
                                    </p>
                                </div>
                            </xsl:for-each>
                        </div>
                    </article>
                </xsl:for-each>
            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>