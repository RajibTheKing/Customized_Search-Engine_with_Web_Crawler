package WebCrawler;
import java.io.File;
import java.util.Vector;

/**
 *
 * @author Rajib Chandra Das
 */
public class ListFilesUtil
{
//    File[] fList = null;
    Vector<String>FV=null;    
    
    public ListFilesUtil()
    {
        
        //listFilesUtil.listFiles(directoryWindows);

    }

    public void listFilesAndFolders(String directoryName)
    {

        File directory = new File(directoryName);
        File[] fList = directory.listFiles();

        //get all the files from a directory
        FV= new Vector<String>();

        for (File file : fList){
            System.out.println(file.getName());
            FV.add("pdfToText/"+file.getName());
        }
    }

    public Vector<String> getFileList()
    {
       return FV;
    }
   
    public void listFiles(String directoryName)
    {

        File directory = new File(directoryName);

        //get all the files from a directory
        File[] fList = directory.listFiles();
        
        try{
        for (File file : fList)
        {
            if (file.isFile())
            {
                String a=file.getName();
                Main.downloaded_files.add(a);
            }
        }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    
    public static String get_user_file(String directoryName)
    {
        File directory = new File(directoryName);
        File[] fList = directory.listFiles();
        if(fList.length==0)
            return "";
        
        return fList[0].toString();
    }

    public void listFolders(String directoryName){

        File directory = new File(directoryName);

        //get all the files from a directory
        File[] fList = directory.listFiles();

        for (File file : fList){
            if (file.isDirectory()){
                System.out.println(file.getName());
            }
        }
    }
    
    public void listFilesAndFilesSubDirectories(String directoryName){

        File directory = new File(directoryName);

        //get all the files from a directory
        File[] fList = directory.listFiles();

        for (File file : fList){
            if (file.isFile()){
                System.out.println(file.getAbsolutePath());
            } else if (file.isDirectory()){
                listFilesAndFilesSubDirectories(file.getAbsolutePath());
            }
        }
    }

   
}