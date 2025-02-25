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
package au.com.trgtd.tr.report.sa;

import java.io.File;
import org.openide.modules.InstalledFileLocator;

/**
 * Resources.
 *
 * @author Jeremy Moore
 */
public class Resources {

    public static final File FILE_REPORT = getInstalledFile("resource/reports/SingleActions.jasper");
    public static final File FILE_REPORT_BY_DESCR = getInstalledFile("resource/reports/SingleActionsByDescr.jasper");
    public static final File FILE_REPORT_BY_DATE = getInstalledFile("resource/reports/SingleActionsByActionDate.jasper");
    public static final File FILE_REPORT_BY_PRIORITY = getInstalledFile("resource/reports/SingleActionsByPriority.jasper");
    public static final File FILE_REPORT_LTR = getInstalledFile("resource/reports/SingleActionsLTR.jasper");
    public static final File FILE_REPORT_BY_DESCR_LTR = getInstalledFile("resource/reports/SingleActionsByDescrLTR.jasper");
    public static final File FILE_REPORT_BY_DATE_LTR = getInstalledFile("resource/reports/SingleActionsByActionDateLTR.jasper");
    public static final File FILE_REPORT_BY_PRIORITY_LTR = getInstalledFile("resource/reports/SingleActionsByPriorityLTR.jasper");

    private static final String CODE_NAME_BASE= "au.com.trgtd.tr.report.sa";
    
    private static File getInstalledFile(String path) {
        File file = InstalledFileLocator.getDefault().locate(path, CODE_NAME_BASE, false);
        if (file != null && file.isFile()) {
            return file;
        }
        return null;
    }

}
