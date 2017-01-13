///////////////////////////////////////
package jlibretto.frontend;
import java.time.LocalDate;
import javafx.scene.control.*;
import jlibretto.middleware.Esame;

class CellaTabellaCalendario extends TableCell<Esame,LocalDate> { //(1)
    private DatePicker datePicker;
    CellaTabellaCalendario() {}

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

// (1): La classe consente di mostrare un datepicker al posto di una cella della tabella
//      quando si entra in modalità di modifica
//      DOCS: https://docs.oracle.com/javafx/2/api/javafx/scene/control/TableCell.html
