/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphdemo;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Admin
 */
public class graph extends JPanel {

    public static final int MAX_VERTEX = 50;
    int numberOfVertex;
    int[][] graph;
    public static final int r = 16;
    ArrayList<Vertex> vertices = new ArrayList<>();
    ArrayList<Edge> edges = new ArrayList<>();
    private int mouseX, mouseY;
    private Graphics2D g = null;
    private int startIndex = 1;
    private int endIndex = 1;
    private int stopIndex = 0;
    private boolean isCtrl = false;
    private boolean isShift = false;
    boolean isCheckDijkstra = false;
    int sumPrim = 0;
    boolean isCheckPrim = false;
// DFS 
    ArrayList<Vertex> listDFS = new ArrayList<>();
    ArrayList<Vertex> listBFS = new ArrayList<>();
    int starttraversal = 0;
    boolean[] isVisited = new boolean[MAX_VERTEX];

    public void reset() {
        for (int i = 0; i < this.MAX_VERTEX; i++) {
            isVisited[i] = false;
        }
    }

    public void DFS(int start) {
        listDFS.clear();
        reset();
        Stack<Integer> s = new Stack<>();
        s.clear();
        s.push(start);
        int fromVertex;
        while (!s.empty()) {
            fromVertex = s.pop();
            if (isVisited[fromVertex] == false) {
                System.out.print(fromVertex + "->");
                isVisited[fromVertex] = true;
                listDFS.add(vertices.get(fromVertex));
                for (int i = numberOfVertex - 1; i >= 0; i--) {
                    if (isVisited[i] == false && graph[fromVertex][i] > 0) {
                        s.push(i);
                    }

                }

            }

        }
        System.out.println("");
        repaint();
    }

    public String printDFS() {
        String Start = JOptionPane.showInputDialog(this, "Enter the start vertex: ");
        starttraversal = Integer.parseInt(Start);
        DFS(starttraversal);
        String result = "";
        if (listDFS.size() == vertices.size()) {
            for (int i = 0; i < listDFS.size(); i++) {
                result += (listDFS.get(i).getValue() + (i < listDFS.size() - 1 ? "," : ""));
            }
        } else {
            result = " There is Not a connected graph";
        }
        return result;
    }

    // BFS
    public void BFS(int start) {
        listBFS.clear();
        reset();
        Queue<Integer> q = new LinkedList<>();
        q.clear();
        q.add(start);
        int fromVertex;
        while (!q.isEmpty()) {
            fromVertex = q.poll();
            if (isVisited[fromVertex] == false) {
                System.out.print(fromVertex + "->");
                isVisited[fromVertex] = true;
                listDFS.add(vertices.get(fromVertex));
                for (int i = 0; i < numberOfVertex; i++) {
                    if (isVisited[i] == false && graph[fromVertex][i] > 0) {
                        q.add(i);
                    }
                }
            }
        }
        System.out.println("");
        repaint();
    }

    public String printBFS() {
        String Start = JOptionPane.showInputDialog(this, "Enter the start vertex: ");
        starttraversal = Integer.parseInt(Start);
        DFS(starttraversal);
        String result = "";
        if (listBFS.size() == vertices.size()) {
            for (int i = 0; i < listBFS.size(); i++) {
                result += (listBFS.get(i).getValue() + (i < listBFS.size() - 1 ? "," : ""));
            }
        } else {
            result = " There is Not a connected graph";
        }
        return result;
    }
    // dijstra
    boolean conected;

    ArrayList<Vertex> listdijkstra = new ArrayList<>();
    int start;
    int end;
    private int resetDistance[] = new int[MAX_VERTEX];

    public void resetDistance() {
        for (int i = 0; i < MAX_VERTEX; i++) {
            resetDistance[i] = Integer.MAX_VALUE;
        }
    }
    private int resetTheVertexBefore[] = new int[MAX_VERTEX];

    public void resetTheVertexBefore() {
        for (int i = 0; i < this.MAX_VERTEX; i++) {
            resetTheVertexBefore[i] = i;
        }
    }

