package com.geovannycode.crud;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyModifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.data.renderer.LocalDateRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoIcon;
import com.vaadin.flow.theme.lumo.LumoUtility;
import com.vaadin.flow.function.SerializableConsumer;

import java.util.List;

@PageTitle("List - Customer")
@Route("")
@Uses(Icon.class)
public class CustomerView extends VerticalLayout {

    private final TextField searchField = new TextField();
    private Grid<Customer> grid;
    private final CustomerServices customerServices;
    private final FormView formView;

    public CustomerView(CustomerServices customerServices) {
        this.customerServices = customerServices;
        this.formView = new FormView(customerServices);

        setSizeFull();
        add(createTopBar(), createGrid());
    }

    private void edit(Customer c) {
        formView.createDialog("Edit Customer", c, this::refreshGrid);
    }

    private HorizontalLayout createTopBar() {
        searchField.focus();
        searchField.setPlaceholder("Search...");
        searchField.setClearButtonVisible(true);
        searchField.addFocusShortcut(Key.KEY_F, KeyModifier.CONTROL);
        searchField.setPrefixComponent(new Icon(VaadinIcon.SEARCH));
        searchField.setValueChangeMode(ValueChangeMode.EAGER);
        searchField.addValueChangeListener(e -> refreshGrid());

        // Action buttons
        final Button resetBtn = new Button("Clear", VaadinIcon.FILE_REMOVE.create());
        resetBtn.addThemeVariants(ButtonVariant.LUMO_SMALL);
        resetBtn.addClickShortcut(Key.ESCAPE);
        resetBtn.setTooltipText("Esc");
        resetBtn.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        resetBtn.addClickListener(e -> {
            searchField.clear();
            refreshGrid();
        });

        final Button createBtn = new Button("New", VaadinIcon.PLUS.create());
        createBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SMALL);
        createBtn.addFocusShortcut(Key.KEY_N, KeyModifier.CONTROL);
        createBtn.setTooltipText("Add new");
        createBtn.addClickListener(e -> formView.createDialog("New Customer", null, CustomerView.this::refreshGrid));

