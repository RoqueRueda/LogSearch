package com.roque.rueda.logsearch.messages.xml;

import com.roque.rueda.logsearch.LogReader;
import com.roque.rueda.logsearch.LogXmlParser;
import com.roque.rueda.logsearch.RegexInput;
import com.roque.rueda.logsearch.file.FileUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.xpath.*;
import java.util.regex.Matcher;

public class AddContainerRequest extends XmlMessage {

    private final static String ADD_CONTAINER_REQUEST_REGEX = "AddContainerRequest_Regex";
    private final static String ADD_CONTAINER_REQUEST_XPATH_CONTAINER_NAME = "AddContainerRequest_Xpath_ContainerName";
    private final static String ADD_CONTAINER_REQUEST_XPATH_LANE_ID = "AddContainerRequest_Xpath_LaneId";
    private final static String ADD_CONTAINER_REQUEST_XPATH_OFFSET = "AddContainerRequest_Xpath_Offset";
    private final static String ADD_CONTAINER_REQUEST_XPATH_TIER = "AddContainerRequest_Xpath_Tier";

    private final String containerName;
    private final String laneId;
    private final String offset;
    private final String tier;

    public AddContainerRequest(
        String containerName,
        String laneId,
        String offset,
        String tier
    ) {
        this.containerName = containerName;
        this.laneId = laneId;
        this.offset = offset;
        this.tier = tier;
    }

    public String getContainerName() {
        return containerName;
    }

    public String getLaneId() {
        return laneId;
    }

    public String getOffset() {
        return offset;
    }

    public String getTier() {
        return tier;
    }

    public static AddContainerRequest readFromXml(
        String filePath,
        String regex,
        String xPathForContainerName,
        String containerNameValue
    ) {
        final Matcher matcher = obtainXmlMatcher(filePath, regex);
        while (matcher.find()) {
            final Document xmlDocument = LogXmlParser.parseStringToXml(matcher.group());
            if (xmlDocument != null) {
                final XPathFactory xpathfactory = XPathFactory.newInstance();
                final XPath xPath = xpathfactory.newXPath();
                try {
                    final XPathExpression expression = xPath.compile(xPathForContainerName);
                    final Object result = expression.evaluate(xmlDocument, XPathConstants.NODESET);
                    final NodeList nodes = (NodeList) result;
                    if (nodes.getLength() > 0) {
                        for (int i = 0; i < nodes.getLength(); i++) {
                            if (nodes.item(i).getNodeValue().equalsIgnoreCase(containerNameValue)) {

                                // Magic here!
                                String laneId = obtainXpathValue(xmlDocument, obtainXpathForLaneId());
                                String offset = obtainXpathValue(xmlDocument, obtainXpathForOffset());
                                String tier = obtainXpathValue(xmlDocument, obtainXpathForTier());

                                return new AddContainerRequest(
                                    containerNameValue,
                                    laneId,
                                    offset,
                                    tier
                                );
                            }
                        }
                    }
                } catch (XPathExpressionException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
        // There is no matches for this item with the given information
        System.out.println("No match was found for AddContainerRequest");
        return null;
    }

    public static String obtainRegexFromPropertiesFile() {
        return FileUtils.obtainPropertyFromPropertiesFile(ADD_CONTAINER_REQUEST_REGEX);
    }

    public static String obtainXpathForContainerName() {
        return FileUtils.obtainPropertyFromPropertiesFile(ADD_CONTAINER_REQUEST_XPATH_CONTAINER_NAME);
    }

    public static String obtainXpathForLaneId() {
        return FileUtils.obtainPropertyFromPropertiesFile(ADD_CONTAINER_REQUEST_XPATH_LANE_ID);
    }

    public static String obtainXpathForOffset() {
        return FileUtils.obtainPropertyFromPropertiesFile(ADD_CONTAINER_REQUEST_XPATH_OFFSET);
    }

    public static String obtainXpathForTier() {
        return FileUtils.obtainPropertyFromPropertiesFile(ADD_CONTAINER_REQUEST_XPATH_TIER);
    }
}
