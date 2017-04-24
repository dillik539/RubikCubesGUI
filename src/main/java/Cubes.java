/*
This class represents objects stored in the database
 */
public class Cubes {
    String name;
    double time;
    //constructor
    Cubes (String n, double t){
        name = n;
        time = t;
    }
    @Override
    public String toString(){
        return "Cube Solver's name: "+ name + " Time taken to solve: " + time;
    }
}
