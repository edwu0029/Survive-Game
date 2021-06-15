/**
 * [PanelManager.java]
 * A class that represents the panel manager for this game. Is responsible for loading and organizing the various
 * panels.
 * @author Edward Wu
 * @version 1.0, June 15, 2021
 */
class PanelManager {
    /*-----References to other objects-----*/
    private MainFrame mainFrame;

    /*-----Variables for this panel manager-----*/
    //For menus
    private StartPanel startPanel;
    private InstructionsPanel instructionsPanel;

    //For the actual game
    private GamePanel gamePanel;
    private InventoryPanel inventoryPanel;
    private CraftingPanel craftingPanel;
    private PlacingPanel placingPanel;
    private GameOverPanel gameOverPanel;
    private WinPanel winPanel;

    /**
     * PanelManager
     * A constructor that constructs a panel manager for a specified frame.
     * @param mainFrame The main frame that this panel manager is created for.
     */
    PanelManager(MainFrame mainFrame){
        this.mainFrame = mainFrame;
    }
    /**
     * setStartPanel
     * Sets the reference of this panel manager's StartPanel(JPanel).
     * @param newStartPanel The new StartPanel object.
     */
    public void setStartPanel(StartPanel newStartPanel){
        startPanel = newStartPanel;
    }
    /**
     * getStartPanel
     * Returns the reference to this panel manager's StartPanel(JPanel).
     * @return A reference to this panel manager's StartPanel(JPanel).
     */
    public StartPanel getStartPanel(){
        return startPanel;
    }
    /**
     * setInstructionsPanel
     * Sets the reference of this panel manager's InstructionsPanel(JPanel).
     * @param newInstructionsPanel The new InstructionsPanel object.
     */
    public void setInstructionsPanel(InstructionsPanel newInstructionsPanel){
        instructionsPanel = newInstructionsPanel;
    }
    /**
     * getInstructionsPanel
     * Returns the reference to this panel manager's InstructionsPanel(JPanel).
     * @return A reference to this panel manager's InstructionsPanel(JPanel).
     */
    public InstructionsPanel getInstructionsPanel(){
        return instructionsPanel;
    }
    /**
     * setGamePanel
     * Sets the reference of this panel manager's GamePanel(JPanel).
     * @param newGamePanel The new GamePanel object.
     */
    public void setGamePanel(GamePanel newGamePanel){
        gamePanel = newGamePanel;
    }
    /**
     * getGamePanel
     * Returns the reference to this panel manager's GamePanel(JPanel).
     * @return A reference to this panel manager's GamePanel(JPanel).
     */
    public GamePanel getGamePanel(){
        return gamePanel;
    }
    /**
     * setInventoryPanel
     * Sets the reference of this panel manager's InventoryPanel(JPanel).
     * @param newInventoryPanel The new InventoryPanel object.
     */
    public void setInventoryPanel(InventoryPanel newInventoryPanel){
        inventoryPanel = newInventoryPanel;
    }
    /**
     * getInventoryPanel
     * Returns the reference to this panel manager's InventoryPanel(JPanel).
     * @return A reference to this panel manager's InventoryPanel(JPanel).
     */
    public InventoryPanel getInventoryPanel(){
        return inventoryPanel;
    }
    /**
     * setCraftingPanel
     * Sets the reference of this panel manager's CraftingPanel(JPanel).
     * @param newCraftingPanel The new CraftingPanel object
     */
    public void setCraftingPanel(CraftingPanel newCraftingPanel){
        craftingPanel = newCraftingPanel;
    }
    /**
     * getCraftingPanel
     * Returns the reference to this panel manager's CraftingPanel(JPanel).
     * @return A reference to this panel manager's CraftingPanel(JPanel).
     */
    public CraftingPanel getCraftingPanel(){
        return craftingPanel;
    }
    /**
     * setPlacingPanel
     * Sets the reference of this panel manager's PlacingPanel(JPanel).
     * @param newPlacingPanel The new PlacingPanel object.
     */
    public void setPlacingPanel(PlacingPanel newPlacingPanel){
        placingPanel = newPlacingPanel;
    }
    /**
     * getPlacingPanel
     * Returns the reference to this panel manager's placingPanel(JPanel).
     * @return A reference to this panel manager's placingPanel(JPanel).
     */
    public PlacingPanel getPlacingPanel(){
        return placingPanel;
    }
    /**
     * setGameOverPanel
     * Sets the reference of this panel manager's GameOverPanel(JPanel).
     * @param newGameOverPanel The new GameOverPanel object.
     */
    public void setGameOverPanel(GameOverPanel newGameOverPanel){
        gameOverPanel = newGameOverPanel;
    }
    /**
     * getGameOverPanel
     * Returns the reference to this panel manager's gameOverPanel(JPanel).
     * @return A reference to this panel manager's gameOverPanel(JPanel).
     */
    public GameOverPanel getGameOverPanel(){
        return gameOverPanel;
    }
    /**
     * setWinPanel
     * Sets the reference of this panel manager's WinPanel(JPanel).
     * @param newWinPanel The new WinPanel object.
     */
    public void setWinPanel(WinPanel newWinPanel){
        winPanel = newWinPanel;
    }
    /**
     * getWinPanel
     * Returns the reference to this panel manager's winPanel(JPanel).
     * @return A reference to this panel manager's winPanel(JPanel).
     */
    public WinPanel getWinPanel(){
        return winPanel;
    }

    /**
     * setActivePanel
     * Sets a certian panel to be visible on the MainFrame of the game.
     * @param panelType The type of panel that is to be set as active.
     */
    public void setActivePanel(String panelType){
        //Clear all panels on the main frame
        mainFrame.clear();

        //Pause gamePanel if it exists
        if(gamePanel!=null){
            gamePanel.togglePause(true);
        }

        //Check for which panel to add to frame
        if(panelType.equals("StartPanel")){ //StartPanel
            mainFrame.setPanel(startPanel);
        }else if(panelType.equals("InstructionsPanel")){ //Instructions panel
            mainFrame.setPanel(instructionsPanel);
        }else if(panelType.equals("GamePanel")){ //GamePanel
            gamePanel.togglePause(false); //Unpause game panel since its being loaded as active
            mainFrame.setPanel(gamePanel);
        }else if(panelType.equals("InventoryPanel")){ //InventoryPanel
            mainFrame.setPanel(inventoryPanel);
        }else if(panelType.equals("CraftingPanel")){ //CraftingPanel
            mainFrame.setPanel(craftingPanel);
        }else if(panelType.equals("PlacingPanel")){ //PlacingPanel
            mainFrame.setPanel(placingPanel);
        }else if(panelType.equals("GameOverPanel")){ //GameOverPanel
            mainFrame.setPanel(gameOverPanel);
        }else if(panelType.equals("WinPanel")){ //WinPanel
            mainFrame.setPanel(winPanel);
        }
    }
}