    public int findNearestVertex() {
        int minIndex = -1;
        int minValue = Integer.MAX_VALUE;
        for (int i = 0; i < numberOfVertex; i++) {
            if (isVisited[i] == false && resetDistance[i] < minValue) {
                minValue = resetDistance[i];
                minIndex = i;
            }
        }
        return minIndex;
    }

    public void dijkstra() {
        reset();
        resetDistance();
        resetTheVertexBefore();
        resetDistance[0] = 0;
        int current;
        conected = true;
        isCheckDijkstra = true;

        for (int i = 0; i < numberOfVertex; i++) {
            current = findNearestVertex();
            if (current == -1) {
                conected = false;
                break;
            } else {
                System.out.print(current + "-> ");

                isVisited[current] = true;

                for (int j = 0; j < numberOfVertex; j++) {
                    if (isVisited[j] == false
                            && graph[current][j] > 0
                            && resetDistance[current] + graph[current][j] < resetDistance[j]) {
                        resetDistance[j] = graph[current][j] + resetDistance[current];
                        resetTheVertexBefore[j] = current;
                    }
                }
            }
        }
        repaint();
    }

    public String printDijkstra() {
        String s = JOptionPane.showInputDialog(this, "Enter the start vertex: ");
        start = Integer.parseInt(s);
        String e = JOptionPane.showInputDialog(this, "Enter the end vertex: ");
        end = Integer.parseInt(e);
        dijkstra();
        String result = "";
        if (conected == true) {
            result = " The sortest path form " + start + " to" + end + ": ";
            String path = "" + end;
            int current = end;
            while (current != start) {
                current = resetTheVertexBefore[current];
                path = current + "->" + path;
            }
            path += ": " + resetDistance[end];
            result += path;
        } else {
            result = " There í Not a connected graph";
        }
        return result;
    }

    public void drawDijskta() {
        if (isCheckDijkstra) {
            if (conected) {
                String path = "" + end;
                int current = end;
                while (current != start) {
                    current = resetTheVertexBefore[current];
                    path = current + "->" + path;
                }
                String[] V = path.split("->");
                int fromVertex, toVertex, edgeIndex, vertexIndex;
                Edge edge;
                for (int i = 1; i < V.length; i++) {
                    fromVertex = Integer.parseInt(V[i - 1]);
                    toVertex = Integer.parseInt(V[i]);
                    edgeIndex = findEdgeByVertex(fromVertex, toVertex);
                    edges.get(edgeIndex).setIsSelected(true);
                    edge = edges.get(edgeIndex);
                    Vertex start = edge.getStart();
                    Vertex end = edge.getEnd();
                    g.setColor(edge.isIsSelected() ? Color.red : Color.black);
                    g.drawLine(start.getX(), start.getY(), end.getX(), end.getY());
                    // draw weight
                    g.setColor(edge.isIsSelected() ? Color.yellow : Color.white);
                    g.fillRoundRect(edge.getX() - 10, edge.getY() - 10, 20, 20, 15, 15);
                    g.setColor(Color.black);
                    drawCenterString(g, edge.getWeight() + "", new Rectangle(edge.getX() - 10,
                            edge.getY() - 10, 20, 20), new Font("Arial", Font.PLAIN, 12));
                    edges.get(edgeIndex).setIsSelected(false);
                }
                // draw vertex
                for (int i = 0; i < V.length; i++) {
                    vertexIndex = Integer.parseInt(V[i]);
                    vertices.get(vertexIndex).setIsSelected(true);
                    Vertex v = vertices.get(vertexIndex);
                    int x = v.getX();
                    int y = v.getY();
                    g.setColor(v.isIsSelected() ? Color.red : Color.white);
                    g.fillOval(x - r, y - r, r * 2, r * 2);
                    g.setColor(v.isIsSelected() ? Color.YELLOW : Color.BLACK);
                    g.drawOval(x - r, y - r, r * 2, r * 2);
                    drawCenterString(g, v.getValue() + "", new Rectangle(x - r, y - r, r * 2, r * 2),
                            new Font("Arial", Font.PLAIN, 12));
                    vertices.get(vertexIndex).setIsSelected(false);
                }

            } else {
                JOptionPane.showMessageDialog(this, "This í NOT a connected graph");
            }
        }
        isCheckDijkstra = false;
    }

