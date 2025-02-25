/*
 * ThinkingRock, a project management tool for Personal Computers. 
 * Copyright (C) 2006 Avente Pty Ltd
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package au.com.trgtd.tr.view.projects.templates;

import au.com.trgtd.tr.resource.Icons;
import au.com.trgtd.tr.appl.Constants;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import javax.swing.AbstractButton;
import javax.swing.ActionMap;
import javax.swing.JToolBar;
import javax.swing.text.DefaultEditorKit;
import org.openide.awt.Toolbar;
import org.openide.explorer.ExplorerManager;
import org.openide.explorer.ExplorerUtils;
import org.openide.explorer.view.BeanTreeView;
import org.openide.explorer.view.TreeView;
import org.openide.nodes.Node;
import org.openide.util.HelpCtx;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.util.NbBundle;
import org.openide.util.actions.SystemAction;
import org.openide.windows.TopComponent;
import tr.model.Data;
import tr.model.DataLookup;
import tr.model.Item.Item;
import au.com.trgtd.tr.prefs.ui.GUIPrefs;
import au.com.trgtd.tr.view.ReprocessAction;
import au.com.trgtd.tr.view.ViewUtils;
import au.com.trgtd.tr.view.Window;
import au.com.trgtd.tr.view.projects.ActionNode;
import au.com.trgtd.tr.view.projects.AddActionAction;
import au.com.trgtd.tr.view.projects.AddProjectAction;
import au.com.trgtd.tr.view.projects.EditorTopComponent;
import au.com.trgtd.tr.view.projects.ProjectNode;
import au.com.trgtd.tr.view.projects.ProjectiseAction;
import au.com.trgtd.tr.view.ToggleHideDoneAction;

/**
 * Top component for the templates tree.
 */
