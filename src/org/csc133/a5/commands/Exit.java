package org.csc133.a5.commands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import org.csc133.a5.GameWorld;

public class Exit extends Command {
    private GameWorld gw;

    public Exit(GameWorld gw) {
        super("Exit");
        this.gw = gw;
    }

    @Override
    public void actionPerformed(ActionEvent event){
        gw.quit();
    }
}