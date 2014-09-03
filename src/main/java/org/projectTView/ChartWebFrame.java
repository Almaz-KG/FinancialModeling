package org.projectTView;

import com.alee.global.StyleConstants;
import com.alee.laf.button.WebButton;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebFrame;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.toolbar.WebToolBar;
import org.projectTModel.entities.StockHistory;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by almu0214 on 03.09.2014.
 */
public class ChartWebFrame extends WebFrame{
    private StockHistory stockHistory;
    private static final Map<String, ImageIcon> iconsCache = new HashMap<String, ImageIcon>();
    private WebPanel jPanel1;
    private WebScrollPane jScrollPane2;
    private WebToolBar jToolBar1;



    public ChartWebFrame(){
        init();
    }
    public ChartWebFrame(StockHistory stock){
        this.stockHistory = stock;
        init();
    }

    public ImageIcon loadIcon ( final String path )
    {
        return loadIcon ( getClass (), path );
    }
    public ImageIcon loadIcon ( final Class nearClass, final String path )
    {
        final String key = nearClass.getCanonicalName () + ":" + path;
        if ( !iconsCache.containsKey ( key ) )
        {
            iconsCache.put ( key, new ImageIcon( nearClass.getResource ( "icons/" + path ) ) );
        }
        return iconsCache.get ( key );
    }
    private void setupToolBar ( WebToolBar toolbar ) {
        toolbar.add ( WebButton.createIconWebButton(loadIcon("toolbar/save.png"), StyleConstants.smallRound, true) );
        toolbar.add ( WebButton.createIconWebButton ( loadIcon ( "toolbar/saveall.png" ), StyleConstants.smallRound, true ) );
        toolbar.addSeparator ();
        toolbar.add ( WebButton.createIconWebButton ( loadIcon ( "toolbar/cut.png" ), StyleConstants.smallRound, true ) );
        toolbar.add ( WebButton.createIconWebButton ( loadIcon ( "toolbar/copy.png" ), StyleConstants.smallRound, true ) );
        toolbar.add ( WebButton.createIconWebButton ( loadIcon ( "toolbar/paste.png" ), StyleConstants.smallRound, true ) );
        toolbar.addToEnd ( WebButton.createIconWebButton ( loadIcon ( "toolbar/settings.png" ), StyleConstants.smallRound, true ) );
    }

    private void init(){
        jToolBar1 = new WebToolBar(WebToolBar.HORIZONTAL);
        setupToolBar(jToolBar1);
        jToolBar1.setFloatable(false);


        jPanel1 = new WebPanel();
        jScrollPane2 = new WebScrollPane(jPanel1);


        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jToolBar1.setRollover(true);

        jPanel1.setForeground(new java.awt.Color(0, 255, 102));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 398, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 263, Short.MAX_VALUE)
        );

        jScrollPane2.setViewportView(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane2)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane2)
                                .addContainerGap())
        );

        pack();
    }
}
