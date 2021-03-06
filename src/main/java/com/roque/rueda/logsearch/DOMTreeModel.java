package com.roque.rueda.logsearch;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

public class DOMTreeModel implements TreeModel {

    private Document document;

    /**
     * Creates a DOM Tree Model using the xml document
     * @param xmlDocument source document
     */
    public DOMTreeModel(Document xmlDocument) {
        document = xmlDocument;
    }

    @Override
    public Object getRoot() {
        return document.getDocumentElement();
    }

    @Override
    public Object getChild(Object parent, int index) {
        Node node = (Node) parent;
        NodeList list = node.getChildNodes();
        return list.item(index);
    }

    @Override
    public int getChildCount(Object parent) {
        Node node = (Node) parent;
        return node.getChildNodes().getLength();
    }

    @Override
    public boolean isLeaf(Object node) {
        return getChildCount(node) == 0;
    }

    @Override
    public void valueForPathChanged(TreePath path, Object newValue) {
        // Not used...
    }

    @Override
    public int getIndexOfChild(Object parent, Object child) {
        Node node = (Node) parent;
        NodeList list = node.getChildNodes();
        for (int i = 0; i < list.getLength(); i++) {
            if (getChild(node, i) == child) return i;
        }
        return -1;
    }

    @Override
    public void addTreeModelListener(TreeModelListener l) {
        // Not used...
    }

    @Override
    public void removeTreeModelListener(TreeModelListener l) {
        // Not used...
    }
}
