
// TODO: ADD STOCK VALUE

public class Stock
{
    private String ticker;
    private String industry;
    private double count;
    // private double value;
    public Stock(String ticker, String industry, double count)
    {
        this.ticker = ticker;
        this.industry = industry;
        this.count = count;
    }

    // public Stock(String ticker, String industry, double count)
    // {
    //     this.ticker = ticker;
    //     this.industry = industry;
    //     this.count = count;
    //     this.value = value;
    // }

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

    // public double getValue()
    // {
    //     return value;
    // }

    // public double calcNetValue()
    // {
    //     return getCount() * getValue();
    // }

    public boolean equals(Stock other)
    {
        return this.getTicker().equals(other.getTicker());
    }
}