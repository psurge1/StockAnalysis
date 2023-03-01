import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.Image;
import java.io.IOException;
import java.awt.event.ItemEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import utils.MFile;

import java.util.HashMap;


public class Gui extends Element implements ActionListener, ItemListener
{
    // MISC DATA STRUCTURES
    private Portfolio p;

    private NewsItem[] items;

    
    final static String HOME = "Home";
    final static String PORTFOLIO = "Portfolio";
    final static String ADDSTOCK = "Add Stock";
    final static String EDITSTOCK = "Edit Stock";
    final static String STOCKSEARCH = "Stock Search";

    JPanel homeCard;
    JPanel portfolioCard;
    JPanel astockCard;
    JPanel estockCard;
    JPanel sstockCard;


    // GUI DATA STRUCTURES

    private static int standardUnit = 40;

    private JPanel panel;
    private Chart chart;


    //  Main Menu
    private JLabel currentPortfolio;
    private JLabel pieChart;
    private JTextArea stockArea;
    private JScrollPane stocks;

    private JLabel recommendedStocks;
    private JTextArea recommendedStockList;
    private JScrollPane recommendedStockScroll;

    private JLabel latestHeadlines;
    private JTextArea headlines;
    private JScrollPane headlineScroll;
    

    // Portfolio Page
    private JLabel Pportfolio;
    private JScrollPane PstocksList;


    // Edit Stock Page
    private JLabel EeditStock;
    private JLabel EsymbolLabel;
    private JTextField Esymbol;
    private JLabel EcountLabel;
    private JTextField Ecount;
    private JButton Eedit;


    // Add Stock Page
    private JLabel AaddStock;
    private JLabel AsymbolLabel;
    private JTextField Asymbol;
    private JLabel AcountLabel;
    private JTextField Acount;
    private JButton Aadd;

    private JRadioButton AsectorInformationtechnology;
    private JRadioButton AsectorConsumerdiscretionary;
    private JRadioButton AsectorCommunicationservices;
    private JRadioButton AsectorHealthcare;
    private JRadioButton AsectorConsumerstaples;
    private JRadioButton AsectorFinancials;
    private JRadioButton AsectorIndustrials;
    private JRadioButton AsectorEnergy;
    private JRadioButton AsectorMaterials;
    private JRadioButton AsectorRealestate;
    private JRadioButton AsectorUtilities;
    JRadioButton[] radioButtonS;


    // Stock Page
    private JButton search;
    private JTextField searchBar;
    
    private JLabel Ssymbol;
    private JLabel Sstock; // Stock Graph
    private JTextArea SvolumeVolatility;

    private JTextArea ScompanyInfo;
    private JLabel SapplyModel;
    private JButton perlinNoise;
    private JButton tennisBall;

    
    // private static ImageIcon logo;
    public Gui()
    {
        // GUI initial setup START
        panel = new JPanel(new CardLayout());

        p = new Portfolio();
        // GUI initial setup END

        NewsItem.queryAPI();
        items = NewsItem.newsItemsFromFile("../storage/data/news.csv");

        homeCard        = new JPanel(null);
        portfolioCard   = new JPanel(null);
        astockCard      = new JPanel(null);
        estockCard      = new JPanel(null);
        sstockCard      = new JPanel(null);
    }

    private static void runGUI() {
        JFrame frame = new JFrame("Stock Management");

        int[] wh = Gui.generateDimensions();
        System.out.println(wh[0] + " " + wh[1]);
        
        frame.setSize(wh[0], wh[1]);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // initialize GUI
        Gui g = new Gui();
        g.setWH(wh[0], wh[1]);
        g.addComponentToPane(frame.getContentPane());
        
        // GUI final setup START
        // frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
        // GUI final setup END
    }

    public void addComponentToPane(Container pane) {
        JPanel comboBoxPane = new JPanel();
        String comboBoxItems[] = {HOME, PORTFOLIO, ADDSTOCK, EDITSTOCK, STOCKSEARCH};
        JComboBox<String> cb = new JComboBox<>(comboBoxItems);
        cb.setEditable(false);
        cb.addItemListener(this);
        comboBoxPane.add(cb);
        
        // Add content to Card pages
        mainMenu();
        portfolioPage();
        addStockPage();
        editStockPage();
        stockPage();

        updatePortfolios();
        
        // Add Card pages to panel
        panel.add(homeCard, HOME);
        panel.add(portfolioCard, PORTFOLIO);
        panel.add(astockCard, ADDSTOCK);
        panel.add(estockCard, EDITSTOCK);
        panel.add(sstockCard, STOCKSEARCH);
         
        pane.add(comboBoxPane, BorderLayout.PAGE_START);
        pane.add(panel, BorderLayout.CENTER);
    }

