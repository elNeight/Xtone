package factory;

import output.Writer;
import translator.Translator;

public interface TranslatorFactory {

    Translator getTranslator();

    Writer getWriter();

}
