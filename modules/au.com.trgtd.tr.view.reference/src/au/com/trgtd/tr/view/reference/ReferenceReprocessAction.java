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
package au.com.trgtd.tr.view.reference;

import au.com.trgtd.tr.resource.Icons;
import org.openide.nodes.Node;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.actions.CookieAction;
import tr.model.information.Information;

/**
 * Action to reprocess a referenced information item.
 *
 * @author Jeremy Moore
 */
public class ReferenceReprocessAction extends CookieAction {
    
    public ReferenceReprocessAction() {
        super();
        setIcon(Icons.Reprocess);
    }
    
    /** Gets the display name. */
    public String getName() {
        return NbBundle.getMessage(getClass(), "CTL_ReferenceReprocessAction");
    }
    
    /** Gets help topic. */
    public HelpCtx getHelpCtx() {
        return HelpCtx.DEFAULT_HELP;
    }
    
    public Class[] cookieClasses() {
        return new Class[] { Information.class };
    }
    
    public int mode() {
        return MODE_EXACTLY_ONE;
    }
    
    @Override
    protected boolean asynchronous() {
        return false;
    }
    
    public void performAction(Node[] nodes) {
        ReferenceReprocessCookie cookie = (ReferenceReprocessCookie)nodes[0].getCookie(ReferenceReprocessCookie.class);
        if (cookie != null) {
            cookie.reprocessReference();
        }
    }
    
}

