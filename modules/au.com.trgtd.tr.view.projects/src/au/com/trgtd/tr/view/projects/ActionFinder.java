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
package au.com.trgtd.tr.view.projects;

import org.openide.nodes.Node;
import tr.model.action.Action;

/**
 * Interface for searchable nodes.
 *
 * @author Jeremy Moore
 */
public interface ActionFinder {
    /**
     * Find the node of the given action if possible.
     * @param action The object
     * @return the node of the action or null if one can not be found.
     */
    public Node find(Action action);
    
}
