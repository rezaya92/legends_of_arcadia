package Controller;

import java.io.*;

public class DeepCopy {
    public static Object copy(Object original){
        Object result = null;
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(original);
            objectOutputStream.flush();
            objectOutputStream.close();

            ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
            result = objectInputStream.readObject();
        }catch (IOException e){
            e.printStackTrace();
            System.out.println("1111111111");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("222222222222222");
        }

        return result;
    }
}
