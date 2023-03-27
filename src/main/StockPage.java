public class StockPage {
    private static String metaDataPath = FilePaths.METADATA.value,
        latestStockPath = FilePaths.LATESTSTOCK.value,
        stockGraphPath = FilePaths.STOCKGRAPH.value;
    
    public static void exec(String symbol)
    {
        String uppercaseSymbol = symbol.toUpperCase();
        CmdExec.exec("py " + FilePaths.PYTHON.value + "retrieve.py stock symbol=\"%s\"", uppercaseSymbol);
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