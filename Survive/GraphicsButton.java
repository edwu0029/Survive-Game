import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

/**
 * [GraphicsButton.java]
 * A class that represents a button using java graphics rather than java's built-in GUI.
 * @author Edward Wu
 * @version 1.0, June 15, 2021
 */
class GraphicsButton implements Drawable{
    /*-----Properties of this GraphicsButton-----*/ 
    //Coordinates of the top-left corner of the button(Relative to top-left corner of JFrame)
    private int x;
    private int y;

    //Dimensions
    private int width;
    private int height;

    //Color of button
    private Color color;

    //Text
    private String text;
    private Font font;
    private FontRenderContext fontRenderContext;
    private Color textColor;

    /**
     * GraphicsButton
     * A constructor that creates a new GraphicsButton with specified coordinates and dimensions.
     * @param x The x-coordinate of the top-left corner this GraphicsButton. This is relative the top-left corner of the JFrame.
     * @param y The y-coordinate of the top-left corner this GraphicsButton. This is relative the top-left corner of the JFrame.
     * @param width The width of this GraphicsButton.
     * @param height The height of this GraphicsButton.
     */
    GraphicsButton(int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.text = "";

        //Default font is Arial, 12
        this.font = new Font("Arial", Font.PLAIN, 12);
        this.fontRenderContext = new FontRenderContext(new AffineTransform(), true, true);

        //Default color is white
        this.color = new Color(255, 255, 255);

        //Default text color is black
        this.textColor = new Color(0, 0, 0);
    }
    /**
     * setText
     * Sets the text of inside of this GraphicsButton to some new text.
     * @param newText The new text that is to be set to this GraphicsButton.
     */
    public void setText(String newText){
        text = newText;
    }
    /**
     * setColor
     * Sets the color of this GraphicsButton to some new color.
     * @param newColor The new color of this GraphicsButton.
     */
    public void setColor(Color newColor){
        color = newColor;
    }
    /**
     * setTextColor
     * Sets the color of this GraphicsButton's text to some new color.
     * @param newColor The new color of this GraphicsButton's text.
     */
    public void setTextColor(Color newColor){
        textColor = newColor;
    }
    /**
     * setFont
     * Sets the font of this GraphicsButton's text to some new font
     * @param fontFamily The new font family of the text.
     * @param fontSize The new font size of the text.
     */
    public void setFont(String fontFamily, int fontSize){
        try{ //Try to create a new font with the specified properties
            font = new Font(fontFamily, Font.PLAIN, fontSize);
        }catch(Exception e){ //Invalid font family or font size
            System.out.println("Invalid font settings");
        }
    }
    /**
     * isInside
     * Determines whether a point is inside this GraphicsButton.
     * @param pointX The x-coordinate of the point. This is relative the top-left corner of the JFrame.
     * @param pointY The y-coordinater of the point. This is relative the top-left corner of the JFrame.
     * @return true if the button has been pressed
     */
    public boolean isInside(int pointX, int pointY){
        if(x<=pointX&&pointX<=x+width&&y<=pointY&&pointY<=y+height){
            return true;
        }else{
            return false;
        }
    }

    //From Drawable Interface
    /**
     * draw
     * A method from the Drawable interface that draws this GraphicsButton to be seen on screen.
     * @param g The graphics object in which this GraphicsButton will be drawn on.
     */
    public void draw(Graphics g){
        int widthOfText;
        int heightOfText;

        //Set draw overall button
        g.setColor(color);
        g.fillRect(x, y, width, height);

        //Get dimensions of text
        widthOfText = (int)(font.getStringBounds(text, fontRenderContext).getWidth());
        heightOfText = (int)(font.getStringBounds(text, fontRenderContext).getHeight());

        //Draw text as centered as possible in the button
        g.setColor(textColor);
        g.setFont(font);
        
        g.drawString(text, x+((width-widthOfText)/2), y+((height+heightOfText)/2));
        
    }
}
