package interfacciagrafica;
import modellodati.Esame;
import java.time.LocalDate;
import javafx.scene.control.*;

public class CellaTabellaCalendario extends TableCell<Esame, LocalDate> {
    private DatePicker datePicker;
    public CellaTabellaCalendario() {}

    @Override
    public void startEdit() {
        if (!isEmpty()) {
            super.startEdit();
            creaDatePicker();
            setText(null);
            setGraphic(datePicker);
        }
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setText(getData().toString());
        setGraphic(null);
    }

    @Override
    public void updateItem(LocalDate item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                if (datePicker != null) {
                    datePicker.setValue(getData());
                }
                setText(null);
                setGraphic(datePicker);
            } else {
                setText(getData().toString());
                setGraphic(null);
            }
        }
    }

    private void creaDatePicker() {
        datePicker = new DatePicker(getData());
        datePicker.setMinWidth(getWidth() - getGraphicTextGap() * 2);
        datePicker.setOnAction((e) -> {
            commitEdit(datePicker.getValue());
        });
    }

    private LocalDate getData() {
        return getItem() == null ? LocalDate.now() : getItem();
    }
}