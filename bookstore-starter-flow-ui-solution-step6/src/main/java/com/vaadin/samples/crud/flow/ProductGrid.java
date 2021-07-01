package com.vaadin.samples.crud.flow;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.data.renderer.NumberRenderer;
import com.vaadin.flow.data.renderer.TemplateRenderer;
import com.vaadin.samples.backend.data.Category;
import com.vaadin.samples.backend.data.Product;

import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.stream.Collectors;

/**
 * Grid of products, handling the visual presentation and filtering of a set of
 * items. This version uses an in-memory data source that is suitable for small
 * data sets.
 */
public class ProductGrid extends Grid<Product> {

    public ProductGrid() {
        setSizeFull();
        // Set the Grid use theme variants with stripes and borders like the original Grid
        addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS);

        addColumn(Product::getId).setHeader("Id");
        addColumn(Product::getProductName).setHeader("Product Name").setFlexGrow(20);

        // Format and add " €" to price
        final DecimalFormat decimalFormat = new DecimalFormat();
        decimalFormat.setMaximumFractionDigits(2);
        decimalFormat.setMinimumFractionDigits(2);

        // To change the text alignment of the column, a template is used.
        final String priceTemplate = "<div style='text-align: right'>[[item.price]]</div>";
        addColumn(TemplateRenderer.<Product>of(priceTemplate)
                .withProperty("price", product -> decimalFormat.format(product.getPrice()) + " €"))
                .setHeader("Price")
                .setComparator(Comparator.comparing(Product::getPrice))
                .setFlexGrow(3);

        // Add an traffic light icon in front of availability
// Version using Component Renderer
//        final String availabilityTemplate = "<iron-icon icon=\"vaadin:circle\" style=\"color: [[item.color]]\"></iron-icon> [[item.availability]]";
//        addColumn(TemplateRenderer.<Product>of(availabilityTemplate)
//                .withProperty("color", product -> {
//                    String color = "";
//                    switch (product.getAvailability()) {
//                        case AVAILABLE:
//                            color = "#2dd085";
//                            break;
//                        case COMING:
//                            color = "#ffc66e";
//                            break;
//                        case DISCONTINUED:
//                            color =  "#f54993";
//                            break;
//                    }
//                    return color;
//                }))
//                .setHeader("Availability")
//                .setComparator(Comparator.comparing(Product::getAvailability))
//                .setFlexGrow(5);

// Version using Component Renderer        
        addComponentColumn(product -> {
        	Div div = new Div();
        	Icon icon = VaadinIcon.CIRCLE.create();
            String color = "";
            switch (product.getAvailability()) {
                case AVAILABLE:
                    color = "#2dd085";
                    break;
                case COMING:
                    color = "#ffc66e";
                    break;
                case DISCONTINUED:
                    color =  "#f54993";
                    break;
            }
            icon.setColor(color);
            icon.setSize("15px"); // Set the size to be the same as Valo default icon size
            div.add(icon,new Text(" "+product.getAvailability().toString()));
        	return div;
        })
        .setHeader("Availability")
        .setComparator(Comparator.comparing(Product::getAvailability))
        .setFlexGrow(5);
        
        // Show empty stock as "-"
        final String stockCountTemplate = "<div style='text-align: right'>[[item.stockCount]]</div>";
        addColumn(TemplateRenderer.<Product>of(stockCountTemplate)
                .withProperty("stockCount", product -> product.getStockCount() == 0 ? "-" : Integer.toString(product.getStockCount())))
                .setHeader("Stock count")
                .setComparator(Comparator.comparingInt(Product::getStockCount))
                .setFlexGrow(3);

        // Show all categories the product is in, separated by commas
        addColumn(this::formatCategories).setHeader("Category").setSortable(false);
    }

    private String formatCategories(Product product) {
        if (product.getCategory() == null || product.getCategory().isEmpty()) {
            return "";
        }
        return product.getCategory().stream()
                .sorted(Comparator.comparing(Category::getId))
                .map(Category::getName).collect(Collectors.joining(", "));
    }
}
