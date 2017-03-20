package WebCrawler;



import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.util.PDFTextStripper;

/**
 *
 * @author rajib_000
 */
public class PDFTextParser
{
    /**
     *
     */
    public static PrintWriter pw;
    PDFParser parser;
    String parsedText;
    PDFTextStripper pdfStripper;
    /**
     *
     */
    public static PDDocument pdDoc;
    /**
     *
     */
    public static COSDocument cosDoc;
    PDDocumentInformation pdDocInfo;

    // PDFTextParser Constructor
    /**
     *
     */
    public PDFTextParser()
    {
    }

    // Extract text from PDF Document
    String pdftoText(String fileName) throws IOException
    {

        System.out.println("Parsing text from PDF file " + fileName + "....");
        File f = new File(fileName);

        if (!f.isFile()) {
            System.out.println("File " + fileName + " does not exist.");
            return null;
        }

        try {
            parser = new PDFParser(new FileInputStream(f));
        } catch (Exception e) {
            System.out.println("Unable to open PDF Parser.");
            return null;
        }

        try {
            parser.parse();
            cosDoc = parser.getDocument();
            pdfStripper = new PDFTextStripper();
           /* pdfStripper.setStartPage(2);
            pdfStripper.setEndPage(3);*/
            pdDoc = new PDDocument(cosDoc);
            parsedText = pdfStripper.getText(pdDoc);
        } catch (Exception e) {
            System.out.println("An exception occured in parsing the PDF Document.");
            cosDoc.close();
            pdDoc.close();
            try {
                   if (cosDoc != null) cosDoc.close();
                   if (pdDoc != null) pdDoc.close();
               } catch (Exception e1) {
               //e.printStackTrace();
                   System.out.println("Picture found error .. I am skipping it");
            }
            return null;
        }
        System.out.println("Done.");
        return parsedText;
    }

    // Write the parsed text from PDF to a file
    void writeTexttoFile(String pdfText, String fileName)
    {
        
    	System.out.println("\nWriting PDF text to output text file " + fileName + "....");
        try {
            pw = new PrintWriter(fileName);
            pw.print(pdfText);
            pw.close();
        } catch (Exception e) {
            System.out.println("An exception occured in writing the pdf text to file.");
            pw.close();

        }
        
    	System.out.println("Done.");
    }

    
}
