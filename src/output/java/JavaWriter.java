package output.java;

import output.Writer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class JavaWriter implements Writer {
    @Override
    public void write(File file, String content) throws FileNotFoundException {

        String fileName = file.getName();
        String outFolder = file.getAbsolutePath().replace(fileName, "");
        fileName = fileName.replace(".ns", "");
        String outFileName = fileName + ".java";

        PrintWriter pw = new PrintWriter(outFolder + outFileName);
        String builder = "import support.*;\n" +
                "public class " + fileName + " {\n" +
                content + "\n" +
                "}";

        pw.write(builder);
        pw.close();

    }
}
