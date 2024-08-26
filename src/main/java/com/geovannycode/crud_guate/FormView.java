package com.geovannycode.crud_guate;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.theme.lumo.LumoUtility;

public class FormView {

    private final TextField firstName = new TextField("First Name");
    private final TextField lastName = new TextField("Last Name");
    private final EmailField email = new EmailField("Email");
    private final TextField numberPhone = new TextField("Phone Number");
    private final DatePicker dateOfBirth = new DatePicker("Birthday");
    private final TextField profession = new TextField("Profession");
    private final ComboBox<String> gender = new ComboBox<>("Gender");
    private final Checkbox status = new Checkbox("Status");

    private final Button delete = new Button("Delete");
    private final Button cancel = new Button("Cancel");
    private final Button save = new Button("Save");

    private final CustomerServices customerServices;
    private Customer element;
    private final BeanValidationBinder<Customer> binder;

    private Runnable actionRunnable;

    public FormView(CustomerServices customerServices) {
        this.customerServices = customerServices;
        delete.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_SMALL);

        cancel.addThemeVariants(ButtonVariant.LUMO_SMALL);

        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SMALL);

        gender.setItems("Male", "Female", "Other");

        binder = new BeanValidationBinder<>(Customer.class);

        binder.bindInstanceFields(this);

        binder.forField(gender)
                .asRequired("This field can not be blank.")
                .bind(Customer::getGender, Customer::setGender);

        save.addClickListener(this::saveOrUpdate);

        delete.addClickListener(this::delete);
    }

    private void saveOrUpdate(ClickEvent<Button> buttonClickEvent) {
        if (this.element == null) {
            this.element = new Customer();
        }

        try {
            binder.writeBean(element);

            var confirmDialog = new ConfirmDialog();
            confirmDialog.setHeader("Unsaved changes");
            confirmDialog.setText("There are unsaved changes. Do you want to discard them or continue?");

            confirmDialog.setRejectable(true);
            confirmDialog.setRejectText("Discard");

            confirmDialog.setConfirmText("Continue");
            confirmDialog.addConfirmListener(event -> {
                customerServices.createOrUpdate(element);
                NotificationUtil.notificationSuccess("The transaction was successful.");
                actionRunnable.run();
            });

            confirmDialog.open();

        } catch (ValidationException validationException) {
            NotificationUtil.notificationError(validationException);
        }
    }

    private void delete(ClickEvent<Button> buttonClickEvent) {
        var confirmDialog = new ConfirmDialog();
        confirmDialog.setHeader("Deleting record");
        confirmDialog.setText("You are deleting a record. Do you want to discard them or continue?");

        confirmDialog.setRejectable(true);
        confirmDialog.setRejectText("Discard");
        confirmDialog.setRejectButtonTheme("tertiary");

        confirmDialog.setConfirmText("Continue");
        confirmDialog.setConfirmButtonTheme("error primary");
        confirmDialog.addConfirmListener(event -> {
            customerServices.deleteCustomer(element.getId());
            NotificationUtil.notificationSuccess("The record was deleted.");
            actionRunnable.run();
        });

        confirmDialog.open();

    }

    public Dialog createDialog(String title, Customer element, Runnable reload) {
        this.element = element;
        binder.readBean(element);

        delete.setVisible(element != null);

        Dialog dialog = new Dialog();
        dialog.setCloseOnOutsideClick(true);
        dialog.setDraggable(true);
        dialog.setResizable(true);
        dialog.setOpened(true);
        dialog.setHeaderTitle(title);
        dialog.add(createFormLayout());
        dialog.getFooter().add(createButtonLayout());

        cancel.addClickListener(event -> {
            dialog.close();
            reload.run();
        });

        actionRunnable = () -> {
            dialog.close();
            reload.run();
        };

        return dialog;
    }

    private FormLayout createFormLayout() {
        FormLayout formLayout = new FormLayout();
        formLayout.add(firstName, lastName, email, numberPhone, dateOfBirth, profession, gender, status);
        formLayout.setResponsiveSteps(
                // Use one column by default
                new FormLayout.ResponsiveStep("0", 1),
                // Use two columns, if layout's width exceeds 500px
                new FormLayout.ResponsiveStep("500px", 2)
        );
        formLayout.addClassNames(LumoUtility.MaxWidth.SCREEN_MEDIUM, LumoUtility.AlignSelf.CENTER);
        return formLayout;
    }

    private HorizontalLayout createButtonLayout() {
        var deleteDiv = new Div();
        deleteDiv.addClassNames(LumoUtility.Margin.End.AUTO);
        deleteDiv.add(delete);

        var buttonLayout = new HorizontalLayout();
        buttonLayout.setWidthFull();
        buttonLayout.addClassNames(LumoUtility.Padding.MEDIUM, LumoUtility.FlexWrap.WRAP, LumoUtility.JustifyContent.CENTER);
        buttonLayout.add(deleteDiv, cancel, save);
        buttonLayout.setAlignItems(FlexComponent.Alignment.END);
        return buttonLayout;
    }

}
