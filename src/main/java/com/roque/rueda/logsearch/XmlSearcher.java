package com.roque.rueda.logsearch;

import org.w3c.dom.*;

public final class XmlSearcher {

    private XmlSearcher() {
        // Prevent instantiation of utility class
    }

    public static NodeList findElementsByName(String name, Document xmlDocument) {
        return xmlDocument.getElementsByTagName(name);
    }

    public static boolean containsElement(String name, Document xmlDocument) {
        return xmlDocument.getElementsByTagName(name).getLength() > 0;
    }

    public static Node findAttributeInElement(String attribute, String element, Document xmlDocument) {
        NodeList elements = xmlDocument.getElementsByTagName(element);
        if (elements.getLength() == 0) {
            return null;
        }

        // TODO: Fix this (O)n2 to search quick in the document
        for (int i = 0; i < elements.getLength(); i++) {
            Node elementItem = elements.item(i);
            NamedNodeMap map = elementItem.getAttributes();
            for (int j = 0; j < map.getLength(); j++) {
                Node mapItem = map.item(j);
                if (mapItem.getNodeName().equalsIgnoreCase(attribute)) {
                    return mapItem;
                }
            }
        }

        return null;
    }

    public static boolean hasAttributeValue(
            String attribute,
            String element,
            String attributeValue,
            Document xmlDocument
    ) {
        Node elementWithAttribute = findAttributeInElement(attribute, element, xmlDocument);
        if (elementWithAttribute instanceof Element) {
            return ((Element) elementWithAttribute).getAttribute(attribute).equalsIgnoreCase(attributeValue);
        } else {
            return false;
        }
    }
}
