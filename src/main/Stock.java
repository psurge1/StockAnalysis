public class Stock
{
    private String ticker;
    private String industry;
    private double count;
    public Stock(String ticker, String industry, double count)
    {
        this.ticker = ticker;
        this.industry = industry;
        this.count = count;
    }

    public String getTicker()
    {
        return ticker;
    }

    public String getIndustry()
    {
        return industry;
    }

    public double getCount()
    {
        return count;
    }

    public boolean equals(Stock other)
    {
        return this.getTicker().equals(other.getTicker());
    }
}