    // draw Prim
    public void prims() {
        reset();
        resetDistance();
        resetTheVertexBefore();
        resetDistance[0] = 0;
        int current;
        sumPrim = 0;
        conected = true;
        isCheckPrim = true;
        for (int i = 0; i < numberOfVertex; i++) {
            current = findNearestVertex();
            if (current == -1) {
                conected = false;
                return;
            } else {
                System.out.println(current + "-> ");
                sumPrim += resetDistance[current];
                isVisited[current] = true;
                for (int j = 0; j < numberOfVertex; j++) {
                    if (isVisited[j] == false && graph[current][j] > 0 && graph[current][j] < resetDistance[j]) {
                        resetDistance[current] = graph[current][j];
                        resetTheVertexBefore[j] = current;
                    }
                }
            }
        }
        repaint();

    }

    public String printMSTPrim() {
        prims();
        String result = "";
        if (conected) {
            result = " The MST (Prim): ";
            String path = "[";
            for (int i = 0; i < numberOfVertex; i++) {
                path += "[" + resetTheVertexBefore[i] + ", " + i + "]: " + graph[resetTheVertexBefore[i]][i] + ", ";
            }
            path += "]: " + sumPrim;
            result += path;

        } else {
            result = " There í Not a connected graph";
        }
        return result;
    }

    public void drawMSTPrim() {
        if (isCheckPrim) {
            if (conected) {
                int fromVertex, toVertex, edgeIndex;
                Edge edge;
                for (int i = 1; i < numberOfVertex; i++) {
                    fromVertex = resetTheVertexBefore[i];
                    toVertex = i;
                    edgeIndex = findEdgeByVertex(fromVertex, toVertex);
                    edges.get(edgeIndex).setIsSelected(true);
                    edge = edges.get(edgeIndex);
                    Vertex start = edge.getStart();
                    Vertex end = edge.getEnd();
                    g.setColor(edge.isIsSelected() ? Color.red : Color.black);
                    g.drawLine(start.getX(), start.getY(), end.getX(), end.getY());
                    // draw weight
                    g.setColor(edge.isIsSelected() ? Color.yellow : Color.white);
                    g.fillRoundRect(edge.getX() - 10, edge.getY() - 10, 20, 20, 15, 15);
                    g.setColor(Color.black);
                    drawCenterString(g, edge.getWeight() + "", new Rectangle(edge.getX() - 10,
                            edge.getY() - 10, 20, 20), new Font("Arial", Font.PLAIN, 12));
                    edges.get(edgeIndex).setIsSelected(false);

                }
                // draw vertex
                for (int i = 0; i < vertices.size(); i++) {
                    vertices.get(i).setIsSelected(true);
                    Vertex v = vertices.get(i);
                    int x = v.getX();
                    int y = v.getY();
                    g.setColor(v.isIsSelected() ? Color.red : Color.white);
                    g.fillOval(x - r, y - r, r * 2, r * 2);
                    g.setColor(v.isIsSelected() ? Color.YELLOW : Color.BLACK);
                    g.drawOval(x - r, y - r, r * 2, r * 2);
                    drawCenterString(g, v.getValue() + "", new Rectangle(x - r, y - r, r * 2, r * 2),
                            new Font("Arial", Font.PLAIN, 12));
                    vertices.get(i).setIsSelected(false);

                }
            } else {
                JOptionPane.showMessageDialog(this, "This í NOT a connected graph");

            }
            isCheckPrim = false;

        }
    }
    //draw kruskal
    int[] parentKrukal = new int[MAX_VERTEX];
    ArrayList<Edge> listEdgeKruskal = new ArrayList<>();
    int sumKruskal = 0;
    boolean isCheckKruskal = false;

    public void make_set() {
        for (int i = 0; i < this.MAX_VERTEX; i++) {
            parentKrukal[i] = i;
        }
    }

    public int findParent(int v) {
        if (v == parentKrukal[v]) {
            return v;
        }

        return findParent(parentKrukal[v]);
    }

    public boolean union(int a, int b) {
        a = findParent(a);
        b = findParent(b);
        if (a == b) {
            return false;
        }
        parentKrukal[b] = a;
        return true;

    }

