package solutions.wo.it.views.tag;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
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
    private Binder<Tag> binder = new Binder<>(Tag.class);
    private Tag bean = new Tag();
    private Grid<Tag> grid;
    private TextField tag;
    private TextField description;
    private TextArea keywords;
    private Button saveButton;
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
        });

        HorizontalLayout keysLayout = new HorizontalLayout();
        keysLayout.setSpacing(true);

        FormLayout formularyLayout = new FormLayout();
        formularyLayout.setSizeFull();

        tag = new TextField();
        tag.setSizeFull();
        tag.setLabel("Tag");
        tag.setClearButtonVisible(true);
        tag.setPrefixComponent(VaadinIcon.TAG.create());
        binder.forField(tag).bind(Tag::getName, Tag::setName);

        description = new TextField();
        description.setSizeFull();
        description.setLabel("Descrição");
        description.setClearButtonVisible(true);
        description.setPrefixComponent(VaadinIcon.TEXT_LABEL.create());
        binder.forField(description).bind(Tag::getDescription, Tag::setDescription);

        keysLayout.add(tag, description);

        keywords = new TextArea();
        keywords.setSizeFull();
        keywords.setLabel("Keywords para vincular na importação (separado por vírgulas)");
        keywords.setClearButtonVisible(true);
        keywords.setPrefixComponent(VaadinIcon.KEY.create());
        binder.forField(keywords).bind(Tag::getKeywords, Tag::setKeywords);

        formularyLayout.add(keysLayout);

        HorizontalLayout horizontalLayoutButtons = new HorizontalLayout();
        saveButton = new Button("Salvar", event -> save());
        deleteButton = new Button("Excluir", event -> delete());

        horizontalLayoutButtons.add(deleteButton, saveButton);
        horizontalLayoutButtons.setAlignItems(Alignment.END);

        add(grid, formularyLayout, keywords, horizontalLayoutButtons);
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
            clearFields();
            Notification.show("Registro excluído com sucesso!");
        } catch (Exception exception) {
            Notification.show("Ocorreu um erro ao excluir o registro!");
        } finally {
            refresh();
        }
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
