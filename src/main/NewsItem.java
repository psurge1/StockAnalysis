import java.io.IOException;
import java.util.ArrayList;



public class NewsItem
{
    private String sourceName, author, title, description, url, urlToImage, publishedAt, content;
    public NewsItem(String sourceName, String author, String title, String description, String url, String urlToImage, String publishedAt, String content)
    {
        this.sourceName   =  sourceName   ;
        this.author       =  author       ;
        this.title        =  title        ;
        this.description  =  description  ;
        this.url          =  url          ;
        this.urlToImage   =  urlToImage   ;
        this.publishedAt  =  publishedAt  ;
        this.content      =  content      ;
    }

    public String stringValue()
    {
        // return String.format("SOURCENAME: %s \nAUTHOR: %s \nTITLE: %s \nDESCRIPTION: %s \nURL: %s \nPUBLISHEDATE: %s", sourceName, author, title,  description, url, urlToImage, publishedAt, content);
        return String.format("SOURCENAME: %s \nAUTHOR: %s \nTITLE: %s \nDESCRIPTION: %s \nURL: %s \nURLTOIMAGE: %s \nPUBLISHEDATE: %s \nCONTENT: %s", sourceName, author, title,  description, url, urlToImage, publishedAt, content);
    }

    @Override
    public String toString()
    {
        return String.format("{ SOURCENAME: %s \nAUTHOR: %s \nTITLE: %s \nDESCRIPTION: %s \nURL: %s \nURLTOIMAGE: %s \nPUBLISHEDATE: %s \nCONTENT: %s }", sourceName, author, title,  description, url, urlToImage, publishedAt, content);
    }



    public static void queryAPI()
    {
        // py Python/retrieve.py news category=[String, optional] country=[String, optional] pagesize=[String, optional] apikey=[String, optional]
        String command = "py " + FilePaths.PYTHON.value + "retrieve.py news";

        CmdExec.exec(command + "%s", "").getInputStream();
    }
    
    public static NewsItem[] newsItemsFromFile(String path)
    {
        String data;
        ArrayList<NewsItem> nl = new ArrayList<NewsItem>();
        try
        {
            data = MFile.fromPath(path);
        }
        catch (IOException e)
        {
            System.out.println(e);
            return null;
        }
        String[] articles = data.split("\n");
        for (int i = 1; i < articles.length; ++i)
        {
            // System.out.println(articles[i]);
            String[] articleArray = articles[i].split("\",\"");
            nl.add(new NewsItem(
                articleArray[0].substring(1),
                articleArray[1],
                articleArray[2],
                articleArray[3],
                articleArray[4],
                articleArray[5],
                articleArray[6],
                articleArray[7].substring(0, articleArray[7].length() - 2)
            ));
        }
        nl.remove(0);
        return nl.toArray(new NewsItem[0]);
    }
}