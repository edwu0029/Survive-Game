import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Toolkit;

class MainFrame extends JFrame{
    private int height;
    private int width;
    
    JFrame frame;

    MainFrame(){
        super("<Game Name>");
        this.frame = this;

        //Configure window size
        this.height = Toolkit.getDefaultToolkit().getScreenSize().height;
        this.width = Toolkit.getDefaultToolkit().getScreenSize().width;

        System.out.println("Screen height: "+height);
        System.out.println("Screen width: "+width);

        this.setSize(1080, 1080); 
        this.setLocationRelativeTo(null); //start the frame in the center of the screen
        this.setResizable (false);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setFocusable(false);
        this.setVisible(true);
    }
    
    public void clear(){
        frame.getContentPane().removeAll();
    }

    public int getScreenHeight(){
        return height;
    }

    public int getScreenWidth(){
        return width;
    }

    public void setPanel(JPanel newPanel){
        frame.add(newPanel);
        System.out.println("Panel loaded");
        frame.setVisible(true);
        newPanel.requestFocusInWindow();
    }
}
