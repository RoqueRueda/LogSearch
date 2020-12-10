package com.roque.rueda.logsearch.messages.xml;

import com.roque.rueda.logsearch.LogReader;
import com.roque.rueda.logsearch.RegexInput;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.xpath.*;
import java.util.regex.Matcher;

class XmlMessage {

    protected static Matcher obtainXmlMatcher(String filePath, String regex) {
        final LogReader reader = LogReader.fromFilePath(filePath);
        final RegexInput regexInput = new RegexInput(regex);
        return regexInput.getPattern().matcher(reader.getFileText());
    }

    protected static String obtainXpathValue(Document xmlDocument, String xPathInput) {
        final XPathFactory xpathfactory = XPathFactory.newInstance();
        final XPath xPath = xpathfactory.newXPath();

        try {
            final XPathExpression expression = xPath.compile(xPathInput);
            final Object result = expression.evaluate(xmlDocument, XPathConstants.STRING);
            return result.toString();
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }

        return null;
    }
}
