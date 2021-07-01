package com.vaadin.samples.flow;


import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.samples.about.flow.AboutView;
import com.vaadin.samples.authentication.flow.AccessControlFactory;
import com.vaadin.samples.crud.flow.SampleCrudView;

/**
 * Content of the UI when the user is logged in.
 * 
 * 
 */
@CssImport("styles/shared-styles.css")
@CssImport(value = "styles/text-field.css", themeFor = "vaadin-text-field")
public class MainLayout extends HorizontalLayout implements RouterLayout, BeforeEnterObserver {
    private Menu menu;
    private final FlexLayout viewContainer;

    public MainLayout() {

        setSpacing(false);
        setClassName("main-screen");

        viewContainer = new FlexLayout();
        viewContainer.addClassName("valo-content");
        viewContainer.setSizeFull();
        viewContainer.setJustifyContentMode(JustifyContentMode.CENTER);
        viewContainer.setAlignItems(Alignment.CENTER);

        menu = new Menu();
        menu.addView(SampleCrudView.class, SampleCrudView.VIEW_NAME,
                setupIcon(VaadinIcon.EDIT.create()));
        menu.addView(AboutView.class, AboutView.VIEW_NAME,
        		setupIcon(VaadinIcon.INFO_CIRCLE.create()));

        add(menu, viewContainer);
        expand(viewContainer);
        setDefaultVerticalComponentAlignment(Alignment.STRETCH);
        setSizeFull();
    }

	private Icon setupIcon(Icon icon) {
		icon.setSize("14px");
		return icon;
	}

    @Override
    public void showRouterLayoutContent(HasElement content) {
        viewContainer.getElement().appendChild(content.getElement());
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (!AccessControlFactory.getAccessControl().isUserSignedIn()) {
            event.rerouteTo("login");
        }
    }
}
