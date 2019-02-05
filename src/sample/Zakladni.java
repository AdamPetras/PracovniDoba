package sample;

import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

/**
 * Created by Adam on 8.6.2015.
 */
public class Zakladni
{
    public Zakladni()
    {

    }

    public void Default(TextField defaultKcZaHodinu,ComboBox defaultHodiny,CheckBox datumCheckBox,CheckBox maxCheckBox,CheckBox delCheckBox,RadioButton razeniSest,RadioButton langCz,Controller mainController,NastaveniController nastaveniController)
    {
        defaultHodiny.getSelectionModel().selectFirst();
        defaultKcZaHodinu.setText("0");
        datumCheckBox.setSelected(true);
        maxCheckBox.setSelected(false);
        delCheckBox.setSelected(false);
        razeniSest.setSelected(true);
        langCz.setSelected(true);
        nastaveniController.WriteNastaveni();
        mainController.AktualniNastaveni();
    }

    public void ApplyZakladni(Controller mainController,NastaveniController nastaveniController)
    {
        nastaveniController.WriteNastaveni();
        mainController.AktualniNastaveni();
    }
}