    public void kruskal() {
        make_set();
        sumKruskal = 0;
        Collections.sort(edges);
        for (int i = 0; i < edges.size(); i++) {
            if (listEdgeKruskal.size() == numberOfVertex - 1) {
                break;
            }
            if (union(edges.get(i).getStart().getValue(), edges.get(i).getEnd().getValue())) {
                listEdgeKruskal.add(edges.get(i));
                sumKruskal += edges.get(i).getWeight();
            }

        }
    }

    public String printKruskal() {
        kruskal();
        String result = "";
        if (conected) {
            result = " The MST(kruskal): ";
            String path = "[";
            for (Edge edge : listEdgeKruskal) {
                path += "[" + edge.getStart().getValue() + ", " + edge.getEnd().getValue() + "]:" + edge.getWeight();

            }
            path += "]: " + sumKruskal;
            result += path;
        } else {
            result = " There í Not a connected graph";
        }
        return result;
    }

    public void drawKruskal() {
        if (isCheckDijkstra) {
            if (conected) {
                int fromVertex, toVertex, edgeIndex;
                for (Edge edge : listEdgeKruskal) {
                    edge.setIsSelected(true);

                    Vertex start = edge.getStart();
                    Vertex end = edge.getEnd();
                    g.setColor(edge.isIsSelected() ? Color.red : Color.black);
                    g.drawLine(start.getX(), start.getY(), end.getX(), end.getY());
                    // draw weight
                    g.setColor(edge.isIsSelected() ? Color.yellow : Color.white);
                    g.fillRoundRect(edge.getX() - 10, edge.getY() - 10, 20, 20, 15, 15);
                    g.setColor(Color.black);
                    drawCenterString(g, edge.getWeight() + "", new Rectangle(edge.getX() - 10,
                            edge.getY() - 10, 20, 20), new Font("Arial", Font.PLAIN, 12));
                    edge.setIsSelected(false);

                }
                //draw vertex
                for (int i = 0; i < vertices.size(); i++) {
                    vertices.get(i).setIsSelected(true);
                    Vertex v = vertices.get(i);
                    int x = v.getX();
                    int y = v.getY();
                    g.setColor(v.isIsSelected() ? Color.red : Color.white);
                    g.fillOval(x - r, y - r, r * 2, r * 2);
                    g.setColor(v.isIsSelected() ? Color.YELLOW : Color.BLACK);
                    g.drawOval(x - r, y - r, r * 2, r * 2);
                    drawCenterString(g, v.getValue() + "", new Rectangle(x - r, y - r, r * 2, r * 2),
                            new Font("Arial", Font.PLAIN, 12));
                    vertices.get(i).setIsSelected(false);
                }
            } else {
                JOptionPane.showMessageDialog(this, "This í NOT a connected graph");

            }
        }
    }
    // IVC
    ArrayList<Vertex> listIVC = new ArrayList<>();
    boolean isCheckIVC = false;

    public void IVC() {
        for (int i = 0; i < numberOfVertex; i++) {
            for (int j = 0; j < numberOfVertex; j++) {
                if (graph[i][j] < 0) {
                    break;
                }
                if (j == numberOfVertex) {
                    listIVC.add(vertices.get(i));
                }
            }
        }
        repaint();
    }

    public String printIVC() {
        IVC();
        String result = "";
        if (listIVC.size() > 0) {
            for (int i = 0; i < listIVC.size(); i++) {
                result += listIVC.get(i).getValue() + (i < listIVC.size() - 1 ? ", " : " ");
            }

        } else {
            result = " There í Not a connected graph";

        }
        return result;
    }

    public void drawIVC() {
        if (isCheckIVC) {
            if (listIVC.size() > 0) {
                for (int i = 0; i < listIVC.size(); i++) {

                    Vertex v = listIVC.get(i);
                    v.setIsSelected(true);
                    int x = v.getX();
                    int y = v.getY();
                    g.setColor(v.isIsSelected() ? Color.red : Color.white);
                    g.fillOval(x - r, y - r, r * 2, r * 2);
                    g.setColor(v.isIsSelected() ? Color.YELLOW : Color.BLACK);
                    g.drawOval(x - r, y - r, r * 2, r * 2);
                    drawCenterString(g, v.getValue() + "", new Rectangle(x - r, y - r, r * 2, r * 2),
                            new Font("Arial", Font.PLAIN, 12));

                    v.setIsSelected(false);
                }
            } else {
                JOptionPane.showMessageDialog(this, "This í NOT a connected graph");

            }
        }
    }

