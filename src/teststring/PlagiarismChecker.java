package teststring;


import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Vector;

class output {
    double normalMatching,characterMatching,weightedMatching;

    public output(double normalMatching, double characterMatching, double weightedMatching) {
        this.normalMatching = normalMatching;
        this.characterMatching = characterMatching;
        this.weightedMatching = weightedMatching;
    }
    
}
/**
 *
 * @author rajib_000
 */
public class PlagiarismChecker {
    
    private Vector<String>fileList;
    private Vector<String>url;
    private String main_file;
    private ArrayList<OutputData> result;
    private String write_directory;
    
    /**
     *
     * @param fileList
     * @param url
     * @param str
     */
    public PlagiarismChecker(Vector<String> fileList , Vector<String> url  ,String str) {
        this.fileList = fileList;
        this.url = url;
        main_file=str;
        
        result= new ArrayList<OutputData>();
        write_directory="searchResult/our_result.txt";
        write(write_directory,"");
    }
    
    
    /**
     *
     * @param fileName
     * @param str
     */
    public  void append(String fileName,String str)
    {
        try {
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(fileName, true)));
            out.println(str);
            out.close();
        } catch (IOException e) {   System.out.println(e.getMessage());
            //oh noes!
        }
    }
      
    /**
     *
     * @param fFileName
     * @param str
     */
    public void write(String fFileName,String str) {
        try {
            System.out.println("Writing to file named " + fFileName );
            
            Writer out = new OutputStreamWriter(new FileOutputStream(fFileName), "UTF8");
            try {
                out.write(str);
            } finally {
                out.close();
            }
        } catch (IOException e) {
            System.out.println("Writing Failed " + e.toString());
        }
    }
    
    
    
    
    
    /**
     *
     */
    public void checkPlagiarism() {
        
        StringFormatter SF= new StringFormatter(main_file);
        String Sentence[] = SF.read();
        String MatchingSentence[];
        
        
        StringAutomaton SA= new StringAutomaton(Sentence);
        SA.process();
        

        PlagiarismCalculator pg;
        
        for(int i=0;i<fileList.size();i++) {
            
            SF= new StringFormatter(fileList.get(i));
            MatchingSentence=SF.read();
            
            pg=SA.CompareWithOther(MatchingSentence);
            
//            result.add( new OutputData( pg.NormalMatching()*100.0 , pg.CharacterMatching()*100.0, pg.WeightedMatching() ) );
            

  
            append(write_directory, "FILE Directory : " +fileList.get(i)+"\r");
            append(write_directory, "URL: " +url.get(i)+"\r");
            append(write_directory, "Normal Matching Found " + pg.NormalMatching()*100.0 +"%"+"\r");
            append(write_directory,"Character Matching Found "+pg.CharacterMatching()*100.0 +"%"+"\r");
            append(write_directory,"Weighted Matching Found "+pg.WeightedMatching()*100.0+"%\r\n\r\n\r\n");
            
//            System.out.println("\n\nURL :"+url.get(i));
//            System.out.println("Normal Matching Found " + pg.NormalMatching()*100.0 +"%");
//            System.out.println("Character Matching Found "+pg.CharacterMatching()*100.0 +"%");
//            System.out.println("Weighted Matching Found "+pg.WeightedMatching()*100.0+"%\n\n\n");
        }
        return ;
    }
    
    
    
}
