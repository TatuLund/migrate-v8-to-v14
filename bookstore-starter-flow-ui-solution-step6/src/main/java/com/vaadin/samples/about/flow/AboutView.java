package com.vaadin.samples.about.flow;


import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.Version;
import com.vaadin.samples.flow.MainLayout;

@Route(value=AboutView.VIEW_NAME, layout = MainLayout.class)
public class AboutView extends HorizontalLayout {

    public static final String VIEW_NAME = "About";

    public AboutView() {    	
        add(VaadinIcon.INFO_CIRCLE.create());
        add(new Span(String.format(" This application is using Vaadin Flow %s", Version.getFullVersion())));

        setClassName("about-content");
        setDefaultVerticalComponentAlignment(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
    }
}
