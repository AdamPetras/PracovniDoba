package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Adam on 1.6.2015.
 */
public class NastaveniController extends AnchorPane
{
    //--------------inicializace komponent-------------------
    @FXML
    private TextField defaultKcZaHodinu;
    @FXML
    private ComboBox defaultHodiny;
    @FXML
    private Label kcLabel,langLabel,hodinyLabel,datumLabel,maxLabel,delLabel,sortLabel;
    @FXML
    private RadioButton razeniSest,razeniVzest,langCz,langEng;
    @FXML
    private CheckBox datumCheckBox,maxCheckBox,delCheckBox;
    @FXML
    private ColorPicker colorPicker;
    @FXML
    private Button applyButton,defaultButton;
    @FXML
    private Tab zakladniTab;
    //********************************************************

    //------------------ostatní inicializace------------------
    private Controller mainController;
    private Zakladni zakladni;
    //********************************************************

    public NastaveniController(Controller mainController)
    {
        this.mainController=mainController;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Nastaveni.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        zakladni = new Zakladni();
        ObservableList<? extends Number> hodinyList = FXCollections.observableArrayList(1, 1.5, 2, 2.5, 3, 3.5, 4, 4.5, 5, 5.5, 6, 6.5, 7, 7.5, 8, 8.5, 9, 9.5, 10, 10.5, 11, 11.5, 12);
        defaultHodiny.setItems(hodinyList);
        SavedNastaveni();
    }

    public void DefaultZakladniHandler(ActionEvent event)
    {
        zakladni.Default(defaultKcZaHodinu,defaultHodiny,datumCheckBox,maxCheckBox,delCheckBox,razeniSest,langCz,mainController,this);
        Jazyk();
    }

    public void ApplyZakladniHandler(ActionEvent event)
    {
        zakladni.ApplyZakladni(mainController,this);
        Jazyk();
    }

    public void SavedNastaveni()
    {
        try {
            //vytvoreni objektu pro bufferovane cteni ze souboru
            BufferedReader reader = new BufferedReader(new FileReader("Settings.txt"));
            //precteni všech potřebných řádků souboru a zavolání alertu
            reader.readLine();      //Settings
            reader.readLine();      //Zakladni
            //---------------Zakladni-------------
            defaultKcZaHodinu.setText(reader.readLine());
            defaultHodiny.getSelectionModel().select(reader.readLine());
            if(reader.readLine().equals("true")) {
                datumCheckBox.setSelected(true);
            }
            else {
                datumCheckBox.setSelected(false);
            }
            if(reader.readLine().equals("true")) {
                maxCheckBox.setSelected(true);
            }
            else {
                maxCheckBox.setSelected(false);
            }
            if(reader.readLine().equals("true")) {
                delCheckBox.setSelected(true);
            }
            else {
                delCheckBox.setSelected(false);
            }
            if(reader.readLine().equals("true")) {
                razeniSest.setSelected(true);
            }
            else {
                razeniVzest.setSelected(true);
            }
            if(reader.readLine().equals("true")) {
                langCz.setSelected(true);
                Jazyk();
            }
            else {
                langEng.setSelected(true);
                Jazyk();
            }
            //************************************
        } catch (IOException e) {
            WriteNastaveni();
        }catch (NullPointerException e)
        {
            WriteNastaveni();
        }
    }

    public void WriteNastaveni()
    {
        try {
            FileWriter writer = new FileWriter("Settings.txt");  //vytvoreni objektu pro zapis do souboru
            writer.write("Settings"+Main.NEW_LINE);
            writer.write("Zakladni"+Main.NEW_LINE);
            writer.write(defaultKcZaHodinu.getText()+Main.NEW_LINE);  //metoda pro zapis do souboru
            writer.write(String.valueOf(defaultHodiny.getSelectionModel().getSelectedItem())+Main.NEW_LINE);
            writer.write(String.valueOf(datumCheckBox.isSelected())+Main.NEW_LINE);
            writer.write(String.valueOf(maxCheckBox.isSelected())+Main.NEW_LINE);
            writer.write(String.valueOf(delCheckBox.isSelected())+Main.NEW_LINE);
            writer.write(String.valueOf(razeniSest.isSelected())+Main.NEW_LINE);
            writer.write(String.valueOf(langCz.isSelected())+Main.NEW_LINE);
            writer.close();  //zavreni znakoveho proudu
        } catch (IOException e) {
        }
    }

    public void Jazyk()
    {
        if(langCz.isSelected())
        {
            langLabel.setText("Nastavení jazyka:");
            hodinyLabel.setText("Výchozí hodiny:");
            datumLabel.setText("Nastavit výchozí datum:");
            maxLabel.setText("Maximalizované okno:");
            delLabel.setText("Zakázat odebírání:");
            sortLabel.setText("Třídění tabulky:");
            kcLabel.setText("Výchozí částka:");
            langCz.setText("Česky");
            langEng.setText("Anglicky");
            datumCheckBox.setText("Povolit");
            maxCheckBox.setText("Maximalizované");
            delCheckBox.setText("Zakázat");
            razeniSest.setText("Sestupně");
            razeniVzest.setText("Vzestupně");
            applyButton.setText("Aplikovat");
            defaultButton.setText("Výchozí");
            zakladniTab.setText("Základní");
        }
        else if(langEng.isSelected())
        {
            langLabel.setText("Language settings:");
            hodinyLabel.setText("Hours settings:");
            datumLabel.setText("Set default date:");
            maxLabel.setText("Maximalized window:");
            delLabel.setText("Disable deleting:");
            sortLabel.setText("Sorting of table:");
            kcLabel.setText("Default price:");
            langCz.setText("Czech");
            langEng.setText("English");
            datumCheckBox.setText("Allow");
            maxCheckBox.setText("Maximalized");
            delCheckBox.setText("Forbid");
            razeniSest.setText("Descend");
            razeniVzest.setText("Ascend");
            applyButton.setText("Apply");
            defaultButton.setText("Default");
            zakladniTab.setText("Primary");
        }
    }
}