        HorizontalLayout layout = new HorizontalLayout(searchField, resetBtn, createBtn);
        layout.setFlexGrow(1, searchField);
        layout.setWidthFull();
        return layout;
    }

    private Component createGrid() {
        grid = new Grid<>(Customer.class, false);
        grid.addColumn(createCustomerRenderer()).setHeader("Customer").setAutoWidth(true);
        grid.addColumn(createContactRenderer()).setHeader("Contact").setAutoWidth(true);
        grid.addColumn(new LocalDateRenderer<>(Customer::getDateOfBirth, "MMM d, YYYY")).setHeader("Birth date").setAutoWidth(true);
        grid.addColumn(Customer::getProfession).setHeader("Profession").setAutoWidth(true);
        grid.addColumn(Customer::getGender).setHeader("Gender").setAutoWidth(true);
        grid.addComponentColumn(c -> createPermissionIcon(c.isStatus())).setHeader("Status").setAutoWidth(true);
        grid.addColumn(createActionButton(this::edit)).setHeader("Action");

        //grid.setItems(customerServices.listAll());
        refreshGrid();
        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        grid.addClassNames(LumoUtility.Border.TOP, LumoUtility.BorderColor.CONTRAST_10);

        return grid;
    }

    private void refreshGrid() {
        String searchTerm = searchField.getValue();
        List<Customer> customers = customerServices.listAll(searchTerm);
        ListDataProvider<Customer> dataProvider = new ListDataProvider<>(customers);
        grid.setDataProvider(dataProvider);
    }

    private static Renderer<Customer> createContactRenderer() {
        return LitRenderer.<Customer>of(
                        "<vaadin-vertical-layout style=\"width: 100%; font-size: var(--lumo-font-size-s); line-height: var(--lumo-line-height-m);\">"
                                + " <a href=\"mailto:${item.email}\" style=\"display: ${item.emailDisplay}; align-items: center;\">"
                                + "     <vaadin-icon icon=\"vaadin:${item.emailIcon}\" style=\"margin-inline-end: var(--lumo-space-xs); width: var(--lumo-icon-size-s); height: var(--lumo-icon-size-s);\"></vaadin-icon>"
                                + "     <span>${item.email}</span>"
                                + " </a>"
                                + " "
                                + " <a href=\"tel:${item.numberPhone}\" style=\"display: ${item.numberPhoneDisplay}; align-items: center;\">"
                                + "     <vaadin-icon icon=\"vaadin:${item.numberPhoneIcon}\" style=\"margin-inline-end: var(--lumo-space-xs); width: var(--lumo-icon-size-s); height: var(--lumo-icon-size-s);\"></vaadin-icon>"
                                + "     <span>${item.numberPhone}</span>"
                                + " </a>"
                                + "</vaadin-vertical-layout>")
                .withProperty("email", r -> r.getEmail() != null ? r.getEmail() : "")
                .withProperty("emailIcon", r -> r.getEmail() != null ? "envelope" : "")
                .withProperty("emailDisplay", r -> r.getEmail() != null ? "flex" : "none")
                .withProperty("numberPhone", r -> r.getNumberPhone() != null ? r.getNumberPhone() : "")
                .withProperty("numberPhoneIcon", r -> r.getNumberPhone() != null ? "phone" : "")
                .withProperty("numberPhoneDisplay", r -> r.getNumberPhone() != null ? "flex" : "none");
    }

    private static Icon createPermissionIcon(boolean isStatus) {
        Icon icon;
        if (isStatus) {
            icon = createIcon(VaadinIcon.CHECK, "Yes");
            icon.getElement().getThemeList().add("badge success");
        } else {
            icon = createIcon(VaadinIcon.CLOSE_SMALL, "No");
            icon.getElement().getThemeList().add("badge error");
        }
        return icon;
    }

    private static Icon createIcon(VaadinIcon vaadinIcon, String label) {
        Icon icon = vaadinIcon.create();
        icon.getStyle().set("padding", "var(--lumo-space-xs");
        // Accessible label
        icon.getElement().setAttribute("aria-label", label);
        // Tooltip
        icon.getElement().setAttribute("title", label);
        return icon;
    }

    private static Renderer<Customer> createActionButton(SerializableConsumer<Customer> consumer) {
        return new ComponentRenderer<>(Button::new, (button, p) -> {
            button.addThemeVariants(ButtonVariant.LUMO_SMALL);
            button.addClickListener(e -> consumer.accept(p));
            button.setIcon(LumoIcon.EDIT.create());
        });
    }

    private static Renderer<Customer> createCustomerRenderer() {
        return LitRenderer.<Customer>of(
                        "<vaadin-horizontal-layout style=\"align-items: center;\" theme=\"spacing\">"
                                + "<vaadin-avatar img=\"${item.pictureUrl}\" name=\"${item.fullName}\" alt=\"User avatar\" color-index=\"${item.colorIndex}\"></vaadin-avatar>"
                                + "  <vaadin-vertical-layout style=\"line-height: var(--lumo-line-height-m);\">"
                                + "    <span style=\"font-size: var(--lumo-font-size-xs); color: var(--lumo-primary-text-color);\">"
                                + "      ${item.firstName}" + "    </span>"
                                + "    <span style=\"font-size: var(--lumo-font-size-m); \">"
                                + "      ${item.lastName}" + "    </span>"
                                + "  </vaadin-vertical-layout>"
                                + "</vaadin-horizontal-layout>")
                .withProperty("initials", customer -> {
                    // Concatenar la primera letra del primer nombre y la primera letra del primer apellido
                    String firstNameInitial = customer.getFirstName() != null && !customer.getFirstName().isEmpty()
                            ? customer.getFirstName().substring(0, 1).toUpperCase()
                            : "";
                    String lastNameInitial = customer.getLastName() != null && !customer.getLastName().isEmpty()
                            ? customer.getLastName().substring(0, 1).toUpperCase()
                            : "";
                    return firstNameInitial + lastNameInitial;
                })
                .withProperty("fullName", customer -> {
                    return customer.getFirstName() + " " + customer.getLastName();
                })
                .withProperty("pictureUrl", customer -> {
                    return customer.getPictureUrl() != null && !customer.getPictureUrl().isEmpty()
                            ? customer.getPictureUrl()
                            : null;
                })
                .withProperty("firstName", Customer::getFirstName)
                .withProperty("lastName", Customer::getLastName)
                .withProperty("colorIndex", customer -> {
                    int colorIndex = (customer.getFirstName().hashCode() + customer.getLastName().hashCode()) % 7 + 1;
                    return colorIndex;
                });
    }

}
