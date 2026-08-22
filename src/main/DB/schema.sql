CREATE TABLE exercises
(
    exercise_id  SERIAL PRIMARY KEY,
    name         VARCHAR(25) NOT NULL UNIQUE,
    muscle_group VARCHAR(25) NOT NULL
);

CREATE TABLE workouts
(
    workout_id   SERIAL PRIMARY KEY,
    name         VARCHAR(25) NOT NULL,
    workout_date DATE        NOT NULL
);

CREATE TABLE workout_exercises
(
    workout_exercise_id SERIAL PRIMARY KEY,
    workout_id          INT NOT NULL,
    exercise_id         INT NOT NULL,
    FOREIGN KEY (workout_id) references workouts (workout_id) ON DELETE CASCADE,
    FOREIGN KEY (exercise_id) references exercises (exercise_id),
    CONSTRAINT unique_workout_exercise UNIQUE (workout_id, exercise_id)
);

CREATE TABLE exercise_sets
(
    set_id              SERIAL PRIMARY KEY,
    workout_exercise_id INT           NOT NULL,
    reps                INT           NOT NULL,
    weight              DECIMAL(5, 2) NOT NULL,
    set_number          INT           NOT NULL,
    FOREIGN KEY (workout_exercise_id) references workout_exercises (workout_exercise_id) ON DELETE CASCADE
);