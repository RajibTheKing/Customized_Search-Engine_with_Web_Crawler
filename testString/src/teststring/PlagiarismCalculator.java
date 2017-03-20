package teststring;

import java.util.Vector;


public class PlagiarismCalculator {

    static  Vector<Integer> length;
    static long tot_sentence,tot_characters,tot_weight;
    
    private Vector<Integer> resultId,count;
    
    public static void Build( Vector<Integer> length , long tot_sentence, long  tot_characters ,long tot_weight) {

        PlagiarismCalculator.length = length;
        
        PlagiarismCalculator.tot_sentence=tot_sentence;
        PlagiarismCalculator.tot_characters= tot_characters;
//        PlagiarismCalculator.tot_weight=tot_characters*6;
        PlagiarismCalculator.tot_weight=tot_weight;
    }
    
    public PlagiarismCalculator(Vector<Integer> getResult, Vector<Integer> how_many) {
        resultId=getResult;
        count=how_many;
    }
    
    public double NormalMatching() {
        return (double)resultId.size()/(double)tot_sentence;
    }
    public double CharacterMatching() {
        
        long tot_character_match=0;
        for(int i=0;i<resultId.size();i++) tot_character_match+=length.get( resultId.get(i)  ) * count.get( i );
        return (double)tot_character_match/(double)tot_characters;
    }
    public double WeightedMatching() {
        long sum=0;
        for(int i=0;i<resultId.size();i++)
        {
            long words=length.get( resultId.get(i)  );
            words/=6;
            words=words*words;
            sum+=words*count.get(i);
        }
        return (double)sum/(double)tot_weight;
    }
    
}
