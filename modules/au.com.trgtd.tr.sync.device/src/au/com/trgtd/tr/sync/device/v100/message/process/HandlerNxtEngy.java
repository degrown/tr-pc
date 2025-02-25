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
package au.com.trgtd.tr.sync.device.v100.message.process;

import au.com.trgtd.tr.sync.device.v100.message.receive.RecvMsg;
import au.com.trgtd.tr.sync.device.v100.data.DataMgr;
import au.com.trgtd.tr.sync.device.v100.message.send.ISender;
import au.com.trgtd.tr.sync.device.v100.message.send.SendMsg;
import au.com.trgtd.tr.sync.device.v100.message.send.SendMsgEnd;
import au.com.trgtd.tr.sync.device.v100.message.send.SendMsgEnergy;

/**
 * Handler for received next energy message.
 *
 * @author Jeremy Moore
 */
class HandlerNxtEngy extends Handler <RecvMsg> {

    private static SendMsg end = new SendMsgEnd();

    HandlerNxtEngy(ISender sender, DataMgr data) {
        super(sender, data);
    }

    @Override
    void handle(RecvMsg nextEnergyMsg) throws Exception
    {
        SendMsgEnergy energyMsg = data.getNxtSendMsgEnergy();
        if (energyMsg == null) {
            sender.send(end);
        } else {
            sender.send(energyMsg);
        }
    }

}
