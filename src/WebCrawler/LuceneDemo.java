package WebCrawler;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

import net.sf.ehcache.*;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Hit;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;


/**
 *
 * @author rajib_000
 */
public class LuceneDemo {

    //public static final String FILES_TO_INDEX_DIRECTORY = "filesToIndex";
    /**
     *
     */
    public static final String FILES_TO_INDEX_DIRECTORY = "pdfToText";
    /**
     *
     */
    public static final String INDEX_DIRECTORY = "indexDirectory";
    /**
     *
     */
    public static final String FIELD_PATH = "path";
    /**
     *
     */
    public static final String FIELD_CONTENTS = "contents";
    //static String[] STOP_WORDS = {""};
     static String[] STOP_WORDS = {"`","~","@","!","#","$","%","^","&","*","(",")","-","_",
                                    "+","=","[","{","]","}",";",":","'","\"","|","\\",",",".","?","/"};
    static HashMap<String, Integer> map = new HashMap<String, Integer>();
    static ValueComparator bvc = new ValueComparator(map);
    static TreeMap<String, Integer> sorted_map = new TreeMap<String, Integer>(bvc);
    static long total_line = 0;
    Directory directory;
    IndexReader indexReader;
    IndexSearcher indexSearcher;
    StandardAnalyzer analyzer;
    QueryParser queryParser;
    File match, dontMatch,result;
    

