import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

class GraphicsButton implements Drawable{
    
    private PanelManager panelManager;

    private int x;
    private int y;
    private int width;
    private int height;
    private String text;
    private Font font;
    private FontRenderContext fontRenderContext;
    private Color color;
    private Color textColor;

    private String nextPanel;

    GraphicsButton(PanelManager panelManager, int x, int y, int width, int height, String nextPanel){
        this.panelManager = panelManager;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.nextPanel = nextPanel;
        this.text = "";
        this.font = new Font("Arial", Font.PLAIN, 12);
        this.fontRenderContext = new FontRenderContext(new AffineTransform(), true, true);
        this.color = new Color(255, 255, 255); //Default colour is white
    }

    public void setText(String newText){
        text = newText;
        int widthOfText = (int)(font.getStringBounds(text, fontRenderContext).getWidth());
        int heightOfText = (int)(font.getStringBounds(text, fontRenderContext).getHeight());
        System.out.println("Width: "+widthOfText);
        System.out.println("Height: "+heightOfText);

        System.out.println(y+((height-heightOfText)/2));
    }
    public void setColor(Color c){
        color = c;
    }
    public void setTextColor(Color newColor){
        textColor = newColor;
    }

    public void setFont(String fontFamily, int fontSize){
        try{
            font = new Font(fontFamily, Font.PLAIN, fontSize);
        }catch(Exception e){
            System.out.println("Invalid font settings");
        }
    }

    public void setNextPanel(String newNextPanel){
        nextPanel = newNextPanel;
    }
    public boolean isPressed(int mouseX, int mouseY){
        if(x<=mouseX&&mouseX<=x+width&&y<=mouseY&&mouseY<=y+height){
            return true;
        }else{
            return false;
        }
    }
    public void pressed(){
        panelManager.setActivePanel(nextPanel);
    }

    //From Drawable Interface
    public void draw(Graphics g){
        g.setColor(color);
        g.fillRect(x, y, width, height);
        // g.drawRect(x, y, width, height);

        int widthOfText = (int)(font.getStringBounds(text, fontRenderContext).getWidth());
        int heightOfText = (int)(font.getStringBounds(text, fontRenderContext).getHeight());

        g.setColor(textColor);
        g.setFont(font);
        g.drawString(text, x+((width-widthOfText)/2), y+((height+heightOfText)/2));

    }
}
