package Function;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import javax.naming.spi.DirStateFactory.Result;
import javax.print.Doc;

import DataObject.Document;
import DataObject.WordIndex;

public class IRFunction 
{
    private static IRFunction instance;

    public static IRFunction Instance()
    {
        if(instance == null)
        {
            instance = new IRFunction();
        }

        return instance;
    }

    private IRFunction() { }

    public Hashtable<String, WordIndex> WorldIndex(List<Document> doc)
    {
        Hashtable<String,WordIndex> result = new Hashtable<String, WordIndex>();

        for (Document document : doc) 
        {
            String[] worlds = document.getContent().split(" ");
        
            for (String world : worlds) 
            {
                if(result.containsKey(world))
                {
                    result.get(world).AddIndex(document.getId());                   
                }
                else
                {
                    WordIndex wi = new WordIndex();
                    wi.AddIndex(document.getId());

                    result.put(world, wi);
                }
            }            
        } 

        return result;
    }

    public Hashtable<String, WordIndex> CalculatorIdf(Hashtable<String, WordIndex> world, int docCount)
    {
        Set<String> keys = world.keySet();

        for (String key : keys) 
        {
            world.get(key).setIdf(Math.log10(docCount / world.get(key).getDocIndex().size()));
        }

        return world;
    }

    public List<Document> VectorSpace (List<Document> documents, Hashtable<String, WordIndex> wordIndexs)
    {
        int countWorld = wordIndexs.size();

        Set<String> keys = wordIndexs.keySet();

        for (Document doc : documents) 
        {
            int i = 0;
            double[] w = new double[countWorld];
            for (String key : keys) 
            {
               int appear = doc.WordAppear(key);
               double wt = appear > 0 ? 1 + Math.log10(appear) : 0;
               w[i] = wt * wordIndexs.get(key).getIdf();
               i++;
            }
            doc.setVectorSpace(w);
        }

        return documents;
    }    

    public Document VectorSpace(Document document, Hashtable<String, WordIndex> wordIndexs)
    {       
        int countWorld = wordIndexs.size();
        Set<String> keys = wordIndexs.keySet();
        int i = 0;
        double[] w = new double[countWorld];

        for (String key : keys) 
        {
            int appear = document.WordAppear(key);
            double wt = appear > 0 ? 1 + Math.log10(appear) : 0;
            w[i] = wt * wordIndexs.get(key).getIdf();
            i++;
        }
        document.setVectorSpace(w);

        return document;
    }

    public List<Document> Cosin(List<Document> documents, Document query)
    {
        for (Document doc : documents) 
        {
            doc.setRSV(Cosin(doc.getVectorSpace(), query.getVectorSpace()));
        }

        return documents;
    }

    public double Cosin(double[] d1, double[] d2)
    {
        double result = 0;

        if(d1.length == d2.length)
        {
            double numerator = 0;
            double denominator = 0;
            double denominator1 = 0;
            double denominator2 = 0;

            for(int i = 0; i < d1.length; i++)
            {
                numerator += d1[i] * d2[i];
                denominator1 += Math.pow(d1[i], 2);
                denominator2 += Math.pow(d2[i], 2);
            }

            denominator = Math.sqrt(denominator1 * denominator2);

            if(denominator != 0)
            {
                result = numerator / denominator;
            }
        }

        return result;
    }
}