public final class TemplatesTopComponent extends Window
        implements LookupListener, ExplorerManager.Provider {
    
    private static final String PREFERRED_ID = "TemplatesTopComponent";
    
    private static TemplatesTopComponent instance;
    
    private transient final ExplorerManager manager = new ExplorerManager();
    private transient boolean initialised;
    private transient Lookup.Result dataResult;
    private transient Lookup.Result itemResult;
    
    // Mantis:772
    private transient JToolBar toolbar;
    
    private TemplatesTopComponent() {
        setName(getText("CTL_TemplatesTopComponent"));
        setToolTipText(getText("HINT_TemplatesTopComponent"));
        setIcon(Icons.ProjectsTemplate.getImage());
        initComponents();
        initialise();
    }
    
    private void initialise() {
        if (initialised) return;
        
        // Mantis:772
        add(getToolbar(), GUIPrefs.getBorderLayoutButtonsPosition());
        
        // data lookup listener to force re-initialisation if data changes
        if (dataResult == null) {
            dataResult = DataLookup.instance().lookupResult(Data.class);
            dataResult.addLookupListener((LookupEvent lookupEvent) -> {
                initialised = false;
            });
        }
        
        Data data = (Data)DataLookup.instance().lookup(Data.class);
        if (data == null) return;
        
        manager.setRootContext(new TemplatesRootNode(data.getRootTemplates(), false));
        manager.getRootContext().setDisplayName(getText("CTL_TemplatesNode"));
        
        ActionMap map = getActionMap();
        map.put(DefaultEditorKit.copyAction, ExplorerUtils.actionCopy(manager));
        map.put(DefaultEditorKit.cutAction, ExplorerUtils.actionCut(manager));
        map.put(DefaultEditorKit.pasteAction, ExplorerUtils.actionPaste(manager));
        map.put("delete", ExplorerUtils.actionDelete(manager, true));
        
        try {
            associateLookup(ExplorerUtils.createLookup(manager, map));
        } catch (IllegalStateException ex) {
            // already associated - ignore
        }
        
        initialised = true;
    }
    
    // Mantis:772
    private JToolBar getToolbar() {
        SystemAction[] actions = new SystemAction[] {
            SystemAction.get(AddActionAction.class),
            null,
            SystemAction.get(AddProjectAction.class),
            null,
            SystemAction.get(ReprocessAction.class),
            null,
            SystemAction.get(ProjectiseAction.class),
            null,
            SystemAction.get(ToggleHideDoneAction.class),
        };
        toolbar = SystemAction.createToolbarPresenter(actions);
        toolbar.setUI((new Toolbar()).getUI());
        toolbar.setFloatable(false);
        toolbar.setOrientation(GUIPrefs.getToolBarOrientation());
        
        Dimension buttonSize = Constants.TOOLBAR_BUTTON_SIZE;
        for (Component component : toolbar.getComponents()) {
            if (component instanceof AbstractButton) {
                component.setPreferredSize(buttonSize);
                component.setMinimumSize(buttonSize);
                component.setMaximumSize(buttonSize);
                component.setSize(buttonSize);
            }
        }
        toolbar.setBorder(ViewUtils.BORDER_TOOLBAR);        
        return toolbar;
    }
    
    @Override
    public void componentOpened() {
        super.componentOpened();
        
        initialise();
    }
    
    @Override
    public void componentActivated() {
        super.componentActivated();
        
        initialise();
        
        EventQueue.invokeLater(() -> {
            activate();
        });
    }
    
    private void activate() {
//      ExplorerUtils.activateActions(manager, true);
        
        // add listener for item selection in the tree
        itemResult = getLookup().lookupResult(Item.class);
        itemResult.addLookupListener(this);
        itemResult.allInstances();
        
        Node[] selectedNodes = manager.getSelectedNodes();
        if (selectedNodes.length == 0) {
            try {
                manager.setSelectedNodes(new Node[] { manager.getRootContext() });
            } catch (Exception ex) {
            }
        } else if (selectedNodes[0] instanceof ProjectNode projectNode) {
//////            Project project = ((ProjectNode) selectedNodes[0]).project;
//////            EditorTopComponent.findInstance().view(project);
            EditorTopComponent.findInstance().view(projectNode);
        } else if (selectedNodes[0] instanceof ActionNode actionNode) {
//////            Action action = ((ActionNode) selectedNodes[0]).action;
//////            EditorTopComponent.findInstance().view(action);
            EditorTopComponent.findInstance().view(actionNode);
        }
    }
    
    @Override
    public void componentDeactivated() {
        super.componentDeactivated();
        
//      ExplorerUtils.activateActions(manager, false);
        
        // remove listener for item selection in the tree
        if (itemResult != null) {
            itemResult.removeLookupListener(this);
            itemResult = null;
        }
    }
    
    public void resultChanged(LookupEvent lookupEvent) {
//////        if (itemResult == null) {
//////            return;
//////        }
//////        Collection instances = itemResult.allInstances();
//////        if (instances == null || instances.isEmpty()) {
//////            return;
//////        }
//////        Object item = instances.iterator().next();
//////        if (item instanceof Action) {
//////            EditorTopComponent.findInstance().view((Action)item);
//////        } else if (item instanceof Project) {
//////            EditorTopComponent.findInstance().view((Project)item);
//////        }
        EventQueue.invokeLater(() -> {
            Node[] nodes = manager.getSelectedNodes();
            Node node = nodes.length > 0 ? nodes[0] : null ;
            EditorTopComponent.findInstance().view(node);
        });
    }
    
    private String getText(String key) {
        return NbBundle.getMessage(getClass(), key);
    }
    
    public TreeView getTreeView() {
        return (TreeView)projectsPane;
    }
    
    /** Generated by the Form Editor. */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        projectsPane = new BeanTreeView();

        setLayout(new java.awt.BorderLayout());

        add(projectsPane, java.awt.BorderLayout.CENTER);

    }// </editor-fold>//GEN-END:initComponents
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane projectsPane;
    // End of variables declaration//GEN-END:variables
    
    /**
     * Gets default instance. Do not use directly: reserved for *.settings files only,
     * i.e. deserialization routines; otherwise you could get a non-deserialized instance.
     * To obtain the singleton instance, use {@link findInstance}.
     */
    public static synchronized TemplatesTopComponent getDefault() {
        if (instance == null) {
            instance = new TemplatesTopComponent();
        }
        return instance;
    }
    
    /**
     * Obtain the TemplatesTopComponent instance. Never call {@link #getDefault} directly!
     */
    public static synchronized TemplatesTopComponent findInstance() {
//        TopComponent tc = WindowManager.getDefault().findTopComponent(PREFERRED_ID);
//        if (tc == null) {
//            ErrorManager.getDefault().log(ErrorManager.WARNING, "Cannot find Templates component. It will not be located properly in the window system.");
//            return getDefault();
//        }
//        if (tc instanceof TemplatesTopComponent) {
//            return (TemplatesTopComponent)tc;
//        }
//        ErrorManager.getDefault().log(ErrorManager.WARNING, "There seem to be multiple components with the '" + PREFERRED_ID + "' ID. That is a potential source of errors and unexpected behavior.");
        return getDefault();
    }
    
    @Override
    public int getPersistenceType() {
//        return TopComponent.PERSISTENCE_ALWAYS;
        return TopComponent.PERSISTENCE_NEVER;
    }
    
    @Override
    protected String preferredID() {
        return PREFERRED_ID;
    }
    
    public ExplorerManager getExplorerManager() {
        return manager;
    }
    
    @Override
    public HelpCtx getHelpCtx() {
        return new HelpCtx("tr.view.projects.template");
    }
    
//    /** replaces this in object stream */
//    public Object writeReplace() {
//        return new ResolvableHelper();
//    }
//
//    final static class ResolvableHelper implements Serializable {
//        private static final long serialVersionUID = 1L;
//        public Object readResolve() {
//            return TemplatesTopComponent.getDefault();
//        }
//    }
    
    @Override
    public void takeFocus() {
        getTreeView().requestFocusInWindow();
    }
    
}
