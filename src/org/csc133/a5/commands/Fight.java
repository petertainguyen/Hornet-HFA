package org.csc133.a5.commands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import org.csc133.a5.GameWorld;

public class Fight extends Command {
    private GameWorld gw;

    public Fight(GameWorld gw) {
        super("Fight");
        this.gw = gw;
    }

    @Override
    public void actionPerformed(ActionEvent event){
        gw.fightFire();
    }
}