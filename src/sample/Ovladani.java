package sample;

import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;

/**
 * Created by Adam on 11.5.2015.
 */
public class Ovladani {
    private TableColumn datumCol;
    private TableColumn hodinyCol;
    private TableColumn castkaCol;
    private ObservableList<Informace> data;
    private double castka;
    private double hodiny;
    private boolean tableOn;
    private Informace informace;
    private boolean trideni;
    private boolean zobraz;
    private String dialog;

    public Ovladani() {
        castka = 0;
        hodiny = 0;
        tableOn = false;
        zobraz = false;
        this.trideni = false;
        datumCol = new TableColumn("Datum");
        hodinyCol = new TableColumn("Hodiny");
        castkaCol = new TableColumn("Částka");
        data = FXCollections.observableArrayList();
        Import();
    }

    public void Add(DatePicker datePicker, ComboBox hodiny, TextField kcZaHodinu, Label caskaCelkem, Label hodinyCelkem, TableView tableView, Label dnuCelkem) {
        if (!kcZaHodinu.getText().isEmpty()) {
            informace = new Informace(datePicker.getValue().toString(), String.valueOf(hodiny.getSelectionModel().getSelectedItem()), (Double.parseDouble(kcZaHodinu.getText()) * Double.parseDouble(hodiny.getSelectionModel().getSelectedItem().toString())));
            data.add(new Informace(informace.getDatum(),
                    informace.getHodiny(),
                    informace.getCastka()
            ));
            Export();
            Aktualizace(caskaCelkem, hodinyCelkem, dnuCelkem, tableView);
        } else
            return;
    }

    public void ZobrazitVseHandler(TableView tableView, TextField searchField, Label castkaCelkem, Label hodinyCelkem, Label dnuCelkem, ToggleButton zobrazitVse) {
        if (zobrazitVse.isSelected()) {
            tableOn = true;
            zobraz = true;
            searchField.setDisable(false);
            tableView.setVisible(true);
            datumCol.setCellValueFactory(new PropertyValueFactory<Informace, String>("datum"));
            hodinyCol.setCellValueFactory(new PropertyValueFactory<Informace, String>("hodiny"));
            castkaCol.setCellValueFactory(new PropertyValueFactory<Informace, String>("castka"));
            datumCol.setPrefWidth(tableView.getWidth() / 3);
            hodinyCol.setPrefWidth(tableView.getWidth() / 3);
            castkaCol.setPrefWidth(tableView.getWidth() / 3);
            tableView.getColumns().setAll(datumCol, hodinyCol, castkaCol);
            tableView.setItems(data);
            Aktualizace(castkaCelkem, hodinyCelkem, dnuCelkem, tableView);
            Filtrovani(tableView, searchField,castkaCelkem,hodinyCelkem,dnuCelkem);
        } else if (!zobrazitVse.isSelected()) {
            tableOn = false;
            zobraz = false;
            searchField.setDisable(true);
            tableView.setVisible(false);
            castkaCelkem.setText("0");
            hodinyCelkem.setText("0");
            dnuCelkem.setText("0");
            zobrazitVse.setText("Zobrazit vše");
        }
    }

