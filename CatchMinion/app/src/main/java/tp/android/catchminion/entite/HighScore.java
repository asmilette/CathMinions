package tp.android.catchminion.entite;

/**
 * Created by dpelleti on 2015-09-15.
 */
public class HighScore {
    private int id, score;
    private String name;

    public HighScore(int id, int score, String name) {
        this.id = id;
        this.score = score;
        this.name = name;
    }

    public HighScore(int score, String name) {
        this(-1, score, name);
    }

    @Override
    public String toString() {
        return "Id: "+String.valueOf(id)+", Score: "+String.valueOf(score)+" Name: "+name;
    }

    public int getId() {
        return id;
    }

    public int getScore() {
        return score;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setName(String name) {
        this.name = name;
    }
}
