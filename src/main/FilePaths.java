public enum FilePaths {
    NEWS("/storage/data/news.csv"),
    LATESTSTOCK("/storage/data/lateststock.txt"),
    METADATA("/storage/data/metadata.txt"),
    STOCKGRAPH("/storage/stockgraph.png"),
    PIECHART("/storage/diversitypie.png"),
    LOGO("/storage/logo.png"),
    DESCRIPTION("/storage/data/description.txt"),
    PORTFOLIO("/storage/data/portfolio.csv"),
    POINTS("/storage/data/points.txt"),
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