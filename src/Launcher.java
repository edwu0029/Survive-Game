/**
 * [Launcher.java]
 * A class that represents the main launcer for this game. In other words, run this file to start the game.
 * @author Edward Wu
 * @version 1.0, June 15, 2021
 */
class Launcher {
    //Main Function
    public static void main(String[]args){
        //Create MainFrame and PanelManager
        MainFrame mainFrame = new MainFrame();
        PanelManager panelManager = new PanelManager(mainFrame);

        //Create the intial panels (Start and Instructions)s
        StartPanel startPanel = new StartPanel(mainFrame, panelManager);
        InstructionsPanel instructionsPanel = new InstructionsPanel(mainFrame, panelManager);

        //Set Start panel as intially loaded panel
        mainFrame.setPanel(startPanel);
    }
}
