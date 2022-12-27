package de.ulewu;

import javafx.animation.AnimationTimer;
import javafx.application.Application;

import javafx.scene.Group;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;




/**
 * JavaFX App
 */
public class App extends Application {

    private Image imgRock;
    private Image imgPaper;
    private Image imgScissor;
    private ArrayList<Image> imageList;
    private ArrayList<GameObject> list;

    private int windowSize= 1050;
    private int objectcount = 900;
    private int simSpeed = 25;
    private Timer tm;



    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Drawing Operations Test");
        Group root = new Group();
        Canvas canvas = new Canvas(windowSize, windowSize);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        //---------------        
        //drawShapes(gc);

        imageList = new ArrayList<Image>();
        loadImages();
 
        list =new ArrayList<GameObject>();//Creating arraylist  
        
        // create number of objects
        for (int i = 0; i < objectcount; i++) {
            list.add(createGameObject());    
        }



        //---------------------------

        root.getChildren().add(canvas);
        primaryStage.setScene(new Scene(root));

/* 
        final long startNanoTime = System.nanoTime();

        
        new AnimationTimer()    
        {    
            public void handle(long currentNanoTime)    
            {
                // Clear the canvas
                gc.clearRect(0, 0, windowSize,windowSize);    
                double t = (currentNanoTime - startNanoTime) / 1000000000.0;     
                double x = 232 + 128 * Math.cos(t);    
                double y = 232 + 128 * Math.sin(t);    
                for (GameObject gm : list) {                    
                    gm.move();
                    gc.drawImage(gm.curImage,gm.posX, gm.posY,20,20);
                }               
                gameLoopTick() ;    
            }
    
        }.start(); */

        startGameLoop(gc );

        primaryStage.show();

        
    }



    /* 
    @Override
    public void start(Stage stage) throws IOException {
        // set the title for the stage s
        stage.setTitle("RockPaperScissor");
        // create a group gp
        Group gp = new Group();
       
        imageList = new ArrayList<Image>();
        loadImages();
 
        list =new ArrayList<GameObject>();//Creating arraylist  
        
        // create number of objects
        for (int i = 0; i < objectcount; i++) {
            list.add(createGameObject());    
        }
                
        

        for (GameObject gm : list)
        gp.getChildren().addAll(gm.imv);
        
        
        // ends the app if window is closed - else the gameloop timer will continiu running
        stage.addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, event -> {
            // add your code here to handle the close event
            // use event.consume(); to prevent the application from closing
            System.exit(0);
        });
       
        // create scene
        // Scene sc = new Scene(gp, 600, 500, Color.RED);
        Scene sc = new Scene(gp, windowSize, windowSize);
        // create scene
        stage.setScene(sc);
        // display result
        stage.show();
        startGameLoop();
    } */

    private void startGameLoop(GraphicsContext gc) {
        tm = new Timer();

        tm.schedule(new TimerTask()
        {
            
            @Override
            public void run()
            {
                               // Clear the canvas
                               gc.clearRect(0, 0, windowSize,windowSize);    
                              // double t = (currentNanoTime - startNanoTime) / 1000000000.0;     
                              // double x = 232 + 128 * Math.cos(t);    
                              // double y = 232 + 128 * Math.sin(t);    
                               for (GameObject gm : list) {                    
                                   gm.move();
                                   gc.drawImage(gm.curImage,gm.posX, gm.posY,20,20);
                               }    
                gameLoopTick(gc);       
            }
        }, 2000,simSpeed );
    }

    private void gameLoopTick(GraphicsContext gc) {
        int indexMain = 0;
        int countR =0;
        int countP =0;
        int countS =0;
        for (GameObject gmMain : list){      
            switch (gmMain.getTyp()) {
                case "R":
                    countR++;
                    break;
                case "P":
                    countP++;
                    break;
                case "S":
                    countS++;
                    break;
                default:
                    break;
            }
            gmMain.move();
            int indexSub = 0;
            for (GameObject gmSub : list){            

                if (indexMain != indexSub) {
                    boolean washit = collisionCheck(gmMain, gmSub);
                    if (washit) {
                        //gmMain.setTyp(gmSub.getTyp());
                        rockpaperscissor(gmMain, gmSub);
                    }
                }

                indexSub++;
            }
            indexMain++;
        }
        
        // end if only one survived
        if (countR == objectcount || countP == objectcount || countS == objectcount) {
            tm.cancel();
            String winner = "";
            if (countR == objectcount)
                winner = "Rocks";
            if (countP == objectcount)
                winner = "Papers";
            if (countS == objectcount)
                winner = "Scissors";  
            
            
            //gc.setFont(new Font("Comic sans MS", 100));           
            
            gc.setFill(Color.BLUE);
            gc.setFont(javafx.scene.text.Font.font("Comic sans MS", 100));
            gc.fillText("Winner is " + winner, 50, 200,500);
        }

    }


    
    private void rockpaperscissor(GameObject first, GameObject second){
       
        boolean firstWins = false;

       if (first.getTyp() =="R") {
        if (second.getTyp() == "S") {
            // first wins
            firstWins = true;
        }
       }

       if (first.getTyp() =="P") {
        if (second.getTyp() == "R") {
            firstWins = true;
        }
       }
        
       if (first.getTyp() =="S") {
        if (second.getTyp() == "P") {
            firstWins = true;
        }
       }

       if (firstWins) {
        second.setTyp(first.getTyp());
       } else {
        first.setTyp(second.getTyp());
       }
        
    }

    private boolean collisionCheck(GameObject first, GameObject second) {

        if(first.imv.getBoundsInParent().intersects(second.imv.getBoundsInParent())){
            //System.out.println("Intersection detected");
            return true;
        }
        return false;
    }

private GameObject createGameObject() {
    
    GameObject gm1 = new GameObject(imageList,windowSize);
    //System.out.println(gm1.posX + " - " + gm1.posY);   

    //gm1.imv.setImage(imgPaper);
    return gm1;
}

private void loadImages() {
 
    
    String FimgRock    = "rock.jpg";
    String FimgPaper   = "paper.jpg";
    String FimgScissor = "scissor.jpg";      

    InputStream isR = getClass().getResourceAsStream(FimgRock);
     imgRock    = new Image(isR);   
     imageList.add(imgRock);
     InputStream isP = getClass().getResourceAsStream(FimgPaper);
     imgPaper   = new Image(isP);   
     imageList.add(imgPaper);
     InputStream isS = getClass().getResourceAsStream(FimgScissor);
     imgScissor = new Image(isS);   
     imageList.add(imgScissor);


}

    public void testImage(Group gp) throws FileNotFoundException {
                
        String fileName = "scissor.jpg";       
        
        InputStream is = getClass().getResourceAsStream(fileName);

        Image img1 = new Image(is);      
                
        

        ImageView imv = new ImageView(img1);
        imv.setX(10);
        imv.setY(10);
        imv.setFitHeight(20);
        imv.setFitWidth(20);
        imv.setPreserveRatio(true);

        gp.getChildren().addAll(imv);

    }

    // main method
    public static void main(String[] args) {
        launch(args);
    }


    

}