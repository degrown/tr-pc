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
package au.com.trgtd.tr.view.criteria.screen;

import au.com.trgtd.tr.resource.Resource;
import au.com.trgtd.tr.prefs.ui.utils.WindowUtils;
import java.awt.EventQueue;
import org.openide.util.*;
import org.openide.util.actions.CallableSystemAction;
import org.openide.windows.Mode;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;
import tr.model.Data;
import tr.model.DataLookup;

/**
 * Action which shows the times, energies and priorities screens.
 *
 * @author Jeremy Moore
 */
public class CriteriaAction extends CallableSystemAction {

    public CriteriaAction() {
        super();
        enableDisable();
        Lookup.Result<Data> r = DataLookup.instance().lookupResult(Data.class);
        r.addLookupListener((LookupEvent lookupEvent) -> {
            enableDisable();
        });
    }

    @Override
    protected String iconResource() {
        return Resource.Criteria;
    }

    @Override
    public String getName() {
        return NbBundle.getMessage(getClass(), "CTL_CriteriaAction");
    }

    private void enableDisable() {
        EventQueue.invokeLater(() -> {
            Data data = DataLookup.instance().lookup(Data.class);
            setEnabled(data != null);
        });
    }

    /** Gets the action identifier. */
    public String getID() {
        return "criteria";
    }

    @Override
    public void performAction() {
        EventQueue.invokeLater(() -> {
            Data data = DataLookup.instance().lookup(Data.class);
            if (data == null) {
                return;
            }

            WindowUtils.closeWindows();

            TopComponent tc1 = TimesTopComponent.findInstance();
            TopComponent tc2 = EnergiesTopComponent.findInstance();
            TopComponent tc3 = PrioritiesTopComponent.findInstance();

            Mode mode = WindowManager.getDefault().findMode("Setup");
            if (mode != null) {
                mode.dockInto(tc1);
                mode.dockInto(tc2);
                mode.dockInto(tc3);
            }

            tc1.open();
            tc2.open();
            tc3.open();

            tc1.requestActive();
        });
    }

    @Override
    protected boolean asynchronous() {
        return false;
    }

    @Override
    public HelpCtx getHelpCtx() {
        return new HelpCtx("tr.view.criteria");
    }

}
