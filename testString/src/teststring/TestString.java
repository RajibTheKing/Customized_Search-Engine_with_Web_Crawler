/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package teststring;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;

/**
 *
 * @author ImtiazShakil
 */
public class TestString {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        
        Vector<String>FileList,url;
        String main_file="D:\\project_file_testing\\userinput\\hello.txt";
        
        
        FileList= new Vector<String>();
        url= new Vector<String>();
        
        FileList.add("D:\\project_file_testing\\filesToIndex\\1212.2713.txt");
        FileList.add("D:\\project_file_testing\\filesToIndex\\hello2.txt");
        FileList.add("D:\\project_file_testing\\filesToIndex\\ttt.txt");
        FileList.add("D:\\project_file_testing\\filesToIndex\\ttt_2.txt");
        FileList.add("D:\\project_file_testing\\filesToIndex\\ttt_2_2.txt");
        FileList.add("D:\\project_file_testing\\filesToIndex\\Rajib_test.txt");
        
        
        url.add("www.google.com");
        url.add("www.hudai.com");
        url.add("www.lightoj.com");
        url.add("www.Ipttorrents.com");
        url.add("www.MATHA NOSHTO.com");
        url.add("www.FILE369KB.com");
        
        PlagiarismChecker pck= new PlagiarismChecker(FileList, url, main_file);
        
        pck.checkPlagiarism();
        
        
    }
}
