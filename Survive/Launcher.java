class Launcher {
    public static void main(String[]args){
        MainFrame mainFrame = new MainFrame();
        PanelManager panelManager = new PanelManager(mainFrame);

        //Create the intial panels (Start and Instructions)
        StartPanel startPanel = new StartPanel(mainFrame, panelManager);
        InstructionsPanel instructionsPanel = new InstructionsPanel(mainFrame, panelManager);
        mainFrame.setPanel(startPanel);
    }
}