    public graph() {
        this.numberOfVertex = 0;
        graph = new int[MAX_VERTEX][MAX_VERTEX];
        for (int i = 0; i < MAX_VERTEX; i++) {
            for (int j = 0; j < MAX_VERTEX; j++) {
                graph[i][j] = 0;

            }
        }
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e); //To change body of generated methods, choose Tools | Templates.
                mouseX = e.getX();
                mouseY = e.getY();
                isCtrl = e.isControlDown();
                isShift = e.isShiftDown();
                checkMouseClicked();
            }

        });
        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e); //To change body of generated methods, choose Tools | Templates.
                mouseX = e.getX();
                mouseY = e.getY();
                mouseVertex();
            }

        });
    }

    private void checkMouseClicked() {
        if (isCtrl) {
            adđVertex();
        } else if (isShift) {
            removeVertex();
            removeEdge();
        } else {
            selectVertex();
            selectEdge();
        }
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g); //To change body of generated methods, choose Tools | Templates.
        this.g = (Graphics2D) g;
        drawGraph();
        drawDijskta();
        drawMSTPrim();
        drawKruskal();
        drawIVC();
    }

    private void adđVertex() {
        vertices.add(new Vertex(numberOfVertex, mouseX, mouseY));
        numberOfVertex++;
        for (int i = 0; i < numberOfVertex; i++) {
            graph[i][numberOfVertex - 1] = 0;
            graph[numberOfVertex][i] = 0;
        }
    }

    public void drawGraph() {
        // draw edges
        Edge edge;
        for (int i = 0; i < edges.size(); i++) {
            edge = edges.get(i);
            Vertex start = edge.getStart();
            Vertex end = edge.getEnd();
            edge.calculateCenterLocation();
            g.setColor(edge.isIsSelected() ? Color.red : Color.black);
            g.drawLine(start.getX(), start.getY(), end.getX(), end.getY());
            // draw weight
            g.setColor(edge.isIsSelected() ? Color.yellow : Color.white);
            g.fillRoundRect(edge.getX() - 10, edge.getY() - 10, 20, 20, 15, 15);
            g.setColor(Color.black);
            drawCenterString(g, edge.getWeight() + "", new Rectangle(edge.getX() - 10, edge.getY() - 10, 20, 20), new Font("Arial", Font.PLAIN, 12));
        }
        // draw vertex
        for (int i = 0; i < numberOfVertex; i++) {
            Vertex v = vertices.get(i);
            int x = v.getX();
            int y = v.getY();
            g.setColor(v.isIsSelected() ? Color.red : Color.white);
            g.fillOval(x - r, y - r, r * 2, r * 2);
            g.setColor(v.isIsSelected() ? Color.YELLOW : Color.BLACK);
            g.drawOval(x - r, y - r, r * 2, r * 2);
            drawCenterString(g, v.getValue() + "", new Rectangle(x - r, y - r, r * 2, r * 2),
                    new Font("Arial", Font.PLAIN, 12));
        }
    }

    public void drawCenterString(Graphics g, String text, Rectangle rect, Font font) {
        FontMetrics metrics = g.getFontMetrics(font);
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        g.setFont(font);
        g.drawString(text, x, y);

    }

    public int findVertexByLocation(int mouseX, int mouseY) {
        for (int i = 0; i < vertices.size(); i++) {
            if (vertices.get(i).isInside(mouseX, mouseY)) {
                return i;
            }
        }
        return -1;
    }

    private void selectVertex() {
        int selectVertexIndex = findVertexByLocation(mouseX, mouseY);
        if (selectVertexIndex != -1) {
            if (startIndex == -1) {
                startIndex = selectVertexIndex;
                vertices.get(startIndex).setIsSelected(true);
            } else if (startIndex == selectVertexIndex) {
                vertices.get(startIndex).setIsSelected(false);
                startIndex = -1;
            } else {
                stopIndex = selectVertexIndex;
                addEdge();
                //vertices.get(startIndex).setIsSelected(false);

            }

        }
    }

    public int findEdgeByVertex(int from, int to) {
        Edge edge;
        for (int i = 0; i < edges.size(); i++) {
            edge = edges.get(i);
            if ((edge.getStart().getValue() == from && edge.getEnd().getValue() == to)
                    || (edge.getStart().getValue() == to && edge.getEnd().getValue() == from)) {
                return i;
            }

        }
        return -1;
    }

    private void addEdge() {
        int selectedEdgeIndex = findEdgeByVertex(startIndex, stopIndex);
        if (selectedEdgeIndex == -1) {
            String str = JOptionPane.showInputDialog(this, "Enter weight: ", "1");
            int edgeWeight = Integer.parseInt(str);
            graph[startIndex][stopIndex] = edgeWeight;
            graph[stopIndex][startIndex] = edgeWeight;
            edges.add(new Edge(vertices.get(startIndex), vertices.get(stopIndex), edgeWeight));
        }
        vertices.get(startIndex).setIsSelected(false);
        vertices.get(stopIndex).setIsSelected(false);
        startIndex = stopIndex = -1;
        repaint();
    }

    private void mouseVertex() {
        int selectedVertexIndex = findVertexByLocation(mouseX, mouseY);
        System.out.println("selectedVertexIndex: " + selectedVertexIndex);
        if (selectedVertexIndex > -1) {
            vertices.get(selectedVertexIndex).setX(mouseX);
            vertices.get(selectedVertexIndex).setY(mouseY);
            repaint();

        }
    }

    private void removeVertex() {
        int selectVertexIndex = findVertexByLocation(mouseX, mouseY);
        if (selectVertexIndex > -1) {
            vertices.get(selectVertexIndex).setIsSelected(true);
            String value = vertices.get(selectVertexIndex).getValue() + "";
            repaint();
            if (JOptionPane.showConfirmDialog(this, "Do you want to delete the vertex " + value + "?", "Warining", JOptionPane.YES_NO_CANCEL_OPTION) == JOptionPane.YES_OPTION) {
                // remove vertex
                removeVertex(selectVertexIndex);
            } else {
                vertices.get(selectVertexIndex).setIsSelected(false);
            }
        }
    }

    private void removeVertex(int index) {
        for (int from = index; from < numberOfVertex; from++) {
            for (int to = 0; to < numberOfVertex; to++) {
                graph[from][to] = graph[from + 1][to];
                graph[to][from] = graph[to][from + 1];
            }
        }
        numberOfVertex--;
        Edge edge;
        for (int i = edges.size() - 1; i >= 0; i--) {
            edge = edges.get(i);
            if (edge.getStart().getValue() == index || edge.getEnd().getValue() == index) {
                edges.remove(i);
            }
        }
        vertices.remove(index);
        for (int i = index; i < numberOfVertex; i++) {
            vertices.get(i).setValue(vertices.get(i).getValue() - 1);
        }
    }

    public int findEdgeByLocation(int mouseX, int MouseY) {
        for (int i = 0; i < edges.size(); i++) {
            if (edges.get(i).isInside(mouseX, MouseY)) {
                return i;
            }
        }
        return -1;
    }

    private void removeEdge() {
        int selectedEdgeIndex = findEdgeByLocation(mouseX, mouseY);
        if (selectedEdgeIndex > -1) {
            Edge edge = edges.get(selectedEdgeIndex);
            String info = edge.getStart().getValue() + " - " + edge.getEnd().getValue();
            edge.setIsSelected(true);
            repaint();
            if (JOptionPane.showConfirmDialog(this, "Do you want to delete the vertex " + info + "?", "Warining", JOptionPane.YES_NO_CANCEL_OPTION) == JOptionPane.YES_OPTION) {
                // remove vertex
                removeEdge(selectedEdgeIndex);
            } else {
                vertices.get(selectedEdgeIndex).setIsSelected(false);
            }

        }
    }

    private void removeEdge(int index) {
        Edge edge = edges.get(index);
        int start = edge.getStart().getValue();
        int end = edge.getEnd().getValue();
        graph[start][end] = graph[end][start] = 0;
        edges.remove(index);

    }

    private void selectEdge() {
        int selectEdgeIndex = findEdgeByLocation(mouseX, mouseY);
        System.out.println("selectEdgeIndex: " + selectEdgeIndex);
        if (selectEdgeIndex > -1) {
            edges.get(selectEdgeIndex).setIsSelected(true);
            repaint();
            Edge edge = edges.get(selectEdgeIndex);
            int start = edge.getStart().getValue();
            int end = edge.getEnd().getValue();
            String w = JOptionPane.showInputDialog(this, " Enter new weight: ", edge.getWeight());
            int newWeight = Integer.parseInt(w);
            edge.setWeight(newWeight);
            graph[start][end] = graph[end][start] = newWeight;
            edges.get(selectEdgeIndex).setIsSelected(false);
        }
    }
