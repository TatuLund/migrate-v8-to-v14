package com.vaadin.samples.authentication.flow;

import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.UI;
import com.vaadin.samples.authentication.AccessControl;

public class AccessControlFactory {

    public static AccessControl getAccessControl() {
        if(ComponentUtil.getData(UI.getCurrent(), AccessControl.class)==null){
            ComponentUtil.setData(UI.getCurrent(), AccessControl.class, new BasicAccessControl());
        }
        return ComponentUtil.getData(UI.getCurrent(), AccessControl.class);
    }
}
