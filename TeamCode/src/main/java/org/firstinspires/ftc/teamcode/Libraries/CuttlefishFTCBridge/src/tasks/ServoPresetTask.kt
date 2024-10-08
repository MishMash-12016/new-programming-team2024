package org.firstinspires.ftc.teamcode.Libraries.CuttlefishFTCBridge.src.tasks

import com.roboctopi.cuttlefish.queue.Task
import org.firstinspires.ftc.teamcode.Libraries.CuttlefishFTCBridge.src.devices.CuttleServo

class ServoPresetTask(val servo: CuttleServo, val preset: Int): Task
{
    override fun loop(): Boolean
    {
        servo.goToPreset(preset);
        return true;
    }
}