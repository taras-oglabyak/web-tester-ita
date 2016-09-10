package com.softserve.webtester.editor;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.softserve.webtester.model.Service;
import com.softserve.webtester.service.MetaDataService;

/**
 * Implementation of PropertyEditorSupport for binding spring form attribute with service field in {@link Request}
 * class.
 * 
 * @author Taras Oglabyak
 *
 */
@Component
public class ServiceEditor extends PropertyEditorSupport {

    @Autowired
    private MetaDataService metaDataService;

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        Service instance = metaDataService.serviceLoad(Integer.parseInt(text));
        this.setValue(instance);
    }
}