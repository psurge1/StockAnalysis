import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;



public class MFile {
    public static String fromPath(String path) throws IOException
    {
        return fromPath(path, StandardCharsets.UTF_8);
    }

    public static String fromPath(String path, Charset encoding) throws IOException
    {
        byte[] data = Files.readAllBytes(Paths.get(path));
        return new String(data, encoding);
    }

    public static void toPath(String path, String item)
    {
        // py src/main/Python/py_utils.py save path="C:\Users\suraj\Code\StockAnalysis\storage\data\portfolio.csv" item="ticker,count,industry\nMSFT,2.0,Information technology \nSNDL,21.0,Consumer discretionary "
        CmdExec.exec("py " + FilePaths.PYTHON.value + "py_utils.py %s path=\"%s\" item=\"%s\"", "save", path, item);
    }

    public void writeToPath(String path, String item)
    {
        try
        {
            BufferedWriter writer = new BufferedWriter(new FileWriter(path));
            writer.write(item);
            writer.close();
        }
        catch (IOException ex)
        {
            System.out.println(ex);
        }
    }

    public void appendToPath(String path, String item)
    {
        try
        {
            BufferedWriter writer = new BufferedWriter(new FileWriter(path));
            writer.append(item);
            writer.close();
        }
        catch (IOException ex)
        {
            System.out.println(ex);
        }
    }
}