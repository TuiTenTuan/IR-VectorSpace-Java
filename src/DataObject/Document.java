package DataObject;

public class Document implements Comparable<Document>
{
    private int id;
    private String content;
    private double rsv;
    private double[] vectorSpace;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public double getRSV()
    {
        return this.rsv;
    }
    
    public void setRSV(double rsv)
    {
        this.rsv = rsv;
    }


    public double getRsv() {
        return this.rsv;
    }

    public void setRsv(double rsv) {
        this.rsv = rsv;
    }

    public double[] getVectorSpace() {
        return this.vectorSpace;
    }

    public void setVectorSpace(double[] vectorSpace) {
        this.vectorSpace = vectorSpace;
    }

    public Document() 
    {
        id = 0;
        content = "";
        rsv = 0;
    }

    public Document(int id, String content) 
    {
        this.id = id;
        this.content = content;
        this.rsv = 0;
    }

    public Document(int id, String content, double rsv) 
    {
        this.id = id;
        this.content = content;
        this.rsv = rsv;
    }

    @Override
    public int compareTo(Document o) 
    {
        return Double.compare(this.rsv, o.getRSV());
    }

    public int WordAppear(String word)
    {
        int result = 0;

        String[] docSplit = this.content.split(" ");

        for (String w : docSplit) 
        {
            if(w.equals(word))
            {
                result++;
            }
        }

        return result;
    }
}
