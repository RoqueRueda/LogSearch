package com.roque.rueda.logsearch;

import org.junit.Test;

import static org.junit.Assert.*;

public class LogReaderTest {

    private static final String FILE_TEXT = "=== Some other info ===\n" +
            "\n" +
            "<ns3:AddContainerRequest RequestID=\"Invalid\"\n" +
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
            "</ns3:AddContainerRequest>\n" +
            "\n" +
            "=== Real Example ===\n" +
            "\n" +
            "<ns3:AddContainerRequest RequestID=\"281eb81c-fca3-426a-9918-e30dd9bac743\"\n" +
            "  Timestamp=\"2020-11-17T11:45:31.604276-08:00\"\n" +
            "  xmlns:ns6=\"kalmar.com/common/qc\"\n" +
            "  xmlns:ns5=\"kalmar.com/common/railoperations\"\n" +
            "  xmlns:ns2=\"kalmar.com/common/objects\"\n" +
            "  xmlns:ns4=\"kalmar.com/common/vesseloperations\"\n" +
            "  xmlns:ns3=\"kalmar.com/controlSystem/messages\">\n" +
            "  <ContainerInformation xsi:type=\"ns2:SingleContainer\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
            "    <ns2:Container>\n" +
            "      <ns2:ID ID=\"9f8da0cf-5508-433e-aa9e-e77ae0b4cc3c\"/>\n" +
            "      <ns2:Name Name=\"TCNU7146564\"/>\n" +
            "      <ns2:ISOCode>45G1</ns2:ISOCode>\n" +
            "      <ns2:Length>12192</ns2:Length>\n" +
            "      <ns2:Height>2895</ns2:Height>\n" +
            "      <ns2:Width>2438</ns2:Width>\n" +
            "      <ns2:Weight>11558</ns2:Weight>\n" +
            "      <ns2:MeasureMetaData>\n" +
            "        <ns2:Weight>0</ns2:Weight>\n" +
            "        <ns2:Height>0</ns2:Height>\n" +
            "        <ns2:ISOCode>45G1</ns2:ISOCode>\n" +
            "      </ns2:MeasureMetaData>\n" +
            "    </ns2:Container>\n" +
            "    <ns2:Location xsi:type=\"ns2:SlotPosition\" Tentative=\"false\">\n" +
            "      <ns2:Stack xsi:type=\"ns2:DynamicStack\">\n" +
            "        <ns2:Length>Unknown</ns2:Length>\n" +
            "        <ns2:LaneID>QCA001.03</ns2:LaneID>\n" +
            "        <ns2:Offset>73946</ns2:Offset>\n" +
            "      </ns2:Stack>\n" +
            "      <ns2:Tier>1</ns2:Tier>\n" +
            "      <ns2:Orientation>West</ns2:Orientation>\n" +
            "    </ns2:Location>\n" +
            "  </ContainerInformation>\n" +
            "  <BlockTargetStack>false</BlockTargetStack>\n" +
            "</ns3:AddContainerRequest>";

    private LogReader getSubject(String filePath) {
        return LogReader.fromFilePath(filePath);
    }

    @Test
    public void shouldBeAbleToOpenAFile() {
        // Arrange
        LogReader reader = getSubject("src/test/resources/ExampleLog.txt");
        String expected = FILE_TEXT;

        // Act
        String actual = reader.getFileText();

        // Assert
        assertEquals("The text and the file text don't match", expected, actual);
    }

    @Test
    public void shouldThrownAnExceptionIfNullPath() {
        // Arrange
        String exceptionMessage = "The path of the log file is null";

        // Act
        try {
            LogReader reader = getSubject(null);
        }catch (Exception ex){

            // Assert
            assertNotNull(ex);
            assertTrue(ex instanceof IllegalArgumentException);
            assertEquals(exceptionMessage, ex.getMessage());
        }
    }
}

