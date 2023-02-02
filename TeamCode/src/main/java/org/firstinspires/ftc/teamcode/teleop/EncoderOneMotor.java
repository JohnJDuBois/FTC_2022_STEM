/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode.teleop;


import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="Robot:EncoderOneMotor", group="Robot")
@Disabled
public class EncoderOneMotor extends LinearOpMode {

    /* Declare OpMode members. */
    private DcMotor LinearSlideMotor = null;

    private ElapsedTime runtime = new ElapsedTime();

    // Calculate the COUNTS_PER_INCH for your specific drive train.
    // Go to your motor vendor website to determine your motor's COUNTS_PER_MOTOR_REV
    // For external drive gearing, set DRIVE_GEAR_REDUCTION as needed.
    // For example, use a value of 2.0 for a 12-tooth spur gear driving a 24-tooth spur gear.
    // This is gearing DOWN for less speed and more torque.
    // For gearing UP, use a gear ratio less than 1.0. Note this will affect the direction of wheel rotation.
    static final double COUNTS_PER_MOTOR_REV = 1440;    // eg: TETRIX Motor Encoder
    // static final double     DRIVE_GEAR_REDUCTION    = 1.0 ;     // No External Gearing.
    static final double WHEEL_DIAMETER_INCHES = 1.25;     // For figuring circumference
    static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV) / (WHEEL_DIAMETER_INCHES * 3.1415);
    // Note: COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * 3.1415) if gears used on motor

    // TPD 01-12-2023 - I would not make the DRIVE_SPEED 1. It is too fast and too much torque. 
    //                  I would set it to a lower number such as 0.75 or so. 
    static final double DRIVE_SPEED = 1; // Value was changed from 0.6

    //static final double     TURN_SPEED              = 1; // Value was changed from 0.5

    @Override
    public void runOpMode() {

        // Initialize the drive system variables.
        LinearSlideMotor = hardwareMap.get(DcMotor.class, "LinearSlideMotor");

        // To drive forward, most robots need the motor on one side to be reversed, because the axles point in opposite directions.
        // When run, this OpMode should start both motors driving forward. So adjust these two lines based on your first test drive.
        // Note: The settings here assume direct drive on left and right wheels.  Gear Reduction or 90 Deg drives may require direction flips

        LinearSlideMotor.setDirection(DcMotor.Direction.FORWARD);

        LinearSlideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        LinearSlideMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Send telemetry message to indicate successful Encoder reset
        telemetry.addData("Starting at", "%7d ",
                LinearSlideMotor.getCurrentPosition());
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // TPD 01-12-2023 - I would not drive the motor automatically in TeleOp Mode. I would use a 
        //                  gamepad button to start the movement. 
        //
        //                  Personally, I would also probably check the gamepad button status in the 
        //                  while loop using an if statement. I would stop the motor if the button 
        //                  is released. This essentially gives you a kill switch by letting go of 
        //                  the button if it something goes wront. 

        // Note: Reverse movement is obtained by setting a negative distance (not speed)
        //encoderDrive(DRIVE_SPEED,  10, 10.0);  // up 10 Inches with 5 Sec timeout
        //while (opModeIsActive()) {
        //}
    }
}
            /*if (gamepad1.x) {
                //encoderDrive(10, 10, 0);
            }

        }

        /*
         *  Method to perform a relative move, based on encoder counts.
         *  Encoders are not reset as the move is based on the current position.
         *  Move will stop if any of three conditions occur:
         *  1) Move gets to the desired position
         *  2) Move runs out of time
         *  3) Driver stops the OpMode running.
         */
        // TPD 01-12-2023 - Your naming convention does not make sense. Why leftInches? change it to slidePosition
        //                  or something similar. Or even just inches.
        /*public void encoderDrive ( double speed,
        double leftInches,
        double timeoutS){
            int newLeftTarget;
            // int newRightTarget;

            // Ensure that the OpMode is still active
            if (opModeIsActive()) {

                // Determine new target position, and pass to motor controller
                newLeftTarget = LinearSlideMotor.getCurrentPosition() + (int) (leftInches * COUNTS_PER_INCH);
                //newRightTarget = rightDrive.getCurrentPosition() + (int)(rightInches * COUNTS_PER_INCH);
                LinearSlideMotor.setTargetPosition(newLeftTarget);
                //rightDrive.setTargetPosition(newRightTarget);

                // Turn On RUN_TO_POSITION
                LinearSlideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                //rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                // reset the timeout time and start motion.
                runtime.reset();
                LinearSlideMotor.setPower(Math.abs(speed));
                //rightDrive.setPower(Math.abs(speed));

                // keep looping while we are still active, and there is time left, and both motors are running.
                // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
                // its target position, the motion will stop.  This is "safer" in the event that the robot will
                // always end the motion as soon as possible.
                // However, if you require that BOTH motors have finished their moves before the robot continues
                // onto the next step, use (isBusy() || isBusy()) in the loop test.
                while (opModeIsActive()) { // Note: was (runtime.seconds() < timeoutS) &&(LinearSlideMotor.isBusy() )&& rightDrive.isBusy()

                    // TPD 01-12-2023 - Here is where I would put the if statement to check the button status
                    //                  with an else to kill the motors if there is a problem.

                    // Display it for the driver.
                    telemetry.addData("Running to", " %7d ", newLeftTarget); //,  newRightTarget);
                    telemetry.addData("Currently at", " at %7d ",
                            LinearSlideMotor.getCurrentPosition()); //,rightDrive.getCurrentPosition());
                    telemetry.update();
                }

                // Stop all motion;
                //LinearSlideMotor.setPower(0);
                //rightDrive.setPower(0);

                // Turn off RUN_TO_POSITION
                //LinearSlideMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                //rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

                // sleep(250);   // optional pause after each move.
            }
        }
    }
}*/
