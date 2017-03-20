package WebCrawler;


import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;


/**
 *
 * @author rajib_000
 */
public class Match_Frame extends JFrame 
{
    private JLabel tr, lc;
    private JButton matchedLines, unmatchedLines, result, ourResult;
    private JLabel back_image;
    private ImageIcon img = new ImageIcon("images/backGround.jpg");
    
    Match_Frame()
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
        
        Initialize_MatchFrame();
        
        matchedLines.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                try {
                    Runtime.getRuntime().exec("notepad searchResult/Match.txt");
                } catch (IOException ex) {
                    System.out.println("File Not Found Match file in Matche_Frame class");
                }
            }
        });
        
        result.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                try {
                    Runtime.getRuntime().exec("notepad searchResult/result.txt");
                } catch (IOException ex) {
                    System.out.println("File Not Found result file in Matche_Frame class");
                }
            }
        });
        
        unmatchedLines.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                try {
                    Runtime.getRuntime().exec("notepad searchResult/Doesn't_Match.txt");
                } catch (IOException ex) {
                    System.out.println("File Not Found result file in Matche_Frame class");
                }
            }
        });
        
        ourResult.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                try {
                    Runtime.getRuntime().exec("notepad searchResult/our_result.txt");
                } catch (IOException ex) {
                    System.out.println("File Not Found result file in Matche_Frame class");
                }
            }
        });
        
        
        
    }

    private void Initialize_MatchFrame() 
    {
        matchedLines = new JButton("Matched Lines");
        matchedLines.setBounds(350, 350, 150, 100);
        add(matchedLines);
        
        unmatchedLines = new JButton("Unmatched Lines");
        unmatchedLines.setBounds(550, 350, 150, 100);
        add(unmatchedLines);
        
        result = new JButton("Main Result");
        result.setBounds(750, 350, 150, 100);
        add(result);
        
        ourResult = new JButton("Show Our Result");
        ourResult.setBounds(690, 100, 150, 100);
        add(ourResult);
        
        tr = new JLabel("Result From Our Algorithm: ");
        lc = new JLabel("Result From Lucene: ");
        tr.setBounds(300, 125, 300, 30);
        tr.setFont(new Font("ubuntu", 0, 20));
        tr.setForeground(Color.WHITE);
        add(tr);
        lc.setBounds(300, 300, 200, 30);
        lc.setForeground(Color.WHITE);
        lc.setFont(new Font("ubuntu", 0, 20));
        add(lc);
        
        
        
        
        
    }
    
            
    
    
}
