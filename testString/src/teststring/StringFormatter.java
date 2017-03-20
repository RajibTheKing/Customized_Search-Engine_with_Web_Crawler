package teststring;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import sun.text.normalizer.Replaceable;


public final class StringFormatter {

  StringFormatter(String aFileName){
        fEncoding = "UTF8";
        fFileName = aFileName;
  }
  
 void write(String str)   {
    try{
         log("Writing to file named " + fFileName + ". Encoding: " + fEncoding);
        Writer out = new OutputStreamWriter(new FileOutputStream(fFileName), fEncoding);
        try {
          out.write(str);
        }
        finally {
          out.close();
        }
    }catch(IOException e) {
        log("Writing Failed "+e.toString());
    }
 }
  
String[] read()  {
    String found="",tmp;
    ArrayList<String> StringList= new ArrayList<String>();
    String str[];
    int cnt=0;  
    try {
            log("Reading from file."+fFileName);      
            Scanner scanner = new Scanner(new FileInputStream(fFileName), fEncoding);
            try {
              while (scanner.hasNextLine()){
                  tmp=scanner.nextLine();
                  tmp=tmp.replaceAll("[^A-Za-z.]","");
                  tmp=tmp.replaceAll("\\.", " ");
                  if(tmp.length()==0) continue;
                  
                  if(cnt<=60) {cnt++;found=found+" "+tmp;}
                  else {
                      
                      found=found+" "+tmp;
//                      found=found.replaceAll("[^A-Za-z.]", "");
//                      found=found.replaceAll("\\.", " ");

                      if(found=="") continue;
                      str=found.split(" ");
                      StringList.addAll( Arrays.asList(str) );
                      
                      cnt=0;
                      found="";
                  }
              }
            }
            finally{
              scanner.close();
            }
            log("Text Reading Complete\n");
      }catch(IOException e) {
          log("Reading Failed "+e.toString());
      }
      
      if(cnt!=0) 
      {
          found=found.replaceAll("[^A-Za-z.]", "");
          found=found.replaceAll("\\.", " ");

          if(found!="") {
              str=found.split(" ");
              StringList.addAll( Arrays.asList(str) );
          }
      }
      
      String[] text = new String[StringList.size()];
      String[] returnedText = StringList.toArray(text);

      return returnedText;
}

  
  // PRIVATE 
  private final String fFileName;
  private final String fEncoding;

  private void log(String aMessage){
    System.out.println(aMessage);
  }
}