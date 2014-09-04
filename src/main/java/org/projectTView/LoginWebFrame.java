package org.projectTView;

import com.alee.extended.layout.TableLayout;
import com.alee.extended.panel.CenterPanel;
import com.alee.extended.panel.GroupPanel;
import com.alee.laf.WebLookAndFeel;
import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebDialog;
import com.alee.laf.rootpane.WebFrame;
import com.alee.laf.text.WebPasswordField;
import com.alee.laf.text.WebTextField;
import com.alee.managers.hotkey.Hotkey;
import com.alee.managers.hotkey.HotkeyManager;


import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Created by almu0214 on 03.09.2014.
 */
public class LoginWebFrame extends WebFrame{
    public LoginWebFrame(){
        setTitle("Example dialog");
        init();
    }

    private void init(){
        setIconImages(WebLookAndFeel.getImages());
        setDefaultCloseOperation(WebDialog.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(290, 140));
        setResizable ( true );

        TableLayout layout = new TableLayout ( new double[][]{ { TableLayout.PREFERRED, TableLayout.FILL },
                { TableLayout.PREFERRED, TableLayout.PREFERRED, TableLayout.PREFERRED } } );
        layout.setHGap ( 5 );
        layout.setVGap ( 5 );
        WebPanel content = new WebPanel ( layout );
        content.setMargin ( 15, 30, 15, 30 );
        content.setOpaque ( false );

        content.add ( new WebLabel( "Login", WebLabel.TRAILING ), "0,0" );
        content.add ( new WebTextField( 15 ), "1,0" );

        content.add ( new WebLabel ( "Password", WebLabel.TRAILING ), "0,1" );
        content.add ( new WebPasswordField( 15 ), "1,1" );

        WebButton login = new WebButton ( "Login" );
        WebButton cancel = new WebButton ( "Cancel" );
        ActionListener loginListener = (e) -> login();
        ActionListener cancelListener = (e) -> dispose();

        login.addActionListener ( loginListener );
        cancel.addActionListener ( cancelListener );
        content.add ( new CenterPanel( new GroupPanel( 5, login, cancel ) ), "0,2,1,2" );

        add ( content );

        HotkeyManager.registerHotkey(this, cancel, Hotkey.ESCAPE);
        HotkeyManager.registerHotkey ( this, login, Hotkey.ENTER );
    }

    private void login(){
        new ChartWebFrame().setVisible(true);

        dispose();
    }
}
