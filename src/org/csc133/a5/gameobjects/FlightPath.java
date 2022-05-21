package org.csc133.a5.gameobjects;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;
import com.codename1.ui.geom.Point2D;

import java.util.ArrayList;

public class FlightPath extends GameObject {
    private Fire fire;

    private ArrayList<Point2D> allControlPoints;
    private ArrayList<Point2D> padToRiverControlPoints;
    private ArrayList<Point2D> riverToFireControlPoints;
    private ArrayList<Point2D> fireToRiverControlPoints;
    private Point2D lastControlPoint;

    public FlightPath(ArrayList<Point2D> controlPoints) {
        this.allControlPoints = controlPoints;
    }

    public FlightPath() {
        padToRiverControlPoints = new ArrayList<>();
        padToRiverControlPoints.add(new Point2D(1025,-200));
        padToRiverControlPoints.add(new Point2D(1025,-700));
        padToRiverControlPoints.add(new Point2D(-25,-825));
        padToRiverControlPoints.add(new Point2D(1025,-825));

        riverToFireControlPoints = new ArrayList<>();
        riverToFireControlPoints.add(new Point2D(1025,-825));
        riverToFireControlPoints.add(new Point2D(2325, -825));
        /*
        riverToFireControlPoints.add(new Point2D(fire.getLocation().getX(),
                fire.getLocation().getY()));
         */
        riverToFireControlPoints.add(new Point2D(1625,-325));

        fireToRiverControlPoints = new ArrayList<>();
        fireToRiverControlPoints.add(new Point2D(1625,-325));
        fireToRiverControlPoints.add(new Point2D(-925,-65));
        fireToRiverControlPoints.add(new Point2D(1025,-825));

        allControlPoints = new ArrayList<>();
        allControlPoints.addAll(padToRiverControlPoints);
        allControlPoints.addAll(riverToFireControlPoints);
        allControlPoints.addAll(fireToRiverControlPoints);
    }

    public ArrayList<Point2D> getAllControlPoints() {
        return allControlPoints;
    }

    public ArrayList<Point2D> getPadToRiverControlPoints(){
        return padToRiverControlPoints;
    }

    public ArrayList<Point2D> getRiverToFireControlPoints() {
        return riverToFireControlPoints;
    }

    public ArrayList<Point2D> getFireToRiverControlPoints() {
        return fireToRiverControlPoints;
    }

    Point2D evaluateCurve(double t, ArrayList<Point2D> controlPoints) {
        Point2D initialPoint = new Point2D(0,0);

        int dimension = controlPoints.size()-1;

        for(int i = 0; i < controlPoints.size(); i++){
            initialPoint.setX(initialPoint.getX()
                    + bernsteinD(dimension,i,t)
                    * controlPoints.get(i).getX());

            initialPoint.setY(initialPoint.getY()
                    + bernsteinD(dimension,i,t)
                    * controlPoints.get(i).getY());
        }

        return initialPoint;
    }

    private void drawBezierCurve(Graphics g, ArrayList<Point2D> controlPoints) {
        final double smallFloatIncrement = 0.005;

        g.setColor(ColorUtil.GREEN);

        for(Point2D p : controlPoints) {
            g.fillArc((int) p.getX() - 15, (int) p.getY() - 15,
                    30, 30, 0, 360);

            //System.err.println("X :" + (p.getX() - 15));
            //System.err.println("Y :" + (p.getY() - 15));
        }

        g.setColor(ColorUtil.GREEN);

        Point2D currentPoint = controlPoints.get(0);
        Point2D nextPoint;

        double t = 0;
        while (t < 1) {
            nextPoint = evaluateCurve(t, controlPoints);

            g.drawLine((int)currentPoint.getX(), (int)currentPoint.getY(),
                    (int)nextPoint.getX(), (int)nextPoint.getY());

            currentPoint = nextPoint;
            t = t + smallFloatIncrement;
        }

        nextPoint = controlPoints.get(controlPoints.size()-1);
        g.drawLine((int)currentPoint.getX(), (int)currentPoint.getY(),
                (int)nextPoint.getX(), (int)nextPoint.getY());
    }

    private double bernsteinD(int dimension, int i, double tParameter) {
        //return choose(dimension,i) * Math.pow(tParameter,i)
        return choose(dimension,i) * Math.pow(tParameter,i)
                * Math.pow(1 - tParameter, dimension - i);
    }

    private double choose(int n, int k) {
        // base case
        if (k <= 0 || k >= n) {
            return 1;
        }

        // recurse using pascal's triangle
        return choose(n - 1, k - 1) + choose (n - 1, k);
    }

    @Override
    public void updateLocalTransform() {
    }

    @Override
    public void localDraw(Graphics g, Point containerOrigin,
                          Point screenOrigin) {
        //containerTranslate(g,containerOrigin);
        drawBezierCurve(g, padToRiverControlPoints);
        drawBezierCurve(g, riverToFireControlPoints);
        drawBezierCurve(g, fireToRiverControlPoints);
    }

    public void setTail(Point2D lastControlPoint) {
        this.lastControlPoint = lastControlPoint;
        fireToRiverControlPoints.set(0, lastControlPoint);
        riverToFireControlPoints.set(riverToFireControlPoints.size()-1,
                lastControlPoint);
    }

    public Point2D getLastControlPoint() {
        return lastControlPoint;
    }
}
