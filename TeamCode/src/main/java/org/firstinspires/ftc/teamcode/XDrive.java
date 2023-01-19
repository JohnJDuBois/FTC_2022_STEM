package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

//Declaring Teleop mode and name of mode
@TeleOp(name = "TeleOpMode: XDrive", group = "drive")
public class XDrive extends LinearOpMode {
    HardwareXDrive robot = new HardwareXDrive();
    private ElapsedTime runtime = new ElapsedTime();

    // variable to represent the arm stage 0-3
    public int stage = 0;

    public int stage0 = 0;
    public int stageLength = 20;

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

        // Shows starting postition of the arm
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
            robot.setDriveMotorPower(rightFrontPower, leftBackPower, rightBackPower); // removed leftFront Because did not have expansion hub on at the time and needed to test
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Front left/Right", "%4.2f", rightFrontPower);
            telemetry.addData("Back  left/Right", "%4.2f, %4.2f", leftBackPower, rightBackPower);

            // Uses the ArmStage Method to increase the stage if X is pressed and decrease if a is pressed
            telemetry.addData("Arm Motor Locat:", "%7d", robot.armMotor.getCurrentPosition());

            if (!robot.armMotor.isBusy()){
                if (gamepad1.x) {

                stage++;

                if (stage == 1) {
                    robot.ArmToPosition(robot.TURN_SPEED, stage * stageLength);
                } else if (stage == 2) {
                    robot.ArmToPosition(robot.TURN_SPEED, stage * stageLength);
                } else if (stage == 3) {
                    robot.ArmToPosition(robot.TURN_SPEED, stage * stageLength);
                }

                //Makes sure the Stage never is greater then 3
                if (stage > 3) {
                    stage = 3;
                }
            }

            }

            if (!robot.armMotor.isBusy()) {
                if (gamepad1.a && stage > 0) {
                    stage--;
                    // Sets Turn Speed to Negative so motor rewinds
                    robot.ArmToPosition(-robot.TURN_SPEED, -stageLength);

                }
            }
            if (gamepad1.y) {
                stage = 0;
                robot.armMotor.setTargetPosition(stage0);
                robot.armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }

            //ArmStageIncrease();
            //ArmStageDecrease();

            telemetry.addData("Stage", "%7d", stage);
            telemetry.update();


        // Method to increase the Arm for each stage check using a button
        /*public void ArmStageIncrease() {
            // Checks if the button then adds one to the stage
            // checks what stage the stage is on then multiples the length but that amount

        }*/

        // Same method But decreases the Stage
        /*public void ArmStageDecrease(){
            // Make sure the Stage is not zero and rewinding

        }*/


        /*public void ArmSetStage0(){
            stage0 = robot.armMotor.getCurrentPosition();


        */}

    }
}