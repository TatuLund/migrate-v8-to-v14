package com.vaadin;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;

@Route("")
public class HelloWorldPage extends Div {
    public HelloWorldPage() {
        setText("Hello World!");
    }
}
