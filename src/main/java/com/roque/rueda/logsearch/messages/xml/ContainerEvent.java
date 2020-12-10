package com.roque.rueda.logsearch.messages.xml;

import com.roque.rueda.logsearch.LogXmlParser;
import com.roque.rueda.logsearch.file.FileUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.xpath.*;
import java.util.regex.Matcher;

public class ContainerEvent extends XmlMessage {

    private final static String CONTAINER_EVENT_REGEX = "ContainerEvent_Regex";
    private final static String CONTAINER_EVENT_XPATH_CONTAINER_NAME = "ContainerEvent_Xpath_ContainerName";
    private final static String CONTAINER_EVENT_XPATH_LANE_ID = "ContainerEvent_Xpath_LaneId";
    private final static String CONTAINER_EVENT_XPATH_OFFSET = "ContainerEvent_Xpath_Offset";
    private final static String CONTAINER_EVENT_XPATH_TIER = "ContainerEvent_Xpath_Tier";
    private final static String CONTAINER_EVENT_XPATH_ACTION = "ContainerEvent_Xpath_Action";

    private final String containerName;
    private final String laneId;
    private final String offset;
    private final String tier;
    private final String action;

    public ContainerEvent(
        String containerName,
        String laneId,
        String offset,
        String tier,
        String action
    ) {
        this.containerName = containerName;
        this.laneId = laneId;
        this.offset = offset;
        this.tier = tier;
        this.action = action;
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

    public String getAction() {
        return action;
    }

    public static ContainerEvent readFromXml(
        String filePath,
        String regex,
        String xPathForContainerName,
        String containerNameValue
    ) {
        final Matcher matcher = obtainXmlMatcher(filePath, regex);
        while (matcher.find()) {
            final Document xmlDocument = LogXmlParser.parseStringToXml(matcher.group());
            if (xmlDocument != null) {
                final XPathFactory xpathFactory = XPathFactory.newInstance();
                final XPath xPath = xpathFactory.newXPath();
                try {
                    final XPathExpression expression = xPath.compile(xPathForContainerName);
                    final Object result = expression.evaluate(xmlDocument, XPathConstants.NODESET);
                    final NodeList nodes = (NodeList) result;
                    if (nodes.getLength() > 0) {
                        for (int i = 0; i < nodes.getLength(); i++) {
                            if (nodes.item(i).getNodeValue().equalsIgnoreCase(containerNameValue)) {

                                // Magic here and kisses!
                                String laneId = obtainXpathValue(xmlDocument, obtainXpathForLaneId());
                                String offset = obtainXpathValue(xmlDocument, obtainXpathForOffset());
                                String tier = obtainXpathValue(xmlDocument, obtainXpathForTier());
                                String action = obtainXpathValue(xmlDocument, obtainXpathForAction());

                                return new ContainerEvent(
                                    containerNameValue,
                                    laneId,
                                    offset,
                                    tier,
                                    action
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
        System.out.println("No match was found for ContainerEvent");
        return null;
    }

    public static String obtainRegexFromPropertiesFile() {
        return FileUtils.obtainPropertyFromPropertiesFile(CONTAINER_EVENT_REGEX);
    }

    public static String obtainXpathForContainerName() {
        return FileUtils.obtainPropertyFromPropertiesFile(CONTAINER_EVENT_XPATH_CONTAINER_NAME);
    }

    public static String obtainXpathForLaneId() {
        return FileUtils.obtainPropertyFromPropertiesFile(CONTAINER_EVENT_XPATH_LANE_ID);
    }

    public static String obtainXpathForOffset() {
        return FileUtils.obtainPropertyFromPropertiesFile(CONTAINER_EVENT_XPATH_OFFSET);
    }

    public static String obtainXpathForTier() {
        return FileUtils.obtainPropertyFromPropertiesFile(CONTAINER_EVENT_XPATH_TIER);
    }

    public static String obtainXpathForAction() {
        return FileUtils.obtainPropertyFromPropertiesFile(CONTAINER_EVENT_XPATH_ACTION);
    }
}
