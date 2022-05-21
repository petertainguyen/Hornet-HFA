package org.csc133.a5.gameobjects;

public abstract class Movable extends GameObject{
    int speed;
    int startingAngle;

    public Movable(){
        speed = 0;
        startingAngle = 0;
    }

    protected void setSpeed(int speed) {
        this.speed = speed;
    }

    protected void setStartingAngle(int startingAngle) {
        this.startingAngle = startingAngle;
    }

    public int getSpeed() {
        return speed;
    }

    public void speedIncrease() {
        if (speed < 10) {
            speed += 1;
        }
    }

    public int getStartingAngle() {
        return startingAngle;
    }

    public double headingAngle(){
        /*
        double angle = Math.toRadians((startingAngle));

        location.setX(location.getX() + speed
                * (int)(speed * Math.cos(angle)));
        location.setY(location.getY() + speed
                * (int)(speed * Math.sin(angle)));
         */

        return startingAngle;
    }

    public void speedDown() {
        if (speed >= 1) {
            speed -= 1;
        }
    }

    public void move() {
        myTranslation.translate((float)(speed
                        * Math.cos(Math.toRadians(startingAngle - 90))),
                (float)(speed
                        * Math.sin(Math.toRadians(startingAngle + 90))));
    }
}

