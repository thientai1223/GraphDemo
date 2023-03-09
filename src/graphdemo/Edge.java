/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphdemo;

/**
 *
 * @author Admin
 */
public class Edge implements Comparable<Edge> {
    private Vertex start ; 
    private Vertex end ;
    private int weight  ;
    private boolean isSelected = false;
    private int x , y ;

    public Edge(Vertex start, Vertex end, int weight) {
        this.start = start;
        this.end = end;
        this.weight = weight;
        this.x = (start.getX()+end.getX())/2;
        this.y =  (start.getY()+end.getY())/2;
    }

    public Vertex getStart() {
        return start;
    }

    public void setStart(Vertex start) {
        this.start = start;
    }

    public Vertex getEnd() {
        return end;
    }

    public void setEnd(Vertex end) {
        this.end = end;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public boolean isIsSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    public boolean isInside(int mouseX, int mouseY){
        int x1 = x-10;
        int y1 = y-10;
        int x2 = x+10;
        int y2 = y+10;
        return x1<= mouseX&& mouseX<=x2 && y1<=mouseY && mouseY <=y2 ;
    }
    public void calculateCenterLocation(){
        this.x = (start.getX()+end.getX())/2;
        this.y =  (start.getY()+end.getY())/2;
    }
     @Override
    public int compareTo(Edge o) {
        return this.getWeight() - o.getWeight();
    }

}
