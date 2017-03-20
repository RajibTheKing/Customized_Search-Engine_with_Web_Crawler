package WebCrawler;

import java.io.File;

/**
 *
 * @author rajib_000
 */
public class DeleteFile
{
    private static int check=1;
    DeleteFile(String s)
    {
        deletefile(s);

    }
    private static void deletefile(String file)
    {
        try {
            File f1 = new File(file);
            boolean success = f1.delete();
            if (!success)
            {
                System.out.println("Deletion failed. "+file);
              
            } 
            else
            {
                
                System.out.println("File deleted. "+file);
            }
        }catch (Exception e)
        {
            System.out.println("I can not delete the file: File Access Denied..............................");
        }
    }
    
}