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


/**
 *
 * @author rajib_000
 */
public class Fetch_Url extends Thread
{
    String s;
    Fetch_Url(String str)
    {
        s=str;
        //start();
        
    }
    public void run()
    {
        String u = s;
        //System.out.print("Thread name : "+Thread.currentThread().getName());
        System.out.printf("Fetching-> %s\n", u);
        CmdOutPut.DownloadStatus.setText("Fetching-> "+u);
        
        /*String R;
        R = Main.cmd.fetchLink.getText().toString();                                                                            ################################ CMD
        R = R + "\n" + "Fetching -> "+u;
        Main.cmd.fetchLink.setText(R);*/
        
        try {
            Document doc = Jsoup.connect(u).get();
            Elements links = doc.select("a[href]");
            Elements media = doc.select("[src]");
            Elements imports = doc.select("link[href]");
            for (Element link : links) {
                String str = link.attr("abs:href");
                //System.out.println("starin loop");
                if (Main.mp.get(str) == null) {
                    //System.out.println("vitore");
                    Main.mp.put(str, new Boolean(false));
                    URL urlq = new URL(str);
                    //System.out.println("url: "+ str+" mother: "+ Main.mother.getHost()+" host: "+ urlq.getHost());
                    if (urlq.getHost().equals(Main.mother.getHost())) 
                    //if(urlq.toString().startsWith(Main.Main_Link))
                    {
                        Main.link_counter++;
                        System.out.print("Number of Link: " + Main.link_counter);
                        CmdOutPut.DownloadStatus.setText("Number of Link: " + Main.link_counter);
                        try{
                            if(Main.Q.size()>10000) sleep(5000);
                        }catch(InterruptedException eee) {
                            System.out.println(eee.getMessage());
                        }
                        
                        Main.Q.add(str);
                        System.out.println("  pusinhg " + str);
                        CmdOutPut.DownloadStatus.setText("  pusinhg "+str);
                        /*R = Main.cmd.pushQueue.getText().toString();                                                      ########################################### CMD
                        R = R + "\n" + "Pushing-> "+str;
                        Main.cmd.pushQueue.setText(R);*/
                    }

                }
            }
            //System.out.println("Queue Size = " + Main.Q.size()+" Thread Name = "+Thread.currentThread().getName());
        } catch (Exception i) {
            System.out.println(i.getMessage());
            System.out.println("This Link Is not Accessible: Server Down or Off");
            CmdOutPut.DownloadStatus.setText("This Link Is not Accessible: Server Down or Off");
        }
    }

}
