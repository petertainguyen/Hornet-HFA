package org.csc133.a5.commands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import org.csc133.a5.GameWorld;

public class StartOrStopEngine extends Command {
    private GameWorld gw;

    public StartOrStopEngine(GameWorld gw) {
        super("Start Engine");
        this.gw = gw;
    }

    @Override
    public void actionPerformed(ActionEvent event){
        gw.startOrStopEngine();
    }
}