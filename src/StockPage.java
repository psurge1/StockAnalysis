import utils.CmdExec;

public class StockPage {
    private static String metaDataPath = "../storage/data/metadata.txt",
    latestStockPath = "../storage/data/lateststock.txt",
    stockGraphPath = "../storage/stockgraph.png";
   
    public static void exec(String symbol)
    {
        String uppercaseSymbol = symbol.toUpperCase();
        CmdExec.exec("py Python/retrieve.py stock %s", "symbol=\"" + uppercaseSymbol + "\"");
    }

    public static String getMetaDataPath()
    {
        return metaDataPath;
    }

    public static String getLatestStockPath()
    {
        return latestStockPath; 
    }

    public static String getStockGraphPath()
    {
        return stockGraphPath; 
    }
}