package com.shared.utilities;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * Created by xeonocide on 9/17/18.
 */

public class Serializer
{
    public static void writeData(Object obj, OutputStream outputStream) throws IOException
    {
        ObjectOutputStream out = new ObjectOutputStream(outputStream);
        out.writeObject(obj);
    }

    public static Object readData(InputStream inputStream) throws IOException, ClassNotFoundException
    {
        ObjectInputStream input = new ObjectInputStream(inputStream);
        return input.readObject();
    }
}
