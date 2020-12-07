package com.roque.rueda.logsearch;

import org.w3c.dom.Document;

import javax.swing.*;

public class XmlTreeFrame extends JFrame {

    private static final int DEFAULT_WIDTH = 400;
    private static final int DEFAULT_HEIGHT = 400;

    public XmlTreeFrame(String title, Document xmlDocument) {
        setTitle(title);
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

        JTree tree = new JTree(new DOMTreeModel(xmlDocument));
        tree.setCellRenderer(new DOMTreeCellRenderer());

        setContentPane(new JScrollPane(
                tree, // component
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, // vs policy
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS) // hs policy
        );

        revalidate();
        pack();
    }



}
