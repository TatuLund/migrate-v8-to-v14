package com.vaadin.samples.authentication.flow;

import com.vaadin.HelloWorldPage;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.Shortcuts;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import java.io.Serializable;

@Route("login")
public class LoginView extends FlexLayout {
    private TextField username;
    private PasswordField password;
    private Button login;
    private Button forgotPassword;

    public LoginView() {
        buildUI();
        username.focus();
    }

    private void buildUI() {
        addClassName("login-screen");

        // login form, centered in the available part of the screen
        Component loginForm = buildLoginForm();

        // layout to center login form when there is sufficient screen space
        // - see the theme for how this is made responsive for various screen
        // sizes
        VerticalLayout centeringLayout = new VerticalLayout();
        centeringLayout.setMargin(false);
        centeringLayout.setSpacing(false);
        centeringLayout.setClassName("centering-layout");
        /**
         * Setting jusitfy conentent mdoe and align items to center for the verticallayout
         * Also need to wrap the formlayout, as formlayout has align-self: stretch
         */
        centeringLayout.add(new Div(loginForm));
        centeringLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        centeringLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        /** Or use margin:auto
         * centeringLayout.add(loginForm);
         * loginForm.getElement().getStyle().set("margin", "auto");
         */


        // information text about logging in
        Div loginInformation = buildLoginInformation();

        add(centeringLayout);
        add(loginInformation);
    }

    private Component buildLoginForm() {
        FormLayout loginForm = new FormLayout();

        loginForm.addClassName("login-form");
        loginForm.setWidth(24,Unit.EM);

        loginForm.addFormItem(username = new TextField(null, "admin", "admin"), "Username");
        username.setWidth(15,Unit.EM);
        loginForm.addFormItem(password = new PasswordField(), "Password");
        password.setWidth(15,Unit.EM);
        Div buttons = new Div();
        buttons.setClassName("buttons");
        loginForm.add(buttons);

        buttons.add(login = new Button("Login"));
        login.setDisableOnClick(true);
        login.addClickListener(event -> {
            try {
                login();
            } finally {
                login.setEnabled(true);
            }
        });
        login.addClickShortcut(Key.ENTER).listenOn(password).allowBrowserDefault();
        loginForm.getElement()
                .addEventListener("keypress", event -> login())
                .setFilter("event.key == 'Enter'");
        login.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);

        buttons.add(forgotPassword = new Button("Forgot password?"));
        forgotPassword.addClickListener(e -> Notification.show("Hint: Try anything"));
        forgotPassword.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        return loginForm;
    }

    private Div buildLoginInformation() {
        Div loginInformation = new Div();
        loginInformation.addClassName("login-information");
        Html loginInfoText = new Html(
                "<div><h1>Login Information</h1>"
                        + "Log in as &quot;admin&quot; to have full access. Log in with any other username to have read-only access. For all users, any password is fine</div>");
        loginInformation.add(loginInfoText);
        return loginInformation;
    }

    private void login() {
        if (AccessControlFactory.getAccessControl().signIn(username.getValue(), password.getValue())) {
            getUI().ifPresent(ui->ui.navigate(HelloWorldPage.class));
        } else {
            Notification.show("Please check your username and password and try again.");
            username.focus();
        }
    }

    public interface LoginListener extends Serializable {
        void loginSuccessful();
    }
}