    public void Odstranit(TableView tableView, Label castkaCelkem, Label hodinyCelkem, Label dnuCelkem) {
        if (!tableView.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Odstranit");
            alert.setHeaderText("Opravdu chceš odstranit tento údaj?");
            alert.setContentText("Pokud chceš odstranit tak zmačkni OK.");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                int index = tableView.getSelectionModel().getSelectedIndex();
                tableView.getSelectionModel().clearSelection();
                data.remove(index);
                Aktualizace(castkaCelkem, hodinyCelkem, dnuCelkem, tableView);
                Export();
            } else {
                return;
            }
        }
    }

    public void Trideni(TableView tableView)
    {
        if(trideni == true)
        {
            datumCol.setSortType(TableColumn.SortType.DESCENDING);
        }
        else if(trideni==false)
        {
            datumCol.setSortType(TableColumn.SortType.ASCENDING);
        }
        tableView.getSortOrder().add(datumCol);
    }

    public void OdstranitVse(TableView tableView, Label castkaCelkem, Label hodinyCelkem, Label dnuCelkem) {
        if (tableOn == true&&!tableView.getItems().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Odstranit vše");
            alert.setHeaderText("Opravdu chceš odstranit všechny údaje?");
            alert.setContentText("Pokud chceš odstranit všechny údaje tak zmáčkni OK.");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                data.clear();
                tableView.getItems().clear();
                Aktualizace(castkaCelkem, hodinyCelkem, dnuCelkem, tableView);
                Export();
            } else {
                return;
            }
        }
    }

    private void Filtrovani(final TableView tableView, final TextField searchField, final Label castkaCelkem, final Label hodinyCelkem, final Label dnuCelkem) {
        searchField.setPromptText("Search");
        searchField.textProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(javafx.beans.Observable observable) {
                if (searchField.textProperty().get().isEmpty()) {
                    tableView.setItems(data);
                    Aktualizace(castkaCelkem, hodinyCelkem, dnuCelkem, tableView);
                    return;
                }
                ObservableList<Informace> tableItems = FXCollections.observableArrayList();
                ObservableList<TableColumn<Informace, ?>> cols = tableView.getColumns();
                for (int i = 0; i < data.size(); i++) {
                    for (int j = 0; j < cols.size(); j++) {
                        TableColumn col = cols.get(j);
                        String cellValue = col.getCellData(data.get(i)).toString();
                        cellValue = cellValue.toLowerCase();
                        if (cellValue.contains(searchField.textProperty().get().toLowerCase())) {
                            tableItems.add(data.get(i));
                            break;
                        }
                    }
                }
                tableView.setItems(tableItems);
                if(tableItems.isEmpty())
                {
                    castkaCelkem.setText("0");
                    hodinyCelkem.setText("0");
                    dnuCelkem.setText("0");
                }
                else
                Aktualizace(castkaCelkem, hodinyCelkem, dnuCelkem, tableView);
            }
        });
    }

    public void Export() {
        try {
            FileWriter writer = new FileWriter("info.txt");  //vytvoreni objektu pro zapis do souboru
            for (int i = 0; i < data.size(); i++) {
                writer.write("Dalsi"+Main.NEW_LINE);
                writer.write(data.get(i).getDatum() + Main.NEW_LINE + data.get(i).getHodiny() + Main.NEW_LINE + data.get(i).getCastka() + Main.NEW_LINE);  //metoda pro zapis do souboru
            }
            writer.close();  //zavreni znakoveho proudu
        } catch (IOException e) {
            dialog = "Vaše Informace nebyla úspěšně exportována.";
            AlertInformationDialog(dialog);     //zavolání alertu když spadne program na vyjimku IO
        }
    }

    public void Import() {
        try {
            //vytvoreni objektu pro bufferovane cteni ze souboru
            BufferedReader reader = new BufferedReader(new FileReader("info.txt"));
            //precteni všech potřebných řádků souboru a zavolání alertu
            String line;
            while (reader.readLine() != null) {
                informace = new Informace(reader.readLine(), reader.readLine(), Double.parseDouble(reader.readLine()));
                data.add(new Informace(informace.getDatum(),
                        informace.getHodiny(),
                        informace.getCastka()
                ));
            }
            reader.close();
        } catch (IOException e) {
            Export();
        }
    }

    public void Aktualizace(Label castkaCelkem, Label hodinyCelkem, Label dnuCelkem, TableView tableView) {
        hodiny = 0;
        castka = 0;
        if (data.isEmpty()&&datumCol.getColumns().isEmpty()) {
            hodiny = 0;
            castka = 0;
            castkaCelkem.setText("0");
            hodinyCelkem.setText("0");
            dnuCelkem.setText("0");
        }
        if (tableOn == true) {
            for (int i = 0; i < tableView.getItems().size(); i++)
                hodinyCelkem.setText(String.valueOf(hodiny += Double.parseDouble(hodinyCol.getCellObservableValue(i).getValue().toString())));
            for (int i = 0; i < tableView.getItems().size(); i++)
                castkaCelkem.setText(String.valueOf(castka += Double.parseDouble(castkaCol.getCellObservableValue(i).getValue().toString())));
                dnuCelkem.setText(String.valueOf(tableView.getItems().size()));
        }
    }

    private Alert AlertInformationDialog(String dialog)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText(dialog);
        alert.showAndWait();
        return alert;
    }
    public void setTrideni(boolean trideni) {
        this.trideni = trideni;
    }

    public boolean isZobraz() {
        return zobraz;
    }

    public TableColumn getDatumCol() {
        return datumCol;
    }

    public void setDatumCol(TableColumn datumCol) {
        this.datumCol = datumCol;
    }

    public TableColumn getHodinyCol() {
        return hodinyCol;
    }

    public void setHodinyCol(TableColumn hodinyCol) {
        this.hodinyCol = hodinyCol;
    }

    public TableColumn getCastkaCol() {
        return castkaCol;
    }

    public void setCastkaCol(TableColumn castkaCol) {
        this.castkaCol = castkaCol;
    }
}