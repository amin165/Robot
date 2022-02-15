package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Util;

import java.util.function.DoubleSupplier;

public class ShooterSubsystem extends SubsystemBase {

    public final WPI_TalonSRX shooterMotorL = new WPI_TalonSRX(Constants.CAN.SHOOTER_L);
    private final WPI_TalonSRX shooterMotorR = new WPI_TalonSRX(Constants.CAN.SHOOTER_R);
    private Boolean latchRunning = false;
    // private final PIDController shooterPID = new PIDController(0, 0,0);
    // private final Encoder encoder = new Encoder(4, 5);
    private double targetRpm = 0d;

    private static final double amountOutputPerRpm = 1d / Constants.SHOOTER.MAX_RPM;

    private DoubleSupplier powerSupplier;

    public ShooterSubsystem() {
        // Change units to revolutions per second
        // encoder.setDistancePerPulse(1d / 360d);
        // encoder.reset();
    }

    @Override
    public void periodic() {
        // if (this.latchRunning) {
        // }
        // if (powerSupplier == null) {
        // double power = amountOutputPerRpm * this.targetRpm;
        // power = Util.clamp(power, -Constants.SHOOTER.MAX_SAFE_PWR,
        // Constants.SHOOTER.MAX_SAFE_PWR);
        // shooterMotor.set(-power);
        // } else {
        // double power = powerSupplier.getAsDouble();
        // if (power >= 1.0) System.out.println("Power output is very large: " + power);
        // power = Util.clamp(power, -Constants.SHOOTER.MAX_SAFE_PWR,
        // Constants.SHOOTER.MAX_SAFE_PWR);
        // shooterMotor.set(-power);
        // //this.targetRpm = power * Constants.SHOOTER.MAX_SAFE_RPM;
        // }

        // double ffPower = targetRpm * amountOutputPerRpm; // Feedfoward power
        // double ffPower = Util.clamp(targetPower, -1, 0) *
        // Constants.SHOOTER.MAX_SAFE_PWR;
        // double adjustedPower = shooterPID.calculate(encoder.getRate(), targetRpm) +
        // ffPower;
        // shooterMotor.set(-ffPower);
    }

    public void setPowerSupplier(DoubleSupplier powerSupplier) {
        this.powerSupplier = powerSupplier;
    }

    // // Revolutions per minute. NOT Radians per minute
    // public void setRPM(double targetRpm) {
    // this.targetRpm = Util.clamp(targetRpm, 0d, Constants.SHOOTER.MAX_SAFE_RPM);
    // }

    public void start() {
        this.latchRunning = true;
        shooterMotorL.set(-1 * Constants.SHOOTER.AUTO_PWR);
        shooterMotorR.set(-1 * Constants.SHOOTER.AUTO_PWR);
    }

    double getPower() {
        double amount = this.powerSupplier.getAsDouble();
        System.out.println(amount);
        System.out.println(amount - 0.5);
        amount = Util.clamp(amount, -1, 1);
        return amount * Constants.SHOOTER.MAX_SAFE_PWR;
    }

    public void run() {
        shooterMotorL.set(getPower());
        shooterMotorR.set(getPower());

    }

    public void reverse() {
        shooterMotorL.set(-1 * getPower());
        shooterMotorR.set(-1 * getPower());
    }

    public void kill() {
        this.latchRunning = false;
        shooterMotorL.set(0);
        shooterMotorR.set(0);

    }

    public void toggle() {
        if (this.latchRunning) {
            this.kill();
        } else {
            this.start();
        }
    }
}