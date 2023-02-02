package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;



@TeleOp(name = "DeadWheelEncoder Test:", group = "robot")
@Disabled
public class DeadWheelTest extends LinearOpMode {

    public static final double TICKS_PER_MOTOR_REV = 1440;
    public static final double WHEEL_RADIUS = 2;
    public static final double GEAR_RATIO = 1;
    public static final double TICKS_PER_INCH = (TICKS_PER_MOTOR_REV * GEAR_RATIO/ WHEEL_RADIUS);


    public DcMotor DeadWheelX = null;
    public DcMotor DeadWheelY = null;

    @Override
    public void runOpMode(){

        DeadWheelX = hardwareMap.get(DcMotor.class, "DeadWheelX");
        DeadWheelY = hardwareMap.get(DcMotor.class, "DeadWheelY");

        DeadWheelX.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        DeadWheelY.setMode(DcMotor.RunMode.RUN_USING_ENCODER);



        waitForStart();

        while (opModeIsActive()){


        }

    }

}
