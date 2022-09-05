import processor.LanguageProcessor;
import processor.XtoneProcessor;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        LanguageProcessor processor = new XtoneProcessor(args);
        processor.process();

    }
}