class Launcher {
    
    public static void main(String[]args){
        MainFrame mainFrame = new MainFrame();
        StartPanel startPanel = new StartPanel(mainFrame);
        mainFrame.setPanel(startPanel);
        //Level level = new Level(mainFrame);
    }
}
