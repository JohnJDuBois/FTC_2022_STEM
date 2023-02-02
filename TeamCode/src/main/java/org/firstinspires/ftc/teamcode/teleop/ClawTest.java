package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

//The following program is just a test for the claw with the controller triggers
@TeleOp(name = "ClawTest", group = "robot")
@Disabled
public class ClawTest extends LinearOpMode {
    //Servo on robot that rotates the claw up or down
    public Servo claw = null;

    //Variables for claw
    //Open position location and closed position location
    //Use a variable to see if the claw is opened or closed
    public final double CLAW_OPEN_POS = 0.9;
    public final double CLAW_CLOSED_POS = 0;
    boolean clawOpened = false;

    @Override
    public void runOpMode() {
        waitForStart();

        while (opModeIsActive()){

            // check is either trigger is hit and then opens or closed the claw
            if (gamepad1.right_trigger > 0){
                OpenClaw();
                telemetry.addData("Claw Opened =", "%7d", clawOpened);
            }

            if (gamepad1.left_trigger > 0){
                ClosedClaw();
                telemetry.addData("Claw Opened =", "%7d", clawOpened);
            }

            telemetry.update();
        }

    }

    public void OpenClaw(){
        claw.setPosition(CLAW_OPEN_POS);
        clawOpened = true;
    }

    public void ClosedClaw(){
        claw.setPosition(CLAW_CLOSED_POS);
        clawOpened = false;
    }

}