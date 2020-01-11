
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

// autonomous program that drives bot forward a set distance, stops then
// backs up to the starting point using encoders to measure the distance.
// This example assumes there is one encoder, attached to the left motor

@Autonomous(name="Autonomous-FoundationBlue")
//@disabled
public class AutonomousFoundationBlue extends LinearOpMode
{
    RobotMecanum robotMecanum;

    @Override
    public void runOpMode() throws InterruptedException
    {
        robotMecanum = new RobotMecanum(hardwareMap, this, false);

        //robotMecanum.initiateVuforia();

        robotMecanum.gyro.calibrate();

        //==========================================================================================
        //Pre init

        robotMecanum.hooks(true);
        robotMecanum.claw(true);

        telemetry.addData("Step 1", "init finished");
        telemetry.update();

        waitForStart();

        //==========================================================================================
        //Official Start

        sideFoundation(1, 750, 1200, 500, 260, 2000, 500, 1000, 500);
        //robotMecanum.driveIncrement(0.75, 0.2, 4000);
        //robotMecanum.turnGyro(90,0.2,true);
    }


    /**
     * @param isRedField -1 means you are on the red field
     * @param driveForwardTime1 the amount of time to drive forward to the first block
     * @param driveBackTime the amount of time to move back to prepare to strafe
     * @param strafeFoundationTime1 amount of time to strafe to the other side
     * @param strafeBrickTime1 amount of time to strafe back to the bricks
     * @param driveForwardTime2 amount of time to drive forward to the second/third block
     * @param strafeFoundationTime2 amount of time to strafe to the other side
     * @param strafeBrickTime2 amount of time to strafe back to the midline
     */
    /*public void sideBricks(int isRedField, int driveForwardTime1, int driveBackTime, int strafeFoundationTime1, int strafeBrickTime1, int driveForwardTime2, int strafeFoundationTime2, int strafeBrickTime2)
    {
        sleep(4000000);
        //isRedField is -1 for
        robotMecanum.omniTime(0.7, 0, driveForwardTime1, true );

        claw("extended");
        sleep(2300);
        robotMecanum.omniTime(-0.6, 0, driveBackTime, true );

        robotMecanum.omniTime(0, 0.7 * -isRedField, strafeFoundationTime1, true );

        claw("home");
        sleep(500);
        robotMecanum.omniTime(0, 0.7 * isRedField, strafeBrickTime1, true );

        robotMecanum.omniTime(0.7, 0, driveForwardTime2, true );

        claw("extended");
        sleep(2300);
        robotMecanum.omniTime(0.6, 0, driveForwardTime2, true );

        robotMecanum.omniTime(-0.6, 0, driveBackTime, true );

        robotMecanum.omniTime(0, 0.7 * -isRedField, strafeFoundationTime2, true );

        claw("home");
        sleep(500);
        robotMecanum.omniTime(0, 0.7 * isRedField, strafeBrickTime2, true );


    }*/

    public void claw(String clawHome)
    {
        if(clawHome.equalsIgnoreCase("home"))
            robotMecanum.claw(true);
        if(clawHome.equalsIgnoreCase("extended"))
            robotMecanum.claw(false);
    }


    /**
     *drives and picks up foundation and moves foundation to target zone <br><br> <b>Note: time is in ms</b>
     *
     * @param isRedField -1 if red and 1 if blue
     * @param strafeTime time to align with middle of foundation <i>Target: 11"</i>
     * @param drive1Time time to drive to foundation      <i>Target: 30"</i>
     * @param drive2Time time to drive foundation back    <i>Target: 6"</i>
     * @param turnTime time to turn foundation against wall
     * @param drive3Time time to drive foundation against wall <i>Target: 6"</i>
     */
    public void sideFoundation(int isRedField, int strafeTime, int drive1Time, int drive2Time, int turnTime, int drive3Time, int strafe2Time, int driveBackTime, int driveBackTime2)
    {
        robotMecanum.setLiftPosition(1,0.9);

        //double leftMove = 0, rightMove = 0;
        //robotMecanum.strafeRange(16, 0.75, true);

        //driveTime(0.65, 900);
        robotMecanum.omniTime(0, -isRedField * 0.75, strafeTime, true );
        sleep(250);
        robotMecanum.omniTime(0.7, 0, drive1Time, true );

        sleep(250);
        //robotMecanum.driveRange(34, 0.7);
        robotMecanum.hooks(false);
        sleep(500);
        robotMecanum.omniTime(-0.75, 0, drive2Time, true );
        sleep(250);

       /* if(isRedField == -1)
            rightMove = -0.65;
        else
            leftMove = -0.65;*/

        double rightMove = isRedField == -1 ? 0.65 : 0;
        double leftMove = isRedField == -1 ? 0 : 0.65;

        if(isRedField == 1)
        {
            robotMecanum.moveMotors(leftMove, leftMove, rightMove, rightMove);
            sleep(turnTime);
        }
        if(isRedField == -1)
        {
            robotMecanum.moveMotors(leftMove, leftMove, rightMove, rightMove);
            sleep(turnTime);
        }
        robotMecanum.moveOmni(0, 0, 0);

        robotMecanum.omniTime(0.75, 0, drive3Time, true );
        //robotMecanum.gyro.resetZAxisIntegrator();
        /*if(isRedField == 1)
        {
            while (robotMecanum.getNewGyroHeading() < 180 + turnAngle)
                robotMecanum.moveMotors(leftMove, leftMove, rightMove, rightMove);
        }
        if(isRedField == -1)
        {
            while (robotMecanum.getNewGyroHeading() > 180 - turnAngle)
                robotMecanum.moveMotors(leftMove, leftMove, rightMove, rightMove);
        }*/
        //robotMecanum.moveOmni(0.5,0, 0.5 * isRedField)

        //robotMecanum.omniTime(0.7, 0, drive3Time);

        robotMecanum.hooks(true);

        robotMecanum.omniTime(0, -isRedField*0.75, strafe2Time, true );

        robotMecanum.omniTime(-0.75, 0, driveBackTime, true );

        robotMecanum.setLiftPosition(0,0.75);

        robotMecanum.omniTime(-0.75,0, driveBackTime2, true );
    }

}

