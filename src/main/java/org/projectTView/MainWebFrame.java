package org.projectTView;

import com.alee.extended.layout.VerticalFlowLayout;
import com.alee.extended.painter.TitledBorderPainter;
import com.alee.extended.panel.BorderPanel;
import com.alee.extended.panel.GroupPanel;
import com.alee.extended.panel.WebButtonGroup;
import com.alee.extended.window.ComponentMoveAdapter;
import com.alee.laf.WebLookAndFeel;
import com.alee.laf.button.WebToggleButton;
import com.alee.laf.checkbox.WebCheckBox;
import com.alee.laf.combobox.WebComboBox;
import com.alee.laf.label.WebLabel;
import com.alee.laf.menu.MenuBarStyle;
import com.alee.laf.menu.WebMenu;
import com.alee.laf.menu.WebMenuBar;
import com.alee.laf.menu.WebMenuItem;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebFrame;
import com.alee.laf.rootpane.WebRootPaneUI;
import com.alee.laf.separator.WebSeparator;
import com.alee.laf.slider.WebSlider;
import com.alee.laf.text.WebTextField;

import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Created by almu0214 on 03.09.2014.
 */
public class MainWebFrame extends WebFrame{
    public MainWebFrame () throws HeadlessException {
        super ( "Example frame" );
        setIconImages ( WebLookAndFeel.getImages() );
        setDefaultCloseOperation ( WebFrame.DISPOSE_ON_CLOSE );

        ComponentMoveAdapter.install(getRootPane(), MainWebFrame.this);

        // Sample menu bar
        final WebMenuBar menuBar = new WebMenuBar ();
        menuBar.setUndecorated ( true );
        menuBar.setBorder ( BorderFactory.createEmptyBorder(2, 2, 2, 2) );
        menuBar.add ( new WebMenu( "Menu 1" )
        {
            {
                add ( new WebMenuItem( "Menu item 1" ) );
                add ( new WebMenuItem ( "Menu item 2" ) );
                addSeparator ();
                add ( new WebMenuItem ( "Menu item 3" ) );
            }
        } );
        menuBar.add ( new WebMenu ( "Menu 2" )
        {
            {
                add ( new WebMenuItem ( "Menu item 1" ) );
                add ( new WebMenuItem ( "Menu item 2" ) );
                add ( new WebMenuItem ( "Menu item 3" ) );
            }
        } );
        menuBar.add ( new WebMenu ( "Menu 3" )
        {
            {
                add ( new WebMenuItem ( "Menu item 1" ) );
                add ( new WebMenuItem ( "Menu item 2" ) );
            }
        } );
        menuBar.add ( new WebMenu ( "Menu 4" )
        {
            {
                add ( new WebMenuItem ( "Menu item 1" ) );
                addSeparator ();
                add ( new WebMenuItem ( "Menu item 2" ) );
            }
        } );
        setJMenuBar ( menuBar );

        // Options panel
        add ( new BorderPanel( new WebPanel( new VerticalFlowLayout( 10, 10 ) )
        {
            {
                setMargin ( 15 );

                final TitledBorderPainter titledBorderPainter = new TitledBorderPainter ( "Window settings" );
                titledBorderPainter.setTitleOffset ( 10 );
                titledBorderPainter.setRound ( Math.max ( 0, MainWebFrame.this.getRound () - 2 ) );
                setPainter ( titledBorderPainter );

                final WebCheckBox showTitle = new WebCheckBox ( "Show title" )
                {
                    {
                        setSelected ( MainWebFrame.this.isShowTitleComponent () );
                        addItemListener ( new ItemListener()
                        {
                            @Override
                            public void itemStateChanged ( ItemEvent e )
                            {
                                MainWebFrame.this.setShowTitleComponent ( isSelected () );
                                MainWebFrame.this.pack ();
                            }
                        } );
                    }
                };
                add ( new GroupPanel( 10, showTitle, new WebTextField( MainWebFrame.this.getTitle (), 1 )
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
                                if ( !MainWebFrame.this.getTitle ().equals ( getText () ) )
                                {
                                    MainWebFrame.this.setTitle ( getText () );
                                    MainWebFrame.this.pack ();
                                }
                            }
                        } );
                    }
                } ) );

                add ( new WebSeparator ( false, true ) );

