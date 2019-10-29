package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import sample.service.Calculator;
import sample.service.JsonRetrievalTask;


import java.net.URL;

import java.util.ResourceBundle;


public class Controller implements Initializable {

    JsonRetrievalTask jsonRetrieval = new JsonRetrievalTask();
    @FXML
    private ListView<String> operacionList;


    @FXML
    private Text output;
    private double num = 0;
    private double num2 = 0;
    private double memory = 0;


    private boolean start = true;
    private String operator = "";
    private Calculator calculator = new Calculator();
    private String operacion = "";

    private void loadData() {
        operacionList.getItems().addAll(jsonRetrieval.getList());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadData();
    }

    @FXML
    private void processSumMem() {
        memory += Double.parseDouble(output.getText());
    }

    @FXML
    private void processSubtractMem() {
        memory -= Double.parseDouble(output.getText());
    }

    @FXML
    private void processClear() {
        num = 0;
        output.setText("");
    }

    @FXML
    private void processClearMem() {
        memory = 0;
        output.setText("");
    }

    @FXML
    private void processMem() {
        if (num == 0) {
            output.setText(Double.toString(memory));
        }
        output.setText(Double.toString(memory));
    }


    @FXML
    private void processNum(ActionEvent event) {
        if (start) {
            output.setText("");
            start = false;
        }
        String value = ((Button) event.getSource()).getText();
        output.setText(output.getText() + value);
    }

    @FXML
    private void processOperator(ActionEvent event) {
        String value = ((Button) event.getSource()).getText();
        if (!"=".equals(value)) {
            if (!operator.isEmpty()) return;
            operator = value;
            num = Double.parseDouble(output.getText());
            output.setText("");
        } else {
            if (operator.isEmpty()) return;
            num2 = Double.parseDouble(output.getText());
            output.setText(String.valueOf(calculator.calculation(num, num2, operator)).replaceAll(".0" , ""));
            operacion = num + " " + operator + " " + num2 + " = " + output.getText();

            num = Double.parseDouble(output.getText());
            if (operacionList.getItems().size() > 19) {
                operacionList.getItems().remove(0);
                operacionList.getItems().add(operacion.replaceAll(".0" , ""));
            } else operacionList.getItems().add(operacion.replaceAll(".0" , ""));
            jsonRetrieval.postJson(operacion.replaceAll(".0" , ""));
            operacion = "";
            operator = "";
            start = true;
        }
    }

}

