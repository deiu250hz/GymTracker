package org.example;

import DataAccess.ExerciseDAO;
import Model.Exercise;
import Model.MuscleGroup;

public class Main {
    public static void main(String[] args) {
        ExerciseDAO exerciseDAO = new ExerciseDAO();
        Exercise ex = new Exercise(0,"Bench Press", MuscleGroup.CHEST);
        exerciseDAO.addExercise(ex);
    }
}