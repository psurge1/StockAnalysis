public enum FilePaths {
    NEWS("/src/main/storage/data/news.csv"),
    LATESTSTOCK("/src/main/storage/data/lateststock.txt"),
    METADATA("/src/main/storage/data/metadata.txt"),
    STOCKGRAPH("/src/main/storage/stockgraph.png"),
    PIECHART("/src/main/storage/diversitypie.png"),
    LOGO("/src/main/storage/logo.png"),
    DESCRIPTION("/src/main/storage/data/description.txt"),
    PORTFOLIO("/src/main/storage/data/portfolio.csv"),
    POINTS("/src/main/storage/data/points.txt"),
    PYTHON("/src/main/Python/");
    
    String value;
    FilePaths(String value)
    {
        this.value = value;
    }

    public static void setAbsolutePath(String absolutePath)
    {
        for (FilePaths c : FilePaths.values())
        {
            c.value = absolutePath + c.value;
        }
    }
}