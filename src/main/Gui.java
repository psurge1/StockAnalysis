import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.Image;

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

import java.io.File;
import java.io.IOException;

import java.util.HashMap;
import java.util.function.Function;



public class Gui extends Element implements ActionListener, ItemListener
{
    // MISC DATA STRUCTURES
    private Portfolio mainPortfolio;

    private NewsItem[] items;

    private final static String HOME = "Home";
    private final static String ADDSTOCK = "Add Stock";
    private final static String EDITSTOCK = "Edit Stock";
    private final static String STOCKSEARCH = "Stock Search";

    private JPanel homeCard;
    private JPanel astockCard;
    private JPanel estockCard;
    private JPanel sstockCard;

    // GUI DATA STRUCTURES
    private static ImageIcon logo = new ImageIcon(FilePaths.LOGO.value);

    private static int standardUnit = 40;

    private JPanel panel;
    private Chart chart;

    //  Home Page
    private JButton clearPortfolio;
    private JButton savePortfolio;
    private JButton loadPortfolio;

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
    private JRadioButton[] radioButtonS;

    // Stock Page
    private JButton search;
    private JTextField searchBar;
    
    private JLabel Ssymbol;
    private JLabel SstockGraphLabel; // Stock Graph
    private JTextArea SmetaDataInfo;

    private JTextArea ScompanyDescriptionInfo;
    private JTextArea ScompanyVolatilityInfo;
    private JTextArea SlatestInfo;
    private JLabel SapplyModel;
    private JTextArea standardMovingAverageValue;
    private JButton standardMovingAverage;
    private JButton tennisBall;


    /**
     * Constructor
     */
    public Gui()
    {
        // GUI initial setup
        panel = new JPanel(new CardLayout());
        mainPortfolio = new Portfolio();

        // retrieve data from News API request
        NewsItem.queryAPI();
        items = NewsItem.newsItemsFromFile(FilePaths.NEWS.value);

        // instantiate JCardLayout panels
        homeCard        = new JPanel(null);
        astockCard      = new JPanel(null);
        estockCard      = new JPanel(null);
        sstockCard      = new JPanel(null);
    }

    /**
     * Initializes the JFrame and instantiates a GUI object
     */
    private static void runGUI() {
        // initialize JFrame
        JFrame frame = new JFrame("Stock Analysis and Management");

        // set GUI icon
        frame.setIconImage(logo.getImage());

        // generate frame dimensions from screen dimensions
        int[] wh = Gui.generateDimensions();
        System.out.println(wh[0] + " " + wh[1]);
        frame.setSize(wh[0], wh[1]);

        // set frame close operation
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // initialize GUI
        Gui g = new Gui();
        g.setWH(wh[0], wh[1]);
        g.addComponentToPane(frame.getContentPane());
        
        // GUI final setup
        frame.setVisible(true);
        frame.setResizable(false);
    }


    /**
     * Adds navigation (JComboBox) and page Card panels (home, addstock, editstock, stocksearch) to the pane Container argument
     * @param pane (Container) Component to which all items will be added
     */
    public void addComponentToPane(Container pane) {
        JPanel comboBoxPane = new JPanel();
        String comboBoxItems[] = {HOME, ADDSTOCK, EDITSTOCK, STOCKSEARCH};
        JComboBox<String> cb = new JComboBox<>(comboBoxItems);
        cb.setEditable(false);
        cb.addItemListener(this);
        comboBoxPane.add(cb);
        
        // add content to Card pages
        homePage();
        addStockPage();
        editStockPage();
        stockPage();

        updatePortfolios();
        
        // add Card pages to panel
        panel.add(homeCard, HOME);
        panel.add(astockCard, ADDSTOCK);
        panel.add(estockCard, EDITSTOCK);
        panel.add(sstockCard, STOCKSEARCH);
         
        pane.add(comboBoxPane, BorderLayout.PAGE_START);
        pane.add(panel, BorderLayout.CENTER);
    }


    /**
     * Executes the GUI application
     * @param args
     */
    public static void main(String[] args)
    {
        String absolutePath;
        try {
            String localPath = "/Gui.java";
            File f = new File("Gui.java");
            String foundPath = f.getAbsolutePath();
            System.out.println(foundPath);
            absolutePath = foundPath.substring(0, foundPath.length() - localPath.length());
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
            return;
        }
        FilePaths.setAbsolutePath(absolutePath);
        for (FilePaths c : FilePaths.values())
        {
            System.out.println(c.value);
        }
        CmdExec.exec("py " + FilePaths.PYTHON.value + "paths.py abs_path=\"%s\"", absolutePath);
        
        // run GUI
        javax.swing.SwingUtilities.invokeLater(new Runnable()
        {
            public void run() {
                runGUI();
            }
        });
    }


