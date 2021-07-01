package com.vaadin.samples.crud.flow;

import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.samples.flow.MainLayout;

@Route(value=SampleCrudView.VIEW_NAME, layout = MainLayout.class)
@RouteAlias(value="", layout = MainLayout.class)
public class SampleCrudView extends FlexLayout {

    public static final String VIEW_NAME = "Inventory";
    private ProductGrid grid;
    private TextField filter;

    private ProductDataProvider dataProvider = new ProductDataProvider();

    public SampleCrudView() {
        setSizeFull();
        addClassName("crud-view");
        HorizontalLayout topLayout = createTopBar();

        grid = new ProductGrid();
        grid.setDataProvider(dataProvider);

        VerticalLayout barAndGridLayout = new VerticalLayout();
        barAndGridLayout.add(topLayout);
        barAndGridLayout.add(grid);
        barAndGridLayout.setSizeFull();
        barAndGridLayout.expand(grid);
        barAndGridLayout.setClassName("crud-main-layout");

        add(barAndGridLayout);
    }

    public HorizontalLayout createTopBar() {
        filter = new TextField();
        filter.setClassName("filter-textfield");
        filter.setPlaceholder("Filter name, availability or category");
        filter.setWidth(300,Unit.PIXELS);
        // Apply the filter to grid's data provider. TextField value is never null
        filter.addValueChangeListener(event -> dataProvider.setFilter(event.getValue()));

        HorizontalLayout topLayout = new HorizontalLayout();
        topLayout.setWidth("100%");
        topLayout.add(filter);
        topLayout.setClassName("top-bar");
        return topLayout;
    }

}
