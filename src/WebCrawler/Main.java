package WebCrawler;



import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Timer;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.store.LockObtainFailedException;
import teststring.*;

/**
 *
 * @author rajib_000
 */
public class Main 
{
    
    /**
     *
     */
    public static CmdOutPut cmd;
    /**
     *
     */
    public static JButton match,
    /**
     *
     */
    home,
    /**
     *
     */
    getLinks,
    /**
     *
     */
    download,
    /**
     *
     */
    userinput,
    /**
     *
     */
    exit,
    /**
     *
     */
    index;
    /**
     *
     */
    public static JMenuBar menubar = new JMenuBar();
    /**
     *
     */
    public static JFrame frame,
    /**
     *
     */
    frame2;
    /**
     *
     */
    public static ButtonHandler handler = new ButtonHandler();
    final static JLabel timeLabel = new JLabel();
    final static DateFormat timeFormat = new SimpleDateFormat("E yyyy.MM.dd 'at' hh:mm:ss a");
    final static String directoryWindows = "downloads";
    /**
     *
     */
    public static HashMap mp = new HashMap();
    /**
     *
     */
    public static HashMap<String, String> pdf_with_Link = new HashMap<String, String>();
    /**
     *
     */
    public static HashMap<String, String> txt_with_Link = new HashMap<String, String>();
    /**
     *
     */
    public static HashMap<String, String> Links = new HashMap<String, String>();
    /**
     *
     */
    public static Queue<String> Q = new LinkedList<String>();
    /**
     *
     */
    public static URL mother = null;
    /**
     *
     */
    public static int counter,
    /**
     *
     */
    link_counter;
    /**
     *
     */
    public static Vector<String> pdf_links = new Vector<String>();
    /**
     *
     */
    public static Vector<String> downloaded_files = new Vector<String>();
    //private static String Main_Link="http://khairullah.eu5.org/";
    /**
     *
     */
    public static String Main_Link = "http://www.sust.edu/";
    //public static String Main_Link = "http://www.sust.edu/publications/";
    //public static String Main_Link = "http://www.arxiv.org//";
    //private static String Main_Link = "http://www.du.ac.bd/";
    /**
     *
     */
    public static ExecutorService Download_Pool,
    /**
     *
     */
    Crawl_Pool;
    /**
     *
     */
    public static boolean bfs=true;
    /**
     *
     */
    public static Link_Information linkInfo[] = new Link_Information[10000];
    /**
     *
     */
    public static LuceneDemo app;
    

    /**
     *
     * @param args
     * @throws MalformedURLException
     */
    public static void main(String[] args) throws MalformedURLException {
        
        Download_Pool = Executors.newFixedThreadPool(50);
        Crawl_Pool = Executors.newFixedThreadPool(50);
        initUI();
        initButton();
        counter = 0;
        link_counter = 0;
        frame = new Home();
//        frame = new Match_Frame();
        
    }