    public void itemStateChanged(ItemEvent evt) {
        CardLayout cl = (CardLayout) (panel.getLayout());
        cl.show(panel, (String) evt.getItem());
    }


    public static void main(String[] args)
    {
        // run GUI
        javax.swing.SwingUtilities.invokeLater(new Runnable()
        {
            public void run() {
                runGUI();
            }
        });
    }


    public void removePieChart()
    {
        try
        {
            homeCard.remove(pieChart);
        }
        catch (NullPointerException ex)
        {
            System.out.println("First Iteration");
        }
    }


    public void pieChartGen(HashMap<String, Number> items, int x, int y, int w, int h)
    {
        removePieChart();
        chart = Chart.chartFromKwargs("../storage/diversitypie.png", 2, 2, items);
        chart.setXY(x, y);
        chart.setWH(w, h);

        chart.setLabel();
        pieChart = chart.getJLabel();
        pieChart.setBounds(chart.getX(), chart.getY(), chart.getWidth(), chart.getH());
        homeCard.add(pieChart);
    }


    public void pieChartGen()
    {
        int currentY = standardUnit * 2;
        int currentHeight = getW() / 3;
        pieChartGen(Portfolio.countIndustry(p), 0, currentY - standardUnit, currentHeight, currentHeight);
    }


    public void updatePortfolios()
    {
        // Home Page Stocks
        String stockAreaText = "";

        // Portfolio Page Stocks

        String[] tickers = p.getAllStockTickers();
        for (int i = 0, n = tickers.length; i < n; ++i)
        {
            Stock temp = p.getStock(tickers[i]);

            // Home Page Stocks
            stockAreaText += tickers[i] + ": " + temp.getCount() + ", " + temp.getIndustry() + "\n";

            // Portfolio Page Stocks
        }
        // Home Page Stocks
        stockArea.setText(stockAreaText);
    }


    public void mainMenu()
    {
        int boundsWidth = getW() / 2;

        // Left Half
        int currentY = standardUnit;
        int currentHeight = standardUnit;

        currentPortfolio = new JLabel("Current Portfolio");
        currentPortfolio.setBounds(0, currentY, boundsWidth, currentHeight);
        homeCard.add(currentPortfolio);

        currentY += currentHeight;
        currentHeight = getW() / 3;

        if (p.getSize() > 0)
        {
            pieChartGen();
        }
        else
        {
            removePieChart();
        }

        stockArea = new JTextArea();
        stocks = new JScrollPane(stockArea);
        stocks.setBounds(0, currentHeight, boundsWidth, getH() - currentHeight - currentY);
        stockArea.setEditable(false);
        homeCard.add(stocks);
        
        // Right Half
        currentY = standardUnit;
        currentHeight = standardUnit;

        recommendedStocks = new JLabel("Recommended Stocks");
        recommendedStocks.setBounds(getW() / 2, currentY, getW() / 2 - 20, currentHeight);
        homeCard.add(recommendedStocks);

        currentY += currentHeight;
        currentHeight = getH() / 3 - currentY;

        recommendedStockList = new JTextArea();
        recommendedStockScroll = new JScrollPane(recommendedStockList);
        recommendedStockScroll.setBounds(getW() / 2, currentY, getW() / 2 - 20, currentHeight);
        recommendedStockList.setText(
            "Information technology: MSFT\n" +
            "Consumer discretionary: SBUX\n" +
            "Communication services: WBD\n" +
            "Health care: TMO\n" +
            "Consumer staples: KO\n" +
            "Financials: IBKR\n" +
            "Industrials: NOC\n" +
            "Energy: SLB\n" +
            "Materials: NTR\n" +
            "Real estate: SBAC\n" +
            "Utilities: PCG\n"
        );
        recommendedStockList.setLineWrap(true);
        recommendedStockList.setEditable(false);
        homeCard.add(recommendedStockScroll);

        currentY += currentHeight;
        currentHeight = standardUnit;

        latestHeadlines = new JLabel("Latest Headlines");
        latestHeadlines.setBounds(getW() / 2, currentY, getW() / 2 - 20, currentHeight);
        homeCard.add(latestHeadlines);

        currentY += currentHeight;
        currentHeight = getH() - currentY - standardUnit;
        
        headlines = new JTextArea();
        headlineScroll = new JScrollPane(headlines);
        headlineScroll.setBounds(getW() / 2, currentY, getW() / 2 - 20, currentHeight);
        headlines.setLineWrap(true);
        headlines.setEditable(false);
        homeCard.add(headlineScroll);

        for (NewsItem item : items)
        {
            // System.out.println(item);
            headlines.append(item.stringValue() + "\n\n\n");
        }
    }


