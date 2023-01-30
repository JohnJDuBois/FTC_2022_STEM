package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

//Declaring Teleop mode and name of mode
@TeleOp(name = "TeleOpMode: XDrive", group = "drive")
public class XDrive extends LinearOpMode {
    HardwareXDrive robot = new HardwareXDrive();
    private ElapsedTime runtime = new ElapsedTime();

    // variable to represent the arm stage 0-3
    public int stage = 0;


    //Stage Each Stage Length Increase or decrease these values depending on your height needed
    public int stage0Height = 0;
    public int stage1Height = 20;
    public int stage2Height = 30;
    public int stage3Height = 40;

    @Override
    public void runOpMode(){

        // Init the robot hardware map(Motors servos)
        robot.init(hardwareMap);

        // Saves the position of when linear slide is on the ground
        int stage0 = robot.armMotor.getCurrentPosition();

        // Update Driver Hub or Phone that the robot is ready to run
        telemetry.addData("Status:", "Ready to Run");
        telemetry.update();

        waitForStart();
        runtime.reset();

        // Shows starting position of the arm
        telemetry.addData("Arm_Starting_at...:", "%7d", robot.armMotor.getCurrentPosition());
        telemetry.update();

        while (opModeIsActive()){

            // Gets the the imput of the gamepad and adjest the Motors power
            double max;

            double axial = -gamepad1.left_stick_y;
            double lateral = gamepad1.left_stick_x;
            double yaw = gamepad1.right_stick_x;

            double leftFrontPower = axial + lateral + yaw;
            double rightFrontPower = axial - lateral - yaw;
            double leftBackPower = axial - lateral + yaw;
            double rightBackPower = axial + lateral - yaw;

            max = Math.max(Math.abs(leftFrontPower), Math.abs(rightFrontPower));
            max = Math.max(max, Math.abs(leftBackPower));
            max = Math.max(max, Math.abs(rightBackPower));


            //Divides the motor powers by the max
            if (max > 1.0)
            {
                leftFrontPower /= max;
                rightFrontPower /= max;
                leftBackPower /= max;
                rightBackPower /= max;
            }

            // Uses the Method to set all motor powers then Ten Telemetry to see how much power each is getting
            robot.setDriveMotorPower(leftFrontPower, rightFrontPower, leftBackPower, rightBackPower); // removed leftFront Because did not have expansion hub on at the time and needed to test
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Front left/Right", "%4.2f, %4.2f", leftFrontPower, rightFrontPower);
            telemetry.addData("Back  left/Right", "%4.2f, %4.2f", leftBackPower, rightBackPower);

            // Uses the ArmStage Method to increase the stage if X is pressed and decrease if a is pressed
            telemetry.addData("Arm Motor Locat:", "%7d", robot.armMotor.getCurrentPosition());
            telemetry.addData("liftLocat:", "%7d", robot.liftMotor.getCurrentPosition());


            //NOTE: All Commented lines of code are the old stage system
            //First make sures the motor is not busy
            /*if (!robot.armMotor.isBusy()){
                // checks if the user hit the button and if its greater then 0 then goes down a stage
                if (gamepad1.a && stage > 0) {
                    stage--;
                }

                // Checks if the user has pressed gamepad button and goes up a stage
                if (gamepad1.x && stage < 3) {

                    //Add increase the stage
                    stage++;
                }


                if (stage == 0){
                    robot.armMotor.setTargetPosition(stage0);
                    robot.armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                }

                //Checks for each stage and sets the robots height to that stage
                if (stage == 1) {
                    robot.ArmToPosition(robot.TURN_SPEED, stage1Length);
                }

                else if (stage == 2) {
                    robot.ArmToPosition(robot.TURN_SPEED, stage2Length);
                }

                else if (stage == 3) {
                    robot.ArmToPosition(robot.TURN_SPEED, stage3Legnth);
                }

            }*/

            /* Note: changed this will set the slide to the stages height if certain button is pressed
            Note: Before the competition change the gamepad to gamepad2
            it is on gamepad1 for testing purposes only
            This is the new system for the stage where if you press a button it just takes you to that stage*/
            //Checks to first make sure the motor is not busy
            if (!robot.armMotor.isBusy()) {
                //Then checks for each button and sets it to the stage height needed for the arm/LinearSlide
                if (gamepad1.b){
                    robot.ArmToPosition(robot.TURN_SPEED, stage0Height);
                    stage = 0;
                }

                else if (gamepad1.a) {
                    robot.ArmToPosition(robot.TURN_SPEED, stage1Height);
                    stage = 1;
                }

                else if (gamepad1.x) {
                    robot.ArmToPosition(robot.TURN_SPEED, stage2Height);
                    stage = 2;
                }

                else if (gamepad1.y) {
                    robot.ArmToPosition(robot.TURN_SPEED, stage3Height);
                    stage = 3;
                }


            }

            if (!robot.liftMotor.isBusy()){
                //Motor for the armLift
                //Note: we use an motor on our arm lift instead of an servo due to issues with torque
                //We check the encoder ticks to see how far we need to move
                if (gamepad1.right_bumper){
                    robot.LiftToPosition(robot.LIFT_TURN_SPEED, 9);
                }

                else if (gamepad1.left_bumper){
                    robot.LiftToPosition(robot.LIFT_TURN_SPEED, -9);
                }


            }

            if (gamepad1.right_trigger > 0){
                robot.OpenClaw();
            }

            else if (gamepad1.left_trigger > 0){
                robot.ClosedClaw();
            }


            telemetry.addData("Stage", "%7d", stage);
            telemetry.addData("Claw Opened:", "%7d", robot.clawOpened);
            telemetry.update();

        }

    }
}
