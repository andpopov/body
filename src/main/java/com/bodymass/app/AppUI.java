package com.bodymass.app;

import java.sql.SQLException;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of an HTML page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Title("My UI")
@Theme("mytheme")
public class AppUI extends UI {
	private UserService userService = new UserService();

	@Override
	protected void init(VaadinRequest vaadinRequest) {
		setContent(createRegistrationPanel());
	}

	private VerticalLayout createRegistrationPanel() {
		FormLayout form = new FormLayout();
		form.setMargin(true);

		Label errorLabel = new Label("");
		errorLabel.setVisible(false);
		form.addComponent(errorLabel);

		TextField emailField = new TextField("Email");
		emailField.setRequiredIndicatorVisible(true);
		form.addComponent(emailField);

		TextField passwordField = new TextField("Пароль");
		passwordField.setRequiredIndicatorVisible(true);
		form.addComponent(passwordField);

		Button saveButton = new Button("Зарегистрироваться");
		saveButton.addClickListener(e -> {
			int isErr = -1;
			try {
				isErr = userService.register(emailField.getValue(), passwordField.getValue());
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			if(isErr < 0) {
				errorLabel.setValue("Ошибка регистрации");
				errorLabel.setVisible(true);
			} else {
				errorLabel.setValue("Вы зарегистрированы");
				errorLabel.setVisible(true);
			}
		});
		form.addComponent(saveButton);

		Panel panel = new Panel("Регистрация");
		panel.setSizeUndefined();
		panel.setContent(form);

		VerticalLayout content = new VerticalLayout();
		content.addComponent(createMenu());
		content.addComponent(panel);

		return content;
	}

	private VerticalLayout createLoginPanel() {
		FormLayout form = new FormLayout();
		form.setMargin(true);

		Label errorLabel = new Label("");
		errorLabel.setVisible(false);
		form.addComponent(errorLabel);

		TextField emailField = new TextField("Email");
		emailField.setRequiredIndicatorVisible(true);
		form.addComponent(emailField);

		TextField passwordField = new TextField("Пароль");
		passwordField.setRequiredIndicatorVisible(true);
		form.addComponent(passwordField);

		Button saveButton = new Button("Войти");
		saveButton.addClickListener(e -> {
			int isErr = -1;
			try {
				isErr = userService.login(emailField.getValue(), passwordField.getValue());
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			if(isErr < 0) {
				errorLabel.setValue("Ошибка входа");
				errorLabel.setVisible(true);
			} else if(isErr == 0) {
				errorLabel.setValue("Вы вошли");
				errorLabel.setVisible(true);
			}
			else if(isErr == 1) {
				errorLabel.setValue("Неверный пароль");
				errorLabel.setVisible(true);
			}
		});
		form.addComponent(saveButton);

		Panel panel = new Panel("Вход");
		panel.setSizeUndefined();
		panel.setContent(form);

		VerticalLayout content = new VerticalLayout();
		content.addComponent(createMenu());
		content.addComponent(panel);

		return content;
	}

	private HorizontalLayout createMenu() {
		HorizontalLayout content = new HorizontalLayout();

		Button loginButton = new Button("Войти");
		loginButton.addClickListener(e -> setContent(createLoginPanel()));
		content.addComponent(loginButton);

		Button registrationButton = new Button("Регистрация");
		registrationButton.addClickListener(e -> setContent(createRegistrationPanel()));
		content.addComponent(registrationButton);

		return content;
	}

	@WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
	@VaadinServletConfiguration(ui = AppUI.class, productionMode = false)
	public static class MyUIServlet extends VaadinServlet {
	}
}
