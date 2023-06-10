import java.util.Collections;

import java.util.Hashtable;
import java.util.List;
import java.util.Scanner;

import DataObject.Document;
import DataObject.WordIndex;
import Function.*;
import Variable.SystemVarible;

public class App {
    private static String pathData = "Data\\doc-text";
    private static String pathQuery = "Data\\query-text";
    private static String pathStorage = "Output\\storage-text.txt";
    private static String pathOutput = "Output\\output-text.txt";
    private static String pathStopWord = "Data\\stop-word.txt";
    private static String pathDocumentClear = "Output\\document-clear.txt";

    private static int topDocumentSuitable = 100; //1% doc
    public static void main(String[] args)
    {
        //read raw data
        List<Document> rawDocuments = Data.Instance().ReadRawFileData(pathData);
        System.out.println("Documents Base Size: " + rawDocuments.size());
        
        //read stop word
        List<String> stopWords = Data.Instance().ReadStopWord(pathStopWord);
        System.out.println("Stop word Size: " + stopWords.size());

        //clear Document
        List<Document> clearDocuments = Data.Instance().RemoveStopWordDocuments(rawDocuments, stopWords);

        //storage Document clear
        if(Data.Instance().WriteDocumentClearFile(clearDocuments, pathDocumentClear))
        {
            System.out.println("Storage Document Clear Success at: " + SystemVarible.GetBaseApplicationPath() + "\\" + pathStorage);
        }
        else
        {
            System.out.println("Storage Document Clear fail!");
        }

        //create word in doc struct;
        Hashtable<String, WordIndex> worlDocIndex = IRFunction.Instance().WorldIndex(clearDocuments);
        System.out.println("Word Index Size: " + worlDocIndex.size());

        //storage word in doc
        if(Data.Instance().WriteWorldIndexFile(worlDocIndex, pathStorage))
        {
            System.out.println("Storage World Index Success at: " + SystemVarible.GetBaseApplicationPath() + "\\" + pathStorage);
        }
        else
        {
            System.out.println("Storage World Index fail!");
        }
        
        Hashtable<String, WordIndex> wordIdf = IRFunction.Instance().CalculatorIdf(worlDocIndex, clearDocuments.size());

        List<Document> docWithVectorSpace = IRFunction.Instance().VectorSpace(clearDocuments, wordIdf);

        String input = "";

        Scanner inputScanner = new Scanner(System.in);

        while(input == "ex")
        {
            System.out.println("Input Query: ");
            String queryInput = inputScanner.nextLine();

            String clearQuery = Data.Instance().ClearDocument(queryInput, stopWords, false);

            Document docQuery = IRFunction.Instance().VectorSpace(new Document(0, clearQuery, 0), wordIdf);

            List<Document> docRSV = IRFunction.Instance().Cosin(clearDocuments, docQuery);

            Collections.sort(docRSV);

            System.out.println(docRSV.get(0).getContent());
        }       
    }    
}
