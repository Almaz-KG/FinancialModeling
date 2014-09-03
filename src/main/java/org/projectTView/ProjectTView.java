package org.projectTView;

import com.alee.laf.WebLookAndFeel;
import com.alee.laf.rootpane.WebFrame;

/**
 * Created by almu0214 on 03.09.2014.
 */
public class ProjectTView {
    public static void main(String[] args) {
        WebLookAndFeel.install();

        TradeTerminal terminal = new TradeTerminal();
        terminal.setVisible(true);


    }
}
