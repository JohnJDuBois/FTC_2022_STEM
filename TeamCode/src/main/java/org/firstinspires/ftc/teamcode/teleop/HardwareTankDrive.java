package org.firstinspires.ftc.teamcode.teleop;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

public class HardwareTankDrive
{
    /*Public OpMode members.  */
    public DcMotor left = null;
    public DcMotor right = null;

    HardwareMap hwMap = null;
    private ElapsedTime period = new ElapsedTime();

    /* Constructor */
    public HardwareTankDrive(){

    }

    public void init(HardwareMap ahwMap) {
        //Save reference to Hardware map
        hwMap = ahwMap;

        // Define and Initialize Motors
        left = hwMap.get(DcMotor.class, "left");
        right = hwMap.get(DcMotor.class, "right");


        // Reversing
        right.setDirection(DcMotor.Direction.FORWARD);
        left.setDirection(DcMotor.Direction.REVERSE);


        // Set all motor powers to zero power
        setMotorPowers(0);

    }

    /**
     * Sets all motor powers to given speeds
     * @param LPower
     * @param RPower
     */
    public void setMotorPowers(double LPower, double RPower){
        left.setPower(LPower);
        right.setPower(RPower);
    }

    public void setMotorPowers(double allPower) {
        setMotorPowers(allPower, allPower);
    }
}