//vertex edge
    // x y
    // x y 
    // start end

    void saveToList(File saveFile) {
        try {
            FileWriter myWrite = new FileWriter(saveFile);
            myWrite.write(numberOfVertex + " " + edges.size() + "\n");
            Vertex v;
            for (int i = 0; i < numberOfVertex; i++) {
                v = vertices.get(i);
                myWrite.write(v.getX() + " " + v.getY() + "\n");
            }
            for (int start = 0; start < numberOfVertex; start++) {
                for (int end = 0; end < numberOfVertex; end++) {
                    if (start < end && graph[start][end] > 0) {
                        myWrite.write(start + " " + end + " " + graph[start][end] + "\n");
                    }
                }
            }
            myWrite.close();
        } catch (Exception e) {
        }
    }

    void saveToMatrix(File saveFile) {
        try {
            FileWriter myWrite = new FileWriter(saveFile);
            myWrite.write(numberOfVertex + "\n");
            Vertex v;
            for (int i = 0; i < numberOfVertex; i++) {
                v = vertices.get(i);
                myWrite.write(v.getX() + " " + v.getY() + "\n");
            }
            for (int start = 0; start < numberOfVertex; start++) {
                for (int end = 0; end < numberOfVertex; end++) {
                    myWrite.write(graph[start][end] + " ");
                }
                myWrite.write("\n");
            }
            myWrite.close();
        } catch (Exception e) {
        }
    }

    void createGraphList(File openFile) {
        try {
            Scanner sc = new Scanner(openFile);
            vertices.clear();
            edges.clear();
            numberOfVertex = sc.nextInt();
            int x, y;
            int countEdge = sc.nextInt();
            for (int i = 0; i < numberOfVertex; i++) {
                x = sc.nextInt();
                y = sc.nextInt();
                vertices.add(new Vertex(i, x, y));
            }
            for (int i = 0; i < numberOfVertex; i++) {
                for (int j = 0; j < numberOfVertex; j++) {
                    graph[i][j] = 0;
                }
            }
            int start, end, weight;
            for (int i = 0; i < countEdge; i++) {
                start = sc.nextInt();
                end = sc.nextInt();
                weight = sc.nextInt();
                edges.add(new Edge(vertices.get(start), vertices.get(end), weight));
                graph[start][end] = graph[end][start] = weight;

            }
            repaint();

        } catch (Exception e) {
        }
    }

    void createGraphMatrix(File openFile) {
        try {
            Scanner sc = new Scanner(openFile);
            vertices.clear();
            edges.clear();
            numberOfVertex = sc.nextInt();
            int x, y;
            int countEdge = sc.nextInt();
            for (int i = 0; i < numberOfVertex; i++) {
                x = sc.nextInt();
                y = sc.nextInt();
                vertices.add(new Vertex(i, x, y));
            }
            for (int i = 0; i < numberOfVertex; i++) {
                for (int j = 0; j < numberOfVertex; j++) {
                    graph[i][j] = sc.nextInt();
                    if (i < j && graph[i][j] > 0) {
                        edges.add(new Edge(vertices.get(i), vertices.get(j), graph[i][j]));

                    }
                }
            }
            repaint();
        } catch (Exception e) {
        }

    }

    void clear() {
        for (int i = 0; i < numberOfVertex; i++) {
            for (int j = 0; j < numberOfVertex; j++) {
                graph[i][j] = 0;
            }
        }
        vertices.clear();
        edges.clear();
        numberOfVertex = 0;
        repaint();
    }
}
