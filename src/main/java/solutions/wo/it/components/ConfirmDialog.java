package solutions.wo.it.components;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Span;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@NoArgsConstructor
public class ConfirmDialog extends Dialog {

    public ConfirmDialog(String title, String description, ComponentEventListener<ClickEvent<Button>> onConfirm, ComponentEventListener<ClickEvent<Button>> onDenying) {
        if (StringUtils.isEmpty(title)) title = "Confirmação";
        if (StringUtils.isEmpty(description)) description = "Você tem certeza que deseja confirmar?";
        if (onDenying == null) onDenying = event -> close();

        setHeaderTitle(title);
        add(new Span(description));

        Button saveButton = new Button("Sim", onConfirm);
        Button cancelButton = new Button("Não", onDenying);

        getFooter().add(cancelButton);
        getFooter().add(saveButton);
    }

}
