package WebCrawler;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;

/**
 *
 * @author rajib_000
 */
public class FileDownloadTest extends Thread
{

    private static String fileName, fileUrl;

    FileDownloadTest(String fn, String f)
    {
        fileName = fn;
        fileUrl = f;
        start();

        
    }

    /**
     *
     * @throws MalformedURLException
     * @throws IOException
     */
    public static void saveFileFromUrlWithJavaIO() throws MalformedURLException, IOException {
        BufferedInputStream in = null;
        FileOutputStream fout = null;
        try {

            in = new BufferedInputStream(new URL(fileUrl).openStream());
            fout = new FileOutputStream(fileName);
            byte data[] = new byte[1024];
            int count;
            while ((count = in.read(data, 0, 1024)) != -1) {
                fout.write(data, 0, count);
            }
        } finally {
            if (in != null) {
                in.close();
            }
            if (fout != null) {
                fout.close();
            }
            System.out.println("Completed downloading "+fileName);
            String R;
            R = Main.cmd.tfcompletedDownload.getText().toString();                                                                          // ######################## CMD
            R = R + "\n" + "Completed Downloading -> "+fileName;
            Main.cmd.tfcompletedDownload.setText(R);
            
        }
    }

    // Using Commons IO library
    // Available at http://commons.apache.org/io/download_io.cgi
    /**
     *
     * @param fileName
     * @param fileUrl
     * @throws MalformedURLException
     * @throws IOException
     */
    public static void saveFileFromUrlWithCommonsIO(String fileName, String fileUrl) throws MalformedURLException, IOException {
        FileUtils.copyURLToFile(new URL(fileUrl), new File(fileName));
    }

    public void run()
    {
        try {
            System.out.println("Starting Download");
            saveFileFromUrlWithJavaIO();
            
        } catch (MalformedURLException ex){
            System.out.println("Sever is down.....sorry");

        } catch (IOException ex){
            System.out.println("Sever is down.....sorry");
            
        }

    }
}