                final WebCheckBox showWindowButtons = new WebCheckBox ( "Show window buttons" )
                {
                    {
                        setSelected ( MainWebFrame.this.isShowWindowButtons () );
                        addItemListener ( new ItemListener ()
                        {
                            @Override
                            public void itemStateChanged ( ItemEvent e )
                            {
                                MainWebFrame.this.setShowWindowButtons ( isSelected () );
                                MainWebFrame.this.pack ();
                            }
                        } );
                    }
                };
                add ( new GroupPanel ( 10, showWindowButtons, new WebButtonGroup( new WebToggleButton ( WebRootPaneUI.minimizeIcon )
                {
                    {
                        setSelected ( MainWebFrame.this.isShowMinimizeButton () );
                        addItemListener ( new ItemListener ()
                        {
                            @Override
                            public void itemStateChanged ( ItemEvent e )
                            {
                                MainWebFrame.this.setShowMinimizeButton ( isSelected () );
                            }
                        } );
                    }
                }, new WebToggleButton ( WebRootPaneUI.maximizeIcon )
                {
                    {
                        setSelected ( MainWebFrame.this.isShowMaximizeButton () );
                        addItemListener ( new ItemListener ()
                        {
                            @Override
                            public void itemStateChanged ( ItemEvent e )
                            {
                                MainWebFrame.this.setShowMaximizeButton ( isSelected () );
                            }
                        } );
                    }
                }, new WebToggleButton( WebRootPaneUI.closeIcon )
                {
                    {
                        setSelected ( MainWebFrame.this.isShowCloseButton () );
                        addItemListener ( new ItemListener ()
                        {
                            @Override
                            public void itemStateChanged ( ItemEvent e )
                            {
                                MainWebFrame.this.setShowCloseButton ( isSelected () );
                            }
                        } );
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
                        setSelected ( MainWebFrame.this.isAttachButtons () );
                        addItemListener ( new ItemListener ()
                        {
                            @Override
                            public void itemStateChanged ( ItemEvent e )
                            {
                                MainWebFrame.this.setAttachButtons ( isSelected () );
                                MainWebFrame.this.pack ();
                            }
                        } );
                    }
                } );

                add ( new WebCheckBox ( "Group window buttons" )
                {
                    {
                        setSelected ( MainWebFrame.this.isGroupButtons () );
                        addItemListener ( new ItemListener ()
                        {
                            @Override
                            public void itemStateChanged ( ItemEvent e )
                            {
                                MainWebFrame.this.setGroupButtons ( isSelected () );
                                MainWebFrame.this.pack ();
                            }
                        } );
                    }
                } );

                add ( new WebSeparator ( false, true ) );

                final WebCheckBox showMenuBar = new WebCheckBox ( "Show menu bar" )
                {
                    {
                        setSelected ( MainWebFrame.this.isShowMenuBar () );
                        addItemListener ( new ItemListener ()
                        {
                            @Override
                            public void itemStateChanged ( ItemEvent e )
                            {
                                MainWebFrame.this.setShowMenuBar ( isSelected () );
                                MainWebFrame.this.pack ();
                            }
                        } );
                    }
                };
                add ( new GroupPanel ( 10, showMenuBar, new WebComboBox( new String[]{ "undecorated", "attached", "standalone" } )
                {
                    {
                        addActionListener ( new ActionListener()
                        {
                            @Override
                            public void actionPerformed ( ActionEvent e )
                            {
                                int i = getSelectedIndex ();
                                if ( i == 0 )

                                {
                                    menuBar.setUndecorated ( true );
                                }
                                else
                                {
                                    menuBar.setUndecorated ( false );
                                    menuBar.setMenuBarStyle ( i == 1 ? MenuBarStyle.attached : MenuBarStyle.standalone );
                                }
                                MainWebFrame.this.pack ();
                            }
                        } );
                        setEnabled ( showMenuBar.isSelected () );
                        showMenuBar.addItemListener ( new ItemListener ()
                        {
                            @Override
                            public void itemStateChanged ( ItemEvent e )
                            {
                                setEnabled ( showMenuBar.isSelected () );
                            }
                        } );
                    }
                } ) );

                add ( new WebSeparator ( false, true ) );

                add ( new WebCheckBox ( "Show resize corner" )
                {
                    {
                        setSelected ( MainWebFrame.this.isShowResizeCorner () );
                        addItemListener ( new ItemListener ()
                        {
                            @Override
                            public void itemStateChanged ( ItemEvent e )
                            {
                                MainWebFrame.this.setShowResizeCorner ( isSelected () );
                                MainWebFrame.this.pack ();
                            }
                        } );
                    }
                } );

                add ( new WebSeparator( false, true ) );

                add ( new WebPanel ()
                {
                    {
                        setOpaque ( false );
                        setLayout ( new BorderLayout ( 10, 0 ) );
                        add ( new WebLabel ( "Corners round" ), BorderLayout.LINE_START );
                        add ( new WebSlider ( 0, 10, MainWebFrame.this.getRound () )
                        {
                            {
                                putClientProperty ( GroupPanel.FILL_CELL, true );
                                addChangeListener ( new ChangeListener ()
                                {
                                    @Override
                                    public void stateChanged ( ChangeEvent e )
                                    {
                                        menuBar.setRound ( Math.max ( 0, MainWebFrame.this.getRound () - 2 ) );
                                        titledBorderPainter.setRound ( Math.max ( 0, MainWebFrame.this.getRound () - 2 ) );
                                        MainWebFrame.this.setRound ( getValue () );
                                    }
                                } );
                            }
                        }, BorderLayout.LINE_END );
                    }
                } );
                add ( new WebPanel ()
                {
                    {
                        setOpaque ( false );
                        setLayout ( new BorderLayout ( 10, 0 ) );
                        add ( new WebLabel( "Shade width" ), BorderLayout.LINE_START );
                        add ( new WebSlider( 0, 50, MainWebFrame.this.getShadeWidth () )
                        {
                            {
                                putClientProperty ( GroupPanel.FILL_CELL, true );
                                addChangeListener ( new ChangeListener()
                                {
                                    @Override
                                    public void stateChanged ( ChangeEvent e )
                                    {
                                        MainWebFrame.this.setShadeWidth ( getValue () );
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

