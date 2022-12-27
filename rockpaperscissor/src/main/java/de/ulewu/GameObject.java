package de.ulewu;


import java.util.*;

import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GameObject {

    private String typ;    
    public ImageView imv;
    public Double posX;
    public Double posY;

    private Image imgRock;
    private Image imgPaper;
    private Image imgScissor;
    private int windowSize;
    public Image curImage;
    

    public GameObject(ArrayList<Image> il, int _windowSize) {
    
        imgRock = il.get(0);
        imgPaper  = il.get(1);
        imgScissor = il.get(2);

        this.windowSize = _windowSize;

        this.imv = new ImageView();
        this.imv.setFitHeight(15);
        this.imv.setFitWidth(15);

        int min = 0;
        int max = windowSize;
        double rndX = Math.random()*(max-min+1)+min;
        double rndY = Math.random()*(max-min+1)+min;
        setPos(rndX, rndY);
               
        
        //int rndStartType = (int)Math.random()*(3-1+1)+1;

        int rndStartType =new Random().nextInt(4 - 1) + 1;
        
        if (rndStartType == 3) {
            System.out.println("->>>>>>>>>>>>>>>>>>>>>>>>>>>> Created: " + rndStartType);    
        }
        

        switch (rndStartType) {
            case 1:
                setTyp("R");                
                break;
            case 2:
                setTyp("P");
                break;
            case 3:
                setTyp("S");
                break;        
            default:
                break;
        }               
        
        System.out.println("Created: " + this.typ);

    }

    public void setPos(Double x, Double y) {
        this.posX = x;
        this.posY = y;
        this.imv.setX(x);
        this.imv.setY(y);
        
    }
    
    /**
     * 
     */
    public void move() {
        int rndDirection =new Random().nextInt(5 - 1) + 1;

        switch (rndDirection) {
            case 1:
                if(this.imv.getX() <= windowSize - 10)
                    this.imv.setX(this.imv.getX() + 5);
                break;
            case 2:
                if(this.imv.getY() <= windowSize - 10)
                    this.imv.setY(this.imv.getY() + 5);
                break;
            case 3:
                if(this.imv.getX() >= 0)
                    this.imv.setX(this.imv.getX() - 5);
                break;
            case 4:
                if(this.imv.getY() >= 0)
                    this.imv.setY(this.imv.getY() - 5);
                break;                
            default:
                break;
        }
        this.posX = this.imv.getX();
        this.posY = this.imv.getY();
    }

    public void setTyp(String typ) {
        switch (typ) {
            case "S":
                this.typ = "S";
                this.imv.setImage(imgScissor);
                this.curImage = imgScissor;
                break;
            case "R":
                this.typ = "R";
                this.imv.setImage(imgRock);
                this.curImage = imgRock;
                break;
            case "P":
                this.typ = "P";
                this.imv.setImage(imgPaper);
                this.curImage = imgPaper;
                break;
            default:
                break;
        }
    }
    public String getTyp(){
        return this.typ;
    }

}