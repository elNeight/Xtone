import factory.TranslatorFactory;
import factory.java.JavaTranslatorFactory;
import processor.LanguageProcessor;
import processor.TranslatableLanguageProcessor;
import processor.java.ToJavaLanguageProcessor;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        args = new String[]{"D:\\samples\\sample3.ns"};

        TranslatorFactory factory = new JavaTranslatorFactory();
        TranslatableLanguageProcessor processor = new ToJavaLanguageProcessor(factory, args);
        processor.process();

    }
}