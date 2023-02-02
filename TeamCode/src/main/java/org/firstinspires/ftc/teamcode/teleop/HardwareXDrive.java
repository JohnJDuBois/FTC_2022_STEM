package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class HardwareXDrive{

    // Declare Drive Motor Variaibles
    public DcMotor leftFront = null; // removed leftFront Because did not have expansion hub on at the time and needed to test
    public DcMotor rightFront = null;
    public DcMotor leftBack = null;
    public DcMotor rightBack = null;

    //Arm motor variable for spool that lifts the linear slide
    public DcMotor armMotor = null;

    //An lift motor for the claw uses encoder ticks to check how far it is
    public DcMotor liftMotor = null;

    //Servo on robot that rotates the claw up or down
    public Servo claw = null;

    //init the the current position variables
    public int startPosition = 0;
    public int liftStart = 0;

    // Converting MotorTicks, Gear Ratio, Spool/ Wheel Diameter to Counts Per Inch for Encoder
    public static final double     COUNTS_PER_MOTOR_REV    = 1440 ;    // eg: TETRIX Motor Encoder
    public static final double     DRIVE_GEAR_REDUCTION    = 1.0 ;     // No External Gearing.
    public static final double     SPOOL_DIAMETER_INCHES   = 4.0 ;     // For figuring circumference
    public static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (SPOOL_DIAMETER_INCHES * 3.1415);

    //Variables for claw
    //Open position location and closed position location
    //Use a variable to see if the claw is opened or closed
    public final double CLAW_OPEN_POS = 1.0;
    public final double CLAW_CLOSED_POS = 0;
    boolean clawOpened = false;


    //Turn Speed variable for arm
    public static double     TURN_SPEED  = 0.5;
    public static double     LIFT_TURN_SPEED = 0.35;

    // Stage Length of linear slide stage in inches


    HardwareMap hwMap  = null;



    // Constructor
    public HardwareXDrive(){

    }

    public void init(HardwareMap ahwMap){

        hwMap = ahwMap;

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        leftFront = hwMap.get(DcMotor.class, "leftFront");
        rightFront = hwMap.get(DcMotor.class, "rightFront");
        leftBack = hwMap.get(DcMotor.class, "leftBack");
        rightBack = hwMap.get(DcMotor.class, "rightBack");

        armMotor = hwMap.get(DcMotor.class, "armMotor");
        liftMotor = hwMap.get(DcMotor.class, "liftMotor");


        claw = hwMap.get(Servo.class, "claw");

        //Make sure that both motors with encoders are running and using the encoders
        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //Set the motor to reverse, so we can have the right direction
        liftMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        //The Start positions for both of the motors
        startPosition = armMotor.getCurrentPosition();
        liftStart = liftMotor.getCurrentPosition();


        // To drive forward, most robots need the motor on one side to be reversed, because the axles point in opposite directions.
        // Pushing the left stick forward MUST make robot go forward. So adjust these two lines based on your first test drive.
        // Note: The settings here assume direct drive on left and right wheels.  Gear Reduction or 90 Deg drives may require direction flips
        leftFront.setDirection(DcMotor.Direction.REVERSE);
        rightFront.setDirection(DcMotor.Direction.FORWARD);
        rightBack.setDirection(DcMotor.Direction.FORWARD);
        leftBack.setDirection(DcMotorSimple.Direction.REVERSE);

        armMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        // Set all powers to 0
        setAllMotorPowers(0);

    }
    /*Method sets each Drive Motors Powers  */
    public void setDriveMotorPower(double LFPower, double RFPower, double LBPower, double RBPower){// removed leftFront Because did not have expansion hub on at the time and needed to test
        leftFront.setPower(LFPower);
        rightFront.setPower(RFPower);
        leftBack.setPower(LBPower);
        rightBack.setPower(RBPower);
    }

    // Sets all the Drive Motor Powers
    public void setAllDrivePower(double allPower){
        setDriveMotorPower(allPower, allPower, allPower, allPower);// removed leftFront Because did not have expansion hub on at the time and needed to test
    }

    //Sets every MotorPower
    public void setAllMotorPowers(double allPower){
        setAllDrivePower(allPower);
        armMotor.setPower(allPower);
    }

    // Moves the arm to the inches passed, Note:Sets the position to the inches Example-- If 20 is passed moves it to 20 then if 30 moves it to 30
    public void ArmToPosition(double speed, double Inches){
        int newTarget;


        // Creates Motors new Target Position then sets its target to new position
        newTarget = startPosition + (int) (Inches * COUNTS_PER_INCH);
        armMotor.setTargetPosition(newTarget);

        //Sets arm motor to run to target position
        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // Sets Arm Power to speed param passed
        armMotor.setPower(speed);

    }


    public void LiftToPosition(double speed, double Inches){
        int newTarget;


        // Creates Motors new Target Position then sets its target to new position
        newTarget = startPosition + (int) (Inches * COUNTS_PER_INCH);
        liftMotor.setTargetPosition(newTarget);

        //Sets arm motor to run to target position
        liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // Sets Arm Power to speed param passed
        liftMotor.setPower(speed);

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