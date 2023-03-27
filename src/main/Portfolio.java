import java.io.IOException;
import java.util.HashMap;



public class Portfolio
{
    HashMap<String, Stock> portfolio;
    public Portfolio()
    {
        portfolio = new HashMap<>();
    }

    public String[] getAllStockTickers()
    {
        return portfolio.keySet().toArray(new String[0]);
    }

    public Stock getStock(String ticker)
    {
        return portfolio.get(ticker);
    }
    
    public int getSize()
    {
        return portfolio.size();
    }

    public boolean containsStock(String s)
    {
        return true;   
    }

    public void addStock(String ticker, Stock stock)
    {
        if (portfolio.containsKey(ticker))
        {
            Stock w = portfolio.get(ticker);
            Stock s = new Stock(stock.getTicker(), stock.getIndustry(), stock.getCount() + w.getCount());
            portfolio.put(ticker, s);
        }
        else
        {
            portfolio.put(ticker, stock);
        }
        removeZerosAndNegatives();
    }

    public void editStock(String ticker, double count)
    {
        if (ArrayTools.exists(ticker, getAllStockTickers()) < 0)
        {
            new Warning("Stock Doesn't Exist in Portfolio");
            return;
        }
        Stock old = portfolio.get(ticker);
        portfolio.replace(ticker, new Stock(ticker, old.getIndustry(), count));
        removeZerosAndNegatives();
    }

    public void removeStock(String ticker)
    {
        portfolio.remove(ticker);
    }

    public void removeZerosAndNegatives()
    {
        String[] stocks = portfolio.keySet().toArray(new String[0]);
        for (int i = 0, n = stocks.length; i < n; ++i)
        {
            double c = portfolio.get(stocks[i]).getCount();
            if (c <= 0)
            {
                portfolio.remove(stocks[i]);
            }
        }
    }

    public void clearPortfolio()
    {
        portfolio.clear();
    }

    public void savePortfolioToFile(String path)
    {
        String portfolioContents = "ticker,count,industry";
        String[] tickers = getAllStockTickers();
        for (String ticker : tickers)
        {
            Stock tickerStock = portfolio.get(ticker);
            portfolioContents += "-"; // separator
            portfolioContents += tickerStock.getTicker() + ",";
            portfolioContents += tickerStock.getCount() + ",";
            portfolioContents += tickerStock.getIndustry();
        }
        MFile.toPath(path, portfolioContents);
    }

    public void updateFromFile(String path)
    {
        String data;
        try
        {
            data = MFile.fromPath(path);
        }
        catch (IOException e)
        {
            System.out.println(e);
            return;
        }

        clearPortfolio();

        String[] pStocks = data.split("\n");
        for (int i = 1; i < pStocks.length; ++i)
        {
            String[] pStockString = pStocks[i].split(",");
            addStock(pStockString[0], new Stock(
                pStockString[0],
                pStockString[2],
                Double.valueOf(pStockString[1])
            ));
        }
    }


    public static HashMap<String, Number> countIndustry(Portfolio p)
    {
        p.removeZerosAndNegatives();

        HashMap<String, Number> industryStockValue = new HashMap<>();
        String[] pStocks = p.portfolio.keySet().toArray(new String[0]);
        String[] ks;

        for (int i = 0; i < pStocks.length; ++i)
        {
            Stock pStock = p.getStock(pStocks[i]);
            if (i == 0)
            {
                industryStockValue.put(pStock.getIndustry(), pStock.getCount());
            }
            else
            {
                ks = industryStockValue.keySet().toArray(new String[0]);
                int loc = ArrayTools.exists(pStock.getIndustry(), ks);
                if (loc < 0) // industry not yet in hashmap
                {
                    industryStockValue.put(pStock.getIndustry(), pStock.getCount());
                }
                else // industry in hashmap
                {
                    double initialValue = (double) industryStockValue.get(pStock.getIndustry());
                    industryStockValue.put(pStock.getIndustry(), initialValue + pStock.getCount());
                }
            }
            // int loc = ArrayTools.exists(pStock.getIndustry(), industries);
        }

        return industryStockValue;
    }
}