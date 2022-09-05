package output;

import java.io.File;
import java.io.FileNotFoundException;

public interface Writer {

    void write(File file, String content) throws FileNotFoundException;

}
