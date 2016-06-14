package org.example.TriviaRedesBot;

//import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import io.github.nixtabyte.telegram.jtelebot.server.impl.DefaultCommandDispatcher;
import io.github.nixtabyte.telegram.jtelebot.server.impl.DefaultCommandQueue;
import io.github.nixtabyte.telegram.jtelebot.server.impl.DefaultCommandWatcher;

//@JsonIgnoreProperties(ignoreUnknown=true)
public class Main {
	
    public static void main(String []args){
    	
        DefaultCommandDispatcher commandDispatcher = new DefaultCommandDispatcher(
        		10,
        		100, 
        		new DefaultCommandQueue());
        commandDispatcher.startUp();
        
        DefaultCommandWatcher commandWatcher = new DefaultCommandWatcher(
        		2000,
        		100,
        		"167418559:AAHpIwfddw22oq2VIiOkhJJumuCNetU_lnM",
        		commandDispatcher,
        		new MessageCommandFactory());
        
        commandWatcher.startUp();
    }
}