    /**
     *
     */
    public LuceneDemo() {
        try {
            analyzer = new StandardAnalyzer(STOP_WORDS);
            createIndex();
            match = new File("searchResult/Match.txt");
            dontMatch = new File("searchResult/Doesn't_Match.txt");
            result = new File("searchResult/result.txt");
            directory = FSDirectory.getDirectory(INDEX_DIRECTORY);
            indexReader = IndexReader.open(directory);
            indexSearcher = new IndexSearcher(indexReader);
            queryParser = new QueryParser(FIELD_CONTENTS, analyzer);
        } catch (IOException ex) {
            Logger.getLogger(LuceneDemo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     */
    public void createIndex() {
        try {
            boolean recreateIndexIfExists = true;
            IndexWriter indexWriter = new IndexWriter(INDEX_DIRECTORY, analyzer, recreateIndexIfExists);
            File dir = new File(FILES_TO_INDEX_DIRECTORY);
            File[] files = dir.listFiles();
            for (File file : files) {
                Document document = new Document();

                String path = file.getCanonicalPath();
                document.add(new Field(FIELD_PATH, path, Field.Store.YES, Field.Index.UN_TOKENIZED));

                Reader reader = new FileReader(file);
                document.add(new Field(FIELD_CONTENTS, reader));

                indexWriter.addDocument(document);
            }
            indexWriter.optimize();
            indexWriter.close();
            System.out.println("finish createindex");
        } catch (CorruptIndexException ex) {
            Logger.getLogger(LuceneDemo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LockObtainFailedException ex) {
            Logger.getLogger(LuceneDemo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LuceneDemo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     *
     * @param studentFile
     */
    public void match_file(String studentFile) 
    {
        FileInputStream fis = null;
        try {
            
            fis = new FileInputStream(studentFile);
            DataInputStream in = new DataInputStream(fis);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            String str_match = "";
            String str_dontmatch = "";
            write("searchResult/Match.txt","");
            write("searchResult/Doesn't_Match.txt","");
            map.clear();
            sorted_map.clear();
            
            while ((strLine = br.readLine()) != null) {
                if(hasValue(strLine))
                {
                    
                     total_line++;
                     String search = strLine;
                     search = "\"" + search + "\"";
                    try {
                        int match = searchIndex(search);
                        
                        if(match==1)
                            append("searchResult/Match.txt",strLine);
                        else if (match==0) append("searchResult/Doesn't_Match.txt",strLine);
                        else total_line--;
                        
                        
                    } catch (Exception ex) {
                        System.out.println("problem with: :" + search);
                    }

                }
               }
            in.close();
            showOutput();
        } catch (IOException ex) {
            Logger.getLogger(LuceneDemo.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fis.close();
            } catch (IOException ex) {
                Logger.getLogger(LuceneDemo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    
    /**
     *
     * @param fileName
     * @param str
     */
    public static void append(String fileName,String str)
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
     * @param str
     */
    public static void map_link(String str)
    {
       // System.out.println("mapping for :"+str);
        int indx = str.indexOf(" LINK :");
        String fileName = "";
        String urlName = "";
        int i;
        int len = str.length();
        for(i=indx-1;i>=0;i--)
        {
            if(str.charAt(i)=='/') break;
            fileName = str.charAt(i)+fileName;
        }
        i = indx + 6;
        fileName = fileName+".txt";
        urlName = (String) str.subSequence(i, len);
        //System.out.println("filename: "+fileName+" urlName: "+ urlName);
        Main.Links.put(fileName, urlName);
        //System.out.println("in map fileName : "+ fileName+ " value is: "+ Main.Links.get(fileName));
        
    }
    private int searchIndex(String searchString){
        try {
            Query query = queryParser.parse(searchString);
            Hits hits = indexSearcher.search(query);
            Iterator<Hit> it = hits.iterator();
            boolean find = false;
            if(!(it.hasNext())) return 0;
            while (it.hasNext()) {
                Hit hit = it.next();
                Document document = hit.getDocument();
                String path = document.get(FIELD_PATH);
                Integer next = map.get(path);
                if (next == null) {
                    next = 0;
                }
                next++;
                map.put(path, next);
               
            }
            return 1;
        }  catch (ParseException ex) {
            System.out.println("searchString searchString");
            System.out.println(ex.getMessage());
            return 2;
        }catch (IOException ex) {
            System.out.println("searchString searchString");
            System.out.println(ex.getMessage());
            return 2;
        }

    }

    private void showOutput() {

        System.out.println("SHOWING OUTPUT");
        write("searchResult/result.txt","");
        sorted_map.putAll(map);
        for (Map.Entry<String, Integer> entry : sorted_map.entrySet()) {
            
            double percentage = ((double) entry.getValue() / (double) total_line) * 100;
            //System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue() +" total line: "+total_line+ " Percentage: " + percentage);
            String path = entry.getKey();
            int i, len;
            len = path.length();
            String fileName = "";
            for(i=len-1;i>=0;i--)
            {
                if(path.charAt(i)=='\\') break;
                fileName =  path.charAt(i) +fileName;
            }
            //System.out.println("Hell: "+fileName);
            
            String str = "\r\n\r\n\r\nFile: "+fileName + " \r\nLink: "+Main.Links.get(fileName)+" \r\nCopied "+percentage+"%";
            //System.out.println("str: "+str);
            append("searchResult/result.txt",str);
    
        }
        
    }
    private boolean isValid(String str)
    {
        return true;
    }
private static String reconstruct(String str)
    {
        int len = str.length();
        String new_str = "";
        String word = "";
        
        int i;
        //System.out.println("len:" +len);
        for(i=0;i<len;i++)
        {
            if(str.charAt(i)==' ')
            {
                if(valid(word))
                {
                    new_str = new_str +" "+ word;
                    
                }
                word = "";
            }
            else if(i==(len-1))
            {
                if(valid(word))
                {
                    new_str = new_str +" "+ word;
                    
                }
            }
            else
            {
                word = word + str.charAt(i);
                
            }
        }
        //System.out.println("return "+ new_str);
        return new_str;
    }

    private static boolean valid(String search) {
        
        //System.out.println("word: "+ search);
        String mid = search;
        int len = mid.length();
        //System.out.println("validiting :"+ mid+" len: "+len);
        int i;
        char a;
        
        for(i=0;i<len;i++)
        {
            //System.out.println(mid.charAt(i)+"i "+i);
           if(!(mid.charAt(i)>=32 && mid.charAt(i)<=126))
           {

               //System.out.println("REturing false i :"+i);
               //System.out.println("error: "+ mid.charAt(i));
               return false;
           }
        }
        //System.out.println("returning true");
        return true;
    }
    
    private String check(String str)
    {
        int len = str.length();
        int i;
        String new_str=""; 
        for(i=0;i<len;i++)
        {
            if(!Character.isLetter(str.charAt(i))&&!Character.isDigit(str.charAt(i))) new_str = new_str +" ";
            else new_str += str.charAt(i);
        }
        return new_str;
    }

    
    private boolean hasValue(String strLine) {
        int len = strLine.length();
        int i;
        for(i=0;i<len;i++)
        {
           // System.out.println(Character.isAlphabetic(strLine.charAt(i))+" and "+ Character.isDigit(strLine.charAt(i)));
            if(Character.isLetter(strLine.charAt(i)) || Character.isDigit(strLine.charAt(i)))
            {
               // System.out.println("Returning true");
                return true;
            }
        }
        return false;
    }

}
class ValueComparator implements Comparator<String> {

    Map<String, Integer> base;

    public ValueComparator(Map<String, Integer> base) {
        this.base = base;
    }

    // Note: this comparator imposes orderings that are inconsistent with equals.
    public int compare(String a, String b) {
        if (base.get(a) >= base.get(b)) {
            return -1;
        } else {
            return 1;
        } // returning 0 would merge keys
    }
}
