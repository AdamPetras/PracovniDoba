//
package sample;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

public class Controller implements Initializable
{
    @FXML
    private TableView tableView;
    @FXML
    private Label hodinyCelkem, castkaCelkem, dnuCelkem, searchLabel,datumLabel,hodinLabel,kcLabel,dnuCelkemLabel,castkaCelkemLabel,hodinCelkemLabel;
    @FXML
    private ComboBox hodiny;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField kcZaHodinu,searchField;
    @FXML
    private ToggleButton zobrazitVse;
    @FXML
    private Button odebrat,odebratVse,vlozButton;
    @FXML
    private Tab prehledTab,vlozTab;
    @FXML
    private Menu souborMenu,napovedaMenu;
    @FXML
    private MenuItem nastaveniItem,ukoncitItem,napovedaItem;
    private Ovladani ovladani;



    public  void VkladHandler()
    {
        ovladani.Add(datePicker,hodiny,kcZaHodinu,castkaCelkem,hodinyCelkem,tableView,dnuCelkem);
        AktualniNastaveni();

    }

    public void ZobrazitVseHandler()
    {
        ovladani.ZobrazitVseHandler(tableView, searchField, castkaCelkem, hodinyCelkem, dnuCelkem, zobrazitVse);
        AktualniNastaveni();
    }

    public void OdeberHodinyHandler()
    {
        ovladani.Odstranit(tableView, castkaCelkem, hodinyCelkem, dnuCelkem);
    }

    public void OdeberVseHandler()
    {
        ovladani.OdstranitVse(tableView, castkaCelkem, hodinyCelkem, dnuCelkem);
    }

    public void InformaceHandler() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText("This program has been made by Adam Petráš.");
        alert.showAndWait();
    }

    public void NastaveniHandler()
    {
        NastaveniController nastaveniController = new NastaveniController(this);
        Stage stage = new Stage();
        stage.setTitle("Nastavení");
        stage.setResizable(false);
        stage.setScene(new Scene(nastaveniController));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(Main.primaryStage);
        stage.show();

    }
    public void AktualniNastaveni() {
        BufferedReader reader = null;
        try {
            //vytvoreni objektu pro bufferovane cteni ze souboru
            reader = new BufferedReader(new FileReader("Settings.txt"));
            //precteni všech potřebných řádků souboru a zavolání alertu
            reader.readLine();      //Settings
            reader.readLine();      //Zakladni
            //---------------Zakladni-------------
            kcZaHodinu.setText(reader.readLine());
            hodiny.getSelectionModel().select(reader.readLine());
            if (reader.readLine().equals("true")) {
                datePicker.setValue(LocalDate.now());
            } else
                datePicker.setValue(null);
            Main.primaryStage.setMaximized(Boolean.parseBoolean(reader.readLine()));
            if (reader.readLine().equals("true")) {
                odebrat.setDisable(true);
                odebratVse.setDisable(true);
            } else
            {
                odebrat.setDisable(false);
                odebratVse.setDisable(false);
            }
            if(reader.readLine().equals("true"))
            {
                ovladani.setTrideni(true);
                ovladani.Trideni(tableView);
            }
            else
            {
                ovladani.setTrideni(false);
                ovladani.Trideni(tableView);
            }
            if(reader.readLine().equals("true"))
            {
                if(!ovladani.isZobraz()){
                    zobrazitVse.setText("Zobrazit vše");}
                else {
                    zobrazitVse.setText("Skryj");
                }
                odebrat.setText("Odeber vybrané");
                odebratVse.setText("Odeber vše");
                searchField.setPromptText("Hledat");
                hodinCelkemLabel.setText("Celkem hodin:");
                castkaCelkemLabel.setText("Částka celkem:");
                dnuCelkemLabel.setText("Dnů celkem:");
                searchLabel.setText("Vyhledávání");
                datumLabel.setText("Datum");
                hodinLabel.setText("Počet hodin");
                kcLabel.setText("Částka");
                prehledTab.setText("Přehled");
                vlozTab.setText("Vloz hodiny");
                souborMenu.setText("Soubor");
                napovedaMenu.setText("Nápověda");
                napovedaItem.setText("O programu");
                nastaveniItem.setText("Nastavení");
                ukoncitItem.setText("Ukončit");
                vlozButton.setText("Vlož");
                ovladani.getCastkaCol().setText("Částka");
                ovladani.getDatumCol().setText("Datum");
                ovladani.getHodinyCol().setText("Hodiny");
            }
            else
            {
                if(!ovladani.isZobraz()){
                    zobrazitVse.setText("Show all");
                }
                else {
                    zobrazitVse.setText("Hide");
                }
                odebrat.setText("Delete selected");
                odebratVse.setText("Delete all");
                searchField.setPromptText("Search");
                hodinCelkemLabel.setText("Hours together:");
                castkaCelkemLabel.setText("Price together:");
                dnuCelkemLabel.setText("Days together:");
                searchLabel.setText("Searching");
                datumLabel.setText("Date");
                hodinLabel.setText("Hours of work");
                kcLabel.setText("Price");
                prehledTab.setText("Summary");
                vlozTab.setText("Insert hours");
                souborMenu.setText("File");
                napovedaMenu.setText("Help");
                napovedaItem.setText("About");
                nastaveniItem.setText("Settings");
                ukoncitItem.setText("Quit");
                vlozButton.setText("Add");
                ovladani.getCastkaCol().setText("Price");
                ovladani.getDatumCol().setText("Date");
                ovladani.getHodinyCol().setText("Hours");
            }
            //************************************
        } catch (Exception e)
        {

        }
        finally {
            if (reader != null)
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }
    public void UkoncitHandler()
    {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Ukončit");
            alert.setHeaderText("Opravdu chceš ukončit tento program?");
            alert.setContentText("Pokud chceš ukončik klikni na OK.");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                Platform.exit();
            }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<? extends Number> hodinyList = FXCollections.observableArrayList(1, 1.5, 2,2.5, 3,3.5, 4,4.5, 5,5.5, 6,6.5, 7,7.5, 8, 8.5, 9, 9.5, 10,10.5, 11,11.5, 12);
        hodiny.setItems(hodinyList);
        datePicker.setValue(LocalDate.now());
        ovladani = new Ovladani();
        AktualniNastaveni();
        Platform.setImplicitExit(false);
        //ImageView image = new ImageView(new Image(getClass().getResourceAsStream("icons/Search.png")));
    }
}