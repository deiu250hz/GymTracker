package org.example;

public class ExerciseSet {
    private int id;
    private int nrReps;
    private double weight;
    private int setNumber;

    public ExerciseSet(int setNumber) {
        this.setNumber = setNumber;
        this.nrReps = 0;
        this.weight = 0.0;
    }

    public ExerciseSet(int id, int workoutExerciseId, int nrReps, double weight, int setNumber) {
        this.nrReps = nrReps;
        this.weight = weight;
        this.setNumber = setNumber;
        this.id = id;
    }

    public double getVolume() {
        return nrReps * weight;
    }

    public int getNrReps() {
        return nrReps;
    }

    public void setNrReps(int nrReps) {
        this.nrReps = nrReps;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getSetNumber() {
        return setNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public void setSetNumber(int setNumber) {
        this.setNumber = setNumber;
    }
}
