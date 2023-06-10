package DataObject;

import java.util.HashSet;

public class WordIndex 
{
    private double idf;
    private HashSet<Integer> docIndex;

    public double getIdf() 
    {
        return this.idf;
    }

    public void setIdf(double idf) 
    {
        this.idf = idf;
    }

    public HashSet<Integer> getDocIndex() 
    {
        return this.docIndex;
    }

    public void setDocIndex(HashSet<Integer> docIndex) 
    {
        this.docIndex = docIndex;
    }


    public WordIndex() 
    { 
        idf = 0;  
        docIndex = new HashSet<Integer>();
    }

    public WordIndex(double idf, HashSet<Integer> docIndex) 
    {
        this.idf = idf;
        this.docIndex = docIndex;
    }

    public void AddIndex(int index)
    {
        this.docIndex.add(index);
    }
}