    public void portfolioPage()
    {
        int boundsWidth = getW();

        Pportfolio = new JLabel("Portfolio");
        Pportfolio.setBounds(0, standardUnit, boundsWidth, standardUnit);
        portfolioCard.add(Pportfolio);

        PstocksList = new JScrollPane();
        PstocksList.setBounds(0, standardUnit * 2, boundsWidth, getH() - standardUnit * 4);
        portfolioCard.add(PstocksList);
    }


    public void addStockPage()
    {
        int boundsWidth = getW();
        int boundsWidth2 = boundsWidth / 3;

        AaddStock = new JLabel("Add Stock");
        AaddStock.setBounds(0, standardUnit, boundsWidth, standardUnit);
        astockCard.add(AaddStock);

        AsymbolLabel = new JLabel("Symbol / Ticker");
        AsymbolLabel.setBounds(boundsWidth2 / 2, getH() / 2 - standardUnit, boundsWidth2, standardUnit);
        astockCard.add(AsymbolLabel);

        Asymbol = new JTextField();
        Asymbol.setBounds(boundsWidth2, getH() / 2 - standardUnit, boundsWidth2, standardUnit);
        astockCard.add(Asymbol);

        AcountLabel = new JLabel("Number of Shares");
        AcountLabel.setBounds(boundsWidth2 / 2, getH() / 2, boundsWidth2, standardUnit);
        astockCard.add(AcountLabel);

        Acount = new JTextField();
        Acount.setBounds(boundsWidth2, getH() / 2, boundsWidth2, standardUnit);
        astockCard.add(Acount);

        AsectorInformationtechnology = new JRadioButton("Information technology");
        AsectorConsumerdiscretionary = new JRadioButton("Consumer discretionary");
        AsectorCommunicationservices = new JRadioButton("Communication services");
        AsectorHealthcare = new JRadioButton("Health care");
        AsectorConsumerstaples = new JRadioButton("Consumer staples");
        AsectorFinancials = new JRadioButton("Financials");
        AsectorIndustrials = new JRadioButton("Industrials");
        AsectorEnergy = new JRadioButton("Energy");
        AsectorMaterials = new JRadioButton("Materials");
        AsectorRealestate = new JRadioButton("Real estate");
        AsectorUtilities = new JRadioButton("Utilities");


        radioButtonS = new JRadioButton[11];
        radioButtonS[0] = AsectorInformationtechnology;
        radioButtonS[1] = AsectorConsumerdiscretionary;
        radioButtonS[2] = AsectorCommunicationservices;
        radioButtonS[3] = AsectorHealthcare;
        radioButtonS[4] = AsectorConsumerstaples;
        radioButtonS[5] = AsectorFinancials;
        radioButtonS[6] = AsectorIndustrials;
        radioButtonS[7] = AsectorEnergy;
        radioButtonS[8] = AsectorMaterials;
        radioButtonS[9] = AsectorRealestate;
        radioButtonS[10] = AsectorUtilities;

        ButtonGroup bg = new ButtonGroup();
        JPanel sub = new JPanel();
        sub.setBounds(getW() / 8, getH() / 4, getW() * 3 / 4, getH() / 4);
        for (JRadioButton jrb : radioButtonS)
        {
            bg.add(jrb);
            sub.add(jrb);
        }
        astockCard.add(sub);

        Aadd = new JButton("Add");
        Aadd.setBounds(boundsWidth2, getH() / 2 + 2 * standardUnit, boundsWidth2, standardUnit);
        Aadd.addActionListener(this);
        astockCard.add(Aadd);
    }


