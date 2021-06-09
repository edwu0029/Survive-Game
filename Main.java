import java.io.File;

class Main {
    
    public static void main(String[]args){
        String path = "";
        try{
            File file = new File("Main.java");
            String absolutePath = file.getAbsolutePath();
            path = absolutePath.substring(0, absolutePath.length()-"Main.java".length());
        }catch(Exception e){
            System.out.println("Directory path not found");
        }
        
        MainFrame mainFrame = new MainFrame();
        
        PanelManager panelManager = new PanelManager(mainFrame, path);
        StartPanel startPanel = new StartPanel(mainFrame, panelManager, path);
        
        panelManager.addPanel(startPanel);
        panelManager.setActivePanel("Start Panel");
    }
    
}
