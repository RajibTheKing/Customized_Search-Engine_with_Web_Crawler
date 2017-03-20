package teststring;

import java.util.Vector;

class data {
    int edges[] =new int[26];
    int complete,string_id;
    data() {
        for(int i=0;i<edges.length;i++) edges[i]=-1;
        complete=0;string_id=-1;
    }
}

class pair {
    int string_id,complete;

    public pair(int string_id, int complete) {
        this.string_id = string_id;
        this.complete = complete;
    }
}

public class StringAutomaton {
    private String target[];
    private String now;
    private int unique,mx=200000;
    private Vector<data>trie = new Vector<data>();
    
    private Vector<Integer>length=new Vector<Integer>();
    
    private Vector<Integer>count=new Vector<Integer>();
    private Vector<Integer>id= new Vector<Integer>();
    
    
    private boolean found[]= new boolean[mx];
    private long total_sentence,total_characters,total_weight;
    
    
    public StringAutomaton(String[] target) {
        this.target = target;
        trie.clear();
        length.clear();
        unique=0;
        total_sentence=0;total_characters=0;
        total_weight=0;
    }
    
    
    private int BuildTrie(int node,int indx) {
        if(indx>=now.length()) 
        {
            if(trie.get(node).string_id==-1) trie.get(node).string_id=unique++;
            trie.get(node).complete++;
            return trie.get(node).string_id;
        } else {
            int ch=now.charAt(indx);
            if(ch>'Z') ch-=32;
            if( trie.get(node).edges[ ch-'A' ]==-1 ) {
                
                trie.add( new data() );
                trie.get(node).edges[ ch-'A' ]=trie.size()-1;
                
                return BuildTrie(trie.size()-1, indx + 1);
            }else {
                return BuildTrie(trie.get(node).edges[ch-'A'], indx+1);
            }
        }
    }
    
    public void process()
    {
        trie.add(new data());
        for(int i=0;i<target.length;i++)
        {
            if(target[i].length()<4) continue;
            now=target[i].toString();
            total_characters+=now.length();
            total_weight+= (now.length()/6) * (now.length()/6);
            int id=BuildTrie(0, 0);
            if( id==length.size() )  {
                length.add(now.length()) ;
                total_sentence++;
            }
            
//            System.out.println("String Processed : "+now+" Id Given "+id);
        }
        
        PlagiarismCalculator.Build(length, total_sentence, total_characters, total_weight);
        System.out.println("INPUT FILE  PROCESSED SUCCESSFULLY ");
    }
    
    private pair TrieGetId(int node, int indx)
    {
         if( indx>=now.length() ) return new pair( trie.get(node).string_id , ( trie.get(node).complete==0 ) ? 1 : trie.get(node).complete  );
         
         int ch=now.charAt(indx);
         if(ch>'Z') ch-=32;
         ch-='A';
         if( trie.get(node).edges[ch]==-1 ) return new pair( -1 , 0 );
         else return TrieGetId( trie.get(node).edges[ch] , indx+1 ) ;
    }
    
    public PlagiarismCalculator CompareWithOther(String[] match) {
        
        id.clear();
        count.clear();
        for(int i=0;i<mx;i++)  found[i]=false;
        
        pair pp;
        for(int i=0;i<match.length;i++)
        {
            if(match[i].length()<4) continue;
            now=match[i];
            pp=TrieGetId(0, 0);
            if(pp.string_id==-1) continue;
            if(found[pp.string_id]==true) continue;
            
            found[pp.string_id]=true;
            
            id.add(pp.string_id);
            count.add(pp.complete);
            
//            System.out.println("Id found "+pp.string_id +" for String "+now+" matched " +pp.complete);
        }
        PlagiarismCalculator pg= new PlagiarismCalculator(id, count);
        
//        System.out.println("Normal Matching Percantage " + pg.NormalMatching() );
//        System.out.println("Character Matching Percantage " + pg.CharacterMatching() );
        return pg;
    }
    
}
