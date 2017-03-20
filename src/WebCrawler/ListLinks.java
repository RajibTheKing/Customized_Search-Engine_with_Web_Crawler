package WebCrawler;


import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.Map;
import javax.swing.JOptionPane;

/**
 *
 * @author rajib_000
 */
public class ListLinks extends Thread {

    private static String u;
    private static boolean m_bListLinkThreadActive;

    /**
     *
     * @throws IOException
     */
    public ListLinks() throws IOException {
        m_bListLinkThreadActive = true;
        start();
    }

    @Override
    public void run() {
        while (m_bListLinkThreadActive) 
        {
            if(Main.Q.isEmpty())
            {
                try {
                    Thread.currentThread().sleep(100);
                    continue;
                } catch (InterruptedException ex) {
                    Logger.getLogger(ListLinks.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            if (Main.bfs == false) 
            {
                break;
            }
            String str = Main.Q.poll();

            if (Main.mp.get(str).equals(new Boolean(false))) 
            {
                try {
                    Main.mp.put(str, new Boolean(true));
                    URL url = new URL(str);
                    URLConnection conn = url.openConnection();
                    String type = conn.getContentType();
                    int i;
                    boolean ok = false;
                    if (type.equalsIgnoreCase("application/pdf")) {
                        ok = true;
                    }
                    
                    if(type.equalsIgnoreCase("image"))
                    {
                        ok = true;
                    }
                    
                    if(str.contains("png") || str.contains("jpg"))
                    {
                        ok = true;
                    }

                    if (ok) {
                        //Updating CMD with Next Link..
                        
                        String R = Main.cmd.tfcompletedDownload.getText().toString();                                                                          // ######################## CMD
                        R = R + "\n"+str;
                        Main.cmd.tfcompletedDownload.setText(R);
                        /*
                        
                        //Skipping Downloads
                        
                        Main.pdf_links.add(str);
                        

                        System.out.print("Found a pdf link:  ");
                        String ss = "";
                        for (int j = str.length() - 1; j >= 0; j--) {
                            if (str.charAt(j) == '/') {
                                break;
                            }
                            ss += String.valueOf(str.charAt(j));

                        }
                        String fileName = "";
                        for (int j = ss.length() - 1; j >= 0; j--) {
                            fileName += String.valueOf(ss.charAt(j));
                        }

                        System.out.println("PDF link, " + Main.pdf_links.size() + " = " + str);

                        Main.pdf_with_Link.put(fileName, str);
                        Main.counter++;
                        String strLine = fileName +" LINK : "+ str;
                         LuceneDemo.append("searchResult/Links.txt", strLine);
                        new FileDownloadTest("downloads/" + fileName, str);
                      */
                    } else {
                        Main.Crawl_Pool.execute(new Fetch_Url(str));
                    }

                } catch (Exception ex) {
                    System.out.println("Link is not valid");
                }
            }
        }
        
        Main.Download_Pool.shutdown();
        while (Main.Download_Pool.isTerminated() == false) {
            System.out.println("in download pool");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                System.out.println(ListLinks.class.getName());
            }
        }
        System.out.println("All PDF are saved in the download folder");
        Main.Crawl_Pool.shutdown();
        parsing_and_deleting();
        JOptionPane.showMessageDialog(null, "COMPLETED DOWNLOADING PDF FILES");

  

    }
    
    
    /**
     *
     */
    public void parsing_and_deleting()
    {
         ListFilesUtil LF = new ListFilesUtil();
        LF.listFiles(Main.directoryWindows);
        //Now I am starting the parsing process....
        PDFTextParser pdfTextParserObj = new PDFTextParser();
        String pdfToText;
        for (int i = 0; i < Main.downloaded_files.size(); i++) {
            String a = Main.downloaded_files.elementAt(i);
            Main.txt_with_Link.put(a + ".txt", Main.pdf_with_Link.get(a));

            pdfToText = "";
            try {
                pdfToText = pdfTextParserObj.pdftoText("downloads/" + a);
                if (pdfToText == null) {
                    System.out.println("PDF to Text Conversion failed.");
                } else {
                    //System.out.println("\nThe text parsed from the PDF Document....\n" + pdfToText);
                    String s = "pdfToText/" + a;
                    //s += String.valueOf(++Main.counter);
                    s += ".txt";
                    pdfTextParserObj.writeTexttoFile(pdfToText, s);

                }

            } catch (IOException ex) {
                System.out.println(ListLinks.class.getName());
            }



        }
        for (int i = 0; i < Main.downloaded_files.size(); i++) {
            String a = Main.downloaded_files.elementAt(i);
            new DeleteFile("downloads/" + a);

        }

        printMap(Main.txt_with_Link);
        
    }

    /**
     *
     * @param mp
     */
    public static void printMap(Map mp) 
    {
        Iterator it = mp.entrySet().iterator();
        while (it.hasNext()) 
        {
            Map.Entry pairs = (Map.Entry) it.next();
            System.out.println(pairs.getKey() + " = " + pairs.getValue());
            
            it.remove(); // avoids a ConcurrentModificationException
        }
    }
}