    /**
     *
     */
    public static void initUI() {
        JMenu file = new JMenu("File");
        JMenuItem fileNew = new JMenuItem("New");
        JMenuItem fileExit = new JMenuItem("Exit");
        fileExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        file.add(fileNew);
        file.add(fileExit);
        JMenu tools = new JMenu("Tools");
        JMenuItem toolsOption = new JMenuItem("Current Status");
        tools.add(toolsOption);
        toolsOption.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Rajib Chandra Das");
            }
        });
        JMenu help = new JMenu("Help");

        JMenuItem helpAbout = new JMenuItem("About");

        helpAbout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Customized Search Engine with Web Crawler\nCreated By: \nSafat Siddique \nImtiaz Shakil Siddique &\nRajib Chandra Das\nShahjalal University of Science & Technology\nSoftware Version: 6.0", "About", 1);
            }
        });
        help.add(helpAbout);
        menubar.add(file);
        menubar.add(tools);
        menubar.add(help);

    }

    private static void initButton() 
    {
        home = new JButton("Home");
        home.setBounds(30, 50, 200, 30);

        getLinks = new JButton("Start Crawling");
        getLinks.setBounds(30, 125, 200, 30);

        userinput = new JButton("User Input Pdf");
        userinput.setBounds(30, 200, 200, 30);
        
        index = new JButton("Index new data");
        index.setBounds(30, 330, 200, 30);

        match = new JButton("Match");
        match.setBounds(30, 400, 200, 30);
        
        

        exit = new JButton("Exit");
        exit.setBounds(30, 470, 200, 30);

        home.addActionListener(handler);
        getLinks.addActionListener(handler);
        userinput.addActionListener(handler);
        index.addActionListener(handler);
        match.addActionListener(handler);
        exit.addActionListener(handler);
        match.setVisible(false);

    }

    /**
     *
     */
    public static class ButtonHandler implements ActionListener {

        public void actionPerformed(ActionEvent e) {

            home.setBackground(null);
            getLinks.setBackground(null);
            match.setBackground(null);
            userinput.setBackground(null);
            exit.setBackground(null);
            index.setBackground(null);


            if (e.getSource() == home) 
            {
                home.setBackground(Color.red);
                frame.dispose();
                frame = new Home();
            }
            if (e.getSource() == getLinks) 
            {
                System.out.println("Start Crawl Button is clicked........");
                getLinks.setBackground(Color.red);
                Main.frame.dispose();
                Main.frame = new GetLinks();
                

            }
            if (e.getSource() == userinput) 
            {
                userinput.setBackground(Color.red);
                new SuperExplorer();
                
            }
            if(e.getSource()==index)
            {
                index.setBackground(Color.red);
                try {
                    app = new LuceneDemo();
//                    app.createIndex();
                     FileInputStream fis = null;
                
                    fis = new FileInputStream("searchResult/Links.txt");
                    DataInputStream in = new DataInputStream(fis);
                    BufferedReader br = new BufferedReader(new InputStreamReader(in));
                    String strLine = "";
                    while ((strLine = br.readLine()) != null) {
                        LuceneDemo.map_link(strLine);
                    }
                    JOptionPane.showMessageDialog(null, "INDEXING COMPLETED");
                    match.setVisible(true);
                    
                } catch (Exception ex) {
                    System.out.println("indexbutton indexbutton");
                } 
            }
            if(e.getSource()==match)
            {
                
                match.setBackground(Color.red);
                LuceneDemo.total_line = 0;
                
                ListFilesUtil LF = new ListFilesUtil();
                String student="";
                student = LF.get_user_file("userinput/");
                String s="";
                if(student.compareTo(s)==0)
                {
                    System.out.println("dont find any input");
                    JOptionPane.showMessageDialog(null, "Sorry, File is not found");
                }
                else
                {
                    System.out.println("Student :" +student);
                    try {
                        app.match_file(student);

                    } catch (Exception ex) {
                        System.out.println("matchbutton matchbutton");
                        System.out.println(ex.getMessage());
                        JOptionPane.showMessageDialog(null, "First Index New Data");
                    }
                }
                
                
//                /*
//                 * Shakil COdes
                
                try {
                    LF.listFilesAndFolders("pdfToText");

                    Vector<String>flList=LF.getFileList();
                    Vector<String>urlList=new Vector<String>();

                    for(int i=0;i<flList.size();i++) {
                        String tmp="";
                        for(int j=flList.get(i).length()-1;j>=0;--j) {
                            if(flList.get(i).charAt(j)=='/') break;
                            tmp=flList.get(i).charAt(j)+tmp;
                        }
                        urlList.add( Main.Links.get(tmp) );
                    }

                    PlagiarismChecker pck= new PlagiarismChecker(flList ,urlList, student);
                    pck.checkPlagiarism();
                }catch(Exception eee ) {
                    JOptionPane.showMessageDialog(null, "Heap Size too large. Please Give a smaller input file");
                    System.out.println("Heap Size Failed");
                }
                
                
                JOptionPane.showMessageDialog(null, "Result Set is Prepared");
                Main.frame.setVisible(false);
                Main.frame = new Match_Frame();
                
            }
            if (e.getSource() == exit) 
            {
                System.exit(0);
            }
        }
    }
}
