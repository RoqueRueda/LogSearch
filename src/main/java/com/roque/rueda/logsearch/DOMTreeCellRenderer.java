package com.roque.rueda.logsearch;

import org.w3c.dom.CharacterData;
import org.w3c.dom.*;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

public class DOMTreeCellRenderer extends DefaultTreeCellRenderer {

    private final int height = 20;

    @Override
    public Component getTreeCellRendererComponent(
        JTree tree,
        Object value,
        boolean sel,
        boolean expanded,
        boolean leaf,
        int row,
        boolean hasFocus
    ) {

        Node node = (Node) value;

        if (node instanceof Element) {
            return renderElementNode((Element) node);
        } else if (node instanceof CharacterData) {
            setText(characterString((CharacterData) node));
        } else {
            setText(node.getClass() + ": " + node.getNodeValue());
        }

        return this;
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension d = super.getPreferredSize();
        d.height = height;
        return d;
    }

    private JPanel renderElementNode(Element element) {
        JPanel panel = new JPanel();
        panel.add(new JLabel("<" + element.getTagName()));
        final NamedNodeMap map = element.getAttributes();
        if (map.getLength() != 0) {
            for (int i = 0; i < map.getLength(); i++) {
                Node item = map.item(i);
                panel.add(new JLabel(item.getNodeName() + "=\"" + item.getNodeValue() + "\""));
            }
        }
        panel.add(new JLabel(">"));
        return panel;
    }

    private JPanel elementPanel(Element e) {
        JPanel panel = new JPanel();
        panel.add(new JLabel("Element: " + e.getTagName()));
        final NamedNodeMap map = e.getAttributes();
        panel.add(new JTable(new AbstractTableModel() {
            @Override
            public int getRowCount() {
                return map.getLength();
            }

            @Override
            public int getColumnCount() {
                // We return 2 because is key value map
                return 2;
            }

            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {
                return columnIndex == 0 ? map.item(rowIndex).getNodeName() : map.item(rowIndex).getNodeValue();
            }
        }));
        return panel;
    }

    private String characterString(CharacterData node) {
        StringBuilder builder = new StringBuilder(node.getData());
        for (int i = 0; i < builder.length(); i++) {
            if (builder.charAt(i) == '\r') {
                builder.deleteCharAt(i);
                i++;
            } else if (builder.charAt(i) == '\n') {
                builder.deleteCharAt(i);
                i++;
            } else if (builder.charAt(i) == '\t') {
                builder.deleteCharAt(i);
                i++;
            }
        }

        if (node instanceof CDATASection) {
            builder.insert(0, "CDATASection: ");
        } else if (node instanceof Comment) {
            builder.insert(0, "Comment: ");
        }

        /*
        if (node instanceof Text) {
            builder.insert(0, "Text: ");
        } else
         */

        return builder.toString();
    }
}
