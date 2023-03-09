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
public class Vertex {
    private int value ;
    private int x , y ;
    public static final int r = 16 ;
    private boolean isSelected = false  ;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
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

    public boolean isIsSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public Vertex(int value, int x, int y) {
        this.value = value;
        this.x = x;
        this.y = y;
    }
   public double distance(int x1, int y1 ,int x2 , int y2 ){
       return Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));
       
   }
   public boolean isInside(int mouseX, int mouseY){
       return distance(x, y, mouseX, mouseY)<= r ;
   }
}
