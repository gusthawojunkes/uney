package solutions.wo.it.views.tag;

import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import solutions.wo.it.views.MainLayout;


@PageTitle("Tags")
@Route(value = "tags", layout = MainLayout.class)
@RouteAlias(value = "/tags", layout = MainLayout.class)
@AnonymousAllowed
public class TagFormularyView extends VerticalLayout {

    public TagFormularyView() {
        FormLayout formularyLayout = new FormLayout();
        TextField tag = new TextField();
        tag.setLabel("Tag");
        tag.setClearButtonVisible(true);
        tag.setPrefixComponent(VaadinIcon.TAG.create());

        TextArea keywords = new TextArea();
        keywords.setLabel("Keywords para vincular na importação");
        keywords.setClearButtonVisible(true);
        keywords.setPrefixComponent(VaadinIcon.KEY.create());

        formularyLayout.add(tag, keywords);

        add(formularyLayout);
    }

}
