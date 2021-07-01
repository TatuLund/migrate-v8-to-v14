package com.vaadin;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.samples.authentication.flow.AccessControlFactory;
import com.vaadin.samples.authentication.flow.LoginView;

@Route("")
public class HelloWorldPage extends Div implements BeforeEnterObserver {
    public HelloWorldPage() {
        setText("Hello World!");
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (!AccessControlFactory.getAccessControl().isUserSignedIn()) {
            event.rerouteTo(LoginView.class);
        }
    }
}
