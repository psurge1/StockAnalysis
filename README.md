## Stock Analysis

GUI application that
- Enables the end user to maintain a portfolio of stocks by industry
- Search for stock values and historical graphs
- Apply analysis models (like simple moving averages, beta measurements, etc.) to stocks
- Before execution, API keys must be placed into src/main/assets/keys.txt (free API keys for AlphaVantage Stock API, on the first line, and News API on the second line) 

The application is primarily written in Java but API interaction for news and stock data is done with Python and involves communication between both languages through a Unix shell.

### Built with
- OpenJDK 17.0.8.1 LTS
- Python 3.9.18
- Bash

### Native Dependencies
- Python 3.9+ interpreter
- Git Bash/MinGW or WSL (only required for systems with non-Unix-like operating systems)

### File Functionality
#### Java
``Gui.java``
- Contains the main method and all code related to the GUI.
``Chart.java``
- Wrapper class for a stock portfolio industry diversity piechart, generated as a PNG by the Python matplotlib library.
``CmdExec.java``
- Contains the `exec` method enabling Java files to execute Python commands through a Unix shell.
``Element.java``
- Child class of JComponent with dimension manipulability  (using width and height).
``ElementD.java``
- Child class of Element with location manipulability (using x and y coordinates).
``FilePaths.java``
- Enum containing paths to various csv, png, and txt files used for storing data.
``MFile.java``
- Contains methods that facilitate file I/O.
``NewsItem.java``
- Class to organize news data retrieved from an API call. Static method `newsItemFromFile(String path)` converts a txt file containing raw data from an API call into an array of NewsItem objects.
``Portfolio.java``
- Class for a Portfolio object which is instantiated on startup. Portfolio objects contain stocks that the user added. Portfolios can be saved and loaded from flash memory, enabling a user to maintain their data, even after the GUI is closed.
``Stock.java``
- Class to organize stock data retrieved from an API call. Stock objects are stored in a Portfolio object when the user adds stocks to their portfolio.
``StockPage.java``
- Class containing method `exec(String symbol)` which executes an API request for stock data given a stock symbol. Data from the execution is stored in files in src/main/storage and src/main/storage/data.
``Warning.java``
- Generic class for warning subwindows, used to display warnings after any improper user I/O.
``ArrayTools.java``
- Wrapper file containing a method to search for a String in a given String array.

#### Python
``api.py``
- Facilitates API requests using the `send_HTTP_request`, `get_stock_data`, `get_stock_info`, and `get_news_headlines` functions.
``retrieve.py``
- Contains methods to search for stock symbols, generate stock charts, and plot data analysis graphs like SMA.
``data.py``
- Contains data analysis functions.
``paths.py``
- Contains daths to all csv, png, and txt files used for storing data.
``plot.py``
- Contains method to plot a portfolio industry diversity pie chart, and save it as a png.
``py_utils.py``
- Facilitates file I/0.
