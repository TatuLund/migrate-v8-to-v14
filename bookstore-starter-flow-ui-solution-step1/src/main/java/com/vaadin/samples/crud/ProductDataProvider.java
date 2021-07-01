package com.vaadin.samples.crud;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.samples.backend.DataService;
import com.vaadin.samples.backend.data.Product;

import java.util.Locale;
import java.util.Objects;

public class ProductDataProvider extends ListDataProvider<Product> {
    
    /** Text filter that can be changed separately. */
    private String filterText = "";

    public ProductDataProvider() {
        super(DataService.get().getAllProducts());
    }

    
    /**
     * Sets the filter to use for the this data provider and refreshes data.
     * <p>
     * Filter is compared for product name, availability and category.
     * 
     * @param filterText
     *           the text to filter by, never null
     */
    public void setFilter(String filterText) {
        Objects.requireNonNull(filterText, "Filter text cannot be null");
        if (Objects.equals(this.filterText, filterText.trim())) {
            return;
        }
        this.filterText = filterText.trim();

        setFilter(product -> passesFilter(product.getProductName(), filterText)
                || passesFilter(product.getAvailability(), filterText)
                || passesFilter(product.getCategory(), filterText));
    }
    
    @Override
    public Integer getId(Product product) {
        Objects.requireNonNull(product, "Cannot provide an id for a null product.");
        
        return product.getId();
    }

    private boolean passesFilter(Object object, String filterText) {
        return object != null && object.toString().toLowerCase(Locale.ENGLISH)
                .contains(filterText);
    }
}