    /**
     * Removes the pie chart and revalidates the "Home" page
     */
    public void removePieChart()
    {
        try
        {
            homeCard.remove(pieChart);
            homeCard.revalidate();
        }
        catch (Exception ex)
        {
            System.out.println("Pie Chart Removal Exception: No Pie Chart Exists");
        }
    }


    /**
     * Generates a pie chart from HashMap
     * @param items (HashMap<String, Number>) String categories and their related Number size
     * @param x (int) location x
     * @param y (int) location y
     * @param w (int) pie chart width
     * @param h (int) pie chart height
     */
    public void pieChartGen(HashMap<String, Number> items, int x, int y, int w, int h)
    {
        removePieChart();
        chart = Chart.chartFromKwargs(FilePaths.PIECHART.value, 2, 2, items);
        chart.setXY(x, y);
        chart.setWH(w, h);

        chart.setLabel();
        pieChart = chart.getJLabel();
        pieChart.setBounds(chart.getX(), chart.getY(), chart.getWidth(), chart.getH());
        homeCard.add(pieChart);
        homeCard.revalidate();
    }


    /**
     * Generates a pie chart to reflect the industry diversity of the portfolio
     */
    public void pieChartGen()
    {
        int currentY = standardUnit * 2;
        int currentHeight = getW() / 3;
        pieChartGen(Portfolio.countIndustry(mainPortfolio), 0, currentY - standardUnit, currentHeight, currentHeight);
    }


    /**
     * Resets the stock graph displayed to the image saved on stockgraph.png
     */
    public void resetStockGraph()
    {
        SstockGraphLabel.setIcon(null);
        Chart stockGraph = new Chart(StockPage.getStockGraphPath());
        Image scaledImage = stockGraph.getImage().getScaledInstance(SstockGraphLabel.getWidth(), SstockGraphLabel.getHeight(), java.awt.Image.SCALE_SMOOTH);
        SstockGraphLabel.setIcon(new ImageIcon(scaledImage));
    }


    /**
     * Updates the "Main Menu" portfolio display to reflect the elements within the portfolio
     */
    public void updatePortfolios()
    {
        // Home Page Stocks
        String stockAreaText = "";

        String[] tickers = mainPortfolio.getAllStockTickers();
        for (int i = 0, n = tickers.length; i < n; ++i)
        {
            Stock temp = mainPortfolio.getStock(tickers[i]);

            // Home Page Stocks
            stockAreaText += tickers[i] + ": " + temp.getCount() + ", " + temp.getIndustry() + "\n";
        }
        // Home Page Stocks
        stockArea.setText(stockAreaText);
    }


