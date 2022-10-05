package factory.java;

import factory.TranslatorFactory;
import output.Writer;
import output.java.JavaWriter;
import translator.Translator;
import translator.java.JavaTranslator;

public class JavaTranslatorFactory implements TranslatorFactory {

    private static final TranslatorFactory instance = new JavaTranslatorFactory();

    private JavaTranslatorFactory() {
    }

    public static TranslatorFactory getTranslatorFactory() {
        return instance;
    }

    @Override
    public Translator getTranslator() {
        return new JavaTranslator();
    }

    @Override
    public Writer getWriter() {
        return new JavaWriter();
    }
}
