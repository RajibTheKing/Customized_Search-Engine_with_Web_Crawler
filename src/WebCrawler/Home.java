package WebCrawler;


import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;


/**
 *
 * @author rajib_000
 */
public class Home extends JFrame
{
    private JLabel back_image;
    private ImageIcon img = new ImageIcon("images/homebackGround.jpg");
    
    Home()
    {
        super("Customized Search Engine With Web Crawler");
        setSize(1000,600);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        back_image = new JLabel(img);
        setContentPane(back_image);
        setResizable(false);
        setVisible(true);
        setLocationRelativeTo(null);
        setLayout(null);
        setJMenuBar(Main.menubar);
        add(Main.home);add(Main.match);add(Main.getLinks);add(Main.userinput);
        add(Main.exit);add(Main.index);
        add(Main.timeLabel);
        new ShowTime();
    }
   
}
