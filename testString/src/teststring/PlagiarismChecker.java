package teststring;

import java.util.Vector;


public class PlagiarismChecker {
    
    private Vector<String>fileList;
    private Vector<String>url;
    private String main_file;
    
    public PlagiarismChecker(Vector<String> fileList, Vector<String> url , String str) {
        this.fileList = fileList;
        this.url = url;
        main_file=str;
    }
    
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
            
            
            System.out.println("\n\nURL :"+url.get(i));
            System.out.println("Normal Matching Found " + pg.NormalMatching()*100.0 +"%");
            System.out.println("Character Matching Found "+pg.CharacterMatching()*100.0 +"%");
            System.out.println("Weighted Matching Found "+pg.WeightedMatching()*100.0+"%\n\n\n");
        }
        
    }
    
}
