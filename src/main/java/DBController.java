import java.util.ArrayList;

/*
This class manages interaction between GUI and database
 */
public class DBController {
    static CubesGUI cubesGUI;
    static CubesDB cubesDB;

    public static void main(String[] args) {

        DBController dbController = new DBController();
        dbController.startApp();

    }

    private void startApp() {

        cubesDB = new CubesDB();
        cubesDB.createTable();
        ArrayList<Cubes> allData = cubesDB.fetchAllRecords();
        cubesGUI = new CubesGUI(this);
        cubesGUI.setListData(allData);
    }
    void delete(Cubes cubes){
        cubesDB.delete(cubes);
    }
    void updateRecord(Cubes cubes){
        cubesDB.updateRecord(cubes);
    }


    ArrayList<Cubes> getAllData() {
        return cubesDB.fetchAllRecords();
    }

    void addRecordToDatabase(Cubes cubes) {
        cubesDB.addRecord(cubes);
    }
}
