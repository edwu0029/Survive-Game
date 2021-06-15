import javax.swing.JPanel;
import java.io.File;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
/**
 * [StartPanel.java]
 * A class that represents the start panel for this game.
 * @author Edward Wu
 * @version 1.0, June 15, 2021
 */
class StartPanel extends JPanel{
    /*-----References to other objects-----*/
    private MainFrame mainFrame;
    private PanelManager panelManager;

    /*-----Variables for this start panel-----*/
    private String path;
    private GraphicsButton startButton;
    private GraphicsButton instructionsButton;
    private GraphicsButton quitButton;

    //Images
    private BufferedImage title;

    /**
     * StartPanel
     * A constructor that consutructs a StartPanel object and displays it to the main frame.
     * @param mainFrame The main frame that this start panel will be displayed on. 
     * @param panelManager The panel manager for this start panel.
     */
    StartPanel(MainFrame mainFrame, PanelManager panelManager){
        this.mainFrame = mainFrame;
        this.panelManager = panelManager;
        panelManager.setStartPanel(this);

        loadPath();
        loadImages();
        
        //Set up Start Button
        this.startButton = new GraphicsButton(290, 400, 500, 100);
        this.startButton.setColor(new Color(0,128,0));
        this.startButton.setText("Start");
        this.startButton.setTextColor(new Color(255, 255, 255));
        this.startButton.setFont("Arial", 50);

        //Set up instructions button
        this.instructionsButton = new GraphicsButton(290, 550, 500, 100);
        this.instructionsButton.setColor(new Color(128,128,128));
        this.instructionsButton.setText("Instructions");
        this.instructionsButton.setTextColor(new Color(255, 255, 255));
        this.instructionsButton.setFont("Arial", 50);

        //Set up quit button
        this.quitButton = new GraphicsButton(290, 700, 500, 100);
        this.quitButton.setColor(new Color(255, 0, 0));
        this.quitButton.setText("Quit");
        this.quitButton.setTextColor(new Color(255, 255, 255));
        this.quitButton.setFont("Arial", 50);

        StartMouseListener mouseListener = new StartMouseListener(mainFrame, panelManager, this);
        this.addMouseListener(mouseListener);

        this.setFocusable(true);
        this.requestFocusInWindow();

        //Start of game loop
        Thread t = new Thread(new Runnable() { public void run() { animate(); }}); //start the gameLoop 
        t.start();
    }
    
    /**
     * animate
     * A method that uses java threads to run the game loop for this start panel.
     */
    public void animate(){
        while(true){

            //delay
            try{
                Thread.sleep(60);
            }catch(Exception exc){
              System.out.println("Thread Error");
            }
            
            //Repaint request
            this.repaint();
          }   
    }
    /**
     * loadPath
     * Finds the value of the path of this game directory and stores it in the private String path.
     */
    public void loadPath(){
        try{
            String absolutePath;
            File file = new File("StartPanel.java");

            //Find absolute path to THIS file(StartPanel.java)
            absolutePath = file.getAbsolutePath();
            //Cut out StartPanel.java to get directory path
            this.path = absolutePath.substring(0, absolutePath.length()-"StartPanel.java".length());
        }catch(Exception e){
            System.out.println("Directory path not found");
        }
    }
    /**
     * loadImages
     * A method that loads the necessary images to draw this start panel.
     */
    public void loadImages(){
        try{
            File titleFile = new File(path+"Sprites\\Panels\\startpanel_title.png");
            title = ImageIO.read(titleFile);
        }catch(Exception e){
            System.out.println("Error loading images for start panel.");
        }
    }
    /**
     * getStartButton
     * Returns a reference to this start panel's start button.
     * @return A reference to this start panel's start button.
     */
    public GraphicsButton getStartButton(){
        return startButton;
    }
    /**
     * getInstructionsButton
     * Returns a reference to this start panel's instruction button.
     * @return A reference to this start panel's instruction button.
     */
    public GraphicsButton getInstructionsButton(){
        return instructionsButton;
    }
    /**
     * getQuitButton
     * Returns a reference to this start panel's quit button.
     * @return A reference to this start panel's quit button.
     */
    public GraphicsButton getQuitButton(){
        return quitButton;
    }
    /**
     * painComponent
     * A method that draws the necessary element of this panel on screen.
     * @param g The graphics object in which the elements of this panel are to be drawn on.
     */
    public void paintComponent(Graphics g){
        //Display title
        g.drawImage(title, 269, 100, null);

        //Display Start button
        this.startButton.draw(g);

        //Display Instructions button
        this.instructionsButton.draw(g);

        //Display Quit button
        this.quitButton.draw(g);
    }

}
