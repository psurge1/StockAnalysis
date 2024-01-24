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
``ArrayTools.java``
- 
``Chart.java``
- 
``Update Chart.java``
- 
``CmdExec.java``
- 
``Element.java``
- 
``ElementD.java``
- 
``10 months ago``
- 
``FilePaths.java``
- 
``Gui.java``
- 
``MFile.java``
- 
``NewsItem.java``
- 
``Portfolio.java``
- 
``Stock.java``
- 
``StockPage.java``
- 
``Warning.java``
- 

#### Python
``api.py``
- 
``data.py``
-
``paths.py``
-
``plot.py``
-
``py_utils.py``
-
``retrieve.py``
-
