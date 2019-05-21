package com.yhguo.common.tool;

import java.io.*;

public class FileUtil {

    public static void saveFile(String folderPath, InputStream inputStream, String fileName) throws Exception {
        FileOutputStream fileOutputStream = null;
        BufferedInputStream bufferedInputStream = null;

        try {
            File folder = new File(folderPath);
            if (!folder.exists()) {
                folder.mkdirs();
            }
            byte[] buf = new byte[1024];
            int size = 0;
            bufferedInputStream = new BufferedInputStream(inputStream);
            fileOutputStream = new FileOutputStream(new File(folderPath + "/" + fileName));
            while ((size = inputStream.read(buf)) != -1) {
                fileOutputStream.write(buf, 0, size);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception("文件保存出错");
        } finally {
            if (bufferedInputStream != null) {
                bufferedInputStream.close();
            }
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
        }
    }

}
