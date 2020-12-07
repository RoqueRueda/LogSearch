package com.roque.rueda.logsearch;

import org.junit.Test;
import org.w3c.dom.Document;

import static org.junit.Assert.assertNotNull;

public class XmlParserTest {

    private static final String EXAMPLE_XML = "<ns3:AddContainerRequest RequestID=\"Invalid\"\n" +
            "  Timestamp=\"2020-11-17T11:45:31.604276-08:00\"\n" +
            "  xmlns:ns6=\"kalmar.com/common/qc\"\n" +
            "  xmlns:ns5=\"kalmar.com/common/railoperations\"\n" +
            "  xmlns:ns2=\"kalmar.com/common/objects\"\n" +
            "  xmlns:ns4=\"kalmar.com/common/vesseloperations\"\n" +
            "  xmlns:ns3=\"kalmar.com/controlSystem/messages\">\n" +
            "  <ContainerInformation xsi:type=\"ns2:SingleContainer\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
            "    <ns2:Container>\n" +
            "      <ns2:ID ID=\"Layla\"/>\n" +
            "      <ns2:Name Name=\"Layla\"/>\n" +
            "      <ns2:ISOCode>Layla</ns2:ISOCode>\n" +
            "      <ns2:Length>Layla</ns2:Length>\n" +
            "      <ns2:Height>Layla</ns2:Height>\n" +
            "      <ns2:Width>Layla</ns2:Width>\n" +
            "      <ns2:Weight>Layla</ns2:Weight>\n" +
            "      <ns2:MeasureMetaData>\n" +
            "        <ns2:Weight>0</ns2:Weight>\n" +
            "        <ns2:Height>0</ns2:Height>\n" +
            "        <ns2:ISOCode>Layla</ns2:ISOCode>\n" +
            "      </ns2:MeasureMetaData>\n" +
            "    </ns2:Container>\n" +
            "    <ns2:Location xsi:type=\"ns2:SlotPosition\" Tentative=\"false\">\n" +
            "      <ns2:Stack xsi:type=\"ns2:DynamicStack\">\n" +
            "        <ns2:Length>Layla</ns2:Length>\n" +
            "        <ns2:LaneID>Layla</ns2:LaneID>\n" +
            "        <ns2:Offset>Layla</ns2:Offset>\n" +
            "      </ns2:Stack>\n" +
            "      <ns2:Tier>1</ns2:Tier>\n" +
            "      <ns2:Orientation>West</ns2:Orientation>\n" +
            "    </ns2:Location>\n" +
            "  </ContainerInformation>\n" +
            "  <BlockTargetStack>false</BlockTargetStack>\n" +
            "</ns3:AddContainerRequest>";

    @Test
    public void parseXmlInput() {
        // Arrange

        // Act
        Document xmlDocument = LogXmlParser.parseStringToXml(EXAMPLE_XML);

        // Assert
        assertNotNull(xmlDocument);
    }
}
