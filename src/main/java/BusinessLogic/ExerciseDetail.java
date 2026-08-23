package BusinessLogic;

public class ExerciseDetail {
    private double maxWeight;
    private double avgReps;
    private double totalVolume;
    private int totalSets;

    public ExerciseDetail(double maxWeight, double avgReps, double totalVolume, int totalSets) {
        this.maxWeight = maxWeight;
        this.avgReps = avgReps;
        this.totalVolume = totalVolume;
        this.totalSets = totalSets;
    }

    public double getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(double maxWeight) {
        this.maxWeight = maxWeight;
    }

    public double getAvgReps() {
        return avgReps;
    }

    public void setAvgReps(double avgReps) {
        this.avgReps = avgReps;
    }

    public double getTotalVolume() {
        return totalVolume;
    }

    public void setTotalVolume(double totalVolume) {
        this.totalVolume = totalVolume;
    }

    public int getTotalSets() {
        return totalSets;
    }

    public void setTotalSets(int totalSets) {
        this.totalSets = totalSets;
    }

    public double getEstimated1RM() {
        if (avgReps >= 37) {
            return maxWeight;
        }
        return maxWeight * (36.0 / (37.0 - avgReps));
    }

}