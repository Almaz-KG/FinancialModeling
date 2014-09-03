package org.projectTView;

import com.alee.laf.WebLookAndFeel;

/**
 * Created by almu0214 on 03.09.2014.
 */
public class ProjectTView {
    public static void main(String[] args) {
        WebLookAndFeel.install();

        WebLookAndFeel.setDecorateAllWindows(true);
        LoginWebFrame frame = new LoginWebFrame();
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


      //  WebLookAndFeel.setDecorateDialogs ( decorateFrames );
    }
}