    public void editStockPage()
    {
        int boundsWidth = getW();
        int boundsWidth2 = boundsWidth / 3;

        EeditStock = new JLabel("Edit Stock");
        EeditStock.setBounds(0, standardUnit, boundsWidth, standardUnit);
        estockCard.add(EeditStock);

        EsymbolLabel = new JLabel("Symbol / Ticker");
        EsymbolLabel.setBounds(boundsWidth2 / 2, getH() / 2 - standardUnit, boundsWidth2, standardUnit);
        estockCard.add(EsymbolLabel);

        Esymbol = new JTextField();
        Esymbol.setBounds(boundsWidth2, getH() / 2 - standardUnit, boundsWidth2, standardUnit);
        estockCard.add(Esymbol);

        EcountLabel = new JLabel("Number of Shares");
        EcountLabel.setBounds(boundsWidth2 / 2, getH() / 2, boundsWidth2, standardUnit);
        estockCard.add(EcountLabel);

        Ecount = new JTextField();
        Ecount.setBounds(boundsWidth2, getH() / 2, boundsWidth2, standardUnit);
        estockCard.add(Ecount);

        Eedit = new JButton("Edit");
        Eedit.setBounds(boundsWidth2, getH() / 2 + 2 * standardUnit, boundsWidth2, standardUnit);
        Eedit.addActionListener(this);
        estockCard.add(Eedit);
    }


    public void stockPage()
    {
        int width = getW() / 2;

        // homeBar();

        // Top Bar
        searchBar = new JTextField(100);
        searchBar.setBounds(0, 0, width, standardUnit);
        sstockCard.add(searchBar);

        search = new JButton("Search");
        search.setBounds(width, 0, width, standardUnit);
        search.addActionListener(this);
        sstockCard.add(search);

        int currentY = standardUnit;
        int currentHeight = standardUnit;

        // Left Side
        Ssymbol = new JLabel("Stock Search");
        Ssymbol.setBounds(0, currentY, width, currentHeight);
        sstockCard.add(Ssymbol);
        
        currentY += currentHeight;
        currentHeight = (getH() - currentY) / 3;
        System.out.println(currentHeight);
        
        ScompanyInfo = new JTextArea();
        ScompanyInfo.setBounds(0, currentY, width, currentHeight);
        ScompanyInfo.setEditable(false);
        sstockCard.add(ScompanyInfo);
        

        currentY += currentHeight + 2;

        SvolumeVolatility = new JTextArea();
        SvolumeVolatility.setBounds(0, currentY, width, (int) (1.5 * currentHeight));
        SvolumeVolatility.setEditable(false);
        sstockCard.add(SvolumeVolatility);

        // SvolumeVolatility = new JTextArea();
        // SvolumeVolatility.setBounds(0, currentY, width, currentHeight);
        // SvolumeVolatility.setEditable(false);
        // sstockCard.add(SvolumeVolatility);

        // currentY += currentHeight + 2;

        // Sdata1 = new JTextArea();
        // Sdata1.setBounds(0, currentY, width / 2 - 1, currentHeight);
        // Sdata1.setEditable(false);
        // sstockCard.add(Sdata1);

        // Sdata2 = new JTextArea();
        // Sdata2.setBounds(width / 2 + 1, currentY, width / 2 - 1, currentHeight);
        // Sdata2.setEditable(false);
        // sstockCard.add(Sdata2);

        // Right Side
        width += 2;
        currentY = 2 * standardUnit;
        currentHeight = (getH() - currentY) / 2;

        Sstock = new JLabel(); // Change to stock graph image: Stock Sstock;
        Sstock.setBounds(width, currentY, width, currentHeight);
        sstockCard.add(Sstock);

        currentY += currentHeight;
        currentHeight = standardUnit;

        SapplyModel = new JLabel("Apply Model");
        SapplyModel.setBounds(width, currentY, width, currentHeight);
        sstockCard.add(SapplyModel);

        currentY += currentHeight;
        currentHeight = (getH() - currentY) / 3;
        
        perlinNoise = new JButton("Perlin Noise");
        perlinNoise.setBounds(width, currentY, width, currentHeight);
        perlinNoise.addActionListener(this);
        sstockCard.add(perlinNoise);

        currentY += currentHeight;

        tennisBall = new JButton("Tennis Ball");
        tennisBall.setBounds(width, currentY, width, currentHeight);
        tennisBall.addActionListener(this);
        sstockCard.add(tennisBall);
    }


