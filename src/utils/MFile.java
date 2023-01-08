package utils;

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
}