package WebCrawler;



/**
 *
 * @author rajib_000
 */
public class Link_Information 
{
    private static String pdf_fileName, txt_fileName, url;
    
    /**
     *
     * @param s
     */
    public static void SetPdf_FileName(String s)
    {
        //System.out.println("333333333333333333333333333333333333333333333333333333333333333333.......pdf"+s);
        pdf_fileName=s;
    }
    /**
     *
     * @param s
     */
    public static void SetTXT_FileName(String s)
    {
        //System.out.println("333333333333333333333333333333333333333333333333333333333333333333......txt"+s);
        txt_fileName=s;
    }
    
    /**
     *
     * @param s
     */
    public static void SetURL(String s)
            
    {
        //System.out.println("333333333333333333333333333333333333333333333333333333333333333333.......url"+s);
        url=s;
    }
    /**
     *
     * @return
     */
    public static String getPDF_name()
    {
        return pdf_fileName;
    }
    /**
     *
     * @return
     */
    public static String getTXT_name()
    {
        return txt_fileName;
    }
    
    /**
     *
     * @return
     */
    public static String getURL_name()
    {
        return url;
    }
    
}
