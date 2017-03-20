package WebCrawler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

class GetLinks extends JFrame implements Runnable {

    private JLabel l, back_image;
    private JTextField urlfield;
    private JButton doButton, stopButton;
    private ImageIcon img = new ImageIcon("images/backGround.jpg");

    public GetLinks() 
    {
        super("Customized Search Engine With Web Crawler");
        setSize(1000, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        back_image = new JLabel(img);
        setContentPane(back_image);

        setResizable(false);
        setVisible(true);
        setLocationRelativeTo(null);
        setLayout(null);
        setJMenuBar(Main.menubar);
        add(Main.home);
        add(Main.getLinks);
        add(Main.userinput);
        add(Main.match);
        add(Main.exit);
        add(Main.index);
        add(Main.timeLabel);
        new ShowTime();

        Initialize_GetLinks();

    }

    private void Initialize_GetLinks() {
        l = new JLabel("Enter a web Link: ");
        l.setBounds(400, 200, 400, 30);
        add(l);

        urlfield = new JTextField();
        urlfield.setBounds(550, 200, 400, 30);
        add(urlfield);

        doButton = new JButton("Do It Now");
        doButton.setBounds(600, 250, 100, 40);
        add(doButton);

        stopButton = new JButton("Stop Working");
        stopButton.setBounds(750, 250, 150, 40);
        add(stopButton);

        doButton.addActionListener(new ActionListener() 
        {

            public void actionPerformed(ActionEvent e) 
            {
                System.out.println(urlfield.getText());
                try {

                    java.awt.EventQueue.invokeLater(new Runnable() {
                        public void run() {
                            Main.cmd = new CmdOutPut();
                            Main.cmd.setVisible(true);
                        }
                    });

                    Main.bfs = true;
                    Main.Main_Link = "http://" + urlfield.getText() + "/";
                    System.out.println("Searching : " + Main.Main_Link);
                    Main.mp.put(Main.Main_Link, new Boolean(false));
                    Main.Q.add(Main.Main_Link);
                    Main.mother = new URL(Main.Main_Link);
                    new ListLinks();

                } catch (Exception ex) {
                    System.out.println("Server Not found");
                }

            }

        });

        stopButton.addActionListener(new ActionListener() 
        {

            public void actionPerformed(ActionEvent e) 
            {
                Main.bfs = false;

            }
        });

    }

    public void run() 
    {

    }

}
