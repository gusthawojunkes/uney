package solutions.wo.it.views.importation.configuration;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import solutions.wo.it.services.TagService;
import solutions.wo.it.services.imports.configuration.ImportConfigurationService;
import solutions.wo.it.views.MainLayout;
import solutions.wo.it.views.tag.TagFormularyView;


@PageTitle("Configurações da importação")
@Route(value = "import/configuration", layout = MainLayout.class)
@RouteAlias(value = "/import/configuration", layout = MainLayout.class)
@AnonymousAllowed
public class ImportConfigurationView extends VerticalLayout {

    public ImportConfigurationView(TagService tagService, ImportConfigurationService configurationService) {
        TabSheet tabSheet = new TabSheet();
        tabSheet.add("Tags", new TagFormularyView(tagService));
        tabSheet.add("Geral", new ImportConfigurationFlagsView(configurationService));
        tabSheet.setSizeFull();

        add(tabSheet);
    }

}
