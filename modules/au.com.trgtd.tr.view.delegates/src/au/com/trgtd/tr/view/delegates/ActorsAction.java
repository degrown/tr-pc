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
package au.com.trgtd.tr.view.delegates;

import au.com.trgtd.tr.appl.InitialAction;
import java.awt.EventQueue;
import org.openide.util.HelpCtx;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.NbBundle;
import org.openide.util.actions.CallableSystemAction;
import org.openide.windows.Mode;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;
import tr.model.Data;
import tr.model.DataLookup;
import au.com.trgtd.tr.prefs.ui.utils.WindowUtils;
import au.com.trgtd.tr.view.delegates.screen.ActorsTopComponent;

/**
 * Action which shows the actors screen.
 *
 * @author Jeremy Moore
 */
public class ActorsAction extends CallableSystemAction implements InitialAction {

    public ActorsAction() {
        super();
        enableDisable();
        Lookup.Result r = DataLookup.instance().lookupResult(Data.class);
        r.addLookupListener((LookupEvent lookupEvent) -> {
            enableDisable();
        });
    }

    @Override
    public String getName() {
        return NbBundle.getMessage(getClass(), "CTL_ActorsAction");
    }

    @Override
    protected String iconResource() {
        return "au/com/trgtd/tr/view/delegates/Delegates.png";
    }

    private void enableDisable() {
        Data data = DataLookup.instance().lookup(Data.class);
        setEnabled(data != null);
    }

    /** Gets the action identifier. */
    @Override
    public String getID() {
        return "actors";
    }

    @Override
    public void performAction() {
        EventQueue.invokeLater(() -> {
            Data data = DataLookup.instance().lookup(Data.class);
            if (data == null) {
                return;
            }

            WindowUtils.closeWindows();

            TopComponent tc = ActorsTopComponent.findInstance();

            Mode mode = WindowManager.getDefault().findMode("Setup");
            if (mode != null) {
                mode.dockInto(tc);
            }

            tc.open();
            tc.requestActive();
        });
    }

    @Override
    protected boolean asynchronous() {
        return false;
    }

    @Override
    public HelpCtx getHelpCtx() {
        return new HelpCtx("tr.view.delegates");
    }

}
