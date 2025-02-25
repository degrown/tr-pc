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
package au.com.trgtd.tr.view.actors;

import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import tr.model.actor.Actor;

/**
 * List cell renderer for actors combo box.
 *
 * @author Jeremy Moore
 */
public class ActorsListCellRenderer extends JLabel implements ListCellRenderer {

    private final ListCellRenderer std;

    public ActorsListCellRenderer(ListCellRenderer std) {
        if (std == null) {
            throw new NullPointerException("Standard renderer is null.");
        }
        this.std = std;
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value,
            int index, boolean isSelected, boolean cellHasFocus) {

        JLabel lbl = (JLabel)std.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

        if (value instanceof Actor actor) {
            if (actor.isInactive()) {
                lbl.setText("<HTML><STRIKE>" + actor.getName() + "</STRIKE></HTML>");
            } else {
                lbl.setText(actor.getName());
            }
        }

        return lbl;
    }
}
