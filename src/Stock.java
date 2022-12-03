import java.util.ArrayList;

public class Stock
{
    private ArrayList<Object> array = new ArrayList<>();
    public Stock(Object ... args)
    {
        for (Object o : args)
            array.add(o);
    }

    public String toString()
    {
        return array.toString();
    }
}