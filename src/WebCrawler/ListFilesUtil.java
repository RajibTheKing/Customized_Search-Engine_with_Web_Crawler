package WebCrawler;



import java.io.File;
import java.util.Vector;

/**
 *
 * @author rajib_000
 */
public class ListFilesUtil
{
//    File[] fList = null;
    Vector<String>FV=null;    

    /**
     *
     */
    public ListFilesUtil()
    {
        
        //listFilesUtil.listFiles(directoryWindows);

    }


    /**
     *
     * @param directoryName
     */
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

    /**
     *
     * @return
     */
    public Vector<String> getFileList()
   {
       return FV;
   }
   
    /**
     *
     * @param directoryName
     */
    public void listFiles(String directoryName)
    {

        File directory = new File(directoryName);

        //get all the files from a directory
        File[] fList = directory.listFiles();

        for (File file : fList)
        {
            if (file.isFile())
            {
                String a=file.getName();
                Main.downloaded_files.add(a);
            }
        }
    }
    /**
     *
     * @param directoryName
     * @return
     */
    public static String get_user_file(String directoryName)
    {
        File directory = new File(directoryName);
        File[] fList = directory.listFiles();
        if(fList.length==0)
            return "";
        
        return fList[0].toString();
    }

    
    /**
     *
     * @param directoryName
     */
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

    /**
     *
     * @param directoryName
     */
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