    /**
     * Adds JComponents to the "Home" page
     */
    public void homePage()
    {
        int boundsWidth = getW() / 2;

        // Left Half
        int currentY = standardUnit;
        int currentHeight = standardUnit;

        currentPortfolio = new JLabel("Current Portfolio");
        currentPortfolio.setBounds(0, currentY, boundsWidth, currentHeight);
        homeCard.add(currentPortfolio);

        currentY = getW() / 3;
        currentHeight = getW() * 9 / 40;

        if (mainPortfolio.getSize() > 0)
        {
            pieChartGen();
        }
        else
        {
            removePieChart();
        }

        stockArea = new JTextArea();
        stocks = new JScrollPane(stockArea);
        stocks.setBounds(0, currentY, boundsWidth, currentHeight);
        stockArea.setEditable(false);
        homeCard.add(stocks);

        currentY += currentHeight;

        int localBWidth = standardUnit * 2;
        clearPortfolio = new JButton("Clear");
        clearPortfolio.setBounds(0, currentY, localBWidth, standardUnit);
        clearPortfolio.addActionListener(this);
        homeCard.add(clearPortfolio);

        savePortfolio = new JButton("Save");
        savePortfolio.setBounds(localBWidth + 2, currentY, localBWidth, standardUnit);
        savePortfolio.addActionListener(this);
        homeCard.add(savePortfolio);

        loadPortfolio = new JButton("Load");
        loadPortfolio.setBounds(2 * (localBWidth + 2), currentY, localBWidth, standardUnit);
        loadPortfolio.addActionListener(this);
        homeCard.add(loadPortfolio);
        
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
            "Utilities: PCG"
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
            headlines.append(item.stringValue() + "\n\n\n");
        }
    }


    /**
     * Adds JComponents to the "Add Stock" page
     */
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


    /**
     * Adds JComponents to the "Edit Stock" page
     */
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


    /**
     * Adds JComponents to the "Stock Search" page
     */
    public void stockPage()
    {
        int width = getW() / 2;

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
        
        ScompanyDescriptionInfo = new JTextArea();
        ScompanyDescriptionInfo.setBounds(0, currentY, width / 2 - 2, currentHeight);
        ScompanyDescriptionInfo.setEditable(false);
        sstockCard.add(ScompanyDescriptionInfo);
        
        ScompanyVolatilityInfo = new JTextArea();
        ScompanyVolatilityInfo.setBounds(width / 2, currentY, width / 2, currentHeight);
        ScompanyVolatilityInfo.setEditable(false);
        sstockCard.add(ScompanyVolatilityInfo);
        

        currentY += currentHeight + 2;
        currentHeight = (int) (0.75 * currentHeight);

        SlatestInfo = new JTextArea();
        SlatestInfo.setBounds(0, currentY, width, currentHeight);
        SlatestInfo.setEditable(false);
        sstockCard.add(SlatestInfo);

        currentY += currentHeight + 2;
        
        SmetaDataInfo = new JTextArea();
        SmetaDataInfo.setBounds(0, currentY, width, currentHeight);
        SmetaDataInfo.setEditable(false);
        sstockCard.add(SmetaDataInfo);


        // Right Side
        width += 2;
        currentY = 2 * standardUnit;
        currentHeight = (getH() - currentY) / 2;

        SstockGraphLabel = new JLabel(); // Change to stock graph image: Stock Sstock;
        SstockGraphLabel.setBounds(width, currentY, width, currentHeight);
        sstockCard.add(SstockGraphLabel);

        currentY += currentHeight;
        currentHeight = standardUnit;

        SapplyModel = new JLabel("Apply Model");
        SapplyModel.setBounds(width, currentY, width, currentHeight);
        sstockCard.add(SapplyModel);

        currentY += currentHeight;
        currentHeight = (getH() - currentY) / 3;

        standardMovingAverageValue = new JTextArea();
        standardMovingAverageValue.setBounds(width, currentY, width / 2, currentHeight);
        sstockCard.add(standardMovingAverageValue);
        
        standardMovingAverage = new JButton("Standard Moving Average");
        standardMovingAverage.setBounds(width * 3 / 2, currentY, width / 2, currentHeight);
        standardMovingAverage.addActionListener(this);
        sstockCard.add(standardMovingAverage);

        currentY += currentHeight;

        tennisBall = new JButton("Tennis Ball");
        tennisBall.setBounds(width, currentY, width, currentHeight);
        tennisBall.addActionListener(this);
        sstockCard.add(tennisBall);
    }


    // OVERRIDEN Interface methods
    // ItemListener
    @Override
    public void itemStateChanged(ItemEvent evt) {
        CardLayout cl = (CardLayout) (panel.getLayout());
        cl.show(panel, (String) evt.getItem());
    }

    // ActionListener
    @Override
    public void actionPerformed(ActionEvent e)
    {
        JButton clickedButton = (JButton) e.getSource();
        if (clickedButton == Aadd)
        {
            System.out.println("Add Stock");
            String ticker = Asymbol.getText().strip().toUpperCase();
            Asymbol.setText("");
            double count = warnAction(Acount.getText(), (String s) -> Double.valueOf(s), -1.0, "Invalid Number");
            if (count < 0)
            {
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
            mainPortfolio.addStock(ticker, new Stock(ticker, industry, count));

            pieChartGen();
            updatePortfolios();
        }
        else if (clickedButton == Eedit)
        {
            System.out.println("Edit Stock");
            String ticker = Esymbol.getText().strip().toUpperCase();
            Esymbol.setText("");
            double count = (double) warnAction(Ecount.getText(), (String s) -> Double.valueOf(s), -1.0, "Invalid Number");
            if (count < 0)
            {
                return;
            }
            Ecount.setText("");
            mainPortfolio.editStock(ticker, count);

            pieChartGen();
            updatePortfolios();
        }
        else if (clickedButton == search)
        {
            String symbol = searchBar.getText().strip().toUpperCase();
            StockPage.exec(symbol);
            String[] descriptionVolatilityArr;
            String descriptionString, volatilityString, metaDataString, latestStockString;
            try
            {
                descriptionVolatilityArr = MFile.fromPath(FilePaths.DESCRIPTION.value).split("\n--");
                descriptionString = descriptionVolatilityArr[0].strip();
                volatilityString = descriptionVolatilityArr[1].strip();
                metaDataString = MFile.fromPath(StockPage.getMetaDataPath()).strip();
                latestStockString = MFile.fromPath(StockPage.getLatestStockPath()).strip();
            }
            catch (IOException ex)
            {
                System.out.println(ex);
                descriptionString = "";
                volatilityString = "";
                metaDataString = "";
                latestStockString = "";
                return;
            }

            if (metaDataString.equals("NONE") || latestStockString.equals("NONE"))
            {
                new Warning("Invalid Stock Symbol");
            }
            else if (metaDataString.equals("EXCEEDED") || latestStockString.equals("EXCEEDED"))
            {
                new Warning("Exceeded API call limit of 5 calls per minute and 500 calls per day");
            }
            else
            {
                Ssymbol.setText(symbol);

                ScompanyDescriptionInfo.setText(descriptionString);
                ScompanyVolatilityInfo.setText(volatilityString);
                SlatestInfo.setText(latestStockString);
                SmetaDataInfo.setText(metaDataString);

                resetStockGraph();
            }
            
        }
        else if (clickedButton == clearPortfolio)
        {
            System.out.println("Clear Portfolio");
            mainPortfolio.clearPortfolio();
            removePieChart(); // is lagging, requires page change to reflect pie chart removal
            updatePortfolios();
        }
        else if (clickedButton == savePortfolio)
        {
            System.out.println("Save Portfolio");
            mainPortfolio.savePortfolioToFile(FilePaths.PORTFOLIO.value);
        }
        else if (clickedButton == loadPortfolio)
        {
            System.out.println("Load Portfolio");
            mainPortfolio.updateFromFile(FilePaths.PORTFOLIO.value);
            pieChartGen();
            updatePortfolios();
        }
        else if (clickedButton == tennisBall)
        {
            new Warning("Tennis Ball Feature Not Yet Available");
        }
        else if (clickedButton == standardMovingAverage)
        {
            int period = (int) warnAction(standardMovingAverageValue.getText(), (String s) -> Integer.valueOf(s), -1, "Invalid Number");
            if (period <= 0)
            {
                return;
            }
            String[] pointsString;
            try
            {
                pointsString = MFile.fromPath(FilePaths.POINTS.value).strip().split("\n");
            }
            catch (IOException ex)
            {
                System.out.println(ex);
                return;
            }
            CmdExec.exec("py " + FilePaths.PYTHON.value + "retrieve.py %s symbol=\"%s\" iteration=\"%d\" s_close_x=\"%s\" s_close_y=\"%s\"", "sma", pointsString[0], period, pointsString[1], pointsString[2]);
            resetStockGraph();
        }
    }


    /**
     * Raises a new Warning if an exception is caught during the execution of the subprocess argument
     * @param <T> (T) Return type
     * @param <V> (V) Parameter type
     * @param input (V) Subprocess parameter
     * @param func (Function<V, T>) Subprocess to be executed with appropriate Parameter and Return types.
     * @param altResult (T) Alternate result to be returned if exception arises. Must be of the same type as the Return type.
     * @param warnMsg (String) Warning to be displayed on Warning GUI
     * @return Result of the subprocess (if successful) or altResult (if Exception is caught)
     */
    public static <T, V> T warnAction(V input, Function<V, T> func, T altResult, String warnMsg)
    {
        T result;
        try
        {
            result = func.apply(input);
        }
        catch (Exception ex)
        {
            new Warning(warnMsg);
            System.out.println(ex);
            result = altResult;
        }
        return result;
    }


    /**
     * Uses the screen width and height to produce proportional width and heigh parameters for the GUI
     * @return (int[]) Screen width and height - {width, height}
     */
    private static int[] generateDimensions()
    {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int[] wh = {gd.getDisplayMode().getWidth() / 2, (int) (gd.getDisplayMode().getHeight() * 1.2) / 2};
        return wh;
    }
}