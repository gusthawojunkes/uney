package solutions.wo.it.views.tag;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import solutions.wo.it.database.entities.Tag;
import solutions.wo.it.services.TagService;

public class TagFormularyView extends VerticalLayout {

    private final TagService service;
    private final Binder<Tag> binder = new Binder<>(Tag.class);
    private final Grid<Tag> grid;
    private Button deleteButton;

    public TagFormularyView(TagService service) {
        this.service = service;

        grid = new Grid<>(Tag.class, false);
        grid.addColumn(Tag::getName).setHeader("Nome");
        grid.addColumn(Tag::getDescription).setHeader("Descrição");
        grid.addColumn(Tag::getKeywords).setHeader("Keywords");
        grid.setItems(this.service.getAll());
        grid.asSingleSelect().addValueChangeListener(event -> {
            this.setTag(event.getValue());
            deleteButton.setEnabled(true);
        });

        HorizontalLayout keysLayout = new HorizontalLayout();
        keysLayout.setSpacing(true);

        FormLayout formularyLayout = new FormLayout();
        formularyLayout.setSizeFull();

        TextField tag = new TextField();
        tag.setSizeFull();
        tag.setLabel("Tag");
        tag.setClearButtonVisible(true);
        tag.setPrefixComponent(VaadinIcon.TAG.create());
        binder.forField(tag).bind(Tag::getName, Tag::setName);

        TextField description = new TextField();
        description.setSizeFull();
        description.setLabel("Descrição");
        description.setClearButtonVisible(true);
        description.setPrefixComponent(VaadinIcon.TEXT_LABEL.create());
        binder.forField(description).bind(Tag::getDescription, Tag::setDescription);

        keysLayout.add(tag, description);

        TextArea keywords = new TextArea();
        keywords.setSizeFull();
        keywords.setLabel("Keywords para vincular na importação (separado por vírgulas)");
        keywords.setClearButtonVisible(true);
        keywords.setPrefixComponent(VaadinIcon.KEY.create());
        binder.forField(keywords).bind(Tag::getKeywords, Tag::setKeywords);

        formularyLayout.add(keysLayout);

        HorizontalLayout horizontalLayoutButtons = new HorizontalLayout();
        Button saveButton = new Button("Salvar", new Icon(VaadinIcon.COPY), event -> save());

        deleteButton = new Button("Excluir", new Icon(VaadinIcon.TRASH),  event -> delete());
        deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        deleteButton.setEnabled(false);

        Button clearButton = new Button("Limpar", new Icon(), event -> clear());

        horizontalLayoutButtons.add(clearButton, deleteButton, saveButton);

        add(grid, formularyLayout, keywords, horizontalLayoutButtons);
        setAlignSelf(Alignment.END, horizontalLayoutButtons);

        Tag bean = new Tag();
        this.binder.setBean(bean);
        this.binder.bindInstanceFields(this);
    }

    private void save() {
        try {
            Tag tag = binder.getBean();
            service.save(tag);
            clearFields();
            Notification.show("Registro cadastrado com sucesso!");
        } catch (Exception exception) {
            Notification.show("Ocorreu um erro ao salvar o registro!");
        } finally {
            refresh();
        }
    }

    private void delete() {
        try {
            Tag tag = binder.getBean();
            service.delete(tag);
            deleteButton.setEnabled(false);
            clearFields();
            Notification.show("Registro excluído com sucesso!");
        } catch (Exception exception) {
            Notification.show("Ocorreu um erro ao excluir o registro!");
        } finally {
            refresh();
        }
    }

    private void clear() {
        binder.getBean().setName("");
        binder.getBean().setDescription("");
        binder.getBean().setKeywords("");
    }

    private void setTag(Tag tag) {
        binder.setBean(tag);
        setVisible(tag != null);
    }

    private void clearFields() {
        binder.setBean(new Tag());
    }

    public void refresh() {
        grid.setItems(this.service.getAll());
    }

}
