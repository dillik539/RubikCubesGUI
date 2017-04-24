

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

/*
This class add different listeners to specific buttons.
 */
public class CubesGUI extends JFrame {
    private JPanel rootPanel;
    private JLabel NameLabel;
    private JLabel TimeLabel;
    private JLabel SearchNameLabel;
    private JLabel NewTimeLabel;
    private JTextField TimeTextField;
    private JTextField NameTextField;
    private JTextField SearchNameTextField;
    private JTextField NewTimeTextField;
    private JButton addButton;
    private JButton deleteButton;
    private JButton updateButton;
    private JList<Cubes> DisplayList; //display list of Cubes object
    private DefaultListModel<Cubes> DisplayListModel; //display list model of cubes object
    private DBController dbController;

    CubesGUI(DBController dbController) {
        super("Rubik's Cubes Solver");
        setPreferredSize(new Dimension(500,350));
        this.dbController = dbController; //store a reference to the DBController object to make request to the database.
        //Configures the list model of Cubes
        DisplayListModel = new DefaultListModel<Cubes>();
        DisplayList.setModel(DisplayListModel);
        setContentPane(rootPanel);
        pack();
        setVisible(true);
        addListeners();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setContentPane(rootPanel);
    }
    private void addListeners() {
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Cubes cubes = DisplayList.getSelectedValue();
                if(cubes ==null){
                    JOptionPane.showMessageDialog(CubesGUI.this,"Please select the record to delete");
                }else {
                    dbController.delete(cubes);
                    ArrayList<Cubes> cubes1 = dbController.getAllData();
                    setListData(cubes1);
                }
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchName = SearchNameTextField.getText();
                if(searchName.isEmpty()){
                    JOptionPane.showMessageDialog(CubesGUI.this,"Please type the name to update time");
                    return;
                }
                double updateTime;
                try{
                    updateTime = Double.parseDouble(NewTimeTextField.getText());
                }catch (NumberFormatException ne){
                    JOptionPane.showMessageDialog(CubesGUI.this,"Enter the new time to update");
                    return;
                }
                Cubes cubesUpdateRecord = new Cubes(searchName,updateTime);
                dbController.updateRecord(cubesUpdateRecord);
                SearchNameTextField.setText("");
                NewTimeTextField.setText("");
                ArrayList<Cubes> allData = dbController.getAllData();
                setListData(allData);
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //Read data, send message to database via controller

                String name = NameTextField.getText();

                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(CubesGUI.this, "Enter the name");
                    return;
                }
                double time;

                try {
                    time = Double.parseDouble(TimeTextField.getText());
                } catch (NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(CubesGUI.this, "Enter the number for time");
                    return;
                }

                Cubes cubesRecord = new Cubes(name, time);
                dbController.addRecordToDatabase(cubesRecord);

                //Clear input JTextFields
                NameTextField.setText("");
                TimeTextField.setText("");

                //request all data from database to update list
                ArrayList<Cubes> allData = dbController.getAllData();
                setListData(allData);
            }
        });
    }
    void setListData(ArrayList<Cubes> data) {
        DisplayListModel.clear();
        //add cubes object to display list model.
        for (Cubes cb : data) {
            DisplayListModel.addElement(cb);
        }
    }
}
