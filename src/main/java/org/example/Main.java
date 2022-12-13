package org.example;
import UI.console;
import java.io.IOException;

/**
 * Main class for running the app
 */
public class Main {
    /**
     * constructor for main
     */
    public Main() {
    }

    /**
     * main function
     * @param args arguments form command line
     * @throws Exception the exceptions thrown by the app
     */
    public static void main(String[] args) throws Exception{
        //Tests.run();
        console app = new console();
        app.run();
    }
}