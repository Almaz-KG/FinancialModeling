package org.projectTView;

import com.alee.extended.layout.VerticalFlowLayout;
import com.alee.extended.painter.TitledBorderPainter;
import com.alee.extended.panel.BorderPanel;
import com.alee.extended.panel.GroupPanel;
import com.alee.extended.panel.WebButtonGroup;
import com.alee.extended.window.ComponentMoveAdapter;
import com.alee.global.StyleConstants;
import com.alee.laf.WebLookAndFeel;
import com.alee.laf.button.WebButton;
import com.alee.laf.button.WebToggleButton;
import com.alee.laf.checkbox.WebCheckBox;
import com.alee.laf.combobox.WebComboBox;
import com.alee.laf.label.WebLabel;
import com.alee.laf.menu.MenuBarStyle;
import com.alee.laf.menu.WebMenu;
import com.alee.laf.menu.WebMenuBar;
import com.alee.laf.menu.WebMenuItem;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebDialog;
import com.alee.laf.rootpane.WebFrame;
import com.alee.laf.rootpane.WebRootPaneUI;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.separator.WebSeparator;
import com.alee.laf.slider.WebSlider;
import com.alee.laf.splitpane.WebSplitPane;
import com.alee.laf.text.WebTextField;
import com.alee.laf.toolbar.WebToolBar;
import org.projectTModel.entities.StockHistory;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by almu0214 on 03.09.2014.
 */
public class ChartWebFrame extends WebFrame{

    public ChartWebFrame(){
        init();
    }

    private void init(){
        setIconImages ( WebLookAndFeel.getImages() );
        setDefaultCloseOperation ( WebFrame.DISPOSE_ON_CLOSE );

        ComponentMoveAdapter.install(getRootPane(), ChartWebFrame.this);

        // Options panel
        add ( new BorderPanel( new WebPanel ( new VerticalFlowLayout( 10, 10 ) )
        {
            {
                setMargin ( 15 );

                final TitledBorderPainter titledBorderPainter = new TitledBorderPainter( "Window settings" );
                titledBorderPainter.setTitleOffset ( 10 );
                titledBorderPainter.setRound ( Math.max ( 0, ChartWebFrame.this.getRound () - 2 ) );
                setPainter ( titledBorderPainter );

                final WebCheckBox showTitle = new WebCheckBox ( "Show title" )
                {
                    {
                        setSelected ( ChartWebFrame.this.isShowTitleComponent () );
                        addItemListener ( new ItemListener ()
                        {
                            @Override
                            public void itemStateChanged ( ItemEvent e )
                            {
                                ChartWebFrame.this.setShowTitleComponent ( isSelected () );
                                ChartWebFrame.this.pack ();
                            }
                        } );
                    }
                };
                add ( new GroupPanel ( 10, showTitle, new WebTextField( ChartWebFrame.this.getTitle (), 1 )
                {
                    {
                        putClientProperty ( GroupPanel.FILL_CELL, true );
                        setEnabled ( showTitle.isSelected () );
                        showTitle.addItemListener ( new ItemListener ()
                        {
                            @Override
                            public void itemStateChanged ( ItemEvent e )
                            {
                                setEnabled ( showTitle.isSelected () );
                            }
                        } );
                        addCaretListener ( new CaretListener()
                        {
                            @Override
                            public void caretUpdate ( CaretEvent e )
                            {
                                if ( !ChartWebFrame.this.getTitle ().equals ( getText () ) )
                                {
                                    ChartWebFrame.this.setTitle ( getText () );
                                    ChartWebFrame.this.pack ();
                                }
                            }
                        } );
                    }
                } ) );

                add ( new WebSeparator( false, true ) );


                final WebCheckBox showWindowButtons = new WebCheckBox ( "Show window buttons" )
                {
                    {
                        setSelected ( ChartWebFrame.this.isShowWindowButtons () );
                        addItemListener ( new ItemListener ()
                        {
                            @Override
                            public void itemStateChanged ( ItemEvent e )
                            {
                                ChartWebFrame.this.setShowWindowButtons ( isSelected () );
                                ChartWebFrame.this.pack ();
                            }
                        } );
                    }
                };
                add ( new GroupPanel ( 10, showWindowButtons, new WebButtonGroup( new WebToggleButton( WebRootPaneUI.minimizeIcon )
                {
                    {
                        setSelected ( ChartWebFrame.this.isShowMinimizeButton () );
                        addItemListener ( (e) -> ChartWebFrame.this.setShowMinimizeButton ( isSelected () ));
                    }
                }, new WebToggleButton ( WebRootPaneUI.maximizeIcon )
                {
                    {
                        setSelected ( ChartWebFrame.this.isShowMaximizeButton () );
                        addItemListener ( (e) -> ChartWebFrame.this.setShowMaximizeButton ( isSelected () ));
                    }
                }, new WebToggleButton ( WebRootPaneUI.closeIcon )
                {
                    {
                        setSelected ( ChartWebFrame.this.isShowCloseButton () );
                        addItemListener ( (e)->ChartWebFrame.this.setShowCloseButton ( isSelected () ) );
                    }
                }
                )
                {
                    {
                        setButtonsMargin ( 2, 4, 2, 4 );
                        setButtonsDrawFocus ( false );
                        setEnabled ( showWindowButtons.isSelected () );
                        showWindowButtons.addItemListener ( new ItemListener ()
                        {
                            @Override
                            public void itemStateChanged ( ItemEvent e )
                            {
                                setEnabled ( showWindowButtons.isSelected () );
                            }
                        } );
                    }
                } ) );

                add ( new WebCheckBox ( "Attach window buttons to sides" )
                {
                    {
                        setSelected ( ChartWebFrame.this.isAttachButtons () );
                        addItemListener ( new ItemListener ()
                        {
                            @Override
                            public void itemStateChanged ( ItemEvent e )
                            {
                                ChartWebFrame.this.setAttachButtons ( isSelected () );
                                ChartWebFrame.this.pack ();
                            }
                        } );
                    }
                } );

                add ( new WebCheckBox ( "Group window buttons" )
                {
                    {
                        setSelected ( ChartWebFrame.this.isGroupButtons () );
                        addItemListener ( new ItemListener ()
                        {
                            @Override
                            public void itemStateChanged ( ItemEvent e )
                            {
                                ChartWebFrame.this.setGroupButtons ( isSelected () );
                                ChartWebFrame.this.pack ();
                            }
                        } );
                    }
                } );

                add ( new WebSeparator ( false, true ) );



                add ( new WebSplitPane(WebSplitPane.VERTICAL_SPLIT){
                    {
                        setDoubleBuffered(true);
                        add(new WebButton("UP"));
                        add(new WebButton("DOWN"));
                    }

                });

                add ( new WebSeparator ( false, true ) );
                add ( new WebPanel ()
                {
                    {
                        setOpaque ( false );
                        setLayout ( new BorderLayout ( 10, 0 ) );
                        add ( new WebLabel( "Shade width" ), BorderLayout.LINE_START );
                        add ( new WebSlider( 0, 50, ChartWebFrame.this.getShadeWidth () )
                        {
                            {
                                putClientProperty ( GroupPanel.FILL_CELL, true );
                                addChangeListener ( new ChangeListener()
                                {
                                    @Override
                                    public void stateChanged ( ChangeEvent e )
                                    {
                                        ChartWebFrame.this.setShadeWidth ( getValue () );
                                    }
                                } );
                            }
                        }, BorderLayout.LINE_END );
                    }
                } );
            }
        }, 10 ) );
    }

}
