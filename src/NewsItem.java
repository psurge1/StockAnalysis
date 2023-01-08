import utils.MFile;

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


    @Override
    public String toString()
    {
        return String.format("{ SOURCENAME: %s \nAUTHOR: %s \nTITLE: %s \nDESCRIPTION: %s \nURL: %s \nURLTOIMAGE: %s \nPUBLISHEDAT: %s \nCONTENT: %s }", sourceName, author, title,  description, url, urlToImage, publishedAt, content);
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
        for (String article : articles)
        {
            String[] articleArray = article.split(",");
            nl.add(new NewsItem(
                articleArray[0],
                articleArray[1],
                articleArray[2],
                articleArray[3],
                articleArray[4],
                articleArray[5],
                articleArray[6],
                articleArray[7]
            ));
        }

        return nl.toArray(new NewsItem[0]);
    }
}