    @Override
    public void actionPerformed(ActionEvent e)
    {
        JButton clickedButton = (JButton) e.getSource();
        // if (clickedButton == home) // home page
        // {
        //     panel.removeAll();
        //     mainMenu();
        // }
        // else if (clickedButton == editPortfolio) // portfolio page
        // {
        //     panel.removeAll();
        //     portfolioPage();
        // }
        // else if (clickedButton == PaddStock) // add stock to portfolio page
        // {
        //     panel.removeAll();
        //     addStockPage();
        // }
        if (clickedButton == Aadd)
        {
            System.out.println("Add Stock");
            String ticker = Asymbol.getText().strip().toUpperCase();
            Asymbol.setText("");
            double count;
            try
            {
                count = Double.valueOf(Acount.getText());
            }
            catch (NumberFormatException ex)
            {
                new Warning("Invalid Number");
                return;
            }
            Acount.setText("");
            String industry = "";
            for (int i = 0, n = radioButtonS.length; i < n; ++i)
            {
                if (radioButtonS[i].isSelected())
                {
                    industry = radioButtonS[i].getText();
                    i = n;
                }
            }
            if (industry.equals(""))
            {
                new Warning("Invalid Industry");
                return;
            }
            p.addStock(ticker, new Stock(ticker, industry, count));

            pieChartGen();
            updatePortfolios();
        }
        else if (clickedButton == Eedit)
        {
            System.out.println("Edit Stock");
            String ticker = Esymbol.getText().strip().toUpperCase();
            Esymbol.setText("");
            double count;
            try
            {
                count = Double.valueOf(Ecount.getText());
            }
            catch (NumberFormatException ex)
            {
                new Warning("Invalid Number");
                return;
            }
            Ecount.setText("");
            p.editStock(ticker, count);

            pieChartGen();
            updatePortfolios();
        }
        else if (clickedButton == search)
        {
            // System.out.println("Search");
            String symbol = searchBar.getText().strip().toUpperCase();
            StockPage.exec(symbol);
            String metaData, latestStock;
            try
            {
                metaData = MFile.fromPath(StockPage.getMetaDataPath());
                latestStock = MFile.fromPath(StockPage.getLatestStockPath());
                // stockGraphPath = MFile.fromPath(StockPage.getStockGraphPath());
            }
            catch (IOException ex)
            {
                System.out.println(ex);
                metaData = "";
                latestStock = "";
                return;
            }

            if (metaData.equals("NONE") || latestStock.equals("NONE"))
            {
                new Warning("Invalid Stock Symbol");
            }
            else
            {
                Ssymbol.setText(symbol);
                SvolumeVolatility.setText(metaData);
                ScompanyInfo.setText(latestStock);

                Sstock.setIcon(null);
                Chart stockGraph = new Chart(StockPage.getStockGraphPath());
                Image image = stockGraph.getImage();
                Image scaledImage = image.getScaledInstance(Sstock.getWidth(), Sstock.getHeight(), java.awt.Image.SCALE_SMOOTH);
                Sstock.setIcon(new ImageIcon(scaledImage));
            }
            
        }
        else if (clickedButton == tennisBall)
        {
            new Warning("Tennis Ball Feature Not Yet Available");
        }
        else if (clickedButton == perlinNoise)
        {
            new Warning ("Perlin Noise Feature Not Yet Available");
        }
    }


    /**
     * 
     * generateDimensions uses the screen width and height to produce proportional width and heigh parameters for the GUI
     */
    private static int[] generateDimensions()
    {
        // Dimension sc = Toolkit.getDefaultToolkit().getScreenSize();
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int[] wh = {gd.getDisplayMode().getWidth() / 2, (int) (gd.getDisplayMode().getHeight() * 1.2) / 2};
        return wh;
    }





    // UTILS //

    /**
     * 
     * @param s
     * @param sep
     * @return double array with all values separated by sep
     */
    public static double[] extract(String s, String sep)
    {
        int r1 = s.indexOf(sep);
        int r2 = s.indexOf(sep, r1 + 1);
        double[] k = {
            Double.valueOf(s.substring(0, r1)),
            Double.valueOf(s.substring(r1 + 1, r2)),
            Double.valueOf(s.substring(r2 + 1, s.length()))
        };
        return k;
    }

    /**
     * 
     * @param arr String array
     * @return double array
     */
    public static double[] stodArr(String[] arr)
    {
        arrprint(arr);
        int n = arr.length;
        double[] dArr = new double[n];
        for (int i = 0; i < n; ++i)
        {
            dArr[i] = Double.valueOf(arr[i]);
        }
        arrprint(dArr);
        return dArr;
    }

    /**
     * 
     * prints array
     * @param arr String array
     */
    public static void arrprint(String[] arr)
    {
        for (String c: arr)
        {
            System.out.print(c + " ");
        }
        System.out.println();
    }

    /**
     * 
     * prints array
     * @param arr double array
     */
    public static void arrprint(double[] arr)
    {
        for (double c: arr)
        {
            System.out.print(c + " ");
        }
        System.out.println();
    }
}