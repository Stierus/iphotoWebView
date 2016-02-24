package ru.stierus.iphotoweb.service.parser;

public class PhotoLibraryParserException extends Exception{

    public PhotoLibraryParserException(String message) {
        super(message);
    }

    public PhotoLibraryParserException(String message, Throwable cause) {
        super(message, cause);
    }
}
